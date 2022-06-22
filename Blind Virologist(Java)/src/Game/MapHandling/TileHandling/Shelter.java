package Game.MapHandling.TileHandling;

import Game.Equipment.Equipment;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;

/**
 * Óvóhelyet reprezentáló osztály.
 * <p>Az óvóhelyet megvalósító osztály.
 * A {@link Tile} osztályból származik le.
 * Az óvóhelyen felvehető eszközök találhatók,
 * melyet egy {@link Equipment} típusokat tároló lista tartalmaz.</p>
 */
public class Shelter extends Tile {

	private ArrayList<Equipment> equipments;

	/**
	 * Óvóhely konstruktor.
	 * <p>
	 * Az óvóhelyet létrehozó
	 * ctor.
	 * </p>
	 */
	public Shelter() {
		equipments = new ArrayList<>();
	}

	/**
	 * A paramtéreként kapott játékos
	 * Óvóhellyel való interakcióját kezeli.
	 * <p>
	 * Az óvóhellyel interakcióba lép egy játékos.
	 * Ha képes felvenni egy itt tárolt eszközt,
	 * és nem akadályozzák negatív hatások,
	 * birtokba veheti az eszközt.
	 * </p>
	 *
	 * @param p A játékos aki interaktál az óvóhellyel.
	 */
	public void interact(Player p) {
		p.pickUpEquipment(equipments.get(equipments.size() - 1));
	}

	/**
	 * A paramtéreként kapott eszközt
	 * beállítja tároltnak.
	 * <p>
	 * Az Óvóhelyen tárolt eszköz beállítása.
	 * </p>
	 *
	 * @param e A beállítandó eszköz.
	 */

	public void addEquipment(Equipment e) {
		equipments.add(e);
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
		return	"Shelter {\n" +
				"\tequipments=" + equipments.size() + "\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				'}';
	}
}
