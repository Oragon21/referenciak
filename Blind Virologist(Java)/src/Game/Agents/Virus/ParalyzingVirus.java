package Game.Agents.Virus;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

import static Game.PrototypeTest.printObject;

/**
 * ParalyzingVirus osztály
 * <p>Az bénító-vírust modellezi a játékban.
 * Amely virológusra e hatás érvényesül, az a virológus nem fog tudni semmilyen cselekvést véghez vinni.
 * Emellett a még le van bénulva egyszer el lehet tőle lopni egy felszerelést vagy némi anyagot.</p>
 */
public class ParalyzingVirus extends Virus {

    public ParalyzingVirus() {}

    public ParalyzingVirus(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * Virus hatásának kifejtése
     * <p> az adott játékos bénulás hatása alá kerül.
     * Ez azt jelenti, hogy ameddig ezzel a vírussal fertőzott, addig nem tud cselekedni.
     * Ezt a paralyzed attribútum beállításával érjük el.</p>
     * @param p jatékos
     */
    public void activateEffect(Player p) {
        p.setParalyzed(true);
    }

    /**
     * Virussal való megfertőzés
     * <p>az adott  játékos megfertőződik az Paralyzing vírussal</p>
     * @param p jatékos
     */
    public void activate(Player p){
        //activateEffect(p);
        super.activate(p);
    }

    /**
     * Virus hatásának leállítása
     */
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public String toString() {
        return	"ParalyzingVirus {\n" +
                "\tgeneticCode=" + printObject(geneticCode) + "\n" +
                "\tactingOn=" + printObject(actingOn) + "\n" +
                "\ttimeLeftInv=" + timeLeftInv + "\n" +
                "\ttimeLeftOnPlayer=" + timeLeftOnPlayer + "\n" +
                '}';
    }

    @Override
    public Player getActingOn() {
        return super.getActingOn();
    }

    @Override
    public void setActingOn(Player actingOn) {
        super.setActingOn(actingOn);
    }

    @Override
    public GeneticCode getGeneticCode() {
        return super.getGeneticCode();
    }

    @Override
    public void setGeneticCode(GeneticCode geneticCode) {
        super.setGeneticCode(geneticCode);
    }


}
