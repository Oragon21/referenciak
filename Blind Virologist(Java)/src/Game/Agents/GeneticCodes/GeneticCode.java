package Game.Agents.GeneticCodes;

import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.Virus;

public abstract class GeneticCode {

    protected int costNucVirus;
    protected int costAmVirus;
    protected int costNucVaccine;
    protected int costAmVaccine;

    public Vaccine makeVaccine() {
        return null;
    }

    public Virus makeVirus() {return null;}

    public int getCostNucVirus() {
        return costNucVirus;
    }

    public int getCostAmVirus() {
        return costAmVirus;
    }

    public int getCostNucVaccine() {
        return costNucVaccine;
    }

    public int getCostAmVaccine() {
        return costAmVaccine;
    }

    public void setCostNucVirus(int costNucVirus) {
        this.costNucVirus = costNucVirus;
    }

    public void setCostAmVirus(int costAmVirus) {
        this.costAmVirus = costAmVirus;
    }

    public void setCostNucVaccine(int costNucVaccine) {
        this.costNucVaccine = costNucVaccine;
    }

    public void setCostAmVaccine(int costAmVaccine) {
        this.costAmVaccine = costAmVaccine;
    }
}