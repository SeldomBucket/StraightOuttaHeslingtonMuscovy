package com.mygdx.game.characters;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Agent;
import com.mygdx.game.CurrentEquipment;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.Statistics;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.assets.Assets;
import com.mygdx.game.battle.BattleParameters;

/**
 * This class represents the vampire boss of the game.
 * ASSESSMENT 3 addition (17)
 */
public class EvilNPC extends NPC {

    private String[] messages;

    public EvilNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "I AM STRONGER EVEN THAN YOU";
        messages[1] = "NOTHING CAN STOP ME";
        messages[2] = "MY CODEBASE IS FLAWLESS";
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
        uiManager.addNotification("'ERROR: UNEXPECTED DEFEAT ON LINE 93'");
        uiManager.addNotification("You defeated the RoboDuck MK II! You got 150 points!");
        //ASSESSMENT 3 change (17)
        gameWorld.game.objectiveManager.completeObjective("RoboDuck2");
        //END ASSESSMENT 3 changes
        BattleParameters params = new BattleParameters(4);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("RoboDuck MK II", Agent.AgentType.ENEMY,new Statistics(300,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1, false);
        enemyDuck.addSkill(18);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);

    }
}
