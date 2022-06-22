package Game.Agents.Vaccine;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Virus.Virus;
import Game.TopEntities.Player;

import static Game.PrototypeTest.printObject;

public class GrizzlyVaccine extends Vaccine {

	public GrizzlyVaccine() {}

	public GrizzlyVaccine(GeneticCode geneticCode) {
		this.geneticCode = geneticCode;
	}

	/**
	 * Vakcina hatásának kifejtése
	 *
	 * <p>A medve vírus elleni vakcina aktiválódik az adott játékoson. Leállítja vagy megakadályozza a vírus hatását.</p>
	 *
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
		return	"GrizzlyVaccine {\n" +
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
