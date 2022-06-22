package Game.Equipment;

import Game.TopEntities.Player;

public class Axe extends Equipment {
	private int usagesLeft;

	public Axe() {
		usagesLeft = 1;
	}

	@Override
	public void activate(Player p) {
		if(!p.getEquipments().contains(this)) {
			super.activate(p);
		}
	}

	@Override
	public void deactivate(Player p) {
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
		return "Axe {" + "\n" +
				"usagesLeft=" + usagesLeft + "\n" +
				'}';
	}
}
