package Game.Graphics.PopUp;

import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;

public class StealPopUp extends PopUp{

    private JButton bNuc;
    private JButton bAm;
    private JButton bEq;
    private EquipmentsPopUp equipmentsPopUp;

    public StealPopUp(){

        //ide kellene kepeket rakni
        bNuc = new JButton("Nucleotide");
        bAm = new JButton("Amino Acid");
        bEq = new JButton("Equipment");

        d.add(bNuc);
        d.add(bAm);
        d.add(bEq);

        d.setVisible(true);
    }

    public void open(Map map, Player targetPlayer){
        Player player = map.getCurrentPlayer();
        Player nextPlayer = map.getNextPlayer();

        // AMINO
        bAm.addActionListener(act -> {
            System.out.println("Steal amino");

            player.stealAminoFromPlayer(targetPlayer);

            clear(bAm);
            this.dispose();
            
            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });

        //NUC
        bNuc.addActionListener(act -> {
            System.out.println("Steal Nuc");

            player.stealNucleotideFromPlayer(targetPlayer);

            clear(bNuc);
            this.dispose();

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
        });

        //EQ
        bEq.addActionListener(act -> {
            System.out.println("Steal eq");

            equipmentsPopUp = new EquipmentsPopUp();
            equipmentsPopUp.open(map, targetPlayer);

            clear(bEq);
            this.dispose();
        });
    }

    public JButton getbNuc() {
        return bNuc;
    }

    public void setbNuc(JButton bNuc) {
        this.bNuc = bNuc;
    }

    public JButton getbAm() {
        return bAm;
    }

    public void setbAm(JButton bAm) {
        this.bAm = bAm;
    }

    public JButton getbEq() {
        return bEq;
    }

    public void setbEq(JButton bEq) {
        this.bEq = bEq;
    }
}
