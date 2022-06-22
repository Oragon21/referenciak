package Game.Agents.Vaccine;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Virus.Virus;
import Game.TopEntities.Player;

import static Game.PrototypeTest.printObject;

/**
 * ChoreaVaccine osztály.
 *
 * <p>Vitustánc vírus elleni vakcina. A vakcina megszünteti a jelenlegi vírus hatását,
 * ha éppen vitustánc vírus hatása alatt áll, valamint immunis lesz rá egy ideig.</p>
 */
public class ChoreaVaccine extends Vaccine {

    public ChoreaVaccine() {}

    public ChoreaVaccine(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * Vakcina hatásának kifejtése.
     *
     * <p>a vitustánc vakcina aktiválódik az adott játékoson. Leállítja vagy megakadályozza az vitustánc vírus hatását.</p>
     * @param p játékos
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
        return	"ChoreaVaccine {\n" +
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
