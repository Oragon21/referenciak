package Game.Agents.GeneticCodes;

import Game.Agents.Vaccine.ParalyzingVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.ParalyzingVirus;
import Game.Agents.Virus.Virus;

/**
 * ParalyzingGeneticCode osztály
 * <p>A bénító-vírus és bénító-vakcina genetikai kódját reprezentáló osztály.
 * ‘Factory’-ként funkcionál, azaz létre tud hozni mind vírusokat, mind vakcinákat, amelyek az bénító-hatást állítják.</p>
 */
public class ParalyzingGeneticCode extends GeneticCode {

	public ParalyzingGeneticCode() {
		new ParalyzingGeneticCode(20, 60, 60, 20);
	}

	public ParalyzingGeneticCode(int costNucVirus, int costAmVirus, int costAmVaccine, int costNucVaccine) {
		this.costAmVaccine = costAmVaccine;
		this.costNucVaccine = costNucVaccine;
		this.costAmVirus = costAmVirus;
		this.costNucVirus = costNucVirus;
	}

	/**
	 * Vakcina készítése
	 * <p>létrehoz és visszaad egy ParalyzingVaccine típusú vakcinát.</p>
	 *
	 * @return ParalyzingVaccine objektum
	 */
	@Override
	public Vaccine makeVaccine() {
		return new ParalyzingVaccine(this);
	}

	/**
	 * Vírus készítése
	 * <p>Létrehoz és visszaad egy ParlyzingVirus típusú vírust.</p>
	 *
	 * @return ParalyzingVirus objektum
	 */
	@Override
	public Virus makeVirus() {
		return new ParalyzingVirus(this);
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
		return	"ParalyzingGeneticCode{\n" +
				"\tcostNucVirus=" + costNucVirus + "\n" +
				"\tcostAmVirus=" + costAmVirus + "\n" +
				"\tcostNucVaccine=" + costNucVaccine + "\n" +
				"\tcostAmVaccine=" + costAmVaccine + "\n" +
				"\t}";
	}

}
