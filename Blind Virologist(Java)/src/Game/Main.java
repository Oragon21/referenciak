package Game;

import Game.Scenes.SceneManager;

import static Game.Scenes.SceneManager.SceneType.MENU;

/**
 * A program kezdő/fő osztálya. Feladata a menü kiírása,
 * a tesztek előkészítése, valamint elindítása.
 */
public class Main{
	
	public static void main(String[] args){
		SceneManager.getInstance().changeScene(MENU);
	}
}