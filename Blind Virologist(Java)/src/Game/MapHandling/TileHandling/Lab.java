package Game.MapHandling.TileHandling;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;
import static Game.PrototypeTest.printObject;


/**
 * Labort reprezentáló osztály.
 * <p>A Labort megvalósító osztály.
 * A {@link Tile} osztályból származik le.
 * A laborokban genetikai kódok találhatók,
 * melyet egy {@link GeneticCode} típusú változó tárol.</p>
 */
public class Lab extends Tile {

	protected GeneticCode geneticCode;

	/**
	 * Lab konstruktor.
	 * <p>
	 * A Labort létrehozó konstruktor.
	 * </p>
	 */
	public Lab(){}

	/**
	 * A paramtéreként kapott játékos
	 * Laboratóriummal való interakcióját kezeli.
	 * <p>
	 * A Laborral interakcióba lép egy játékos.
	 * Ha még nem ismeri a laborban lévő
	 * genetikai kódot, és nem akadályozzák negatív hatások,
	 * megtanulja a tárolt kódot.
	 * </p>
	 *
	 * @param p A játékos aki interaktál a mazővel.
	 */
	public void interact(Player p) {
		p.learn(geneticCode);
	}

	/**
	 * A paramtéreként kapott genetikai kódot
	 * beállítja tároltnak.
	 * <p>
	 * A Laborban tárolt kód beállítása.
	 * </p>
	 *
	 * @param gc A beállítandó genetikai kód.
	 */
	public void setGeneticCode(GeneticCode gc) {
		this.geneticCode = gc;
	}

	@Override
	public void addPlayer(Player p) {
		super.addPlayer(p);
	}

	@Override
	public void removePlayer(Player p) {
		super.removePlayer(p);
	}

	@Override
	public ArrayList<Player> getTouchable() {
		//Mindenki érinthető, aki a Tile-on van.
		return super.getTouchable();
	}

	@Override
	public void addNeighbour(Tile t) {
		super.addNeighbour(t);
	}
	@Override
	public ArrayList<Tile> getNeighbours() {
		return super.getNeighbours();
	}


	@Override
	public String toString() {
		return	"Lab {\n" +
				"\tgeneticCode=" + printObject(geneticCode) + "\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				'}';
	}
}
