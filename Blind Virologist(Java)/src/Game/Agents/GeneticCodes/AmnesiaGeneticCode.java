package Game.Agents.GeneticCodes;

import Game.Agents.Vaccine.AmnesiaVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.AmnesiaVirus;
import Game.Agents.Virus.Virus;

/**
 * AmnesiaGenetic osztály
 * <p> Az amnézia-vírus és amnézia-vakcina genetikai kódját megvalósító osztály.
 * ‘Factory’-ként funkcionál, azaz létre tud hozni mind vírusokat, mind vakcinákat,
 * amelyek az amnézia hatást állítják.</p>
 */
public class AmnesiaGeneticCode extends GeneticCode {

    public AmnesiaGeneticCode() {
        new AmnesiaGeneticCode(30, 30, 30, 30);
    }

    public AmnesiaGeneticCode(int costNucVirus, int costAmVirus, int costAmVaccine, int costNucVaccine) {
        this.costAmVaccine = costAmVaccine;
        this.costNucVaccine = costNucVaccine;
        this.costAmVirus = costAmVirus;
        this.costNucVirus = costNucVirus;
    }

    /**
     * Vakcina készítése
     *
     * <p>létrehoz és visszaad egy AmnesiaVaccine típusú vakcinát.</p>
     * @return AmnesiaVaccine objektum
     */
    @Override
    public Vaccine makeVaccine() {
        return new AmnesiaVaccine(this);
    }

    /**
     * Vírus készítése
     *
     * <p>Létrehoz és visszaad egy AmnesiaVirus típusú vírust.</p>
     * @return AmnesiaVirus objektum
     */
    @Override
    public Virus makeVirus() {
        return new AmnesiaVirus(this);
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
        return  "AmnesiaGeneticCode {\n" +
                "\tcostNucVirus=" + costNucVirus + "\n" +
                "\tcostAmVirus=" + costAmVirus + "\n" +
                "\tcostNucVaccine=" + costNucVaccine + "\n" +
                "\tcostAmVaccine=" + costAmVaccine + "\n" +
                "\t}";
    }
}
