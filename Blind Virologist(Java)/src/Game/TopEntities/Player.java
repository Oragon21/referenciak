package Game.TopEntities;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Vaccine.GrizzlyVaccine;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.GrizzlyVirus;
import Game.Agents.Virus.Virus;
import Game.Equipment.Equipment;
import Game.Equipment.Glove;
import Game.MapHandling.TileHandling.Tile;
import Game.Scenes.Game;

import java.util.ArrayList;
import java.util.List;

import static Game.PrototypeTest.printArray;
import static Game.PrototypeTest.printObject;


/**
 * Játékost, avagy virológust reprezentáló osztály.
 */
public class Player {

	private int maxInvCap;
	private int inventoryAm;
	private int inventoryNuc;
	private double dodgeChance;
	private boolean immunity;
	private boolean paralyzed;
	private boolean hasBeenStolenFrom;
	private boolean moved;
	private Tile actTile;
	private ArrayList<Equipment> equipments;

	private  ArrayList<Virus> createdViruses;
	private  ArrayList<Vaccine> createdVaccines;

	private ArrayList<Vaccine> vaccinedBy;
	private Virus infectedBy;
	private ArrayList<GeneticCode> learnedGeneticCodes;

	private boolean current;

	/**
	 * Konstruktor.
	 * <p>A listákat inicializálja.
	 * Emellett a logger működéséhez szükséges egy String-et átadni.</p>
	 */
	public Player() {
		dodgeChance = 0;
		maxInvCap = 30;
		moved = false;
		//createdAgents = new ArrayList<>();
		createdViruses = new ArrayList<>();
		createdVaccines = new ArrayList<>();

		vaccinedBy = new ArrayList<>();
		learnedGeneticCodes = new ArrayList<>();
		equipments = new ArrayList<>();
		current = false;
	}

	/**
	 * Játékos mozgását megvalósító függvény.
	 * <p>Feladata, hogy a jelenlegi mezőjéről kiléptesse, az új mezőre pedig berakja a játékost.</p>
	 *
	 * @param t a cél mező
	 */
	public void move(Tile t) {
		this.actTile.removePlayer(this);
		t.addPlayer(this);
		this.actTile = t;
		this.moved = true;
	}

	/**
	 * A {@link Game.MapHandling.TileHandling.Lab Lab} által hívott függvény.
	 * <p>Ha még nem tanulta meg, akkor elementi a paraméterül kapott
	 * genetikai kódot a tanultak közé.</p>
	 *
	 * @param gc az őj genetikai kód
	 */
	public void learn(GeneticCode gc) {
		if (!learnedGeneticCodes.contains(gc)) {
			learnedGeneticCodes.add(gc);
		}
	}

	/**
	 * Megfertőződésre szolgáló függvény.
	 *
	 * <p>A cél a paraméterül kapott vírussal megfertőzni a játékost.
	 * Ez nem biztos hogy sikerült, erről a felhasználó dönt. Ha sikerül,
	 * akkor a vírus aktiválódik, ha nem, akkor csak visszatér hamis értékkel.</p>
	 *
	 * <p>Amennyiben van kesztyű a játékoson (szintén felhasználói döntés), akkor
	 * igaz értékkel tér vissza, ezzel jelezve a kesztyű viselést a másik félnek.</p>
	 *
	 * @param v a vírus, amivel meg kívánjuk fertőzni
	 * @return Volt-e kesztyű rajta
	 */
	public boolean infect(Virus v) {
		//van-e rajta kesztyu, ha nincs akkor meg volt-e már fertőzve vagy köpeny levedte-e, vagy be van-e már oltva erre a virusra
		if (!immunity) {
			boolean sameGeneticCode = false;
			for (Vaccine vaccine : vaccinedBy) {
				if (vaccine.compare(v)) sameGeneticCode = true;
			}
			if (this.infectedBy == null && (this.dodgeChance < Math.random()) && !sameGeneticCode) {
				v.activate(this);
				//createdAgents.remove(v);
				createdViruses.remove(v);
			}
			return false;
		} else {
			for (Equipment e: equipments) {
				if(e.getClass() == Glove.class){
					e.use();
				}
			}
			return true;
		}
	}

