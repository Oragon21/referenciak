package Game.Agents.Virus;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.util.ArrayList;
import java.util.Random;

import static Game.PrototypeTest.printObject;

/**
 * ChoreaVirus osztály
 * <p>A vitustánc vírussal megfertőzött virológus önkéntelenül kezd mozogni.
 * Ez felülírja a mozgási lehetőségét, tehát a még ennek hatása alatt áll, az összes elmozdulása véletlenszerű irányba történik.
 * Ez nem befolyásolja, hogy ahol áll ott hogyan tud cselekedni.</p>
 */
public class ChoreaVirus extends Virus {

    public ChoreaVirus() {}

    public ChoreaVirus(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }

    /**
     * Virus hatásának kifejtése
     * <p>adott játékoson vitustánc-hatás aktiválása, azaz ameddig a játékos fertőzött állapotban van egy véletlenszerű szomszédos mezőre lép.
     * Ezt úgy éri el, hogy a játékos move metódusát maga hívja meg.</p>
     * @param p játékos
     */
    public void activateEffect(Player p) {
        Tile t = p.getActTile();
        ArrayList<Tile> n = t.getNeighbours();
        Random rand = new Random();
        int randomNeighbour = rand.nextInt(n.size());

        p.move(n.get(randomNeighbour));
    }

    /**
     * Virussal való megfertőzés
     * <p>az adott  játékos megfertőződik az Chorea vírussal</p>
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
        return	"ChoreaVirus {\n" +
                "\tgeneticCode=" + printObject(geneticCode) + "\n" +
                "\tactingOn=" + printObject(actingOn) + "\n" +
                "\ttimeLeftInv=" + timeLeftInv + "\n" +
                "\ttimeLeftOnPlayer=" + timeLeftOnPlayer + "\n" +
                '}';
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
