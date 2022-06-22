package Game.Scenes;

import Game.Agents.GeneticCodes.*;
import Game.Equipment.*;
import Game.Graphics.PlayerButton.PlayerButton;
import Game.Graphics.TileButtons.CustomShapedButton;
import Game.Graphics.TileButtons.HexagonButton;
import Game.Graphics.TileButtons.PentagonButton;
import Game.MapHandling.LabTypes.GrizzlyInfectedLab;
import Game.MapHandling.Map;
import Game.MapHandling.StorageHandling.AminoAcidStorage;
import Game.MapHandling.StorageHandling.NucleotideStorage;
import Game.MapHandling.TileHandling.Lab;
import Game.MapHandling.TileHandling.Shelter;
import Game.MapHandling.TileHandling.Tile;
import Game.Steppable;
import Game.TopEntities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Timer;
import java.util.*;

import static Game.Scenes.SceneManager.SceneType.WIN;

/**
 * Játék fő kezelő osztálya.
 */
@SuppressWarnings({"IsNeverUsed"})
public class Game extends Scene implements Steppable {
	public static Game obj;
	private static int FPS = 1; //10ms-ként frissítünk
	private Map map;
	private Timer gameTimer;
	private Player currentPlayer;
	private List<PlayerButton> playerButtons = new ArrayList<>();
	private List<CustomShapedButton> tileButtons = new ArrayList<>();
	private Random rand = new Random();
	private int OFFSET = 70;

	private boolean exit = false;
	private final String path = "./src/map.txt";

	/**
	 * Konstruktor.
	 * <p>A listákat inicializálja.
	 * Emellett a logger működéséhez szükséges egy String-et átadni.</p>
	 */
	public Game() {
		obj = this;
	}

	/**
	 * Játék végetérésénél meghívandó függvény.
	 */
	public void endGame() {
		//System.out.println("A győztes: " + map.getWinner().toString());
		SceneManager.getInstance().changeScene(WIN);
	}

	/**
	 * Idő eltelését jelző függvény.
	 * <p>Megnézi, hogy van-e győztes, és ha igen, akkor kihirdeti.</p>
	 */
	public void Step() {
		if (map.getWinner() != null) {
			endGame();
		}
	}

