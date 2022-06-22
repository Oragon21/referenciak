//=============================================================================================
// Computer Graphics Sample Program: GPU ray casting
//=============================================================================================
#include "framework.h"

// vertex shader in GLSL
const char* vertexSource = R"(
	#version 450
    precision highp float;

	uniform vec3 wLookAt, wRight, wUp;          // pos of eye
	layout(location = 0) in vec2 cCamWindowVertex;	// Attrib Array 0
	out vec3 p;

	void main() {
		gl_Position = vec4(cCamWindowVertex, 0, 1);
		p = wLookAt + wRight * cCamWindowVertex.x + wUp * cCamWindowVertex.y;
	}
)";
// fragment shader in GLSL
const char* fragmentSource = R"(
	#version 450
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

	struct Sphere {
		vec3 center;
		float radius;
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
	uniform int dP[12][5];
	uniform Light light;     
	uniform Material materials[4];  // diffuse, gold, mirror, space
	uniform int nObjects;
	uniform Sphere objects[1];

	const float epsilon = 0.0001f;
	const int maxdepth = 5;
	const int objFaces = 12;

	in  vec3 p;					// point on camera window corresponding to the pixel
	out vec4 fragmentColor;		// output that goes to the raster memory as told by glBindFragDataLocation

	void getObjPlane(int i, out vec3 p, out vec3 normal, out vec3 center){
		vec3 p1 = dV[dP[i][0]-1], p2 = dV[dP[i][1]-1], p3 = dV[dP[i][2]-1], p4 = dV[dP[i][3]-1], p5 = dV[dP[i][4]-1];
		normal = cross(p2-p1,p3-p1);
		if(dot(p1,normal)<0)normal = -normal;
		p=p1;
		center = (p1+p2+p3+p4+p5)/5;
		// vec3 side_center = p3+(p2-p3)/2;
		// center = p1+normalize(side_center-p1);
	}

	Hit intersectDodecahedronAndPortal(Ray ray, Hit hit, int mat, float portal_size){
		for(int i = 0; i < objFaces; i++){
			vec3 p1, normal, center;
			getObjPlane(i,p1,normal,center);
			float ti = abs(dot(normal,ray.dir)) > epsilon ? dot(p1-ray.start,normal) / dot(normal,ray.dir) : -1;
			if(ti <= epsilon || (ti > hit.t && hit.t > 0))	continue;
			vec3 pintersect = ray.start + ray.dir * ti;
			bool outside = false;
			bool portal = true;
			for(int j = 0; j < objFaces; j++){
				if(i==j) continue;
				vec3 p11,n,nul;
				getObjPlane(j,p11,n,nul);
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
				hit.normal = normalize(normal);
				hit.mat = portal ? 2 : mat;
				hit.rotation_center = center;
			}
		}
		return hit;
	}

	Hit solveQuadratic(float a, float b, float c, Ray ray, Hit hit, float zmin, float zmax, float normz){
		float discr = b*b-4.0f*a*c;
		if(discr>=0){
			float sqrt_discr = sqrt(discr);
			float t1 = (-b+sqrt_discr)/2.0f/a;
			vec3 p = ray.start + ray.dir*t1;
			if(p.z>zmax || p.z<zmin) t1 = -1;
			float t2 = (-b-sqrt_discr) /2.0f/a;
			p = ray.start + ray.dir*t2;
			if(p.z>zmax || p.z<zmin) t2 = -1;
			if(t2>0 && (t2<t1 || t1<0)) t1 = t2;
			if(t1>0 && (t1<hit.t || hit.t < 0)) {
				hit.t = t1;
				hit.position = ray.start + ray.dir*hit.t;
				hit.normal = normalize(vec3(-hit.position.x, -hit.position.y, normz));
				hit.mat=1;
			}
		}
		return hit;
	}

	Hit intersectMirascope(Ray ray, Hit hit){
		const float f = 0.3f;
		
		float a = dot(ray.dir.xy, ray.dir.xy);
		float b = dot(ray.dir.xy, ray.start.xy) * 2 - 4 * f * ray.dir.z;
		float c = dot(ray.start.xy, ray.start.xy) -4*f*ray.start.z;
		hit = solveQuadratic(a, b, c, ray, hit, 0, f/2, 2*f);
		return hit;
	}

	Hit firstIntersect(Ray ray) {
		Hit bestHit;
		bestHit.t = -1;
		bestHit = intersectMirascope(ray, bestHit);
		bestHit = intersectDodecahedronAndPortal(ray,bestHit,0,0.95);
		if (dot(ray.dir, bestHit.normal) > 0) bestHit.normal = bestHit.normal * (-1);
		return bestHit;
	}

	vec3 rotate(vec3 position, vec3 normal, vec3 center, float degree){
		vec3 v = position-center;
		vec3 k = normalize(normal);
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
					ray.start = rotate(hit.position,hit.rotation_center,hit.normal,72);
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

//---------------------------
struct Material {
	//---------------------------
	vec3 ka, kd, ks;
	float  shininess;
	vec3 F0;
	int rough, reflective;
};

//---------------------------
struct RoughMaterial : Material {
	//---------------------------
	RoughMaterial(vec3 _kd, vec3 _ks, float _shininess) {
		ka = _kd * M_PI;
		kd = _kd;
		ks = _ks;
		shininess = _shininess;
		rough = true;
		reflective = false;
	}
};

//---------------------------
struct SmoothMaterial : Material {
	//---------------------------
	SmoothMaterial(vec3 _F0) {
		F0 = _F0;
		rough = false;
		reflective = true;
	}
};

//---------------------------
struct Sphere {
	//---------------------------
	vec3 center;
	float radius;

	Sphere(const vec3& _center, float _radius) { center = _center; radius = _radius; }
};

//---------------------------
struct Camera {
	//---------------------------
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

//---------------------------
struct Light {
	//---------------------------
	vec3 direction;
	vec3 Le, La;
	Light(vec3 _direction, vec3 _Le, vec3 _La) {
		direction = normalize(_direction);
		Le = _Le; La = _La;
	}
};

//---------------------------
class Shader : public GPUProgram {
	//---------------------------
public:
	void setUniformMaterials(const std::vector<Material*>& materials) {
		char name[256];
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
		for (int x = 0; x < 12; x++)
		{
			for (int y = 0; y < 5; y++)
			{
				int put = planes[x * 5 + y];
				setUniform(put, "dP[" + std::to_string(x) + "]" + "[" + std::to_string(y) + "]");
			}
		}
	}

	void setUniformObjects(const std::vector<Sphere*>& objects) {
		setUniform((int)objects.size(), "nObjects");
		char name[256];
		for (unsigned int o = 0; o < objects.size(); o++) {
			sprintf(name, "objects[%d].center", o);  setUniform(objects[o]->center, name);
			sprintf(name, "objects[%d].radius", o);  setUniform(objects[o]->radius, name);
		}
	}
};

float rnd() { return (float)rand() / RAND_MAX; }

float F(float n, float k) {
	return (pow(n - 1, 2) + pow(k, 2)) / (pow(n + 1, 2) + pow(k, 2));
};

//---------------------------
class Scene {
	//---------------------------
	std::vector<Sphere*> objects;
	std::vector<Light*> lights;
	Camera camera;
	std::vector<Material*> materials;

	// dodekaeder adatai
	const float g = 0.618f, G = 1.618f; // btcxau=exp(-8.8)*S2F^3.3
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
		6,7,20,11,15 ,
	};

public:
	void build() {
		vec3 eye = vec3(0, 0, 1);
		vec3 vup = vec3(0, 1, 0);
		vec3 lookat = vec3(0, 0, 0);
		float fov = 100 * (float)M_PI / 180;
		camera.set(eye, lookat, vup, fov);

		lights.push_back(new Light(vec3(1, 1, 1), vec3(3, 3, 3), vec3(0.5f, 0.5f, 0.8f)));

		vec3 kd(0.3f, 0.2f, 0.1f), ks(10, 10, 10);

		// dodekaeder material
		materials.push_back(new RoughMaterial(kd, ks, 50));

		// arany objektum material
		materials.push_back(new SmoothMaterial(vec3(F(0.17f, 3.1f), F(0.35f, 2.7f), F(1.5f, 1.9f))));

		// tukor
		materials.push_back(new SmoothMaterial(vec3(1.0f, 1.0f, 1.0f)));

		materials.push_back(new RoughMaterial(vec3(0.3f,0.3f,0.9f), vec3(0, 0, 0), 0));

		// fenyes objektum adatai
		objects.push_back(new Sphere(vec3(0, 0, 0), 0.1f));
		objects.push_back(new Sphere(vec3(0.5, 0, 0), 0.1f));

	}

	void setUniform(Shader& shader) {
		shader.setUniformVertices(dodecahedronVertices);
		shader.setUniformPlanes(dodecahedronPlanes);
		shader.setUniformMaterials(materials);
		shader.setUniformLight(lights[0]);
		shader.setUniformCamera(camera);
		//shader.setUniformObjects(objects);
	}

	void Animate(float dt) { camera.Animate(dt); }
};

Shader shader; // vertex and fragment shaders
Scene scene;

//---------------------------
class FullScreenTexturedQuad {
	//---------------------------
	unsigned int vao = 0;	// vertex array object id and texture id
public:
	void create() {
		glGenVertexArrays(1, &vao);	// create 1 vertex array object
		glBindVertexArray(vao);		// make it active

		unsigned int vbo;		// vertex buffer objects
		glGenBuffers(1, &vbo);	// Generate 1 vertex buffer objects

		// vertex coordinates: vbo0 -> Attrib Array 0 -> vertexPosition of the vertex shader
		glBindBuffer(GL_ARRAY_BUFFER, vbo); // make it active, it is an array
		float vertexCoords[] = { -1, -1,  1, -1,  1, 1,  -1, 1 };	// two triangles forming a quad
		glBufferData(GL_ARRAY_BUFFER, sizeof(vertexCoords), vertexCoords, GL_STATIC_DRAW); // copy to that part of the memory which is not modified 
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, GL_FALSE, 0, NULL);     // stride and offset: it is tightly packed
	}

	void Draw() {
		glBindVertexArray(vao);	// make the vao and its vbos active playing the role of the data source
		glDrawArrays(GL_TRIANGLE_FAN, 0, 4);	// draw two triangles forming a quad
	}
};

FullScreenTexturedQuad fullScreenTexturedQuad;

// Initialization, create an OpenGL context
void onInitialization() {
	glViewport(0, 0, windowWidth, windowHeight);
	scene.build();
	fullScreenTexturedQuad.create();

	// create program for the GPU
	shader.create(vertexSource, fragmentSource, "fragmentColor");
	shader.Use();
}

// Window has become invalid: Redraw
void onDisplay() {
	static int nFrames = 0;
	nFrames++;
	static long tStart = glutGet(GLUT_ELAPSED_TIME);
	long tEnd = glutGet(GLUT_ELAPSED_TIME);
	//printf("%d msec\r", (tEnd - tStart) / nFrames);

	glClearColor(1.0f, 0.5f, 0.8f, 1.0f);							// background color 
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the screen

	scene.setUniform(shader);
	fullScreenTexturedQuad.Draw();

	glutSwapBuffers();									// exchange the two buffers
}

// Key of ASCII code pressed
void onKeyboard(unsigned char key, int pX, int pY) {
}

// Key of ASCII code released
void onKeyboardUp(unsigned char key, int pX, int pY) {

}

// Mouse click event
void onMouse(int button, int state, int pX, int pY) {
}

// Move mouse with key pressed
void onMouseMotion(int pX, int pY) {
}

// Idle event indicating that some time elapsed: do animation here
void onIdle() {
	scene.Animate(0.01f);
	glutPostRedisplay();
}