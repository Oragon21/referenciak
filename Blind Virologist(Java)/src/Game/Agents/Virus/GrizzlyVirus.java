package Game.Agents.Virus;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printObject;

public class GrizzlyVirus extends Virus {

	public GrizzlyVirus() {}

	public GrizzlyVirus(GeneticCode geneticCode) {
		this.geneticCode = geneticCode;
	}

	/**
	 * Virus hatásának kifejtése
	 * <p>adott játékoson vitustánc-hatás aktiválása, azaz ameddig a játékos fertőzött állapotban van egy véletlenszerű szomszédos mezőre lép.
	 * Ezt úgy éri el, hogy a játékos move metódusát maga hívja meg.</p>
	 *
	 * @param p játékos
	 */
	public void activateEffect(Player p) {
		Tile t = p.getActTile();
		ArrayList<Tile> n = t.getNeighbours();
		int randomNeighbour = (int) (Math.random() * (n.size()));

		//random mozog
		p.move(n.get(randomNeighbour));

		//megfertozi az osszes playert akihez hozzaer
		for (Player pl: n.get(randomNeighbour).getTouchable()) {
			p.infectPlayer(pl, new GrizzlyVirus());
		}

		//elpusztitja a raktarkat
		p.getActTile().destroyResources();
	}

	/**
	 * Virussal való megfertőzés
	 * <p>az adott  játékos megfertőződik az Chorea vírussal</p>
	 *
	 * @param p jatékos
	 */
	public void activate(Player p) {
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
		return	"GrizzlyVirus {\n" +
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
