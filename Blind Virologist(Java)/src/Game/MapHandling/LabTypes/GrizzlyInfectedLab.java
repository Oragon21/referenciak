package Game.MapHandling.LabTypes;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.GeneticCodes.GrizzlyGeneticCode;
import Game.Agents.Virus.GrizzlyVirus;
import Game.MapHandling.TileHandling.Lab;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;

public class GrizzlyInfectedLab extends Lab {

	public GrizzlyInfectedLab(){
		//This is a Grizzly Labor! GRRR!!
		this.geneticCode = new GrizzlyGeneticCode();
	}

	public void interact(Player p) {
		p.learn(geneticCode);
	}

	@Override
	public void setGeneticCode(GeneticCode gc) {
		super.setGeneticCode(gc);
	}

	@Override
	public void addPlayer(Player p) {
		//p.infect(this.geneticCode.makeVirus());
		p.infect(new GrizzlyVirus());
		super.addPlayer(p);
	}

	@Override
	public void removePlayer(Player p) {
		super.removePlayer(p);
	}

	@Override
	public ArrayList<Player> getTouchable() {
		//Mindenki érinthető, aki a Tile-on van.
		return super.getTouchable();
	}

	@Override
	public void addNeighbour(Tile t) {
		super.addNeighbour(t);
	}
	@Override
	public ArrayList<Tile> getNeighbours() {
		return super.getNeighbours();
	}

	@Override
	public String toString() {
		return "GrizzlyInfectedLab {\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				"}";
	}
}
