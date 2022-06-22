#include "framework.h"

const char* vertexSource = R"(
	#version 330
    precision highp float;

	uniform vec3 wLookAt, wRight, wUp;          // pos of eye
	layout(location = 0) in vec2 cCamWindowVertex;	// Attrib Array 0
	out vec3 p;

	void main() {
		gl_Position = vec4(cCamWindowVertex, 0, 1);
		p = wLookAt + wRight * cCamWindowVertex.x + wUp * cCamWindowVertex.y;
	}
)";

const char* fragmentSource = R"(
	#version 330
    precision highp float;

	struct Material {
		vec3 ka, kd, ks;
		float  shininess;
		vec3 F0;
		int rough, reflective;
	};

	struct Light {
		vec3 direction;
		vec3 Le, La;
	};

	struct Hit {
		float t;
		vec3 position, normal, rotation_center;
		int mat;	// material index
	};

	struct Ray {
		vec3 start, dir, weight;
	};

	const int nMaxObjects = 1;

	uniform vec3 wEye;
	uniform vec3 dV[20];
	uniform int dP[60];
	uniform Light light;     
	uniform Material materials[3];  // diffuse, gold, mirror
	uniform int nObjects;

	const float epsilon = 0.0001f;
	const int maxdepth = 5;
	const int objFaces = 12;

	in  vec3 p;					// point on camera window corresponding to the pixel
	out vec4 fragmentColor;		// output that goes to the raster memory as told by glBindFragDataLocation

	void getPentagonPlane(int i, out vec3 p, out vec3 normal, out vec3 center){
		vec3 p1 = dV[dP[i*5+0]-1], p2 = dV[dP[i*5+1]-1], p3 = dV[dP[i*5+2]-1], p4 = dV[dP[i*5+3]-1], p5 = dV[dP[i*5+4]-1];
		normal = normalize(cross(p2-p1,p3-p1));
		if(dot(p1,normal)<0)normal = -normal;
		p=p1;
		center = (p1+p2+p3+p4+p5)/5;
	}
	
	Hit intersectDodecahedronAndPortal(Ray ray, Hit hit, int mat, float portal_size){
		for(int i = 0; i < objFaces; i++){
			vec3 p1, normal, center;
			getPentagonPlane(i,p1,normal,center);
			float ti = abs(dot(normal,ray.dir)) > epsilon ? dot(p1-ray.start,normal) / dot(normal,ray.dir) : -1;
			if(ti <= epsilon || (ti > hit.t && hit.t > 0))	continue;
			vec3 pintersect = ray.start + ray.dir * ti;
			bool outside = false;
			bool portal = true;
			for(int j = 0; j < objFaces; j++){
				if(i==j) continue;
				vec3 p11,n,nul;
				getPentagonPlane(j,p11,n,nul);
				if((dot(n,pintersect-p11)>0)){
					outside = true;
					break;
				}
				if((dot(n,pintersect-p11*portal_size)>0)){
					portal = false;
				}
			}
			if(!outside){
				hit.t = ti;
				hit.position = pintersect;
				hit.normal = normal;
				hit.mat = portal ? 2 : mat;
				hit.rotation_center = center;
			}
		}
		return hit;
	}

	bool inTheSphere(vec3 point, float radius){
		return (pow(point.x,2) + pow(point.y,2) + pow(point.z,2) - pow(radius,2)) < 0;
	}
		
	Hit intersectHyperbolicParaboloid(Ray ray, Hit hit){
		const float a = 0.420;
		const float b = 0.69;
		const float c = 0.0987654321f;

		float p = ray.start.x; //rx
		float q = ray.start.y; //ry
		float r = ray.start.z; //rz

		float m = ray.dir.x; //vx
		float n = ray.dir.y; //vy
		float o = ray.dir.z; //vz
		
		if(ray.dir*(a*pow(m,2)+b*pow(n,2))==vec3(0,0,0)) return hit;
		
		// a*(rx+vx*t)^2+b*(ry+vy*t)^2-c*(rz+vz*t)=0 megoldásai t-re nézve:
		// Reduce[a (p + m t)^2 + b (q + n t)^2 - c (r + o t) == 0, t] https://tinyurl.com/y625p395
		
		float t1 = -(sqrt(pow(c* o - 2* (a* m* p + b* n* q),2) - 4* (a* pow(m,2) + b* pow(n,2))* (a* pow(p,2) + b* pow(q,2) - c* r)) + 2* a* m* p + 2* b* n* q - c* o)/(2* (a* pow(m,2) + b* pow(n,2)));
		float t2 =  (sqrt(pow(c* o - 2* (a* m* p + b* n* q),2) - 4* (a* pow(m,2) + b* pow(n,2))* (a* pow(p,2) + b* pow(q,2) - c* r)) - 2* a* m* p - 2* b* n* q + c* o)/(2* (a* pow(m,2) + b* pow(n,2)));
		
		vec3 point = ray.start + ray.dir * t1;
		t1 = inTheSphere(point,0.3) ? t1 : -1;
		point = ray.start + ray.dir * t2;
		t2 = inTheSphere(point,0.3) ? t2 : -1;

		if(t2>0 && (t2<t1 || t1<0)) t1 = t2;
			if(t1>0 && (t1<hit.t || hit.t < 0)) {
				hit.t = t1;
				hit.position = ray.start + ray.dir*hit.t;
				// D[a x^2 + b y^2 - c z, {{a, b, c, x, y, z}}]  https://tinyurl.com/mahbrrhy
				hit.normal = normalize(vec3(2*a*hit.position.x, 2*b*hit.position.y, -c));
				hit.mat=1;
		}
		return hit;
	}

	Hit firstIntersect(Ray ray) {
		Hit bestHit;
		bestHit.t = -1;
		bestHit = intersectHyperbolicParaboloid(ray, bestHit);
		bestHit = intersectDodecahedronAndPortal(ray,bestHit,0,0.95);
		if (dot(ray.dir, bestHit.normal) > 0) bestHit.normal = bestHit.normal * (-1);
		return bestHit;
	}

	vec3 rotate(vec3 position, vec3 center, vec3 normal, float degree){
		vec3 v = position-center;
		vec3 k = normal;
		float r = radians(degree);
		return (v*cos(r)+cross(k,v)*sin(r)+k*dot(k,v)*(1-cos(r)))+center;
	}

	vec3 Fresnel(vec3 F0, float cosTheta) { 
		return F0 + (vec3(1, 1, 1) - F0) * pow(cosTheta, 5);
	}

	vec3 trace(Ray ray) {
		vec3 weight = ray.weight;
		vec3 outRadiance = vec3(0, 0, 0);
		for(int d = 0; d < maxdepth; d++) {
			Hit hit = firstIntersect(ray);
			if (hit.t < 0) return weight * light.La;

			if (materials[hit.mat].rough == 1) {
				outRadiance += weight * materials[hit.mat].ka * light.La;
				float cosTheta = dot(hit.normal, light.direction);
				if (cosTheta > 0) {
					outRadiance += weight * light.Le * materials[hit.mat].kd * cosTheta;
					vec3 halfway = normalize(-ray.dir + light.direction);
					float cosDelta = dot(hit.normal, halfway);
					if (cosDelta > 0) outRadiance += weight * light.Le * materials[hit.mat].ks * pow(cosDelta, materials[hit.mat].shininess);
				}
			}

			if (materials[hit.mat].reflective == 1) {
				weight *= Fresnel(materials[hit.mat].F0, dot(-ray.dir, hit.normal));
				ray.dir = reflect(ray.dir, hit.normal);
				if (hit.mat == 2){ // portal
					// hit.position = rotate(hit.position,hit.rotation_center,hit.normal,72);
					// ray.start = hit.position + hit.normal;
					ray.start = rotate(hit.position + hit.normal*epsilon,hit.rotation_center,hit.normal,72);
					ray.dir = rotate(ray.dir,hit.rotation_center,hit.normal,72);

				}
				else{ // golden object
					ray.start = hit.position + hit.normal;
				}
			}else return outRadiance;	
		}
		if (outRadiance==vec3(0, 0, 0))	return light.La*weight;
	}

	void main() {
		Ray ray;
		ray.start = wEye;
		ray.dir = normalize(p - wEye);
		ray.weight = vec3(1,1,1);
		fragmentColor = vec4(trace(ray), 1); 
	}
)";

