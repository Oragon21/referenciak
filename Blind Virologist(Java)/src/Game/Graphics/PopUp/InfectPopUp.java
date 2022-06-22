package Game.Graphics.PopUp;

import Game.Agents.Virus.AmnesiaVirus;
import Game.Agents.Virus.ChoreaVirus;
import Game.Agents.Virus.ParalyzingVirus;
import Game.Agents.Virus.Virus;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;


public class InfectPopUp extends PopUp{

	private JButton bAmnesia;
	private JButton bChorea;
	private JButton bParalyzing;

	public InfectPopUp(){
		//ide kellene kepeket rakni
		bAmnesia = new JButton("Amnesia");
		bChorea = new JButton("Chorea");
		bParalyzing = new JButton("Paralyzing");

		d.add(bAmnesia);
		d.add(bChorea);
		d.add(bParalyzing);

		d.setVisible(true);
	}

	public void open(Map map, Player targetPlayer){

		Player player = map.getCurrentPlayer();
		Player nextPlayer = map.getNextPlayer();

		// AMNESIA
		bAmnesia.addActionListener(act -> {
			System.out.println("Amnesia Virus");
			AmnesiaVirus virus = null;

			for (Virus v: player.getCreatedViruses()) {
				if(v.getClass() == AmnesiaVirus.class){
					virus = (AmnesiaVirus) v;
				}
			}
			if(virus != null) {
				player.infectPlayer(targetPlayer, virus);
				System.out.println("fertőzés megtörtént");
			}


			clear(bAmnesia);
			this.dispose();
			player.setCurrent(false);
			nextPlayer.setCurrent(true);
			map.Step();
		});

		// CHOREA
		bChorea.addActionListener(act -> {
			System.out.println("Chorea Virus");
			ChoreaVirus virus = null;

			for (Virus v: player.getCreatedViruses()) {
				if(v.getClass() == ChoreaVirus.class){
					virus = (ChoreaVirus) v;
				}
			}
			if(virus != null) {
				player.infectPlayer(targetPlayer, virus);
				System.out.println("fertőzés megtörtént");
			}


			clear(bChorea);
			this.dispose();
			player.setCurrent(false);
			nextPlayer.setCurrent(true);
			map.Step();
		});

		//PARALYZING
		bParalyzing.addActionListener(act -> {
			System.out.println("Paralyzing Virus");
			ParalyzingVirus virus = null;

			for (Virus v: player.getCreatedViruses()) {
				if(v.getClass() == ParalyzingVirus.class){
					virus = (ParalyzingVirus) v;
				}
			}
			if(virus != null) {
				player.infectPlayer(targetPlayer, virus);
				System.out.println("fertőzés megtörtént");
			}


			clear(bParalyzing);
			this.dispose();
			player.setCurrent(false);
			nextPlayer.setCurrent(true);
			map.Step();
		});
	}

	public JButton getbAmnesia() {
		return bAmnesia;
	}

	public void setbAmnesia(JButton bAmnesia) {
		this.bAmnesia = bAmnesia;
	}

	public JButton getbChorea() {
		return bChorea;
	}

	public void setbChorea(JButton bChorea) {
		this.bChorea = bChorea;
	}


	public JButton getbParalyzing() {
		return bParalyzing;
	}

	public void setbParalyzing(JButton bParalyzing) {
		this.bParalyzing = bParalyzing;
	}




}
