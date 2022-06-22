package Game.MapHandling.StorageHandling;

import Game.MapHandling.TileHandling.Storage;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.util.ArrayList;

import static Game.PrototypeTest.printArray;

public class AminoAcidStorage extends Storage {


	private int aminoAcid;

	public AminoAcidStorage() {
		aminoAcid = 100;
	}

	/**
	 * A paramtéreként kapott játékos
	 * AminoSavRaktárral való interakcióját kezeli.
	 * <p>
	 * A raktárral interakcióba lép egy játékis.
	 * Ha van aminosav a ratárban,
	 * akkor felvehti azt a mennyiséget, amennyi van a raktában,
	 * mely nem haladhatja meg a játékos aminosav tárolható mennyiségét.
	 * Ezt követően a raktárban az elvett mennyiség le lesz kezelve.
	 * </p>
	 *
	 * @param p A játékos aki interaktál az raktárral.
	 */
	public void interact(Player p) {
		aminoAcid -= p.pickUpAminoAcid(aminoAcid);
	}

	@Override
	public void destroyResources() {
		aminoAcid = 0;
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

	public int getAminoAcid() {
		return aminoAcid;
	}

	public void setAminoAcid(int aminoAcid) {
		this.aminoAcid = aminoAcid;
	}

	@Override
	public String toString() {
		return "AminoAcidStorage {\n" +
				"\taminoAcid=" + aminoAcid + "\n" +
				"\tid=" + id + "\n" +
				"\tneighbours=" + NeighbourStringified() + "\n" +
				"\tplayers=" + printArray(players) + "\n" +
				"}";
	}
}
