package Game.Graphics.TileButtons;

import Game.MapHandling.LabTypes.GrizzlyInfectedLab;
import Game.MapHandling.Map;
import Game.MapHandling.StorageHandling.AminoAcidStorage;
import Game.MapHandling.StorageHandling.NucleotideStorage;
import Game.MapHandling.TileHandling.Lab;
import Game.MapHandling.TileHandling.Shelter;
import Game.MapHandling.TileHandling.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PentagonButton extends CustomShapedButton {

	/**
	 * Konstruktor.
	 *
	 * @param map a pálya, amin van
	 */
	public PentagonButton(Map map) {
		super(map);
		this.setOpaque(false);
		polygon = generatePolygon();
	}

	/**
	 * Soksszög elkészítése.
	 *
	 * @return alakzat
	 */
	@Override
	protected Polygon generatePolygon() {
		Polygon pen = new Polygon();
		int w = getWidth() - 1;
		int h = getHeight() - 1;
		int hRatio = (int) (h * .40);
		int wRatio = (int) (w * .85);

		pen.addPoint(w / 2, 0);
		pen.addPoint(w, hRatio);
		pen.addPoint(wRatio, h);
		pen.addPoint(w - wRatio, h);
		pen.addPoint(0, hRatio);

		AffineTransform transform = new AffineTransform();
		transform.scale(0.84, 0.84);

		Point2D[] points = new Point2D[pen.npoints];
		for (int i = 0; i < pen.npoints; i++) {
			points[i] = new Point(pen.xpoints[i], pen.ypoints[i]);
		}
		Point2D[] newPoints = new Point2D[pen.npoints];
		transform.transform(points, 0, newPoints, 0, pen.npoints);

		for (int i = 0; i < newPoints.length; i++) {
			pen.xpoints[i] = (int) newPoints[i].getX();
			pen.ypoints[i] = (int) newPoints[i].getY();
		}

		return pen;
	}

	/**
	 * Kirajzolás.
	 *
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (map.getCurrentPlayer().getActTile() == tile || map.getCurrentPlayer().getActTile().getNeighbours().contains(tile)) {
			File imageFile = null;
			try {
				if (tile.getClass() == Lab.class) {
					imageFile = new File("src/Fields/c_lab.png");
				}
				if (tile.getClass() == Shelter.class) {
					imageFile = new File("src/Fields/c_shelter.png");
				}
				if (tile.getClass() == AminoAcidStorage.class || tile.getClass() == NucleotideStorage.class) {
					imageFile = new File("src/Fields/c_storage_ok.png");
				}
				if (tile.getClass() == Tile.class) {
					imageFile = new File("src/Fields/empty_3.png");
				}
				if (tile.getClass() == GrizzlyInfectedLab.class) {
					imageFile = new File("src/Fields/c_lab.png");
				}

				if (imageFile != null) {
					BufferedImage image = ImageIO.read(imageFile);
					image = rotate(image);

					BufferedImage after = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
					AffineTransform at = new AffineTransform();
					at.scale(0.85, 0.85);
					AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
					after = scaleOp.filter(image, after);

					g.drawImage(after, 0, 0, null);
				}


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