struct Material {
	vec3 ka, kd, ks;
	float  shininess;
	vec3 F0;
	int rough, reflective;
};

struct RoughMaterial : Material {
	RoughMaterial(vec3 _kd, vec3 _ks, float _shininess) {
		ka = _kd * M_PI;
		kd = _kd;
		ks = _ks;
		shininess = _shininess;
		rough = true;
		reflective = false;
	}
};

struct SmoothMaterial : Material {
	SmoothMaterial(vec3 _F0) {
		F0 = _F0;
		rough = false;
		reflective = true;
	}
};

struct Camera {
	vec3 eye, lookat, right, up;
	float fov;
public:
	void set(vec3 _eye, vec3 _lookat, vec3 vup, float _fov) {
		eye = _eye;
		lookat = _lookat;
		fov = _fov;
		vec3 w = eye - lookat;
		float f = length(w);
		right = normalize(cross(vup, w)) * f * tanf(fov / 2);
		up = normalize(cross(w, right)) * f * tanf(fov / 2);
	}
	void Animate(float dt) {
		eye = vec3((eye.x - lookat.x) * cos(dt) + (eye.z - lookat.z) * sin(dt) + lookat.x,
			eye.y,
			-(eye.x - lookat.x) * sin(dt) + (eye.z - lookat.z) * cos(dt) + lookat.z);
		set(eye, lookat, up, fov);
	}
};

