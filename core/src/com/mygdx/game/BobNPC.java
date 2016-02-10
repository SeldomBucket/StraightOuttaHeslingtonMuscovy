package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;

/**
 * This class represents the first npc of the game.
 */
public class BobNPC extends NPC {

    private boolean doneInteraction;
    private String[] messages;

    public BobNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Thank you for defeating RoboDuck!";
        messages[1] = "A new and improved RoboDuck has now appeared";
        messages[2] = "The last time I saw him was by the Glasshouse in Langwith.";
        doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if (!doneInteraction) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }
    }

    @Override
    public boolean updateInteracting(float delta) {
        if (doneInteraction) {
            return false;
        }
        return uiManager.updateDialogue(delta);
    }

    @Override
    public void action(GameWorld gameWorld) {
        if (!doneInteraction) {
            uiManager.addNotification("You talked to Bob! You got 40 points!");
            level.characters.add(new EvilNPC(level, new Vector2(152, 123)));
            gameWorld.game.objectiveManager.completeObjective("Bob");
            gameWorld.game.objectiveManager.gameObjectives.remove("Bob");
            gameWorld.game.objectiveManager.addObjective("RoboDuck2", new Objective("Defeat RoboDuck Mk2 beind the Glasshouse by Langwith", 100, "100 Points", false));
//            Game.pointsScore += 40;
//            Game.objectivesComplete += 1;
//            Game.objectives[0] = true;
            doneInteraction = true;
//            level.characters.remove(this);
        }
    }
}
