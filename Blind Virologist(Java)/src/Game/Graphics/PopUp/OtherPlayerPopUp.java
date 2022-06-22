package Game.Graphics.PopUp;

import Game.Equipment.Axe;
import Game.Equipment.Equipment;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;


public class OtherPlayerPopUp extends PopUp{

	private JButton bInfect;
	private JButton bSteal;
	private JButton bSwing;
	private InfectPopUp infectPopUp;
	private StealPopUp stealPopUp;

	public OtherPlayerPopUp(){
		bInfect = new JButton("Infect");
		bSteal = new JButton("Steal");
		bSwing = new JButton("Swing Axe");
		d.add(bInfect);
		d.add(bSteal);
		d.add(bSwing);

		d.setVisible(true);
	}


	public void open(Map map, Player targetPlayer){
		Player player = map.getCurrentPlayer();
		Player nextPlayer = map.getNextPlayer();

		bInfect.addActionListener(e -> {

			infectPopUp = new InfectPopUp();
			infectPopUp.open(map, targetPlayer);

			clear(bInfect);
			this.dispose();
		});

		bSteal.addActionListener(e -> {

			stealPopUp = new StealPopUp();
			stealPopUp.open(map, targetPlayer);

			clear(bSteal);
			this.dispose();
		});

		bSwing.addActionListener(e -> {

			for (Equipment eq: player.getEquipments()) {
				if(eq.getClass() == Axe.class){
					player.swingAxeAtGrizzly(targetPlayer);
				}
			}

			clear(bSwing);
			this.dispose();
		});

	}

	public JButton getbInfect() {
		return bInfect;
	}

	public void setbInfect(JButton bInfect) {
		this.bInfect = bInfect;
	}

	public JButton getbSteal() {
		return bSteal;
	}

	public void setbSteal(JButton bSteal) {
		this.bSteal = bSteal;
	}

	public JButton getbSwing() {
		return bSwing;
	}

	public void setbSwing(JButton bSwing) {
		this.bSwing = bSwing;
	}
}
