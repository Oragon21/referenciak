package Game;

import Game.Agents.GeneticCodes.AmnesiaGeneticCode;
import Game.Agents.GeneticCodes.ChoreaGeneticCode;
import Game.Agents.GeneticCodes.GrizzlyGeneticCode;
import Game.Agents.GeneticCodes.ParalyzingGeneticCode;
import Game.Agents.Vaccine.AmnesiaVaccine;
import Game.Agents.Vaccine.ChoreaVaccine;
import Game.Agents.Vaccine.GrizzlyVaccine;
import Game.Agents.Vaccine.ParalyzingVaccine;
import Game.Agents.Virus.AmnesiaVirus;
import Game.Agents.Virus.ChoreaVirus;
import Game.Agents.Virus.GrizzlyVirus;
import Game.Agents.Virus.ParalyzingVirus;
import Game.Equipment.Axe;
import Game.Equipment.Bag;
import Game.Equipment.Cloak;
import Game.Equipment.Glove;
import Game.MapHandling.LabTypes.GrizzlyInfectedLab;
import Game.MapHandling.StorageHandling.AminoAcidStorage;
import Game.MapHandling.StorageHandling.NucleotideStorage;
import Game.MapHandling.TileHandling.Lab;
import Game.MapHandling.TileHandling.Shelter;
import Game.Scenes.Game;
import Game.TopEntities.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class PrototypeTest {

    private static HashMap<String, CombinedKey<Object, Class<?>>> objects;
    private static List<String> commands;
    private static Methods methods;
    private static List<String> goals;
    public static boolean random = true;

    /**
     * Program fő metódusa, itt kezdődik a lefutás.
     *
     * @param args
     */
	/*public static void main(String[] args) {
		boolean quit = true;
		Scanner sc = new Scanner(System.in);
		objects = new HashMap<>();
		commands = new ArrayList<>();
		goals = new ArrayList<>();
		methods = new Methods();

		while (!quit) {
			String line = sc.nextLine();
			commands.add(line);

			String[] command = line.split(" ");
			quit = executeCommand(command);
		}
	}*/

    /**
     * A paraméterként kapott feldarabolt szöveget megpróbálja lefuttatni
     * a nyelv specifikációja szerint.
     *
     * @param command a feladarbolt parancs
     * @return végrahajtás vége-e
     */
    private static boolean executeCommand(String[] command) {
        command[0] = command[0].toUpperCase();
        boolean shouldBeSaved = true;  // to know whether to save the last command given

        switch (command[0]) {
            case "CREATE": {
                if (command.length == 3) {
                    shouldBeSaved = createObject(command[1], command[2]);
                } else {
                    System.out.println("CREATE always requires 2 arguments!");
                    shouldBeSaved = false;
                }
                break;
            }

            case "FUNC": {
                if (command.length > 2) {
                    String[] params = Arrays.copyOfRange(command, 3, command.length);
                    shouldBeSaved = executeFunction(command[1], command[2], params);
                } else {
                    System.out.println("FUNC requires at least 2 arguments!");
                    shouldBeSaved = false;
                }
                break;
            }

            // Goal-ok ellenőrzése
            case "START_TEST":
                startTest();
                break;

            case "QUIT":
                return true;

            // commandokat és a fenti tárolókat törli
            case "RESET":
                resetTest();
                break;

            // fájlból minden sort beolvasni
            // ötlet: switch-t külön függvénybe, és rekurzívan hívni
            case "LOAD": {
                if (command.length != 2) {
                    System.out.println("Incorrect number of arguments!");
                } else {
                    try {
                        if (command[1].equalsIgnoreCase("ALL")) {
                            File folder = new File("./src/Tests/");
                            File[] files = folder.listFiles();
                            for (File f : files) {
                                Scanner fileScanner = new Scanner(f);
                                while (fileScanner.hasNext()) {
                                    executeCommand(fileScanner.nextLine().split(" "));
                                }
                                resetTest();
                            }
                        } else {
                            String path = "./src/Tests/" + command[1];
                            File file = new File(path);
                            Scanner fileScanner = new Scanner(file);
                            while (fileScanner.hasNext()) {
                                executeCommand(fileScanner.nextLine().split(" "));
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

            // commandokat kiírni fájlba
            case "SAVE": {
                if (command.length != 2) {
                    System.out.println("Incorrect number of arguments!");
                } else {
                    try {
                        String path = "./src/Tests/" + command[1];
                        File file = new File(path);
                        if (file.createNewFile()) {
                            FileWriter writer = new FileWriter(file);
                            for (int i = 0; i < commands.size() - 1; i++) {
                                writer.write(commands.get(i) + "\n");
                            }
                            writer.close();
                        } else {
                            System.out.println("File with that name already exists");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            // paraméterül kapott osztály összes attribútumát kiiratni abc sorrendben
            // ha nincs paramétere akkor minden létező osztályra mindent kiírni
            // ötlet: minden osztálynak vagy toString() vagy saját ugyan olyan nevű metódus
            case "INFO": {
                if (command.length == 1) listAll();
                else listSpecifiedObject(command[1]);
                break;
            }

            case "RANDOM": {
                if (command[1].equals("0")) {
                    random = false; //randomitás kikapcsolva = mindig bekövetkezik
                } else if (command[1].equals("1")) {
                    random = true; //randomitás bekapcsolva = 83.7%
                } else System.out.println("Incorrect param");
                break;
            }

            // Minden makró csak a helyes FUNC-ot hívja meg
            case "MOVE": {
                shouldBeSaved = executeFunction(command[1], "move", command[2]);
                break;
            }

            case "INFECT": {
                shouldBeSaved = executeFunction(command[1], "infectPlayer", command[2], command[3]);
                break;
            }

            case "DISINFECT": {
                shouldBeSaved = executeFunction(command[1], "disinfect", command[2]);
                break;
            }

            case "STEAL_MATERIAL": {
                if (command[3].equals("amino"))
                    shouldBeSaved = executeFunction(command[1], "stealAminoFromPlayer", command[2]);
                else shouldBeSaved = executeFunction(command[1], "stealNucleotideFromPlayer", command[2]);
                break;
            }

            case "STEAL_EQUIPMENT": {
                shouldBeSaved = executeFunction(command[1], "stealEquipmentFromPlayer", command[2], command[3]);
                break;
            }

            case "INTERACT": {
                //!!!!!! INTERACT MAKRÓNÁL NEM IS KELL A TILE, HANEM AZZAL INTERACTOL AMIN ÁLL NYILVAN !!!!!!!!!!
                shouldBeSaved = executeFunction(command[1], "interactWithTile");
                break;
            }

            case "MAKE_VACCINE": {
                shouldBeSaved = executeFunction(command[1], "makeVaccine", command[2]);
                break;
            }

            case "MAKE_VIRUS": {
                shouldBeSaved = executeFunction(command[1], "makeVirus", command[2]);
                break;
            }

            case "SWING_AXE": {
                shouldBeSaved = executeFunction(command[1], "swingAxeAtGrizzly", command[2]);
                break;
            }

            // Listában tárolni, startTest hatására execute-oljuk őket
            case "GOAL":
                if (command.length == 4 || command.length == 5) {   // megfelelő hosszú
                    if (!objects.containsKey(command[1])) {         // van ilyen objektum
                        System.out.println("No such object exists!");
                        shouldBeSaved = false;
                    } else {
                        CombinedKey<String, Class<?>> searcherKey = new CombinedKey<>(command[2], objects.get(command[1]).key2);
                        Method method = findMethodWithKey(searcherKey);
                        if (method == null || method.getReturnType() == void.class) {   //van ilyen metódus és van visszatérési értéke
                            System.out.println("Invalid method!");
                            shouldBeSaved = false;
                        } else if (!command[3].toUpperCase().matches("EQ|NOT_EQ|CONTAINS|NOT_CONTAINS|ISNULL|NOTNULL|NOT_EMPTY|EMPTY")) { // helyes összehasonlítási operátor
                            System.out.println("Invalid comparing operator!");
                            shouldBeSaved = false;
                        } else {
                            StringBuilder s = new StringBuilder(command[1]);
                            for (int i = 2; i < command.length; i++) {
                                s.append(" ").append(command[i]);
                            }
                            goals.add(s.toString());
                        }
                    }
                } else {
                    System.out.println("Incorrect number of arguments!");
                    shouldBeSaved = false;
                }
                break;

            default:
                System.out.println("Unknown command");
                shouldBeSaved = false;
                break;
        }
        if (!shouldBeSaved) commands.remove(commands.size() - 1);
        return false;
    }

    /**
     * A START_TEST parancs hatására lefut ez a metódus, ami ellenőrzi a
     * megadott GOAL-okat, hogy helyesen lefutnak-e
     */
    private static void startTest() {
        boolean success = true;
        for (String goal : goals) {
            String[] splitGoal = goal.split(" ");
            String objName = splitGoal[0];
            String funcName = splitGoal[1];
            String comparingOperator = splitGoal[2];
            String param = "";
            if (splitGoal.length == 4) {
                param = splitGoal[3];
            }

            try {
                CombinedKey<String, Class<?>> searcherKey = new CombinedKey<>(funcName, objects.get(objName).key2);
                Method method = findMethodWithKey(searcherKey);
                switch (comparingOperator) {
                    case "EQ": {
                        Object ret = method.invoke(objects.get(objName).key1);
                        if (objects.containsKey(param)) {
                            if (!ret.equals(objects.get(param).key1)) {
                                success = false;
                            }
                        } else if (!param.equals("" + ret)) {
                            success = false;
                        }
                        break;
                    }
                    case "NOT_EQ": {
                        Object ret = method.invoke(objects.get(objName).key1);
                        if (objects.containsKey(param)) {
                            if (ret.equals(objects.get(param).key1)) {
                                success = false;
                            }
                        } else if (param.equals("" + ret)) {
                            success = false;
                        }
                        break;
                    }
                    case "CONTAINS": {
                        ArrayList<?> ret = (ArrayList<?>) method.invoke(objects.get(objName).key1);
                        if (!ret.contains(objects.get(param).key1)) {
                            success = false;
                        }
                        break;
                    }
                    case "NOT_CONTAINS": {
                        ArrayList<?> ret = (ArrayList<?>) method.invoke(objects.get(objName).key1);
                        if (ret.contains(objects.get(param).key1)) {
                            success = false;
                        }
                        break;
                    }
                    case "ISNULL": {
                        Object ret = method.invoke(objects.get(objName).key1);
                        if (ret != null) {
                            success = false;
                        }
                        break;
                    }
                    case "NOTNULL": {
                        Object ret = method.invoke(objects.get(objName).key1);
                        if (ret == null) {
                            success = false;
                        }
                        break;
                    }
                    case "NOT_EMPTY": {
                        ArrayList<?> ret = (ArrayList<?>) method.invoke(objects.get(objName).key1);
                        if (ret.isEmpty()) {
                            success = false;
                        }
                        break;
                    }
                    case "EMPTY": {
                        ArrayList<?> ret = (ArrayList<?>) method.invoke(objects.get(objName).key1);
                        if (!ret.isEmpty()) {
                            success = false;
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (success) {
            System.out.println("Successful test");
        } else {
            System.out.println("Unsuccessful test");
        }
    }

    /**
     * Az összes objektum kilistázása.
     */
    private static void listAll() {
        try {
            objects.forEach((key, value) -> System.out.println(key + " : " + value.key1.toString() + "\n"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Egy specifikus objektum kilistázása.
     *
     * @param param Az objektum felhasználó által megadott neve,
     *              amire ki akarjuk iratni az információt
     */
    private static void listSpecifiedObject(String param) {
        try {
            System.out.println(param + " : " + objects.get(param).key1.toString());
        } catch (Exception e) {
            System.out.println("No such object exists!");
            e.printStackTrace();
        }
    }

    /**
     * Tömb kiiratása. Visszaadja a szöveget, ami
     * a tömb kiírásához kell
     *
     * @param list a lista
     * @return a generált szöveg
     */
    public static String printArray(ArrayList<?> list) {
        return "[" + list.size() + "]";
    }

    public static String printObject(Object o) {
        if (o == null) return "null";
        String asd = o.getClass().toString();
        String asd2 = asd.substring(6);
        String asd3 = asd2.replace(".", " ");
        String[] asd4 = asd3.split(" ");
        String asd5 = asd4[asd4.length - 1];
        return asd5;
    }

    private static void resetTest() {
        goals.clear();
        objects.clear();
        commands.clear();
    }

    /**
     * Végrehajt egy függvényt egy specifikált objektumon.
     * Paraméterezhető a meghívott függvény kívánt paramétereivel.
     *
     * @param objName  Az objektum neve, mely alapján a függvény kikeresi az objects-ből azt.
     * @param funcName A függvény neve, melyet a függvény meghív az objName-en.
     * @param params   Opcionális paraméterek.
     * @return was the command correct
     */
    private static boolean executeFunction(String objName, String funcName, String... params) {
        try {
            CombinedKey<String, Class<?>> searcherKey = new CombinedKey<>(funcName, objects.get(objName).key2);

            //megkeressük a megfelelő metódust, amit a neve és az osztálya határoz meg (osztálya = amelyik osztályban van definiálva a metódus)
            Method method = findMethodWithKey(searcherKey);

            if (method != null) {
                if (params.length == 0) { // paraméter nélküli
                    method.invoke(objects.get(objName).key1);
                } else if (params.length == 1) { // egy paraméter
                    // a paraméter vagy objektum vagy int típusú
                    // ha benne van az objects-ben akkor egy object a paraméter
                    if (objects.containsKey(params[0])) {
                        method.invoke(objects.get(objName).key1, objects.get(params[0]).key1); // itt meghivjuk a methodot
                    } else if (params[0].equalsIgnoreCase("true") || params[0].equalsIgnoreCase("false")) {
                        method.invoke(objects.get(objName).key1, Boolean.parseBoolean(params[0].toLowerCase()));
                    } else {
                        // egyebkent int a paraméter tipusa
                        method.invoke(objects.get(objName).key1, Integer.parseInt(params[0]));
                    }
                } else if (params.length == 2) { // ketto paraméter
                    if (objects.containsKey(params[0]) && objects.containsKey(params[1])) { // ha mindkét param object típusú
                        method.invoke(objects.get(objName).key1, objects.get(params[0]).key1, objects.get(params[1]).key1);
                    } else if (objects.containsKey(params[0]) && !objects.containsKey(params[1])) { //elso object, masodik int
                        method.invoke(objects.get(objName).key1, objects.get(params[0]).key1, Integer.parseInt(params[1]));
                    } else if (!objects.containsKey(params[0]) && objects.containsKey(params[1])) { //elso int, masodik object
                        method.invoke(objects.get(objName).key1, Integer.parseInt(params[0]), objects.get(params[1]).key1);
                    } else { //mindkét paraméter int
                        method.invoke(objects.get(objName).key1, Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                    }
                }
                return true;
            }
            System.out.println("Invalid method!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Method findMethodWithKey(CombinedKey<String, Class<?>> searcherKey) {
        for (java.util.Map.Entry<CombinedKey<String, Class<?>>, Method> entry : methods.getMethodMap().entrySet()) {
            CombinedKey<String, Class<?>> key = entry.getKey();
            if (key.equals(searcherKey)) {
                //System.out.println("megkeresett method neve: " + methods.getMethodMap().get(key).getName());
                return methods.getMethodMap().get(key);
            }
        }
        return null;
    }

    /**
     * Létrehoz és visszaad egy "name" nevű, "obj" típusú objektumot.
     * Az objektumot hozzáfűzi a kezelt objektumokhoz,
     * amennyiben nem létezik még "name" kulcsú bejegyzés.
     *
     * @param obj  Megadja a típust.
     * @param name Beállítja a nevet.
     * @return was the command correct
     */
    private static boolean createObject(String obj, String name) {
        if (objects.containsKey(name)) {
            System.out.println("Object with that name already exists");
            return false;
        }

        switch (obj) {

            case "Player": {
                objects.put(name, new CombinedKey<>(new Player(), Player.class));
                break;
            }

            /*case "Tile": {
                objects.put(name, new CombinedKey<>(new Tile(), Tile.class));
                break;
            }*/
            case "Lab": {
                objects.put(name, new CombinedKey<>(new Lab(), Lab.class));
                break;
            }
            case "Shelter": {
                objects.put(name, new CombinedKey<>(new Shelter(), Shelter.class));
                break;
            }
            case "GrizzlyInfectedLab": {
                objects.put(name, new CombinedKey<>(new GrizzlyInfectedLab(), GrizzlyInfectedLab.class));
                break;
            }
            case "AminoAcidStorage": {
                objects.put(name, new CombinedKey<>(new AminoAcidStorage(), AminoAcidStorage.class));
                break;
            }
            case "NucleotideStorage": {
                objects.put(name, new CombinedKey<>(new NucleotideStorage(), NucleotideStorage.class));
                break;
            }

            //Equipments
            case "Axe": {
                objects.put(name, new CombinedKey<>(new Axe(), Axe.class));
                break;
            }
            case "Bag": {
                objects.put(name, new CombinedKey<>(new Bag(), Bag.class));
                break;
            }
            case "Cloak": {
                objects.put(name, new CombinedKey<>(new Cloak(), Cloak.class));
                break;
            }
            case "Glove": {
                objects.put(name, new CombinedKey<>(new Glove(), Glove.class));
                break;
            }
            // Vaccines
            case "AmnesiaVaccine": {
                objects.put(name, new CombinedKey<>(new AmnesiaVaccine(), AmnesiaVaccine.class));
                break;
            }
            case "ChoreaVaccine": {
                objects.put(name, new CombinedKey<>(new ChoreaVaccine(), ChoreaVaccine.class));
                break;
            }
            case "GrizzlyVaccine": {
                objects.put(name, new CombinedKey<>(new GrizzlyVaccine(), GrizzlyVaccine.class));
                break;
            }
            case "ParalyzingVaccine": {
                objects.put(name, new CombinedKey<>(new ParalyzingVaccine(), ParalyzingVaccine.class));
                break;
            }
            // Viruses
            case "AmnesiaVirus": {
                objects.put(name, new CombinedKey<>(new AmnesiaVirus(), AmnesiaVirus.class));
                break;
            }
            case "ChoreaVirus": {
                objects.put(name, new CombinedKey<>(new ChoreaVirus(), ChoreaVirus.class));
                break;
            }
            case "GrizzlyVirus": {
                objects.put(name, new CombinedKey<>(new GrizzlyVirus(), GrizzlyVirus.class));
                break;
            }
            case "ParalyzingVirus": {
                objects.put(name, new CombinedKey<>(new ParalyzingVirus(), ParalyzingVirus.class));
                break;
            }
            // GeneticCodes
            case "AmnesiaGeneticCode": {
                objects.put(name, new CombinedKey<>(new AmnesiaGeneticCode(), AmnesiaGeneticCode.class));
                break;
            }
            case "ChoreaGeneticCode": {
                objects.put(name, new CombinedKey<>(new ChoreaGeneticCode(), ChoreaGeneticCode.class));
                break;
            }
            case "GrizzlyGeneticCode": {
                objects.put(name, new CombinedKey<>(new GrizzlyGeneticCode(), GrizzlyGeneticCode.class));
                break;
            }
            case "ParalyzingGeneticCode": {
                objects.put(name, new CombinedKey<>(new ParalyzingGeneticCode(), ParalyzingGeneticCode.class));
                break;

            }
            case "Game": {
                objects.put(name, new CombinedKey<>(new Game(), Game.class));
                break;
            }
            case "Map": {
                //objects.put(name, new CombinedKey<>(new Game.MapHandling.Map(), Game.MapHandling.Map.class));
                break;
            }
            default: {
                System.out.println("No such class exists.");
                return false;
            }
        }
        return true;
    }
}
