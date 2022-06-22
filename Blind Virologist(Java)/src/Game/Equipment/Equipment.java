package Game.Equipment;

import Game.TopEntities.Player;

public abstract class Equipment {

	/**
	 * Az eszköz a játékos birtokába kerül.
	 *
	 * @param p - paraméterül kapott játékoson meghívja az adott eszköz aktiváló függvényét.
	 */
	public void activate(Player p) {
		p.getEquipments().add(this);
	}

	/**
	 * Az eszköz többet nem lesz a játékos birtokában. Nem használhatja fel többet.
	 *
	 * @param p -  paraméterül kapott játékoson meghívja az adott eszköz deaktiváló függvényét.
	 */
	public void deactivate(Player p) {
		p.getEquipments().remove(this);
	}

	/**
	 * Felszerelés használata.
	 * True-val tér vissza, ha már nincs több használatra lehetőség, amúgy false-szal
	 */
	public boolean use() {
		return false;
	}
}
