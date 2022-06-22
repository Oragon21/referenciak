package Game.Agents;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.TopEntities.Player;

public abstract class Agent {

    protected int timeLeftInv = 3;
    protected int timeLeftOnPlayer = 3;
    protected Player actingOn;
    protected GeneticCode geneticCode;

    public Agent() {}

    public void activateEffect(Player p) {}

    public void activate(Player p) {}

    public void deactivate() {}

    public void Step() {
        if(actingOn != null){
            timeLeftOnPlayer--;
            if(timeLeftOnPlayer == 0) this.deactivate();
        } else {
            timeLeftInv--;
        }
    }

    public boolean compare(Agent a) {
        return this.geneticCode.equals(a.geneticCode);
    }

    public void setActingOn(Player actingOn) {
        this.actingOn = actingOn;
    }

    public Player getActingOn() {
        return actingOn;
    }

    public int getTimeLeftInv() {
        return timeLeftInv;
    }

    public int getTimeLeftOnPlayer() {
        return timeLeftOnPlayer;
    }

    public GeneticCode getGeneticCode() {
        return geneticCode;
    }

    public void setTimeLeftInv(int timeLeftInv) {
        this.timeLeftInv = timeLeftInv;
    }

    public void setTimeLeftOnPlayer(int timeLeftOnPlayer) {
        this.timeLeftOnPlayer = timeLeftOnPlayer;
    }

    public void setGeneticCode(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }
}
