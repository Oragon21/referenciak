package Game.Agents.GeneticCodes;

import Game.Agents.Vaccine.GrizzlyVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.GrizzlyVirus;
import Game.Agents.Virus.Virus;

public class GrizzlyGeneticCode extends GeneticCode {

	public GrizzlyGeneticCode() {
		new GrizzlyGeneticCode(80, 80, 80, 80);
	}

	public GrizzlyGeneticCode(int costNucVirus, int costAmVirus, int costAmVaccine, int costNucVaccine) {
		this.costAmVaccine = costAmVaccine;
		this.costNucVaccine = costNucVaccine;
		this.costAmVirus = costAmVirus;
		this.costNucVirus = costNucVirus;
	}

	/**
	 * Vakcina készítése
	 * <p>létrehoz és visszaad egy GrizzlyVaccine típusú vakcinát.</p>
	 *
	 * @return GrizzlyVaccine objektum
	 */
	@Override
	public Vaccine makeVaccine() {
		return new GrizzlyVaccine(this);
	}

	/**
	 * Vírus készítése
	 * <p>Létrehoz és visszaad egy GrizzlyVirus típusú vírust.</p>
	 *
	 * @return GrizzlyVirus objektum
	 */
	@Override
	public Virus makeVirus() {
		return new GrizzlyVirus(this);
	}

	@Override
	public void setCostAmVaccine(int costAmVaccine) {
		super.setCostAmVaccine(costAmVaccine);
	}

	@Override
	public void setCostAmVirus(int costAmVirus) {
		super.setCostAmVirus(costAmVirus);
	}

	@Override
	public void setCostNucVaccine(int costNucVaccine) {
		super.setCostNucVaccine(costNucVaccine);
	}

	@Override
	public void setCostNucVirus(int costNucVirus) {
		super.setCostNucVirus(costNucVirus);
	}

	@Override
	public int getCostAmVaccine() {
		return super.getCostAmVaccine();
	}

	@Override
	public int getCostAmVirus() {
		return super.getCostAmVirus();
	}

	@Override
	public int getCostNucVaccine() {
		return super.getCostNucVaccine();
	}

	@Override
	public int getCostNucVirus() {
		return super.getCostNucVirus();
	}

	@Override
	public String toString() {
		return	"GrizzlyGeneticCode{" +
				"\tcostNucVirus=" + costNucVirus + "\n" +
				"\tcostAmVirus=" + costAmVirus + "\n" +
				"\tcostNucVaccine=" + costNucVaccine + "\n" +
				"\tcostAmVaccine=" + costAmVaccine + "\n" +
				"\t}";
	}
}