	/**
	 * A Skeleton által hívott függvény arra, hogy fertőzzön meg egy másik játékost.
	 * <p>Amennyiben a {@link #infect(Virus) megfertőzés} igazan tér vissza,
	 * akkor saját magát kell megfertőznie.</p>
	 *
	 * @param p2 a játékos, akit meg kell fertőzzön
	 * @param v  a vírus, amivel megfertőzi
	 */
	public void infectPlayer(Player p2, Virus v) {
		boolean hadGlove = p2.infect(v);
		if (hadGlove) {
			boolean sameGeneticCode = false;
			for (Vaccine vaccine : vaccinedBy) {
				if (vaccine.compare(v)) sameGeneticCode = true;
			}
			if (this.infectedBy == null && (this.dodgeChance < Math.random()) && !sameGeneticCode) {
				v.activate(this);
				//createdAgents.remove(v);
				createdViruses.remove(v);
			}

		}
	}

	/**
	 * Vakcina beadására szolgáló függvény.
	 * <p>A paraméterül kapott vakcinát aktiváljuk.</p>
	 *
	 * @param v a vakcina, amivel be szeretnénk oltani
	 */
	public void disinfect(Vaccine v) {
		v.activate(this);
	}

	/**
	 * A játékostól való lopás egkísérlését leíró függvény
	 * <p>A paraméterként kapott eszközt megpróbáljuk ellopni a játékostól.
	 * Amnnyiben ezt valami emgakadályozza, akkor null-lal ér vissza.
	 * Ha sikeres a lopás, akkor deaktiválja azt, majd visszaadja.</p>
	 *
	 * @param e az eszköz, amit el szeretnénk lopni
	 * @return Az ellopott eszköz (ha nem sikerült lopni akkor null)
	 */
	public Equipment stealEquipment(Equipment e) {

		Equipment realEquipment = null;
		for (Equipment eq: equipments) {
			if(e.getClass() == eq.getClass()){
				realEquipment = eq;
			}
		}

		if (paralyzed && !hasBeenStolenFrom && equipments.contains(realEquipment)) {
			hasBeenStolenFrom = true;
			realEquipment.deactivate(this);
			return realEquipment;
		} else {
			return null;
		}
	}

