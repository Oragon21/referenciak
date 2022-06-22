package Game.Graphics.PopUp;

import Game.Equipment.Axe;
import Game.Equipment.Bag;
import Game.Equipment.Cloak;
import Game.Equipment.Glove;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;

public class EquipmentsPopUp extends PopUp{

    private JButton bAxe;
    private JButton bBag;
    private JButton bCloak;
    private JButton bGlove;

    public EquipmentsPopUp(){
        //ide kellene kepeket rakni
        bAxe = new JButton("Axe");
        bBag = new JButton("Bag");
        bCloak = new JButton("Cloak");
        bGlove = new JButton("Glove");

        d.add(bAxe);
        d.add(bBag);
        d.add(bCloak);
        d.add(bGlove);

        d.setVisible(true);
    }

    public void open(Map map, Player targetPlayer){
        Player player = map.getCurrentPlayer();
        Player nextPlayer = map.getNextPlayer();

        //AXE
        bAxe.addActionListener(act2 -> {
            System.out.println("Picked Axe");

            player.stealEquipmentFromPlayer(targetPlayer, new Axe());

            clear(bAxe);
            this.dispose();

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });

        //BAG
        bBag.addActionListener(act2 -> {
            System.out.println("Picked Bag");

            player.stealEquipmentFromPlayer(targetPlayer, new Bag());

            clear(bBag);
            this.dispose();

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });

        //CLOAK
        bCloak.addActionListener(act2 -> {
            System.out.println("Picked Cloak");

            player.stealEquipmentFromPlayer(targetPlayer, new Cloak());

            clear(bCloak);
            this.dispose();

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });

        //GLOVE
        bGlove.addActionListener(act2 -> {
            System.out.println("Picked Glove");

            player.stealEquipmentFromPlayer(targetPlayer, new Glove());

            clear(bGlove);
            this.dispose();

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });
    }

    public JButton getbAxe() {
        return bAxe;
    }

    public void setbAxe(JButton bAxe) {
        this.bAxe = bAxe;
    }

    public JButton getbBag() {
        return bBag;
    }

    public void setbBag(JButton bBag) {
        this.bBag = bBag;
    }

    public JButton getbCloak() {
        return bCloak;
    }

    public void setbCloak(JButton bCloak) {
        this.bCloak = bCloak;
    }

    public JButton getbGlove() {
        return bGlove;
    }

    public void setbGlove(JButton bGlove) {
        this.bGlove = bGlove;
    }
}
