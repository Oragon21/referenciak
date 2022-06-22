package Game.Graphics.PopUp;

import Game.Agents.GeneticCodes.AmnesiaGeneticCode;
import Game.Agents.GeneticCodes.ChoreaGeneticCode;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.GeneticCodes.ParalyzingGeneticCode;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;
import java.util.List;

public class CreateVirusPopUp extends PopUp{

    private JButton bAmnesia;
    private JButton bChorea;
    private JButton bParalyzing;

    public CreateVirusPopUp(Map map) {
        //ide kellene kepeket rakni
        bAmnesia = new JButton("Amnesia Genetic Code");
        bChorea = new JButton("Chorea Genetic Code");
        bParalyzing = new JButton("Paralyzing Genetic Code");

        d.add(bAmnesia);
        d.add(bChorea);
        d.add(bParalyzing);

        Player player = map.getCurrentPlayer();
        List<GeneticCode> gcList = player.getLearnedGeneticCodes();

        AmnesiaGeneticCode amnesiaGeneticCode = null;
        ChoreaGeneticCode choreaGeneticCode = null;
        ParalyzingGeneticCode paralyzingGeneticCode = null;

        for (GeneticCode gc: gcList) {
            if(gc.getClass() == AmnesiaGeneticCode.class){
                amnesiaGeneticCode = (AmnesiaGeneticCode) gc;
            }
            if(gc.getClass() == ChoreaGeneticCode.class){
                choreaGeneticCode = (ChoreaGeneticCode) gc;
            }
            if(gc.getClass() == ParalyzingGeneticCode.class){
                paralyzingGeneticCode = (ParalyzingGeneticCode) gc;
            }
        }
        bAmnesia.setEnabled(
                amnesiaGeneticCode != null &&   //A player ismeri a kódot;
                        amnesiaGeneticCode.getCostAmVirus() <= player.getInventoryAm() && //Van elég amino
                        amnesiaGeneticCode.getCostNucVirus() <= player.getInventoryNuc()  //Van elég nucleo
        );

        bChorea.setEnabled(
                choreaGeneticCode != null &&   //A player ismeri a kódot;
                        choreaGeneticCode.getCostAmVirus() <= player.getInventoryAm() && //Van elég amino
                        choreaGeneticCode.getCostNucVirus() <= player.getInventoryNuc()  //Van elég nucleo
        );

        bParalyzing.setEnabled(
                paralyzingGeneticCode != null &&   //A player ismeri a kódot;
                        paralyzingGeneticCode.getCostAmVirus() <= player.getInventoryAm() && //Van elég amino
                        paralyzingGeneticCode.getCostNucVirus() <= player.getInventoryNuc()  //Van elég nucleo
        );

        d.pack();
        d.setVisible(true);
    }

    public void open(Map map, Player targetPlayer){
        Player player = map.getCurrentPlayer();
        Player nextPlayer = map.getNextPlayer();

        // AMNESIA
        bAmnesia.addActionListener(act -> {
            System.out.println("Amnesia Genetic Code");
            AmnesiaGeneticCode amnesiaGeneticCode = null;


            for (GeneticCode gc: player.getLearnedGeneticCodes()) {
                if(gc.getClass() == AmnesiaGeneticCode.class){
                    amnesiaGeneticCode = (AmnesiaGeneticCode) gc;
                }
            }
            if(amnesiaGeneticCode != null) {
                player.makeVirus(amnesiaGeneticCode);
                System.out.println("Virus létrehozva");
            }


            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
            clear(bAmnesia);
            this.dispose();
        });

        // CHOREA
        bChorea.addActionListener(act -> {
            System.out.println("Chorea Genetic Code");
            ChoreaGeneticCode choreaGeneticCode = null;


            for (GeneticCode gc: player.getLearnedGeneticCodes()) {
                if(gc.getClass() == ChoreaGeneticCode.class){
                    choreaGeneticCode = (ChoreaGeneticCode) gc;
                }
            }
            if(choreaGeneticCode != null) {
                player.makeVirus(choreaGeneticCode);
                System.out.println("Virus létrehozva");
            }

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
            clear(bChorea);
            this.dispose();
        });

        //PARALYZING
        bParalyzing.addActionListener(act -> {
            System.out.println("Paralyzing Genetic Code");
            ParalyzingGeneticCode paralyzingGeneticCode = null;


            for (GeneticCode gc: player.getLearnedGeneticCodes()) {
                if(gc.getClass() == ParalyzingGeneticCode.class){
                    paralyzingGeneticCode = (ParalyzingGeneticCode) gc;
                }
            }
            if(paralyzingGeneticCode != null) {
                player.makeVirus(paralyzingGeneticCode);
                System.out.println("Virus létrehozva");
            }

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();
            clear(bParalyzing);
            this.dispose();
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