	/**
	 * Aminosav lopása a játékostól.
	 * <p>A paraméterül kapott szám azt jelzi, hogy legfeljebb mennyi aminosavat
	 * tudunk/szeretnénk lopni a játékostól. Ennek és a tárolt aminosav mennyiségnek
	 * kell a minimumát venni, annyit tud ellopni a másik fél. Ezt ki kell vonni a
	 * tárolt mennyiségből, majd visszaadni.</p>
	 *
	 * @param max a maximum ellopandó mennyiség
	 * @return A sikeresen ellopott mennyiség
	 */
	public int stealAminoAcid(int max) {
		if (paralyzed && !hasBeenStolenFrom) {
			hasBeenStolenFrom = true;
			if (inventoryAm > max) {
				inventoryAm -= max;
				return max;
			} else {
				int ret = inventoryAm;
				inventoryAm = 0;
				return ret;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Nukleotid lopása a játékostól.
	 * <p>A paraméterül kapott szám azt jelzi, hogy legfeljebb mennyi nukleotidot
	 * tudunk/szeretnénk lopni a játékostól. Ennek és a tárolt nukleotid mennyiségnek
	 * kell a minimumát venni, annyit tud ellopni a másik fél. Ezt ki kell vonni a
	 * tárolt mennyiségből, majd visszaadni.</p>
	 *
	 * @param max a maximum ellopandó mennyiség
	 * @return A sikeresen ellopott mennyiség
	 */
	public int stealNucleotide(int max) {
		if (paralyzed && !hasBeenStolenFrom) {
			hasBeenStolenFrom = true;
			if (inventoryNuc > max) {
				inventoryNuc -= max;
				return max;
			} else {
				int ret = inventoryNuc;
				inventoryNuc = 0;
				return ret;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Az {@link Game.MapHandling.TileHandling.Shelter óvóhely} által hívott függvény.
	 * <p>A paraméterül kapott eszközt aktiváljuk a játékoson.</p>
	 *
	 * @param e a szerzett eszköz
	 */
	public void pickUpEquipment(Equipment e) {
		e.activate(this);
	}

	/**
	 * Az {@link Game.MapHandling.StorageHandling.AminoAcidStorage AminoAcidStorage} által hívott függvény.
	 *
	 * @param max maximum mennyi aminosavat tud szolgáltatni a raktár
	 * @return A maximumból mennyit tud felvenni.
	 */
	public int pickUpAminoAcid(int max) {
		int maxAmount = Math.min(max, maxInvCap - inventoryAm);
		inventoryAm += maxAmount;
		return maxAmount;
	}

	/**
	 * A {@link Game.MapHandling.StorageHandling.NucleotideStorage NucleotideStoreage} által hívott függvény.
	 *
	 * @param max maximum mennyi nukleotidot tud szolgáltatni a raktár
	 * @return A maximumból mennyit tud felvenni.
	 */
	public int pickUpNucleotide(int max) {
		int maxAmount = Math.min(max, maxInvCap - inventoryNuc);
		inventoryNuc += maxAmount;
		return maxAmount;
	}


	public void swingAxeAtGrizzly(Player p) {

		p.swingAxe();
		//axe.deactivate(this);
	}

	public void swingAxe() {
		if(infectedBy != null && infectedBy.getClass() == GrizzlyVirus.class)
		die();
	}

	/**
	 * Az idő telését jelző függvény.
	 * <p>Ez a függvény akkor hívódik meg, amikor a játékosra kerül a sor, hogy cselekedjen.
	 * Először {@link #reset() levesz minden effektet róla}, majd az {@link #infectedBy}-jal interaktálva
	 * visszaállítjuk a releváns állapotot. Ezután cselekedhetne.</p>
	 */
	public void timePassed() {
		reset();
		if(infectedBy != null)
		infectedBy.activateEffect(this);
	}

	/**
	 * Az állapotok visszaállítása.
	 * <p>Ilyen lehet a paralízis megszűntetése.</p>
	 */
	public void reset() {
		if(paralyzed) infectedBy.deactivate();
		this.moved = false;
	}


	/**
	 * A Skeleton által hívott fügvény arra, hogy gyártson egy vakcinát a játékos.
	 * <p>A felhasználótól bekérjük, hogy van-e elég anyaga megcsinálni a vakcinát.
	 * Ez helyettesíti azt, hogy lekérdezzük a vakcina árát.</p>
	 *
	 * @param gc ezen genetikai kódhoz tartozó vakcinát csináljon
	 */
	public void makeVaccine(GeneticCode gc) {
		if (gc.getCostNucVaccine() <= this.inventoryNuc && gc.getCostAmVaccine() <= this.inventoryAm) {
			this.inventoryNuc -= gc.getCostNucVaccine();
			this.inventoryAm -= gc.getCostAmVaccine();
			//addCreatedAgent(gc.makeVaccine());
			addCreatedVaccine(gc.makeVaccine());
		}
	}

	/**
	 * A Skeleton által hívott fügvény arra, hogy gyártson egy vírust a játékos.
	 * <p>A felhasználótól bekérjük, hogy van-e elég anyaga megcsinálni a vírust.
	 * Ez helyettesíti azt, hogy lekérdezzük a vírus árát.</p>
	 *
	 * @param gc ezen genetikai kódhoz tartozó vírust csináljon
	 */
	public void makeVirus(GeneticCode gc) {
		if (gc.getCostNucVirus() <= this.inventoryNuc && gc.getCostAmVirus() <= this.inventoryAm) {
			this.inventoryNuc -= gc.getCostNucVirus();
			this.inventoryAm -= gc.getCostAmVirus();
			//addCreatedAgent(gc.makeVirus());
			addCreatedVirus(gc.makeVirus());
		}
	}

	/**
	 * A Skeleton által hívott függvény arra, hogy a játékos interaktáljon a jelenlegi mezőjével.
	 */
	public void interactWithTile() {
		actTile.interact(this);
	}

	/**
	 * A Skeleton hívása arra, hogy a jatékos lopjon egy másik játékostól adott felszerelést.
	 *
	 * @param p a cél játékos
	 * @param e az ellopni kívánt felszerelés
	 */
	public void stealEquipmentFromPlayer(Player p, Equipment e) {
		Equipment stolenEquipment = p.stealEquipment(e);
		if (stolenEquipment != null) {
			stolenEquipment.activate(this);
		}
	}

	/**
	 * A Skeleton hívása arra, hogy a játékos lopjon aminosavat egy másiktól.
	 *
	 * @param p a cél játékos
	 */
	public void stealAminoFromPlayer(Player p) {
		inventoryAm += p.stealAminoAcid(maxInvCap - inventoryAm);
	}

	/**
	 * A Skeleton hívása arra, hogy a játékos lopjon nukleotidot egy másiktól.
	 *
	 * @param p a cél játékos
	 */
	public void stealNucleotideFromPlayer(Player p) {
		inventoryNuc += p.stealNucleotide(maxInvCap - inventoryNuc);
	}


	public void die() {
		System.out.println("MEGHALTAL HAHAHAHAHA");
		Game.obj.playerDied(this);

		/*equipments.removeAll(equipments);
		//createdAgents.removeAll(createdAgents);
		createdVaccines.removeAll(createdVaccines);
		createdViruses.removeAll(createdViruses);
		vaccinedBy.removeAll(vaccinedBy);
		learnedGeneticCodes.removeAll(learnedGeneticCodes);*/
		actTile.removePlayer(this);
		actTile = null;
		infectedBy.deactivate();
		Game.obj.repaint();
	}

	/**
	 * Új felszerelés felvételére szolgáló függvény.
	 *
	 * @param e az új felszerelés
	 */
	public void addEquipment(Equipment e) {
		e.activate(this);
	}

	/**
	 * Új használt vakcina hozzáadására használt függvény.
	 *
	 * @param v a használt vakcina
	 */
	public void addVaccine(Vaccine v) {
		vaccinedBy.add(v);
	}

	/**
	 * Függvény arra, amikor lejár egy vakcina védelmi időszaka.
	 *
	 * @param v a lejárt vakcina
	 */
	public void removeVaccine(Vaccine v) {
		vaccinedBy.remove(v);
	}

	/**
	 * Új tanult genetikai kód hozzáadására szolgáló függvény.
	 *
	 * <p>Nem azonos a {@link #learn(GeneticCode) tanulással}, mivel azt
	 * csak a {@link  Game.MapHandling.TileHandling.Lab Labor} hívja</p>
	 *
	 * @param gc az új genetikai kód
	 */
	public void addGeneticCode(GeneticCode gc) {
		learnedGeneticCodes.add(gc);
	}

	/**
	 * Felszerelés kivételére szolgáló függvény.
	 *
	 * @param e a kivevendő felszarelés.
	 */
	public void removeEquipment(Equipment e) {
		e.deactivate(this);
	}

	/**
	 * A megtanult genetikai kódok lekérésére szolgáló függvény.
	 *
	 * @return megtanult genetikai kódok
	 */
	public List<GeneticCode> getLearnedGeneticCodes() {
		return learnedGeneticCodes;
	}

	/**
	 * Megtanult genetikai kódok beállítása.
	 * <p>Akkor kell, amikor el akarunk felejtetni minden kódot a játékossal.
	 * Ezt megkönnyítendő ha null-t kap, akkor csak kiüríti a listát, hogy az objektum ne vesszen el</p>
	 *
	 * @param learnedGeneticCodes a tanult genetikai kódok listája
	 */
	public void setLearnedGeneticCodes(ArrayList<GeneticCode> learnedGeneticCodes) {
		if (learnedGeneticCodes == null) {
			this.learnedGeneticCodes.clear();
		} else {
			this.learnedGeneticCodes = learnedGeneticCodes;
		}
	}

	/**
	 * Jelenlegi fertőző vírus beállítása.
	 *
	 * @param infectedBy az új vírus
	 */
	public void setInfectedBy(Virus infectedBy) {
		this.infectedBy = infectedBy;
	}

	/**
	 * Jelenlegi fertőző vírus lekérése.
	 *
	 * @return a vírus, ami a játékoson van
	 */
	public Virus getInfectedBy() {
		return infectedBy;
	}

	/**
	 * Jelenlegi mező beállítása.
	 *
	 * @param actTile a mező, amin a játékos áll
	 */
	public void setActTile(Tile actTile) {
		this.actTile = actTile;
	}

	/**
	 * Jelenlegi mező lekérése.
	 *
	 * @return a mező, amin a játékos áll
	 */
	public Tile getActTile() {
		return actTile;
	}

	/**
	 * A bénulást beállító függvény.
	 *
	 * @param paralyzed a bénülás új értéke
	 */
	public void setParalyzed(boolean paralyzed) {
		this.paralyzed = paralyzed;
	}

	public int getInventoryAm() {
		return inventoryAm;
	}

	public int getInventoryNuc() {
		return inventoryNuc;
	}

	public void setInventoryAm(int inventoryAm) {
		this.inventoryAm = inventoryAm;
	}

	public void setInventoryNuc(int inventoryNuc) {
		this.inventoryNuc = inventoryNuc;
	}

	public int getMaxInvCap() {
		return maxInvCap;
	}

	public void setMaxInvCap(int maxInvCap) {
		this.maxInvCap = maxInvCap;
		if (this.maxInvCap < inventoryAm) inventoryAm = this.maxInvCap;
		if (this.maxInvCap < inventoryNuc) inventoryNuc = this.maxInvCap;
	}

	public double getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(double dodgeChance) {
		this.dodgeChance = dodgeChance;
	}

	public boolean getImmunity() {
		return immunity;
	}

	public void setImmunity(boolean immunity) {
		this.immunity = immunity;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(ArrayList<Equipment> equipments) {
		this.equipments = equipments;
	}

	/*public List<Agent> getCreatedAgent() {
		return createdAgents;
	}

	public void addCreatedAgent(Agent ca) {
		createdAgents.add(ca);
	}*/

	public ArrayList<Virus> getCreatedViruses() {
		return createdViruses;
	}

	public ArrayList<Vaccine> getCreatedVaccines() {
		return createdVaccines;
	}

	public void addCreatedVirus(Virus v) {
		createdViruses.add(v);
	}

	public void addCreatedVaccine(Vaccine v) {
		createdVaccines.add(v);
	}


	public ArrayList<Vaccine> getVaccinedBy() {
		return vaccinedBy;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean isImmunity() {
		return immunity;
	}

	public boolean isParalyzed() {
		return paralyzed;
	}

	public boolean isHasBeenStolenFrom() {
		return hasBeenStolenFrom;
	}

	public void setHasBeenStolenFrom(boolean hasBeenStolenFrom) {
		this.hasBeenStolenFrom = hasBeenStolenFrom;
	}

	@Override
	public String toString() {
		return "Player {\n" +
				"\tmaxInvCap=" + maxInvCap + "\n" +
				"\tinventoryAm=" + inventoryAm + "\n" +
				"\tinventoryNuc=" + inventoryNuc + "\n" +
				"\tdodgeChance=" + dodgeChance + "\n" +
				"\timmunity=" + immunity + "\n" +
				"\tparalyzed=" + paralyzed + "\n" +
				"\thasBeenStolenFrom=" + hasBeenStolenFrom + "\n" +
				"\tmoved=" + moved + "\n" +
				"\tactTile=" + printObject(actTile) + "\n" +
				"\tequipments=" + printArray(equipments) + "\n" +
				"\tcreatedViruses=" + printArray(createdViruses) + "\n" +
				"\tcreatedVaccines=" + printArray(createdVaccines) + "\n" +
				"\tvaccinedBy=" + printArray(vaccinedBy) + "\n" +
				"\tinfectedBy=" + printObject(infectedBy) + "\n" +
				"\tlearnedGeneticCodes=" + printArray(learnedGeneticCodes) + "\n" +
				"}";
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
}
