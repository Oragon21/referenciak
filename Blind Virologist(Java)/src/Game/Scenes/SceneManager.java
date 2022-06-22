package Game.Scenes;

import java.util.ArrayList;

public class SceneManager {

    private static SceneManager instance;
    private SceneType currentScene;
    private ArrayList<Scene> scenes;

    public enum SceneType{
        MENU,
        GAME,
        WIN,
    }

    public SceneManager() {
        scenes = new ArrayList<>();
        scenes.add(new Menu());
        scenes.add(new Game());
        scenes.add(new WinScene());
    }

    public SceneType getCurrentScene() {
        return currentScene;
    }

    public static SceneManager getInstance() {
        if(instance == null) instance = new SceneManager();
        return instance;
    }

    public void changeScene(SceneType scene){
        currentScene = scene;
        scenes.get(scene.ordinal()).init();
    }
}
