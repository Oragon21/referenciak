package Game.MapHandling;

import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.GrizzlyVirus;
import Game.Agents.Virus.Virus;
import Game.Graphics.PopUp.InventoryPopUp;
import Game.Graphics.PopUp.OtherPlayerPopUp;
import Game.MapHandling.TileHandling.Tile;
import Game.Scenes.Game;
import Game.Steppable;
import Game.TopEntities.Player;

import java.util.ArrayList;
import java.util.List;

import static Game.PrototypeTest.printObject;

/**
 * Map osztály.
 * <p>A térképet megvalósító osztály.
 * Kezeli a győzelmi kondíciók ellenőrzésést és
 * a térkép mezőit.</p>
 */
public class Map implements Steppable {

	private ArrayList<Tile> tiles;
	private ArrayList<Player> players;
	private ArrayList<GeneticCode> allGeneticCode;
	private Player winner;
	private Player currentPlayer;
	private Player nextPlayer;

	/**
	 * Map osztály konstruktora.
	 * <p>Létrehozzuk a játékosokat (players) és
	 * genetikai kódokat (allgeneticCode)
	 * tároló listákat.</p>
	 */
	public Map() {
		tiles = new ArrayList<>();
		players = new ArrayList<>();
		allGeneticCode = new ArrayList<>();
		currentPlayer = null;
		nextPlayer = null;
		winner = null;
	}

	/**
	 * Győzelmi kondíciók ellenőrzése egy játékosra.
	 * <p>Ehhez a játékos által megtanult genetikai kódokat
	 * vetjük össze a pályán szétszort genetikai kódok listájával.</p>
	 *
	 * @param p A játékos, akit ellenőrzünk.
	 */
	public void checkWinCond(Player p) {
		List<GeneticCode> gc = p.getLearnedGeneticCodes();
		if (allGeneticCode.equals(gc)) {
			winner = p;
			System.out.println("A győztes: " + winner);
		}
	}

	/**
	 * Játékos felvétele a Map-ra.
	 * <p>Egy új játékos elhelyezése a players listában.</p>
	 *
	 * @param p A játékos, akit hozzáadunk a listához.
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}

	public void addGeneticCode(GeneticCode gc) {
		allGeneticCode.add(gc);
	}

	public Player getPlayer(int i) {
		try {
			return players.get(i);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Mező felvétele a Map-ba.
	 * <p>Egy új {@link Tile} elhelyezése a tiles listában.</p>
	 *
	 * @param t A mező, amit hozzáadunk a mezők listájához.
	 */
	public void addTile(Tile t) {
		tiles.add(t);
	}

	/**
	 * Map léptetése.
	 * <p>Map léptetése, mely során ellenőrizzük
	 * a győzelmi kondíciókat is a játékosokra.</p>
	 */
	public void Step() {

		// ha current akkor allitsuk be őt a currentPlayernek
		// és lista következőjét nextPlayer-re

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isCurrent()) {
				currentPlayer = players.get(i);

				if (players.size() == i + 1) {    //utolso elem -> ugrunk az elejére
					nextPlayer = players.get(0);
				} else {
					nextPlayer = players.get(i + 1);
				}

				//megnezzuk nyert-e
				checkWinCond(currentPlayer);

				//virus ami rajta van kifejti hatasat
				currentPlayer.timePassed();

				//vakcinak idejenek csökkentese
				for (Vaccine v : currentPlayer.getCreatedVaccines()) {
					v.Step();
					if (v.getTimeLeftInv() == 0) {
						currentPlayer.getCreatedVaccines().remove(v);
						break;
					}
					System.out.println("Vaccine timeLeftInv: " + v.getTimeLeftInv());
					System.out.println("Vaccine timeLeftOnPlayer: " + v.getTimeLeftOnPlayer());

				}

				//virusok idejenek csokkentese
				for (Virus v : currentPlayer.getCreatedViruses()) {
					v.Step();
					if (v.getTimeLeftInv() == 0) {
						currentPlayer.getCreatedViruses().remove(v);
						break;
					}
					System.out.println("Virus timeLeftInv: " + v.getTimeLeftInv());
					System.out.println("Virus timeLeftOnPlayer: " + v.getTimeLeftOnPlayer());
				}
				if (currentPlayer.getInfectedBy() != null && currentPlayer.getInfectedBy().getClass() != GrizzlyVirus.class) {
					currentPlayer.getInfectedBy().Step();
				}

			}
			Game.obj.repaint();
		}


	}


	//ertesítes hogy megnyomtuk egy tile gombot
	public void handleTilePressed(Tile tile) {
		System.out.println(currentPlayer.toString());
		System.out.println(currentPlayer.getEquipments());

		if (currentPlayer.getActTile().equals(tile)) {
			//ha arra a tile-ra nyomtunk amin epp all a jatekos
			System.out.println("interact Tile");
			currentPlayer.interactWithTile();

		} else if (currentPlayer.getActTile().getNeighbours().contains(tile) && !currentPlayer.isMoved() && !currentPlayer.isParalyzed()) {
			//a currentPlayernek szomszedos tile-ra nyomtunk akkor nyilvan move-ltunk
			currentPlayer.move(tile);
			nextPlayer.setMoved(false);

			System.out.println(currentPlayer.toString());
			Game.obj.repaint(currentPlayer);
		}
		// kulonben teljesen masik tile-t nyomunk meg nem csinalunk semmit
	}

	//ertesítes hogy megnyomtuk egy player gombot
	public void handlePlayerPressed(Player p) {
		System.out.println("Player pressed");

		//magamra nyomok
		if (currentPlayer.equals(p)) {
			System.out.println("Magadra nyomtál!");

			InventoryPopUp inventoryPopUp = new InventoryPopUp();
			inventoryPopUp.open(this, null);

			//changeCurrentPlayer();

			Game.obj.repaint(currentPlayer);

		}
		//masik playerre nyomok aki ugyanazon a tile-n all mint en
		else {
			Tile t = p.getActTile();
			if (currentPlayer.getActTile().equals(t)) {
				System.out.println("Szomszédos player-re nyomtál!");

				OtherPlayerPopUp otherPlayerPopUp = new OtherPlayerPopUp();
				otherPlayerPopUp.open(this, p);

				Game.obj.repaint(currentPlayer);
			}
		}

		//kulonben olyan playerre nyomok aki nem szomszedos
	}


	public void changeCurrentPlayer() {
		currentPlayer.setCurrent(false);
		nextPlayer.setCurrent(true);
	}


	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}

	public Tile getTileByID(int i) {
		if (!(i < 0)) {
			for (Tile t : tiles) {
				if (t.getId() == i) {
					return t;
				}
			}
		}
		return null;
	}

	/**
	 * Győztes játékos hirdetése.
	 * <p>A paraméterként kapott győztes játékos
	 * után a Map győztesének beállítása.</p>
	 *
	 * @param winner a győztes játkos.
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	/**
	 * Map léptetése.
	 * <p>Visszaadja a tárolt győztes játékost.</p>
	 *
	 * @return A győztes játékos, a Map 'winner' Field-éből.
	 */
	public Player getWinner() {
		return winner;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<GeneticCode> getAllGeneticCode() {
		return allGeneticCode;
	}

	@Override
	public String toString() {
		return "Map {\n" +
				"\tallGeneticCode=" + allGeneticCode.size() + "\n" +
				"\tplayers=" + players.size() + "\n" +
				"\ttiles=" + tiles.size() + "\n" +
				"\twinner=" + printObject(winner) + "\n" +
				'}';
	}
}
