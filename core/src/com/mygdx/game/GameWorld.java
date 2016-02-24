package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// Assessment 3 Change (7)
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UICurrentLocation;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.UI.UIScore;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.battle.BattleParameters;
import com.mygdx.game.characters.NPC;
import com.mygdx.game.characters.SallyNPC;
import com.mygdx.game.input.InputHandler;

/**
 * This class contains the high level logic for the game world and contains the level and UI objects.
 */
public class GameWorld {

    public Game game;
    public Level level;
    public UIManager uiManager;
    public GameState gameState;

    private NPC interactingNPC;
    private BattleParameters battleParams;
    private int battleChance;

    /**
     * Constructor for the GameWorld generates a new level and adds the characters to be used in the game.
     * The initial state for the game is FREEROAM.
     */
    public GameWorld(Game game) {
        this.game = game;
        gameState = GameState.FREEROAM;
        level = new Level(this);
        // Assessment 3 change (12,14)
        uiManager = new UIManager(Game.party, game.objectiveManager, level);
        // END ASSESSMENT 3
        battleChance = 2000;
        level.characters.add(new SallyNPC(level, new Vector2(108, 91)));
        // Assessment 3 deletion (11) - RoboDuck now created by completing obj
        uiManager.addUIComponent(new UIScore());
        //ASSESSMENT 3 change (13)
        uiManager.addUIComponent(new UICurrentLocation());
        //END ASSESSMENT 3
        battleParams = new BattleParameters(0);
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck.addSkill(0);
        Agent enemyDuck2 = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck2.addSkill(0);
        battleParams.addEnemy(enemyDuck);
        battleParams.addEnemy(enemyDuck2);
    }

    /**
     * Called once per frame to update GameWorld logic.
     * This looks at the current game's current state and acts accordingly.
     * @param delta The time since the last frame was rendered.
     */
    public void update(float delta) {
        InputHandler.update();
        level.update(delta);
        uiManager.updateNotification(delta);
        switch (gameState) {
            case BATTLE_DIALOGUE:
                if (!uiManager.updateDialogue(delta)) {
                    level.stopInput = false;
                    gameState = GameState.BATTLE;
                    game.newBattle(battleParams);
                }
                break;
            case FREEROAM:
                level.stopInput = false;
                Random random = new Random();
                if (level.player.getState() == Character.CharacterState.TRANSITIONING && random.nextInt(battleChance--) == 1){
                    uiManager.createDialogue(new String[] {"You have been stopped by a group of... somethings!"});
                    level.stopInput = true;
                    battleChance = 1000;
                    // ASSESSMENT 3 Change (13)
                    BattleParameters params = new BattleParameters(getBackgroundFromLocation());
                    // END ASSESSMENT 3
                    
                    //Get a number of agents from the list of enemies, make new agent instances with their information and setup the next battle
                    for(int i=0;i<random.nextInt(3)+1;i++){
                        Agent thisAgent = Game.enemies.getMember(random.nextInt(Game.enemies.size()));
                        Statistics thisAgentStats = thisAgent.getStats();
                        Statistics newStats = new Statistics(thisAgentStats.getMaxHP(),thisAgentStats.getMaxMP(),thisAgentStats.getSpeed(),thisAgentStats.getStrength(),thisAgentStats.getDexterity(),thisAgentStats.getIntelligence(),thisAgentStats.getBaseArmourVal(),thisAgentStats.getExperience(),thisAgentStats.getCurrentLevel());
                        params.addEnemy(new Agent(thisAgent.getName(), thisAgent.getType(), newStats, thisAgent.getSkills(), thisAgent.getCurrentEquipment(), thisAgent.getTexture()));
                    }

                    battleParams = params;
                    Assets.worldMusic.stop();//Stop the worldMusic
                    Assets.sfx_battleStart.play(Game.masterVolume);
                    gameState = GameState.BATTLE_DIALOGUE;
                    level.stopInput = true;
                } else
                if (InputHandler.isActJustPressed()) {
                    interactingNPC = level.player.interactingNPC;
                    level.stopInput = true;
                    if (interactingNPC != null) {
                        interactingNPC.initializeInteraction(delta, uiManager);
                        gameState = GameState.INTERACTION;
                    } else {
                        uiManager.openPartyMenu();
                        gameState = GameState.PARTY_MENU;
                    }
                }
                //ASSESSMENT 3 change (12)
                else
                    if (InputHandler.isOJustPressed()) {
                        interactingNPC = level.player.interactingNPC;
                        level.stopInput = true;
                        if (interactingNPC != null) {
                            interactingNPC.initializeInteraction(delta, uiManager);
                            gameState = GameState.INTERACTION;
                        } else {
                            uiManager.openObjectiveMenu();
                            gameState = GameState.OBJECTIVE_MENU;
                        }
                    }
                    //END ASSESSMENT 3 change
                // ASSESSMENT 3 change (14)
                    else if (InputHandler.isMJustPressed()){
                    	interactingNPC = level.player.interactingNPC;
                        level.stopInput = true;
                        if (interactingNPC != null) {
                            interactingNPC.initializeInteraction(delta, uiManager);
                            gameState = GameState.INTERACTION;
                        } else {
                            uiManager.openMap();
                            gameState = GameState.MAP;
                        }
                      //END ASSESSMENT 3 change
                    }
                break;

            case PARTY_MENU:
                if (!uiManager.updatePartyMenu(delta)){
                    gameState = GameState.FREEROAM;
                }
                break;
            //ASSESSMENT 3 change (12)
            case OBJECTIVE_MENU:
                if (!uiManager.updateObjectiveMenu(delta)){
                    gameState = GameState.FREEROAM;
                }
                break;
            //END ASSESSMENT 3 change
             // ASSESSMENT 3 change (14)
            case MAP:
            	 if (!uiManager.updateMap(delta)){
                     gameState = GameState.FREEROAM;
                 }
                 break;
               //END ASSESSMENT 3 change
            case INTERACTION:
                if (!interactingNPC.updateInteracting(delta)) {
                    interactingNPC.action(this);
                    gameState = GameState.FREEROAM;
                }
                break;

            case BATTLE:
                if (game.wonBattle) {
                    uiManager.addNotification("You won the battle!");
                    //ASSESSMENT 3 chnages (10)
//                    game.objectiveManager.battleWon(uiManager);
                } else {
//                    Game.party.setHealths(1);
//                    level.player.setCurrentTile(new Vector2(118, 94));
                    uiManager.addNotification("You lost the battle!");
                    game.newGameOverScreen();
                    //END ASSESSMENT 3 changes
                }
                gameState = GameState.FREEROAM;
                break;
        }
    }

    /**
     * changes the game state to BATTLE and loads a new battle in the Game object.
     * @param battleParams The parameters used to create a battle.
     */
    public void setBattle(BattleParameters battleParams){
        gameState = GameState.BATTLE;
        this.battleParams = battleParams;
        game.newBattle(battleParams);
    }

    /**
     * Function gets the index of the location image from the current location string
     * ASSESSMENT 3 change (16)
     */
    private int getBackgroundFromLocation(){
    	switch (Game.currentLocation){
            case CATALYST:
                return 8;
            case CS:
                return 0;
            case TFTV:
                return 7;
            case LMB:
                return 1;
            case GOODRICKE:
                return 9;
            case RCH:
                return 2;
            case LAKE:
                return 10;
            case LANGWITH:
                return 4;
            case CONSTANTINE:
                return 5;
    	}
    	return 6;
    }

}
