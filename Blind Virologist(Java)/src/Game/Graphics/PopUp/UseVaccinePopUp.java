package Game.Graphics.PopUp;

import Game.Agents.Vaccine.AmnesiaVaccine;
import Game.Agents.Vaccine.ChoreaVaccine;
import Game.Agents.Vaccine.ParalyzingVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;
import java.util.List;

public class UseVaccinePopUp extends PopUp{

    private JButton bAmnesia;
    private JButton bChorea;
    private JButton bParalyzing;

    public UseVaccinePopUp(Map map) {
        //ide kellene kepeket rakni
        bAmnesia = new JButton("Amnesia Vaccine");
        bChorea = new JButton("Chorea Vaccine");
        bParalyzing = new JButton("Paralyzing Vaccine");

        d.add(bAmnesia);
        d.add(bChorea);
        d.add(bParalyzing);

        Player player = map.getCurrentPlayer();
        List<Vaccine> createdVaccines = player.getCreatedVaccines();

        AmnesiaVaccine amnesiaGeneticCode = null;
        ChoreaVaccine choreaGeneticCode = null;
        ParalyzingVaccine paralyzingGeneticCode = null;

        for (Vaccine gc: createdVaccines) {
            if(gc.getClass() == AmnesiaVaccine.class){
                amnesiaGeneticCode = (AmnesiaVaccine) gc;
            }
            if(gc.getClass() == ChoreaVaccine.class){
                choreaGeneticCode = (ChoreaVaccine) gc;
            }
            if(gc.getClass() == ParalyzingVaccine.class){
                paralyzingGeneticCode = (ParalyzingVaccine) gc;
            }
        }
        bAmnesia.setEnabled(amnesiaGeneticCode != null);
        bChorea.setEnabled(choreaGeneticCode != null);
        bParalyzing.setEnabled(paralyzingGeneticCode != null);

        d.pack();
        d.setVisible(true);
    }

    public void open(Map map, Player targetPlayer){
        Player player = map.getCurrentPlayer();
        Player nextPlayer = map.getNextPlayer();

        // AMNESIA
        bAmnesia.addActionListener(act -> {
            System.out.println("Amnesia Vaccine");
            AmnesiaVaccine vaccine = null;

            for (Vaccine v: player.getCreatedVaccines()) {
                if(v.getClass() == AmnesiaVaccine.class){
                    vaccine = (AmnesiaVaccine) v;
                }
            }
            if(vaccine != null) {
                player.disinfect(vaccine);
                System.out.println("boltotta magat");
            }

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();

            clear(bAmnesia);
            this.dispose();
        });

        // CHOREA
        bChorea.addActionListener(act -> {
            System.out.println("Chorea Virus");
            ChoreaVaccine vaccine = null;

            for (Vaccine v: player.getCreatedVaccines()) {
                if(v.getClass() == ChoreaVaccine.class){
                    vaccine = (ChoreaVaccine) v;
                }
            }
            if(vaccine != null) {
                player.disinfect(vaccine);
                System.out.println("boltotta magat");
            }

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();

            clear(bChorea);
            this.dispose();
        });

        //PARALYZING
        bParalyzing.addActionListener(act -> {
            System.out.println("Paralyzing Virus");
            ParalyzingVaccine vaccine = null;

            for (Vaccine v: player.getCreatedVaccines()) {
                if(v.getClass() == ParalyzingVaccine.class){
                    vaccine = (ParalyzingVaccine) v;
                }
            }
            if(vaccine != null) {
                player.disinfect(vaccine);
                System.out.println("Beoltotta magat");
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
