package Game.Agents.GeneticCodes;

import Game.Agents.Vaccine.ChoreaVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.ChoreaVirus;
import Game.Agents.Virus.Virus;

/**
 * ChoreaGeneticCode osztály
 * <p>Az vitus-vírus és vitus-vakcina genetikai kódját reprezentáló osztály.
 * ‘Factory’-ként funkcionál, azaz létre tud hozni mind vírusokat, mind vakcinákat, amelyek az vitustánc-hatást állítják.</p>
 */
public class ChoreaGeneticCode extends GeneticCode {

	public ChoreaGeneticCode() {
		new ChoreaGeneticCode(40, 40, 40, 40);
	}

	public ChoreaGeneticCode(int costNucVirus, int costAmVirus, int costAmVaccine, int costNucVaccine) {
		this.costAmVaccine = costAmVaccine;
		this.costNucVaccine = costNucVaccine;
		this.costAmVirus = costAmVirus;
		this.costNucVirus = costNucVirus;
	}

	/**
	 * Vakcina készítése
	 * <p>létrehoz és visszaad egy ChoreaVaccine típusú vakcinát.</p>
	 *
	 * @return ChoreaVaccine objektum
	 */
	@Override
	public Vaccine makeVaccine() {
		return new ChoreaVaccine(this);
	}

	/**
	 * Vírus készítése
	 * <p>Létrehoz és visszaad egy ChoreaVirus típusú vírust.</p>
	 * @return ChoreaVirus objektum
	 */
	@Override
	public Virus makeVirus() {
		return new ChoreaVirus(this);
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
		return	"ChoreaGeneticCode {\n" +
				"\tcostNucVirus=" + costNucVirus + "\n" +
				"\tcostAmVirus=" + costAmVirus + "\n" +
				"\tcostNucVaccine=" + costNucVaccine + "\n" +
				"\tcostAmVaccine=" + costAmVaccine + "\n" +
				"\t}";
	}

}