struct Light {
	vec3 direction;
	vec3 Le, La;
	Light(vec3 _direction, vec3 _Le, vec3 _La) {
		direction = normalize(_direction);
		Le = _Le; La = _La;
	}
};

class Shader : public GPUProgram {
public:
	void setUniformMaterials(const std::vector<Material*>& materials) {
		char name[25];
		for (unsigned int mat = 0; mat < materials.size(); mat++) {
			sprintf(name, "materials[%d].ka", mat); setUniform(materials[mat]->ka, name);
			sprintf(name, "materials[%d].kd", mat); setUniform(materials[mat]->kd, name);
			sprintf(name, "materials[%d].ks", mat); setUniform(materials[mat]->ks, name);
			sprintf(name, "materials[%d].shininess", mat); setUniform(materials[mat]->shininess, name);
			sprintf(name, "materials[%d].F0", mat); setUniform(materials[mat]->F0, name);
			sprintf(name, "materials[%d].rough", mat); setUniform(materials[mat]->rough, name);
			sprintf(name, "materials[%d].reflective", mat); setUniform(materials[mat]->reflective, name);
		}
	}

	void setUniformLight(Light* light) {
		setUniform(light->La, "light.La");
		setUniform(light->Le, "light.Le");
		setUniform(light->direction, "light.direction");
	}

	void setUniformCamera(const Camera& camera) {
		setUniform(camera.eye, "wEye");
		setUniform(camera.lookat, "wLookAt");
		setUniform(camera.right, "wRight");
		setUniform(camera.up, "wUp");
	}

	void setUniformVertices(const std::vector<vec3> vertices) {
		for (size_t i = 0; i < 20; i++)
		{
			setUniform(vertices[i], "dV[" + std::to_string(i) + "]");
		}
	}

	void setUniformPlanes(const int* planes) {
		for (int x = 0; x < 12*5; x++)
		{
			int put = planes[x];
			setUniform(put, "dP[" + std::to_string(x) + "]");
		}
	}
};

float F(float n, float k) {
	return (pow(n - 1, 2) + pow(k, 2)) / (pow(n + 1, 2) + pow(k, 2));
};

