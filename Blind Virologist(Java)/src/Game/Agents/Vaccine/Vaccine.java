package Game.Agents.Vaccine;

import Game.Agents.Agent;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

public abstract class Vaccine extends Agent {

    public void activate(Player p) {
        actingOn = p;
        p.addVaccine(this);
    }

    public void deactivate() {
        actingOn.removeVaccine(this);
        actingOn = null;
        //actingOn.disinfect(this);
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
