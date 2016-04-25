package com.mygdx.game;
//ASSESSMENT 3 removed maps import (change 6)
import com.badlogic.gdx.math.Vector2;
//ASSESSMENT 3 updated packages (change 7)
import com.mygdx.game.characters.NPC;
import com.mygdx.game.input.InputHandler;

/**
 * This class is a character that is controlled by the user.
 */
public class Player extends Character {

    private Direction tempDirection;

    public NPC interactingNPC;

    private float runningTime;

    //ASSESSMENT 4 CHANGE (S2)
    protected boolean speedCheatActive = false;
    private float speedCheatTimer = 0;

    protected boolean powerCheatActive = false;
    //ASSESSMENT 4 END LINE

    public Player(Level level, Vector2 currentTile) {
        super(level, currentTile);
        tempDirection = getDirection();
    }

    /**
     * Updates the movement of the character based on user input.
     * @param delta The time since the last frame was rendered.
     */
    protected void updateStationary(float delta) {
        if (InputHandler.isUpPressed()) {
            updateMovement(Direction.UP);
        } else if (InputHandler.isDownPressed()) {
            updateMovement(Direction.DOWN);
        } else if (InputHandler.isLeftPressed()) {
            updateMovement(Direction.LEFT);
        } else if (InputHandler.isRightPressed()) {
            updateMovement(Direction.RIGHT);
        }
    }

    /**
     * Similar to NPC but requires user input to determine direction.
     * @param delta The time since the last frame was rendered.
     */
    protected void updateTransitioning(float delta) {
        runningTime += delta;
        //ASSESSMENT 3 change (15)
        float t = runningTime / transitionSpeed;
        //END ASSESSMENT 3 change
        getAbsPos().set(oldPos.x + (targetPos.x - oldPos.x) * t, oldPos.y + (targetPos.y - oldPos.y) * t);
        if (t >= 1) {
            setState(CharacterState.STATIONARY);
            runningTime = 0;
            getCurrentTile().set(targetTile);
            oldPos.set(getAbsPos());
            setDirection(tempDirection);
        }
        if (InputHandler.isUpPressed()) {
            tempDirection = Direction.UP;
        } else if (InputHandler.isDownPressed()) {
            tempDirection = Direction.DOWN;
        } else if (InputHandler.isLeftPressed()) {
            tempDirection = Direction.LEFT;
        } else if (InputHandler.isRightPressed()) {
            tempDirection = Direction.RIGHT;
        }
    }

    /**
     * Extends parent class by also updating the current interactingNPC of the player.
     * @param delta The time since the last frame was rendered.
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        switch (getDirection()) {
            case UP:
                interactingNPC = (NPC) level.getCharacterAt(getCurrentTile().x, getCurrentTile().y + 1);
                break;
            case DOWN:
                interactingNPC = (NPC) level.getCharacterAt(getCurrentTile().x, getCurrentTile().y - 1);
                break;
            case LEFT:
                interactingNPC = (NPC) level.getCharacterAt(getCurrentTile().x - 1, getCurrentTile().y);
                break;
            case RIGHT:
                interactingNPC = (NPC) level.getCharacterAt(getCurrentTile().x + 1, getCurrentTile().y);
                break;
        }
        //ASSESSMENT 4 CHANGE (S1)
        Game.currentLocation = level.locationMap[(int) getCurrentTile().x][(int) getCurrentTile().y];
        //ASSESSMENT 4 CHANGE END
        //ASSESSMENT 4 CHANGE (S2)
        if(isSpeedCheatActive()){
            updateSpeedCheat(delta);
        }
        //ASSESSMENT 4 CHANGE END
    }

    /**
     * Function ADDED for ASSESSMENT 4 (S2)
     * @param delta The time since the last frame was rendered.
     */
    private void updateSpeedCheat(float delta){
        if (speedCheatTimer <=0){
            speedCheatActive = false;
        }else{
            speedCheatTimer -= delta;
        }
    }

    //ASSESSMENT 4 CHANGE (S2)
    @Override
    public void updateSpeed(){
        if(isFlying()){
            transitionSpeed = 0.05f;
            if (speedCheatActive){
                transitionSpeed = 0.02f;
            }
        }else if (isSwimming){
            transitionSpeed = 0.1f;
            if (speedCheatActive){
                transitionSpeed = 0.05f;
            }
        }else{
            transitionSpeed = 0.25f;
            if (speedCheatActive){
                transitionSpeed = 0.1f;
            }
        }
    }

    public boolean isSpeedCheatActive() {
        return speedCheatActive;
    }

    public void setSpeedCheatActive(boolean speedCheatActive) {
        this.speedCheatActive = speedCheatActive;
    }

    public void activateSpeedCheat(){
        setSpeedCheatActive(true);
        speedCheatTimer = 30;
    }

    public boolean isPowerCheatActive() {
        return powerCheatActive;
    }

    public void setPowerCheatActive(boolean powerCheatActive) {
        this.powerCheatActive = powerCheatActive;
    }

    public void activatePowerCheat(){
        setPowerCheatActive(true);
    }

    public void deactivatePowerCheat(){
        setPowerCheatActive(false);
    }

    //ASSESSMENT 4 CHANGE END
}