class Scene {
	std::vector<Light*> lights;
	Camera camera;
	std::vector<Material*> materials;

	const float g = 0.618f, G = 1.618f;
	std::vector<vec3> dodecahedronVertices = {
			vec3(0, g, G), vec3(0, -g, G), vec3(0, -g, -G), vec3(0, g, -G),
			vec3(G, 0, g), vec3(-G, 0, g), vec3(-G, 0, -g), vec3(G, 0, -g),
			vec3(g, G, 0), vec3(-g, G, 0), vec3(-g, -G, 0), vec3(g, -G, 0),
			vec3(1, 1, 1), vec3(-1, 1, 1), vec3(-1, -1, 1), vec3(1, -1, 1),
			vec3(1, -1, -1), vec3(1, 1, -1), vec3(-1, 1, -1), vec3(-1, -1, -1)
	};

	int dodecahedronPlanes[60] = {
		1,2,16,5,13  ,
		1,13,9,10,14 ,
		1,14,6,15,2  ,
		2,15,11,12,16,
		3,4,18,8,17  ,
		3,17,12,11,20,
		3,20,7,19,4  ,
		19,10,9,18,4 ,
		16,12,17,8,5 ,
		5,8,18,9,13  ,
		14,10,19,7,6 ,
		6,7,20,11,15
	};

public:
	void build() {
		vec3 eye = vec3(0, 0, 1);
		vec3 vup = vec3(0, 1, 0);
		vec3 lookat = vec3(0, 0, 0);
		float fov = 55 * (float)M_PI / 180;
		camera.set(eye, lookat, vup, fov);

		lights.push_back(new Light(vec3(1, 1, 1), vec3(3, 3, 3), vec3(0.12f, 0.18f, 0.71f)));

		vec3 kd(0.7f, 0.3f, 0.1f), ks(20, 30, 40);

		// dodekaeder material
		materials.push_back(new RoughMaterial(kd, ks, 30));

		// arany objektum material
		materials.push_back(new SmoothMaterial(vec3(F(0.17f, 3.1f), F(0.35f, 2.7f), F(1.5f, 1.9f))));

		// tukor (portal) material
		materials.push_back(new SmoothMaterial(vec3(1.0f, 1.0f, 1.0f)));

	}

	void setUniform(Shader& shader) {
		shader.setUniformVertices(dodecahedronVertices);
		shader.setUniformPlanes(dodecahedronPlanes);
		shader.setUniformMaterials(materials);
		shader.setUniformLight(lights[0]);
		shader.setUniformCamera(camera);
	}

	void Animate(float dt) { camera.Animate(dt); }
};

Shader shader;
Scene scene;

class FullScreenTexturedQuad {
	unsigned int vao = 0;
public:
	void create() {
		glGenVertexArrays(1, &vao);
		glBindVertexArray(vao);

		unsigned int vbo;
		glGenBuffers(1, &vbo);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		float vertexCoords[] = { -1, -1,  1, -1,  1, 1,  -1, 1 };
		glBufferData(GL_ARRAY_BUFFER, sizeof(vertexCoords), vertexCoords, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, GL_FALSE, 0, NULL);
	}

	void Draw() {
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
	}
};

FullScreenTexturedQuad fullScreenTexturedQuad;

void onInitialization() {
	glViewport(0, 0, windowWidth, windowHeight);
	scene.build();
	fullScreenTexturedQuad.create();

	shader.create(vertexSource, fragmentSource, "fragmentColor");
	shader.Use();
}

void onDisplay() {
	scene.setUniform(shader);
	fullScreenTexturedQuad.Draw();
	glutSwapBuffers();
}

void onKeyboard(unsigned char key, int pX, int pY) {
}

void onKeyboardUp(unsigned char key, int pX, int pY) {
}

void onMouse(int button, int state, int pX, int pY) {
}

void onMouseMotion(int pX, int pY) {
}

void onIdle() {
	scene.Animate(0.005f);
	glutPostRedisplay();
}