package Game.MapHandling.TileHandling;

import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;

public class Tile {

	protected ArrayList<Tile> neighbours;
	protected ArrayList<Player> players;
	private static int IdCounter = 0;
	protected int id;

	public Tile() {
		id = IdCounter++;
		players = new ArrayList<>();
		neighbours = new ArrayList<>();
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public void removePlayer(Player p) {
		players.remove(p);
	}

	public ArrayList<Player> getTouchable() {
		//Mindenki érinthető, aki a Tile-on van.
		return players;
	}

	public void interact(Player p) {
	}

	public void addNeighbour(Tile t) {
		if (!neighbours.contains(t)) {
			neighbours.add(t);
			t.addNeighbour(this);
		}
	}

	public ArrayList<Tile> getNeighbours() {
		return neighbours;
	}

	public void destroyResources() {
	}

	@Override
	public String toString() {
		return "Tile {" + "\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				"}";
	}

	protected String NeighbourStringified() {
		StringBuilder n = new StringBuilder("[");
		for (Tile t : neighbours) {
			n.append(t.id).append(" ");
		}
		n = new StringBuilder(n.toString().trim().replace(" ", ", "));
		n.append("]");
		return n.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		//Fájlból való beolvasásnál
	}
}
