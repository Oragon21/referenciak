package Game.Agents.Virus;

import Game.Agents.Agent;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

public abstract class Virus extends Agent {

    public void activateEffect(Player p) {}

    public void activate(Player p){
        actingOn = p;
        p.setInfectedBy(this);
    }

    public void deactivate() {
        actingOn.setInfectedBy(null);
        actingOn.setParalyzed(false);
        actingOn = null;
    }

    @Override
    public Player getActingOn() {
        return super.getActingOn();
    }

    @Override
    public void setActingOn(Player actingOn) {
        super.setActingOn(actingOn);
    }

    @Override
    public GeneticCode getGeneticCode() {
        return super.getGeneticCode();
    }

    @Override
    public void setGeneticCode(GeneticCode geneticCode) {
        super.setGeneticCode(geneticCode);
    }
}
