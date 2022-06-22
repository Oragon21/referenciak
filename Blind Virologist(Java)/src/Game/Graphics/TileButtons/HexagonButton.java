package Game.Graphics.TileButtons;

import Game.MapHandling.Map;
import Game.MapHandling.StorageHandling.AminoAcidStorage;
import Game.MapHandling.StorageHandling.NucleotideStorage;
import Game.MapHandling.TileHandling.Lab;
import Game.MapHandling.TileHandling.Shelter;
import Game.MapHandling.TileHandling.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HexagonButton extends CustomShapedButton {
	Polygon hexagonalShape;

	public HexagonButton(Map map) {
		super(map);
		this.setOpaque(false);
		hexagonalShape = generatePolygon();
	}

	@Override
	protected Polygon generatePolygon() {
		Polygon hex = new Polygon();
		int w = getWidth() - 1;
		int h = getHeight() - 1;
		int ratio = (int) (h * .25);

		hex.addPoint(w / 2, 0);
		hex.addPoint(w, ratio);
		hex.addPoint(w, h - ratio);
		hex.addPoint(w / 2, h);
		hex.addPoint(0, h - ratio);
		hex.addPoint(0, ratio);

		return hex;
	}

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

				if (tile.getClass() == AminoAcidStorage.class) {
					AminoAcidStorage am = (AminoAcidStorage) tile;
					if(am.getAminoAcid() == 0){
						imageFile = new File("src/Fields/c_storage_des.png");
					} else {
						imageFile = new File("src/Fields/c_storage_ok.png");
					}
				}

				if (tile.getClass() == NucleotideStorage.class) {
					NucleotideStorage nuc = (NucleotideStorage) tile;
					if(nuc.getNucleotide() == 0){
						imageFile = new File("src/Fields/c_storage_des.png");
					} else {
						imageFile = new File("src/Fields/c_storage_ok.png");
					}
				}


				if (tile.getClass() == Tile.class) {
					imageFile = new File("src/Fields/empty_3.png");
				}

				if (imageFile != null) {
					BufferedImage image = ImageIO.read(imageFile);
					image = rotate(image);
					g.drawImage(image, 0, 0, null);
				}


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}