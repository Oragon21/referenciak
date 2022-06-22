package Game.Equipment;

import Game.TopEntities.Player;

public class Bag extends Equipment {

	private int changeAmount;

	public Bag() {
		changeAmount = 10;
	}

	/**
	 * @param p - a játékos paraméter, akin aktiválódik a zsák hatás, azaz  növeli a tároló kapacitását
	 */
	@Override
	public void activate(Player p) {
		if(!p.getEquipments().contains(this)){
			p.setMaxInvCap(p.getMaxInvCap() + changeAmount);
			super.activate(p);
		}
	}

	/**
	 * Visszaállítja az aktiválás előtti állapotot.
	 *
	 * @param p - a játékos paraméter, akin a zsák hatás kikapcsolódik
	 */
	@Override
	public void deactivate(Player p) {
		p.setMaxInvCap(p.getMaxInvCap() - changeAmount);
		super.deactivate(p);
	}

	public int getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(int changeAmount) {
		this.changeAmount = changeAmount;
	}

	@Override
	public String toString() {
		return "Bag {" + "\n" +
				"changeAmount=" + changeAmount + "\n" +
				'}';
	}
}
