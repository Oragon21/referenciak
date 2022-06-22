package Game.MapHandling.TileHandling;

import static Game.PrototypeTest.printArray;

/**
 * Raktárat reprezentáló osztály.
 * <p>A raktárat megvalósító osztály.
 * A {@link Tile} osztályból származik le.
 * A raktárból felvehetőek aminósavak és nuklotidok.
 * </p>
 */
public abstract class Storage extends Tile {

	/**
	 * Raktár konstruktor.
	 * <p>
	 * A raktárat létrehozó
	 * ctor.
	 * </p>
	 */
	public Storage(String title) {
		//amino + nucleo
	}

	public Storage() {

	}

	@Override
	public void destroyResources(){}


	@Override
	public String toString() {
		return "Storage {\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				'}';
	}
}
