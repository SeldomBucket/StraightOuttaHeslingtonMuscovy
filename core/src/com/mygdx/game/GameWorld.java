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

    private int speedCheatState, powerCheatState;

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
        speedCheatState = 0; //S O N I C
        powerCheatState = 0; //up up down down left right left right B A
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
                        Statistics newStats;
                        if(level.player.isPowerCheatActive()){
                            newStats = new Statistics(thisAgentStats.getMaxHP(),thisAgentStats.getMaxMP(),thisAgentStats.getSpeed(),thisAgentStats.getStrength()+2,thisAgentStats.getDexterity()+2,thisAgentStats.getIntelligence()+2,thisAgentStats.getBaseArmourVal(),thisAgentStats.getExperience(),thisAgentStats.getCurrentLevel());
                        }else{
                            newStats = new Statistics(thisAgentStats.getMaxHP(),thisAgentStats.getMaxMP(),thisAgentStats.getSpeed(),thisAgentStats.getStrength(),thisAgentStats.getDexterity(),thisAgentStats.getIntelligence(),thisAgentStats.getBaseArmourVal(),thisAgentStats.getExperience(),thisAgentStats.getCurrentLevel());
                        }
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
                else
                    if (InputHandler.isMJustPressed()){
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
                else
                    if (InputHandler.isGraveJustPressed()){
                        gameState = GameState.CHEAT_ENTRY;
                        level.stopInput = true;
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
                    //ASSESSMENT 4 CHANGE (S1)
                    level.player.deactivatePowerCheat();
                    //ASSESSMENT 4 CHANGE END
                    //ASSESSMENT 3 changes (10)
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
            case CHEAT_ENTRY:
                if (InputHandler.isEnterJustPressed()){
                    if (speedCheatState == 5){
                        level.player.activateSpeedCheat();
                        uiManager.addNotification("SPEED CHEAT ACTIVATED FOR 30s");
                        speedCheatState = 0;
                    }
                    if (powerCheatState == 10){
                        uiManager.addNotification("POWER CHEAT ACTIVATED UNTIL END OF NEXT BATTLE");
                        powerCheatState = 0;
                        level.player.activatePowerCheat();
                        //DO THE POWER CHEAT
                    }
                    gameState = GameState.FREEROAM;
                }else{
                    if(!InputHandler.isOtherJustPressed()){
                        switch (speedCheatState){
                            case 0:
                                if(InputHandler.isDownJustPressed()){
                                    speedCheatState +=1;
                                }
                                break;
                            case 1:
                                if(InputHandler.isOJustPressed()){
                                    speedCheatState +=1;
                                }
                                break;
                            case 2:
                                if(InputHandler.isNJustPressed()){
                                    speedCheatState +=1;
                                }
                                break;
                            case 3:
                                if(InputHandler.isIJustPressed()){
                                    speedCheatState +=1;
                                }
                                break;
                            case 4:
                                if(InputHandler.isCJustPressed()){
                                    speedCheatState +=1;
                                }
                                break;
                        }
                        switch (powerCheatState){
                            case 0:
                                if(InputHandler.isUpArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 1:
                                if(InputHandler.isUpArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 2:
                                if(InputHandler.isDownArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 3:
                                if(InputHandler.isDownArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 4:
                                if(InputHandler.isLeftArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 5:
                                if(InputHandler.isRightArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 6:
                                if(InputHandler.isLeftArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 7:
                                if(InputHandler.isRightArrowJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 8:
                                if(InputHandler.isBJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                            case 9:
                                if(InputHandler.isLeftJustPressed()){
                                    powerCheatState +=1;
                                }
                                break;
                        }
                    }else{
                        speedCheatState = 0;
                        powerCheatState = 0;
                    }
                }
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
