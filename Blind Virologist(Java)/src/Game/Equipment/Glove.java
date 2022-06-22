package Game.Equipment;

import Game.TopEntities.Player;

public class Glove extends Equipment {

	private int usagesLeft;

	public Glove() {
		usagesLeft = 3;
	}

	/**
	 * Aktivált állapotba a játékosra kent ágens visszakenhető a kezdeményező játékosra.
	 *
	 * @param p - paraméterül kapott játékoson meghívja a kesztyű aktiváló függvényét.
	 */
	@Override
	public void activate(Player p) {
		if(!p.getEquipments().contains(this)) {
			p.setImmunity(true);
			super.activate(p);
		}
	}

	/**
	 * A kesztyű hatás kikapcsolása miatt a játékos nem lesz képes visszakenni a rákenendő ágenst.
	 *
	 * @param p -  paraméterül kapott játékoson meghívja a kesztyű deaktiváló függvényét.
	 */
	@Override
	public void deactivate(Player p) {
		p.setImmunity(false);
		super.deactivate(p);
	}

	@Override
	public boolean use() {
		if (usagesLeft > 0){
			usagesLeft--;
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Glove {" + "\n" +
				"usagesLeft=" + usagesLeft + "\n" +
				'}';
	}
}
