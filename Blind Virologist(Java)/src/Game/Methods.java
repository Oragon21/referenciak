package Game;

import Game.Agents.Agent;
import Game.Agents.GeneticCodes.GeneticCode;
import Game.Agents.Vaccine.Vaccine;
import Game.Agents.Virus.Virus;
import Game.Equipment.Equipment;
import Game.MapHandling.Map;
import Game.MapHandling.TileHandling.Tile;
import Game.TopEntities.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A játék meghívható/tesztelendő megódusait tároló osztály, amiből
 * a metódusok név szerint lekérdezhetőek.
 */
public class Methods {
	private HashMap<CombinedKey<String, Class<?>>, Method> methodMap;

	/**
	 * Konstruktor, amiben feltöltjük automatikusan a listát
	 */
	public Methods() {
		methodMap = new HashMap<>();

		/*Player Methods*/
		addMethod("Game.TopEntities.Player", "move", Tile.class);
		addMethod("Game.TopEntities.Player", "getInventoryAm");
		addMethod("Game.TopEntities.Player", "setInventoryAm", int.class);
		addMethod("Game.TopEntities.Player", "getInventoryNuc");
		addMethod("Game.TopEntities.Player", "setInventoryNuc", int.class);
		addMethod("Game.TopEntities.Player", "getActTile");
		addMethod("Game.TopEntities.Player", "setActTile", Tile.class);
		addMethod("Game.TopEntities.Player", "stealEquipmentFromPlayer", Player.class, Equipment.class);
		addMethod("Game.TopEntities.Player", "learn", GeneticCode.class);
		addMethod("Game.TopEntities.Player", "infect", Virus.class);
		addMethod("Game.TopEntities.Player", "disinfect", Vaccine.class);
		addMethod("Game.TopEntities.Player", "makeVirus", GeneticCode.class);
		addMethod("Game.TopEntities.Player", "makeVaccine", GeneticCode.class);
		addMethod("Game.TopEntities.Player", "stealAminoAcid", int.class);
		addMethod("Game.TopEntities.Player", "pickUpAminoAcid", int.class);
		addMethod("Game.TopEntities.Player", "stealNucleotide", int.class);
		addMethod("Game.TopEntities.Player", "pickUpNucleotide", int.class);
		addMethod("Game.TopEntities.Player", "pickUpEquipment", Equipment.class);
		addMethod("Game.TopEntities.Player", "timePassed");
		addMethod("Game.TopEntities.Player", "swingAxe");
		addMethod("Game.TopEntities.Player", "swingAxeAtGrizzly", Player.class);
		addMethod("Game.TopEntities.Player", "die");
		addMethod("Game.TopEntities.Player", "reset");
		addMethod("Game.TopEntities.Player", "getLearnedGeneticCodes");
		addMethod("Game.TopEntities.Player", "infectPlayer", Player.class, Virus.class);
		addMethod("Game.TopEntities.Player", "makeVaccine", GeneticCode.class);
		addMethod("Game.TopEntities.Player", "interactWithTile");
		addMethod("Game.TopEntities.Player", "stealAminoFromPlayer", Player.class);
		addMethod("Game.TopEntities.Player", "stealNucleotideFromPlayer", Player.class);
		addMethod("Game.TopEntities.Player", "addEquipment", Equipment.class);
		addMethod("Game.TopEntities.Player", "addCreatedAgent", Agent.class);
		addMethod("Game.TopEntities.Player", "addVaccine", Vaccine.class);
		addMethod("Game.TopEntities.Player", "removeVaccine", Vaccine.class);
		addMethod("Game.TopEntities.Player", "addGeneticCode", GeneticCode.class);
		addMethod("Game.TopEntities.Player", "removeEquipment", Equipment.class);
		addMethod("Game.TopEntities.Player", "setLearnedGeneticCodes", ArrayList.class);
		addMethod("Game.TopEntities.Player", "setInfectedBy", Virus.class);
		addMethod("Game.TopEntities.Player", "getInfectedBy");
		addMethod("Game.TopEntities.Player", "setParalyzed", boolean.class);
		addMethod("Game.TopEntities.Player", "getMaxInvCap");
		addMethod("Game.TopEntities.Player", "setMaxInvCap", int.class);
		addMethod("Game.TopEntities.Player", "getDodgeChance");
		addMethod("Game.TopEntities.Player", "setDodgeChance", double.class);
		addMethod("Game.TopEntities.Player", "getImmunity");
		addMethod("Game.TopEntities.Player", "setImmunity", boolean.class);
		addMethod("Game.TopEntities.Player", "getEquipments");
		addMethod("Game.TopEntities.Player", "getCreatedAgent");
		addMethod("Game.TopEntities.Player", "getVaccinedBy");

		/*Axe Methods*/
		addMethod("Game.Equipment.Axe", "activate", Player.class);
		addMethod("Game.Equipment.Axe", "deactivate", Player.class);


		/*Bag Methods*/
		addMethod("Game.Equipment.Bag", "activate", Player.class);
		addMethod("Game.Equipment.Bag", "deactivate", Player.class);
		addMethod("Game.Equipment.Bag", "getChangeAmount");
		addMethod("Game.Equipment.Bag", "setChangeAmount", int.class);

		/*Cloak Methods*/
		addMethod("Game.Equipment.Cloak", "activate", Player.class);
		addMethod("Game.Equipment.Cloak", "deactivate", Player.class);
		addMethod("Game.Equipment.Cloak", "getDodgeChance");

		/*Glove Methods*/
		addMethod("Game.Equipment.Glove", "activate", Player.class);
		addMethod("Game.Equipment.Glove", "deactivate", Player.class);
		addMethod("Game.Equipment.Glove", "use");

		/*GeneticCode*/
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostAmVaccine", int.class);

		/*Tile Methods*/
		addMethod("Game.MapHandling.TileHandling.Tile", "addPlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Tile", "removePlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Tile", "getTouchable");
		addMethod("Game.MapHandling.TileHandling.Tile", "interact", Player.class);
		addMethod("Game.MapHandling.TileHandling.Tile", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.TileHandling.Tile", "getNeighbours");
		addMethod("Game.MapHandling.TileHandling.Tile", "destroyResources");

		/*Lab*/
		addMethod("Game.MapHandling.TileHandling.Lab", "interact", Player.class);
		addMethod("Game.MapHandling.TileHandling.Lab", "setGeneticCode", GeneticCode.class);
		addMethod("Game.MapHandling.TileHandling.Lab", "addPlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Lab", "removePlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Lab", "getTouchable");
		addMethod("Game.MapHandling.TileHandling.Lab", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.TileHandling.Lab", "getNeighbours");

		/*GrizzlyInfectedLab*/
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "interact", Player.class);
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "setGeneticCode", GeneticCode.class);
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "addPlayer", Player.class);
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "removePlayer", Player.class);
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "getTouchable");
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.LabTypes.GrizzlyInfectedLab", "getNeighbours");

		/*AminoAcidStorage*/
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "interact", Player.class);
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "destroyResources");
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "addPlayer", Player.class);
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "removePlayer", Player.class);
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "getTouchable");
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "getNeighbours");
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "getAminoAcid");
		addMethod("Game.MapHandling.StorageHandling.AminoAcidStorage", "setAminoAcid", int.class);

		/*NucleotideStorage*/
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "interact", Player.class);
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "destroyResources");
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "addPlayer", Player.class);
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "removePlayer", Player.class);
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "getTouchable");
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "getNeighbours");
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "getNucleotide");
		addMethod("Game.MapHandling.StorageHandling.NucleotideStorage", "setNucleotide", int.class);

		/*Shelter*/
		addMethod("Game.MapHandling.TileHandling.Shelter", "interact", Player.class);
		addMethod("Game.MapHandling.TileHandling.Shelter", "addEquipment", Equipment.class);
		addMethod("Game.MapHandling.TileHandling.Shelter", "addPlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Shelter", "removePlayer", Player.class);
		addMethod("Game.MapHandling.TileHandling.Shelter", "getTouchable");
		addMethod("Game.MapHandling.TileHandling.Shelter", "addNeighbour", Tile.class);
		addMethod("Game.MapHandling.TileHandling.Shelter", "getNeighbours");

		/*Agent*/
		addMethod("Game.Agents.Agent", "activateEffect", Player.class);
		addMethod("Game.Agents.Agent", "activate", Player.class);
		addMethod("Game.Agents.Agent", "deactivate");
		addMethod("Game.Agents.Agent", "Step");
		addMethod("Game.Agents.Agent", "compare", Agent.class);
		addMethod("Game.Agents.Agent", "setActingOn", Player.class);
		addMethod("Game.Agents.Agent", "getActingOn");
		addMethod("Game.Agents.Agent", "getTimeLeftInv");
		addMethod("Game.Agents.Agent", "getTimeLeftOnPlayer");
		addMethod("Game.Agents.Agent", "setTimeLeftInv", int.class);
		addMethod("Game.Agents.Agent", "setTimeLeftOnPlayer", int.class);
		addMethod("Game.Agents.Agent", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Agent", "getGeneticCode");

		/*Virus Methods*/
		addMethod("Game.Agents.Virus.Virus", "activate", Player.class);
		addMethod("Game.Agents.Virus.Virus", "activateEffect", Player.class);
		addMethod("Game.Agents.Virus.Virus", "deactivate");
		addMethod("Game.Agents.Virus.Virus", "setActingOn", Player.class);
		addMethod("Game.Agents.Virus.Virus", "getActingOn");
		addMethod("Game.Agents.Virus.Virus", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Virus.Virus", "getGeneticCode");

		/*ParalyzingVirus Methods*/
		addMethod("Game.Agents.Virus.ParalyzingVirus", "activate", Player.class);
		addMethod("Game.Agents.Virus.ParalyzingVirus", "activateEffect", Player.class);
		addMethod("Game.Agents.Virus.ParalyzingVirus", "deactivate");
		addMethod("Game.Agents.Virus.ParalyzingVirus", "setActingOn", Player.class);
		addMethod("Game.Agents.Virus.ParalyzingVirus", "getActingOn");
		addMethod("Game.Agents.Virus.ParalyzingVirus", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Virus.ParalyzingVirus", "getGeneticCode");

		/*GrizzlyVirus Methods*/
		addMethod("Game.Agents.Virus.GrizzlyVirus", "activate", Player.class);
		addMethod("Game.Agents.Virus.GrizzlyVirus", "activateEffect", Player.class);
		addMethod("Game.Agents.Virus.GrizzlyVirus", "deactivate");
		addMethod("Game.Agents.Virus.GrizzlyVirus", "setActingOn", Player.class);
		addMethod("Game.Agents.Virus.GrizzlyVirus", "getActingOn");
		addMethod("Game.Agents.Virus.GrizzlyVirus", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Virus.GrizzlyVirus", "getGeneticCode");

		/*ChoreaVirus Methods*/
		addMethod("Game.Agents.Virus.ChoreaVirus", "activate", Player.class);
		addMethod("Game.Agents.Virus.ChoreaVirus", "activateEffect", Player.class);
		addMethod("Game.Agents.Virus.ChoreaVirus", "deactivate");
		addMethod("Game.Agents.Virus.ChoreaVirus", "setActingOn", Player.class);
		addMethod("Game.Agents.Virus.ChoreaVirus", "getActingOn");
		addMethod("Game.Agents.Virus.ChoreaVirus", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Virus.ChoreaVirus", "getGeneticCode");

		/*AmnesiaVirus Methods*/
		addMethod("Game.Agents.Virus.AmnesiaVirus", "activate", Player.class);
		addMethod("Game.Agents.Virus.AmnesiaVirus", "activateEffect", Player.class);
		addMethod("Game.Agents.Virus.AmnesiaVirus", "deactivate");
		addMethod("Game.Agents.Virus.AmnesiaVirus", "setActingOn", Player.class);
		addMethod("Game.Agents.Virus.AmnesiaVirus", "getActingOn");
		addMethod("Game.Agents.Virus.AmnesiaVirus", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Virus.AmnesiaVirus", "getGeneticCode");

		/*Vaccine Methods*/
		addMethod("Game.Agents.Vaccine.Vaccine", "activate", Player.class);
		addMethod("Game.Agents.Vaccine.Vaccine", "deactivate");
		addMethod("Game.Agents.Vaccine.Vaccine", "setActingOn", Player.class);
		addMethod("Game.Agents.Vaccine.Vaccine", "getActingOn");
		addMethod("Game.Agents.Vaccine.Vaccine", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Vaccine.Vaccine", "getGeneticCode");

		/*GrizzlyVaccine Methods*/
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "activate", Player.class);
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "deactivate");
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "setActingOn", Player.class);
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "getActingOn");
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Vaccine.GrizzlyVaccine", "getGeneticCode");

		/*ChoreaVaccine Methods*/
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "activate", Player.class);
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "deactivate");
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "setActingOn", Player.class);
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "getActingOn");
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Vaccine.ChoreaVaccine", "getGeneticCode");

		/*AmnesiaVaccine Methods*/
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "activate", Player.class);
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "deactivate");
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "setActingOn", Player.class);
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "getActingOn");
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Vaccine.AmnesiaVaccine", "getGeneticCode");

		/*ParalyzingVaccine Methods*/
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "activate", Player.class);
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "deactivate");
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "setActingOn", Player.class);
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "getActingOn");
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "setGeneticCode", GeneticCode.class);
		addMethod("Game.Agents.Vaccine.ParalyzingVaccine", "getGeneticCode");

		/*GeneticCode Methods*/
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "makeVaccine");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "makeVirus");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.GeneticCode", "setCostAmVaccine", int.class);

		/*AmnesiaGeneticCode Methods*/
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "makeVaccine");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "makeVirus");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.AmnesiaGeneticCode", "setCostAmVaccine", int.class);

		/*ChoreaGeneticCode Methods*/
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "makeVaccine");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "makeVirus");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.ChoreaGeneticCode", "setCostAmVaccine", int.class);

		/*GrizzlyGeneticCode Methods*/
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "makeVaccine");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "makeVirus");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.GrizzlyGeneticCode", "setCostAmVaccine", int.class);

		/*ParalyzingGeneticCode Methods*/
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "makeVaccine");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "makeVirus");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "getCostNucVirus");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "getCostAmVirus");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "getCostNucVaccine");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "getCostAmVaccine");
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "setCostNucVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "setCostAmVirus", int.class);
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "setCostNucVaccine", int.class);
		addMethod("Game.Agents.GeneticCodes.ParalyzingGeneticCode", "setCostAmVaccine", int.class);

		/*Map*/
		addMethod("Game.MapHandling.Map", "checkWinCond", Player.class);
		addMethod("Game.MapHandling.Map", "addPlayer", Player.class);
		addMethod("Game.MapHandling.Map", "addTile", Tile.class);
		addMethod("Game.MapHandling.Map", "Step");
		addMethod("Game.MapHandling.Map", "setWinner", Player.class);
		addMethod("Game.MapHandling.Map", "getWinner");

		/*Game*/
		addMethod("Game.Scenes.Game", "endGame");
		addMethod("Game.Scenes.Game", "Step");
		addMethod("Game.Scenes.Game", "setMap", Map.class);


	}

	/**
	 * Felvesz egy metódust a methodMap-be, a metódus a nevével és azzal az osztállyal amiben van a metódus határozható meg.
	 *
	 * @param className  Az osztály, amely tartalmazza a methodName-et.
	 * @param methodName A metódus, amit a methodMap-ba fel akarunk venni.
	 * @param params     A methodName-metódus paramétereinek osztályai.
	 *                   Fontos, hogy ugyanabban a sorrendben legyenek, mint a valódi metódusnál!
	 */
	public void addMethod(String className, String methodName, Class<?>... params) {
		try {
			Method method;
			Class<?> c = Class.forName(className);
			method = c.getDeclaredMethod(methodName, params);
			methodMap.put(new CombinedKey<>(methodName, c), method);

		} catch (ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kilistázza az éppen tárolt metódusokat.
	 */
	public void listMethods() {
		methodMap.forEach((key, value) -> System.out.println(key + ": " + value.toString()));
	}

	/**
	 * Visszaadja a methodMap-ot.
	 *
	 * @return A metódusokat tároló HashMap-ot.
	 */
	public HashMap<CombinedKey<String, Class<?>>, Method> getMethodMap() {
		return methodMap;
	}

	/**
	 * Előre definiált metódus-HashMap-ot állít be methodmap-nak..
	 *
	 * @param methodMap A metódusokat tároló HashMap-ot.
	 */
	public void setMethodMap(HashMap<CombinedKey<String, Class<?>>, Method> methodMap) {
		this.methodMap = methodMap;
	}
}
