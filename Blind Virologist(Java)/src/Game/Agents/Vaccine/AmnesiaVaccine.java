package Game.Agents.Vaccine;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Virus.Virus;
import Game.TopEntities.Player;

import static Game.PrototypeTest.printObject;

/**
 * AmnesaiVaccine osztály.
 *
 * <p>Az amnézia-vírus elleni oltást reprezentálja.
 * Ha ennek a hatása alatt áll egy virológus, akkor nem lehet amnézia vírussal megfertőzni a még ez a hatás tart.</p>
 */
public class AmnesiaVaccine extends Vaccine {

    public AmnesiaVaccine() {}

    public AmnesiaVaccine(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * Vakcina hatásának kifejtése.
     *
     * <p>az adott játékoson az amnézia vakcina aktiválódik meghatározott ideig. Ha volt a játékoson amnézia vírus, akkor megakadályozza a vírus hatását.
     * Ha nem volt megfertőzve ezzel a vírussal, akkor csak immunitást ad ellene egy megadott időintervallumig, így megakadályozza az amnézia vírus hatását.</p>
     * @param p az adott játékos
     */
    public void activate(Player p) {
        super.activate(p);
        boolean sameGeneticCode = false;
        Virus infectedBy = p.getInfectedBy();
        if(infectedBy != null){
            sameGeneticCode = infectedBy.compare(this);
        }
        if(sameGeneticCode){
            infectedBy.deactivate();
        }
    }

    /**
     * Vakcina hatásának leállítása.
     */
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public String toString() {
        return	"AmnesiaVaccine {\n" +
                "\tgeneticCode=" + printObject(geneticCode) + "\n" +
                "\tactingOn=" + printObject(actingOn) + "\n" +
                "\ttimeLeftInv=" + timeLeftInv + "\n" +
                "\ttimeLeftOnPlayer=" + timeLeftOnPlayer + "\n" +
                "}";
    }

    @Override
    public Player getActingOn() { return super.getActingOn();}

    @Override
    public void setActingOn(Player actingOn) {super.setActingOn(actingOn);}

    @Override
    public GeneticCode getGeneticCode() {
        return super.getGeneticCode();
    }

    @Override
    public void setGeneticCode(GeneticCode geneticCode) {
        super.setGeneticCode(geneticCode);
    }
}
