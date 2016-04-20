package com.mygdx.game;

// ASSESSMENT 3 UPDATE - Package Update (7, 10)
import com.mygdx.game.assets.Assets;
import com.mygdx.game.battle.BattleParameters;
import com.mygdx.game.battle.BattleScreen;
import com.mygdx.game.managers.ItemManager;
import com.mygdx.game.managers.ObjectiveManager;
import com.mygdx.game.managers.PartyManager;
import com.mygdx.game.managers.SkillManager;
import com.mygdx.game.screens.EndScreen;
import com.mygdx.game.screens.StartScreen;
import com.mygdx.game.screens.WinScreen;
import com.mygdx.game.screens.WorldScreen;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is used to switch between the StartScreen, WorldScreen and BattleScreen.
 * It also stores the instances for the friendly party and all items and skills.
 * More information can be found at http://www.teampochard.co.uk/game-releases/
 */
public class Game extends com.badlogic.gdx.Game {

	public static PartyManager party;
	public static PartyManager enemies;
	public static ItemManager items;
	public static SkillManager skills;
	private static JsonLoader jsonLoader = new JsonLoader();

	//ASSESSMENT 3 change (11)
	public static int pointsScore=0;
	//ASSESSMENT 4 CHANGE (S1)
	public static Location currentLocation = Location.SOMEWHERE;
	//ASSESSMENT 4 CHANGE END
	public ObjectiveManager objectiveManager = new ObjectiveManager(this);
	//END ASSESSMENT 3 change
	public static float masterVolume = 0.1f;

	
	private WorldScreen worldScreen;
	private BattleScreen battleScreen;
	// ASSESSMENT 3 change (10)
	private EndScreen gameOverScreen;
	private WinScreen gameWinScreen;
	// END ASSESSMENT 3 change

	public boolean wonBattle;
	public boolean created;


	@Override
	public void create() {
		loadFiles();
		Assets.load();
		wonBattle = false;
		setScreen(new StartScreen(this));
		this.created = true;
	}

	/**
	 * Loads json files for the party, items and skills.
	 */
	private void loadFiles(){
		try {
			skills = jsonLoader.parseSkillManager("skills.json");
			items = jsonLoader.parseItemManager("items.json");
			party = jsonLoader.parsePartyManager("party.json");
			enemies = jsonLoader.parsePartyManager("enemies.json");
		}
		catch (FileNotFoundException ex) {
			// Do something with 'ex'
		} catch (IOException ex2) {
			// Do something with 'ex2'
		}
	}

	/**
	 * Called when a battle has ended.
	 * @param won True if the player won the battle.
	 */
	public void returnToWorld(boolean won){
		wonBattle = won;
		setScreen(worldScreen);
	}

	/**
	 * Disposes of assets when game is no longer used.
	 */
	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	/**
	 * Creates a new battle and sets the battleScreen as the current screen.
	 * @param battleParams
	 */
	public void newBattle(BattleParameters battleParams) {
		battleScreen = new BattleScreen(this, battleParams);
		setScreen(battleScreen);
	}

	/**
	 * Used when switching to the worldScreen from the startScreen.
	 */
	public void newWorldScreen() {
		worldScreen = new WorldScreen(this);
		setScreen(worldScreen);
	}
	
	/**
	 * Assessment 3 Change (10)
	 * NEW METHOD: Used when switching from the current screen to the GameOverScreen
	 */
	public void newGameOverScreen(){
		gameOverScreen = new EndScreen(this);
		setScreen(gameOverScreen);
		
	}
	
	/**
	 * Asessement 3 Change (10)
	 * NEW METHOD: Used when switching from current screen to Game Win Screen
	 */
	public void newWinScreen(){
		gameWinScreen = new WinScreen(this);
		setScreen(gameWinScreen);
		
	}
	//ASSESSMENT 3 change (MULX)
	public WorldScreen getWorldScreen(){
		return worldScreen;
	}
	//END ASSESSMENT 3 change
}
