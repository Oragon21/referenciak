package Game.Graphics.PopUp;

import Game.Agents.GeneticCodes.AmnesiaGeneticCode;
import Game.Agents.GeneticCodes.ChoreaGeneticCode;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.GeneticCodes.ParalyzingGeneticCode;
import Game.Equipment.*;
import Game.MapHandling.Map;
import Game.TopEntities.Player;

import javax.swing.*;
import java.awt.*;

public class InventoryPopUp extends PopUp {

    private int width = 500;
    private int height = 400;

    JButton bCreateVaccine;
    JButton bCreateVirus;
    JButton bUseVaccine;
    JButton bPass;

    JLabel lTitleMaterial;
    JLabel lAmino;
    JLabel lNucleotide;

    JLabel lTitleGeneticCodes;
    JLabel lAmnesiaVaccine;
    JLabel lChoreaVaccine;
    JLabel lParalyzingVaccine;
    JLabel lAmnesiaVirus;
    JLabel lChoreaVirus;
    JLabel lParalyzingVirus;

    JLabel lTitleEq;
    JLabel lGlove;
    JLabel lBag;
    JLabel lAxe;
    JLabel lCloak;

    CreateVaccinePopUp createVaccinePopUp;
    CreateVirusPopUp createVirusPopUp;
    UseVaccinePopUp useVaccinePopUp;


    public InventoryPopUp() {
        d.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        d.setLocation((int)(screenSize.getWidth()/2) - d.getWidth()/2, (int)(screenSize.getHeight()/2) - d.getHeight()/2 + 100);
        initButtons();
        initLabels();


        d.setLayout(null);
        d.setVisible(true);
    }

    public void initButtons() {
        Insets margin = new Insets(0,0,0,0);

        bCreateVaccine = new JButton("CreateVaccine");
        bCreateVaccine.setBounds(20, 320, 100, 30);
        bCreateVaccine.setMargin(margin);

        bCreateVirus = new JButton("CreateVirus");
        bCreateVirus.setBounds(140, 320, 100, 30);
        bCreateVirus.setMargin(margin);

        bUseVaccine = new JButton("UseVaccine");
        bUseVaccine.setBounds(260, 320, 100, 30);
        bUseVaccine.setMargin(margin);

        bPass = new JButton("Pass");
        bPass.setBounds(380, 320, 100, 30);
        bPass.setMargin(margin);


        d.add(bCreateVaccine);
        d.add(bCreateVirus);
        d.add(bUseVaccine);
        d.add(bPass);
    }

    public void initLabels() {
        lNucleotide = new JLabel();
        lAmino = new JLabel();

        lAmnesiaVaccine = new JLabel();
        lChoreaVaccine = new JLabel();
        lParalyzingVaccine = new JLabel();
        lAmnesiaVirus = new JLabel();
        lChoreaVirus = new JLabel();
        lParalyzingVirus = new JLabel();

        lGlove = new JLabel();
        lBag = new JLabel();
        lCloak = new JLabel();
        lAxe = new JLabel();

        lTitleMaterial = new JLabel();
        lTitleGeneticCodes = new JLabel();
        lTitleEq = new JLabel();


        lTitleMaterial.setBounds(40, 0, 130,30);
        lNucleotide.setBounds(40, 20, 130, 30);
        lAmino.setBounds(40, 40, 130, 30);

        lTitleGeneticCodes.setBounds(40,80,130,30);
        lAmnesiaVaccine.setBounds(40, 110, 300,30);
        lAmnesiaVirus.setBounds(40, 140, 300,30);
        lChoreaVaccine.setBounds(40, 170, 300,30);
        lChoreaVirus.setBounds(40, 200, 300,30);
        lParalyzingVaccine.setBounds(40, 230, 300,30);
        lParalyzingVirus.setBounds(40, 260, 300,30);

        lTitleEq.setBounds(280,0,130,30);
        lGlove.setBounds(280, 20, 130, 30);
        lAxe.setBounds(380, 20, 130, 30);
        lBag.setBounds(280, 50, 130, 30);
        lCloak.setBounds(380, 50, 130, 30);


        d.add(lTitleMaterial);
        d.add(lTitleGeneticCodes);
        d.add(lTitleEq);
        d.add(lNucleotide);
        d.add(lAmino);
        d.add(lAmnesiaVaccine);
        d.add(lChoreaVaccine);
        d.add(lParalyzingVaccine);
        d.add(lAmnesiaVirus);
        d.add(lChoreaVirus);
        d.add(lParalyzingVirus);
        d.add(lAxe);
        d.add(lGlove);
        d.add(lBag);
        d.add(lCloak);

    }

