package Game.Equipment;

import Game.TopEntities.Player;

import static Game.PrototypeTest.random;

public class Cloak extends Equipment {

	private final double dodgeChance;

	public Cloak () {
		if(random) dodgeChance = 0.837;
		else dodgeChance = 1;
	}

	/**
	 * A hatás bekapcsolása lehetővé teszi, hogy a játékos ne minden esetben legyen fertőzhető.
	 *
	 * @param p - a játékos paraméter, akin aktiválódik a köpeny hatás.
	 */
	@Override
	public void activate(Player p) {
		if(!p.getEquipments().contains(this)) {
			p.setDodgeChance(dodgeChance);
			super.activate(p);
		}
	}

	/**
	 * A hatás kikapcsolását követően a játékos újra fertőzhető lesz minden esetre.
	 *
	 * @param p - a játékos paraméter, akin kikapcsolja a köpeny hatás.
	 */
	@Override
	public void deactivate(Player p) {
		p.setDodgeChance(0);
		super.deactivate(p);
	}

	public double getDodgeChance() {
		return dodgeChance;
	}

	@Override
	public String toString() {
		return "Cloak {" + "\n" +
				"dodgeChance=" + dodgeChance + "\n" +
				'}';
	}
}
