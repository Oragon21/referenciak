package Game.MapHandling.StorageHandling;

import Game.MapHandling.TileHandling.Storage;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;

public class NucleotideStorage extends Storage {


	private int nucleotide;

	public NucleotideStorage() {
		nucleotide = 100;
	}

	/**
	 * A paramtéreként kapott játékos
	 * NukleotidRaktárral való interakcióját kezeli.
	 * <p>
	 * A raktárral interakcióba lép egy játékis.
	 * Ha van nukleotid a ratárban,
	 * akkor felvehti azt a mennyiséget, amennyi van a raktában,
	 * mely nem haladhatja meg a játékos nukleotid tárolható mennyiségét.
	 * Ezt követően a raktárban az elvett mennyiség le lesz kezelve.
	 * </p>
	 *
	 * @param p A játékos aki interaktál az raktárral.
	 */
	public void interact(Player p) {
		nucleotide -= p.pickUpNucleotide(nucleotide);
	}

	@Override
	public void destroyResources() {
		nucleotide = 0;
	}

	@Override
	public void addPlayer(Player p) {
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

	public int getNucleotide() {
		return nucleotide;
	}

	public void setNucleotide(int nucleotide) {
		this.nucleotide = nucleotide;
	}

	@Override
	public String toString() {
		return "NucleotideStorage {\n" +
				"\tnucleotide=" + nucleotide + "\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				"}";
	}
}