	/**
	 * A pálya beállítása.
	 *
	 * @param map az új pálya.
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void init() {
		gameTimer = new Timer();
		currentPlayer = null;

		this.setSize(1360, 750);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - this.getSize().getWidth() / 2), (int) (screenSize.getHeight() / 2 - this.getSize().getHeight() / 2));

		this.setResizable(false);
		this.setTitle("Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		map = new Map();

		// pálya betöltése
		Load(2);

		this.setVisible(true);
		map.Step();
	}

	/**
	 * Pálya betöltése fájlból
	 *
	 * @param playerCount játékosok száma
	 */
	private void Load(int playerCount) {
		//Laborok random gc-ot kapnak innen.
		GeneticCode[] gc = {
				new AmnesiaGeneticCode(10,10,20,20),
				new ChoreaGeneticCode(15,15,30,30),
				new ParalyzingGeneticCode(30,30,50,50)
		};
		//Shelterek random eq-et kapnak innen.
		Equipment[] equipment = {
				new Glove(),
				new Bag(),
				new Axe(),
				new Cloak()
		};
		this.map = new Map();
		HashMap<Integer, List<Integer>> id2Tile = new HashMap<>();
		HashMap<Integer, CustomShapedButton> id2Csb = new HashMap<>();


		Scanner scanner;
		try {
			scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		while (scanner.hasNextLine()) {
			String[] data = scanner.nextLine().split("\t");

			//Tile	H	x	y	rot	id	neighbors...

			// beolvasott adatok nevesítése
			String tileType = data[0];
			String shapeClassName = data[1];
			int x = Integer.parseInt(data[2]);
			int y = Integer.parseInt(data[3]);
			int rotation = Integer.parseInt(data[4]);
			int id = Integer.parseInt(data[5]);
			List<Integer> neighbourIds = new ArrayList<>();
			for (int i = 6; i < data.length; i++) {
				neighbourIds.add(Integer.parseInt(data[i]));
			}

			// tile szomszédainak eltárolása
			id2Tile.put(id, neighbourIds);

			// gomb eltárolása
			CustomShapedButton customShapedButton;
			if ("P".equals(shapeClassName)) {
				customShapedButton = new PentagonButton(map);
			} else {
				customShapedButton = new HexagonButton(map);
			}
			customShapedButton.setBounds(x, y, 200, 200);
			customShapedButton.setBackground(getColorToTileType(tileType));
			customShapedButton.rotatePolygon(rotation);
			id2Csb.put(id, customShapedButton);

			// tile létrehozása
			switch (tileType) {
				case "Lab":
					Lab l = new Lab();
					l.setGeneticCode(gc[rand.nextInt(gc.length)]);
					l.setId(id);
					map.addTile(l);
					break;

				case "GrizzlyInfectedLab":
					GrizzlyInfectedLab gil = new GrizzlyInfectedLab();
					gil.setGeneticCode(new GrizzlyGeneticCode());
					gil.setId(id);
					map.addTile(gil);
					break;

				case "Shelter":
					Shelter s = new Shelter();
					s.setId(id);
					s.addEquipment(equipment[rand.nextInt(gc.length)]);
					map.addTile(s);
					break;

				case "AminoStorage":
					AminoAcidStorage aas = new AminoAcidStorage();
					aas.setId(id);
					map.addTile(aas);
					break;

				case "NucleoStorage":
					NucleotideStorage ns = new NucleotideStorage();
					ns.setId(id);
					map.addTile(ns);
					break;

				case "Tile":
					Tile t = new Tile();
					t.setId(id);
					map.addTile(t);
					break;
			}
		}

		// tile szomszédainak beállítása és gombhoz rendelése
		for (Tile t : map.getTiles()) {
			List<Integer> neighbourIDs = id2Tile.get(t.getId());
			for (int i = 0; i < neighbourIDs.size(); i++) {
				t.addNeighbour(map.getTileByID(neighbourIDs.get(i)));
			}
			id2Csb.get(t.getId()).setTile(t);
		}

		// playerer létrehozása
		if (playerCount < 2) playerCount = 2;
		for (int i = 0; i < playerCount; i++) {
			Player newPlayer = new Player();
			newPlayer.setCurrent(false);
			map.addPlayer(newPlayer);

			CustomShapedButton rndTileButton = null;
			int randTileId = rand.nextInt(map.getTiles().size());
			while (rndTileButton == null) {
				if (id2Csb.get(randTileId).getTile().getTouchable().isEmpty()) {
					rndTileButton = id2Csb.get(randTileId);
				} else {
					randTileId = rand.nextInt(map.getTiles().size());
				}
			}

			rndTileButton.getTile().addPlayer(newPlayer);
			newPlayer.setActTile(rndTileButton.getTile());
			PlayerButton playerButton = new PlayerButton(newPlayer, map);
			playerButton.setPosition(rndTileButton.getX() + 70, rndTileButton.getY() + 70);
			playerButtons.add(playerButton);
			this.add(playerButton);
		}

		// kezdő player beállítása
		map.getPlayer(0).setCurrent(true);
		currentPlayer = map.getPlayer(0);

		// buttonök frame-hez adása
		for (CustomShapedButton c : id2Csb.values()) {
			this.add(c);
			tileButtons.add(c);
		}
		this.add(new JPanel()); //ez kell mert csak

		scanner.close();
	}


	public void repaint(Player currentPlayer) {
		for (CustomShapedButton c : tileButtons) {
			c.repaint();
		}
		//mindig csak az akt jatekost rajzoljuk ujra, csak igy tudtam megoldani hogy kulonfele helyeken legyenek egy tile-on belul
		for (PlayerButton p : playerButtons) {
			if (p.getPlayer().equals(currentPlayer)) {
				Tile t = p.getPlayer().getActTile();        // lekérjük a mezőt ami áll
				CustomShapedButton tileButton = null;       //
				for (CustomShapedButton c : tileButtons) {  // megkeressük a mezőhöz tartozó gombot
					if (c.getTile().equals(t)) {
						tileButton = c;
						break;
					}
				}

				//random helyre rakom a jatekost a tile-on belul
				//ezert kell a currentPlayer hogy csak ot rajzoljuk ujra, mert ha az osszeset ujrajazolnank akkor ugralna az osszes a random szam miatt
				//lehet erre jobb megoldast talalni
				p.setPosition(getRandomNumber(tileButton.getX() + OFFSET, tileButton.getX() + tileButton.getWidth() - OFFSET), getRandomNumber(tileButton.getY() + OFFSET, tileButton.getY() + tileButton.getHeight() - OFFSET));
			}
		}
	}

	public void playerDied(Player p){
		for (PlayerButton bPlayer: playerButtons) {
			if(bPlayer.getPlayer().equals(p)){
				this.remove(bPlayer);
				map.getPlayers().remove(p);
				if(map.getPlayers().size() == 1){
					endGame();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public Color getColorToTileType(String t) {
		HashMap<String, Color> book = new HashMap<>();
		book.put("Lab", Color.BLUE);
		book.put("Tile", Color.GRAY);
		book.put("AminoStorage", Color.YELLOW);
		book.put("NucleoStorage", Color.RED);
		book.put("Shelter", Color.GREEN);
		return t != null ? book.get(t) : Color.BLACK;
	}
}