    public void open(Map map, Player targetPlayer) {
        Player player = map.getCurrentPlayer();
        Player nextPlayer = map.getNextPlayer();


        lTitleMaterial.setText("Materials");
        lTitleGeneticCodes.setText("Genetic Codes");
        lTitleEq.setText("Equipments");

        lAmino.setText("Amino Acid: " + player.getInventoryAm());
        lNucleotide.setText("Nucleotide: " + player.getInventoryNuc());


        showEquipments(player);
        showGeneticCodes(player);


        bUseVaccine.addActionListener(e -> {
            System.out.println("Use Vaccine");

            useVaccinePopUp = new UseVaccinePopUp(map);
            useVaccinePopUp.open(map, null);


            clear(bUseVaccine);
            this.dispose();
        });

        bCreateVaccine.addActionListener(e -> {
            System.out.println("Create Vaccine");

            createVaccinePopUp = new CreateVaccinePopUp(map);
            createVaccinePopUp.open(map, null);

            clear(bCreateVaccine);
            this.dispose();
        });

        bCreateVirus.addActionListener(e -> {
            System.out.println("Create Virus");

            createVirusPopUp = new CreateVirusPopUp(map);
            createVirusPopUp.open(map, null);

            clear(bCreateVirus);
            this.dispose();
        });

        bPass.addActionListener(e -> {
            System.out.println("Pass");

            player.setCurrent(false);
            nextPlayer.setCurrent(true);
            map.Step();

            clear(bPass);
            this.dispose();
        });

    }

    public void showGeneticCodes(Player player){
        AmnesiaGeneticCode amnesiaGeneticCode = null;
        ChoreaGeneticCode  choreaGeneticCode = null;
        ParalyzingGeneticCode paralyzingGeneticCode = null;

        for (GeneticCode gc: player.getLearnedGeneticCodes()) {
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

        if(amnesiaGeneticCode != null){
            lAmnesiaVaccine.setText("Amnesia Vaccine cost: " +  "       am: " + amnesiaGeneticCode.getCostAmVaccine() + "       " + "nuc: " + amnesiaGeneticCode.getCostNucVaccine());
            lAmnesiaVirus.setText("Amnesia Virus cost: " +  "       am: " + amnesiaGeneticCode.getCostAmVirus() + "       " + "nuc: " + amnesiaGeneticCode.getCostNucVirus());
        }

        if(choreaGeneticCode != null){
            lChoreaVaccine.setText("Chorea Vaccine cost" +  "       am: " + choreaGeneticCode.getCostAmVaccine() + "       " + "nuc: " + choreaGeneticCode.getCostNucVaccine());
            lChoreaVirus.setText("Chorea Virus cost" +  "       am: " + choreaGeneticCode.getCostAmVirus() + "       " + "nuc: " + choreaGeneticCode.getCostNucVirus());
        }

        if(paralyzingGeneticCode != null){
            lParalyzingVaccine.setText("Paralyzing Vaccine cost" +  "       am: " + paralyzingGeneticCode.getCostAmVaccine() + "       " + "nuc: " + paralyzingGeneticCode.getCostNucVaccine());
            lParalyzingVirus.setText("Paralyzing Virus cost" +  "       am: " + paralyzingGeneticCode.getCostAmVirus() + "       " + "nuc: " + paralyzingGeneticCode.getCostNucVirus());
        }

    }

    public void showEquipments(Player player){

        for (Equipment e: player.getEquipments()) {
            if(e.getClass() == Bag.class){
                lBag.setText("Bag" );
            }
            if(e.getClass() == Cloak.class){
                lCloak.setText("Cloak");
            }
            if(e.getClass() == Glove.class){
                lGlove.setText("Glove");
            }
            if(e.getClass() == Axe.class){
                lAxe.setText("Axe");
            }
        }
    }







}
