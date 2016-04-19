package com.mygdx.game.characters;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Agent;
import com.mygdx.game.CurrentEquipment;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.Objective;
import com.mygdx.game.Statistics;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.battle.BattleParameters;

/**
 * This class represents the robot boss of the game.
 */
public class RoboNPC extends NPC {

    private String[] messages;

    public RoboNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[2];
        //ASSESSMENT 3 change(11)
        messages[0] = "01010000 01001100 01000101 01000001 01010011 01000101 00100000 01001000 01000101 01001100 01010000 00100000 01001101 01000101 00001101 00001010!!!";
        //END ASSESMENT 3 change
        messages[1] = "Robo duck has challenged you to a battle.";
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        uiManager.createDialogue(messages);
        this.uiManager = uiManager;
    }

    @Override
    public boolean updateInteracting(float delta) {
        return uiManager.updateDialogue(delta);
    }

    @Override
    public void action(GameWorld gameWorld) {
        Assets.sfx_battleStart.play(Game.masterVolume);
        //ASSESSMENT 3 change(11)
        if(gameWorld.game.objectiveManager.gameObjectives.containsKey("RoboDuck")){
        	uiManager.addNotification("You defeated Roboduck! You got 100 points!");
        	uiManager.addNotification("Talk to Bob by TFTV ");
        	gameWorld.game.objectiveManager.addObjective("Bob", new Objective("Talk to Bob by TFTV", 40, "40 Points", false));
        	gameWorld.game.objectiveManager.completeObjective("RoboDuck");
        }
        else{
        	uiManager.addNotification("You needed to talk to Sally! No points for you!");
        }
        level.characters.add(new BobNPC(level, new Vector2(80, 83)));
        //END ASSESSMENT 3 change
        BattleParameters params = new BattleParameters(8);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        //ASSESSMENT 3 change (17)
        Agent enemyDuck = new Agent("Robo Duck", Agent.AgentType.ENEMY,new Statistics(150,300,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1, false);
        //END ASSESSMENT 3 change
        enemyDuck.addSkill(4);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);

    }
}
