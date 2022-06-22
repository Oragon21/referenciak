package Game.Graphics.TileButtons;

import Game.MapHandling.Map;
import Game.MapHandling.TileHandling.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class CustomShapedButton extends JButton {
	protected Polygon polygon = new Polygon();
	protected Tile tile;
	protected Map map;
	protected int angleToRotate;

	/**
	 * Sokszög generálása.
	 *
	 * @return alakzat
	 */
	abstract protected Polygon generatePolygon();

	public CustomShapedButton(Map map) {
		this.map = map;
		addActionListener(e -> map.handleTilePressed(tile));
	}

	/**
	 * Alakzat forgatása.
	 *
	 * @param angle szög fokban
	 */
	public void rotatePolygon(int angle) {
		this.angleToRotate = angle;
		Point2D[] points = new Point2D[polygon.npoints];
		for (int i = 0; i < polygon.npoints; i++) {
			points[i] = new Point(polygon.xpoints[i], polygon.ypoints[i]);
		}

		Point2D[] newPoints = new Point2D[polygon.npoints];
		AffineTransform.getRotateInstance(Math.toRadians(angle),
				getBounds().width / 2.0,
				getBounds().height / 2.0).transform(points, 0, newPoints, 0, polygon.npoints);

		for (int i = 0; i < newPoints.length; i++) {
			polygon.xpoints[i] = (int) newPoints[i].getX();
			polygon.ypoints[i] = (int) newPoints[i].getY();
		}
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param p a pont
	 * @return benne van-e
	 * @see JButton#contains(Point)
	 */
	@Override
	public boolean contains(Point p) {
		return polygon.contains(p);
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param x the <i>x</i> coordinate of the point
	 * @param y the <i>y</i> coordinate of the point
	 * @return benne van-e
	 * @see JButton#contains(int, int)
	 */
	@Override
	public boolean contains(int x, int y) {
		return polygon.contains(x, y);
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param d the dimension specifying the new size
	 *          of this component
	 * @see JButton#setSize(Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		polygon = generatePolygon();
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param w the new width of this component in pixels
	 * @param h the new height of this component in pixels
	 * @see JButton#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		polygon = generatePolygon();
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param x      the new <i>x</i>-coordinate of this component
	 * @param y      the new <i>y</i>-coordinate of this component
	 * @param width  the new <code>width</code> of this component
	 * @param height the new <code>height</code> of this
	 *               component
	 * @see JButton#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		polygon = generatePolygon();
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param r the new bounding rectangle for this component
	 * @see JButton#setBounds(Rectangle)
	 */
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		polygon = generatePolygon();
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param e the mouse event
	 * @see JButton#processMouseEvent(MouseEvent)
	 */
	@Override
	protected void processMouseEvent(MouseEvent e) {
		if (contains(e.getPoint()))
			super.processMouseEvent(e);
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param g the <code>Graphics</code> object to protect
	 * @see JButton#paintBorder(Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getBackground());
		g.drawPolygon(polygon);
		g.fillPolygon(polygon);
	}

	/**
	 * JButton metódus felüldefiniálása alakzat miatt.
	 *
	 * @param g the <code>Graphics</code> context in which to paint
	 * @see JButton#paintBorder(Graphics)
	 */
	@Override
	protected void paintBorder(Graphics g) {
		if (map.getCurrentPlayer().getActTile() == tile || map.getCurrentPlayer().getActTile().getNeighbours().contains(tile)) {
			g.setColor(Color.BLACK);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5));
			g2.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
			g2.drawLine(polygon.xpoints[polygon.npoints - 1], polygon.ypoints[polygon.npoints - 1], // drawPolyLine doesn't draw a line from
					polygon.xpoints[0], polygon.ypoints[0]);                        // the last to the first point

		}
	}

	/**
	 * Gombban lévő kép forgatása.
	 *
	 * @param img a kép
	 * @return elforgatott kép
	 */
	public BufferedImage rotate(BufferedImage img) {

		// Getting Dimensions of image
		int width = img.getWidth();
		int height = img.getHeight();

		// Creating a new buffered image
		BufferedImage newImage = new BufferedImage(
				img.getWidth(), img.getHeight(), img.getType());

		// creating Graphics in buffered image
		Graphics2D g2 = newImage.createGraphics();

		// Rotating image by degrees using toradians()
		// method
		// and setting new dimension t it
		g2.rotate(Math.toRadians(angleToRotate), width / 2, height / 2);
		g2.drawImage(img, null, 0, 0);

		// Return rotated buffer image
		return newImage;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
}

