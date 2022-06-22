package Game.Agents.Virus;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

import static Game.PrototypeTest.printObject;

/**
 * AmnesiaVirus osztály
 * <p>Az amnézia-vírust modellezi a játékban. Amely virológusra e hatás érvényesül, az általa megtanult összes genetikai kódot elfelejti.
 * Ennek következtében nem fog tudni ágenseket gyártani, a még újra össze nem gyűjti a genetikai kódot hozzá.</p>
 */
public class AmnesiaVirus extends Virus {

	public AmnesiaVirus() {
	}

	public AmnesiaVirus(GeneticCode geneticCode) {
		this.geneticCode = geneticCode;
	}

	/**
	 * Virus hatásának kifejtése
	 * <p>az adott  játékos Amnézia hatása alá kerül, ami azt jelenti hogy elfelejti az eddig tanult összes genetikai kódot.
	 * Ameddig a hatás érvényesül, ha tanul is új kódot a játékos rögtön elfelejti azt. </p>
	 *
	 * @param p játékos
	 */
	public void activateEffect(Player p) {
		p.setLearnedGeneticCodes(null);
	}

	/**
	 * Virussal való megfertőzés
	 * <p>az adott  játékos megfertőződik az Amnézia vírussal</p>
	 *
	 * @param p játékos
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
		return "AmnesiaVirus {\n" +
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
