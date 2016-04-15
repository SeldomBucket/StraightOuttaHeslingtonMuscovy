package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Handles input
 * There are 6 different inputs for the game. Up, down, left, right, esc and act.
 * The keybinds for these inputs are assigned here.
 */
public class InputHandler {
    private static Boolean isInputEnabled=true;

    private static Boolean upPressed = false;
    private static Boolean downPressed = false;
    private static Boolean rightPressed = false;
    private static Boolean leftPressed = false;
    private static Boolean actPressed = false;
    private static Boolean escPressed = false;
    //ASSESSMENT 3 change (11,15)
    private static Boolean oPressed = false;
    private static Boolean mPressed = false;
    private static Boolean shiftPressed = false;
    //ASSESSMENT 3 change
    //ASSESSMENT 4 change (S2)
    private static Boolean enterPressed = false;
    private static Boolean NPressed = false;
    private static Boolean IPressed = false;
    private static Boolean CPressed = false;
    private static Boolean BPressed = false;
    private static Boolean gravePressed = false;
    //END assessment 4 change

    private static Boolean upJustPressed = false;
    private static Boolean downJustPressed = false;
    private static Boolean rightJustPressed = false;
    private static Boolean leftJustPressed = false;
    private static Boolean actJustPressed = false;
    private static Boolean escJustPressed = false;
    //ASSESSMENT 3 change (11,15)
    private static Boolean OJustPressed = false;
    private static Boolean MJustPressed = false;
    private static Boolean shiftJustPressed = false;
    //END assessment 3 change
    //ASSESSMENT 4 change (S2)
    private static Boolean enterJustPressed = false;
    private static Boolean NJustPressed = false;
    private static Boolean IJustPressed = false;
    private static Boolean CJustPressed = false;
    private static Boolean BJustPressed = false;
    private static Boolean graveJustPressed = false;
    //END assessment 4 change

    private static final int UP = Input.Keys.W;
    private static final int DOWN = Input.Keys.S;
    private static final int LEFT = Input.Keys.A;
    private static final int RIGHT = Input.Keys.D;
    private static final int ACT = Input.Keys.E;
    private static final int ESC = Input.Keys.Q;
    //ASSESSMENT 3 change (11,15)
    private static final int O = Input.Keys.O;
    private static final int M = Input.Keys.M;
    private static final int SHIFT = Input.Keys.SHIFT_LEFT;
    //END ASSESSMENT 3 change
    //ASSESSMENT 4 change (S2)
    private static final int ENTER = Input.Keys.ENTER;
    private static final int N = Input.Keys.N;
    private static final int I = Input.Keys.I;
    private static final int C = Input.Keys.C;
    private static final int B = Input.Keys.B;
    private static final int GRAVE = Input.Keys.GRAVE;
    //END ASSESSMENT 4 change


    /**
     * Updates and polls to see which inputs are active.
     */
    public static void update() {
        if(isInputEnabled) {
            upPressed = false;
            upJustPressed = false;
            if (Gdx.input.isKeyPressed(UP)) {
                upPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(UP)) {
                upJustPressed = true;
            }

            downPressed = false;
            downJustPressed = false;
            if (Gdx.input.isKeyPressed(DOWN)) {
                downPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(DOWN)) {
                downJustPressed = true;
            }

            rightPressed = false;
            rightJustPressed = false;
            if (Gdx.input.isKeyPressed(RIGHT)) {
                rightPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(RIGHT)) {
                rightJustPressed = true;
            }

            leftPressed = false;
            leftJustPressed = false;
            if (Gdx.input.isKeyPressed(LEFT)) {
                leftPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(LEFT)) {
                leftJustPressed = true;
            }

            actPressed = false;
            actJustPressed = false;
            if (Gdx.input.isKeyPressed(ACT)) {
                actPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(ACT)) {
                actJustPressed = true;
            }

            escPressed = false;
            escJustPressed = false;
            if (Gdx.input.isKeyPressed(ESC)) {
                escPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(ESC)) {
                escJustPressed = true;
            }
            
            //ASSESSMENT 3 change (11,15)
            oPressed = false;
            OJustPressed = false;
            if (Gdx.input.isKeyPressed(O)) {
                oPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(O)) {
                OJustPressed = true;
            }
            
            mPressed = false;
            MJustPressed = false;
            if (Gdx.input.isKeyPressed(M)) {
                mPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(M)) {
                MJustPressed = true;
            }
            
            shiftPressed = false;
            shiftJustPressed = false;
            if (Gdx.input.isKeyPressed(SHIFT)) {
            	shiftPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(SHIFT)) {
            	shiftJustPressed = true;
            }
            //END ASSESSMENT 3 change

            //ASSESSMENT 4 change (S2)
            enterPressed = false;
            enterJustPressed = false;
            if (Gdx.input.isKeyPressed(ENTER)) {
                enterPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(ENTER)) {
                enterJustPressed = true;
            }

            NPressed = false;
            NJustPressed = false;
            if (Gdx.input.isKeyPressed(N)) {
                NPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(N)) {
                NJustPressed = true;
            }

            IPressed = false;
            IJustPressed = false;
            if (Gdx.input.isKeyPressed(I)) {
                IPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(I)) {
                IJustPressed = true;
            }

            CPressed = false;
            CJustPressed = false;
            if (Gdx.input.isKeyPressed(C)) {
                CPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(C)) {
                CJustPressed = true;
            }

            BPressed = false;
            BJustPressed = false;
            if (Gdx.input.isKeyPressed(B)) {
                BPressed = true;
            }
            if (Gdx.input.isKeyJustPressed(B)) {
                BJustPressed = true;
            }

            gravePressed = false;
            graveJustPressed = false;
            if (Gdx.input.isKeyPressed(GRAVE)) {
                gravePressed = true;
            }
            if (Gdx.input.isKeyJustPressed(GRAVE)) {
                graveJustPressed = true;
            }
            //END ASSESSMENT 4 change
        }
    }

    public static Boolean isUpPressed() {
        return upPressed;
    }

    public static Boolean isUpJustPressed() {
        return upJustPressed;
    }

    public static Boolean isDownPressed() {
        return downPressed;
    }

    public static Boolean isDownJustPressed() {
        return downJustPressed;
    }

    public static Boolean isRightPressed() {
        return rightPressed;
    }

    public static Boolean isRightJustPressed() {
        return rightJustPressed;
    }

    public static Boolean isLeftPressed() {
        return leftPressed;
    }

    public static Boolean isLeftJustPressed() {
        return leftJustPressed;
    }

    public static Boolean isActPressed() { return actPressed; }

    public static Boolean isActJustPressed() {
        return actJustPressed;
    }

    public static Boolean isEscPressed(){return escPressed;}

    public static Boolean isEscJustPressed(){return escJustPressed;}
    
    public static Boolean isOPressed(){
    	return oPressed;
    }
    
    //ASSESSMENT 3 change (11,15)

    public static Boolean isOJustPressed(){
    	return OJustPressed;
    }
    
    public static Boolean isMPressed(){
    	return mPressed;
    }
    
    public static Boolean isMJustPressed(){
    	return MJustPressed;
    }
    
    public static Boolean isShiftPressed(){
    	return shiftPressed;
    }
    
    public static Boolean isShiftJustPressed(){
    	return shiftJustPressed;
    }

    public enum inputType{
        UP,DOWN,LEFT,RIGHT,ACT,ESC, O, M, SHIFT
    }

    //END ASSESSMENT 3 change

    //ASSESSMENT 4 change (S2)

    public static Boolean isEnterPressed(){
        return enterPressed;
    }

    public static Boolean isEnterJustPressed(){
        return enterJustPressed;
    }

    public static Boolean isNPressed(){
        return NPressed;
    }

    public static Boolean isNJustPressed(){
        return NJustPressed;
    }

    public static Boolean isIPressed(){
        return IPressed;
    }

    public static Boolean isIJustPressed(){
        return IJustPressed;
    }

    public static Boolean isCPressed(){
        return CPressed;
    }

    public static Boolean isCJustPressed(){
        return CJustPressed;
    }

    public static Boolean isBPressed(){
        return BPressed;
    }

    public static Boolean isBJustPressed(){
        return BJustPressed;
    }

    public static Boolean isGravePressed(){
        return gravePressed;
    }

    public static Boolean isGraveJustPressed(){
        return graveJustPressed;
    }

    //END ASSESSMENT 4 change

    /**
     * Disables all input updating and sets inputs to false.
     * 
     * Updated to comply with assessment 3
     */
    public static void disableAllInput(){
        isInputEnabled=false;
        upPressed = false;
        downPressed = false;
        rightPressed = false;
        leftPressed = false;
        actPressed = false;
        escPressed = false;
      //ASSESSMENT 3 change(11,15)
        oPressed = false;
        mPressed = false;
        shiftPressed = false;
        // END CHANGE
        //ASSESSMENT 4 change (S2)
        enterPressed = false;
        NPressed = false;
        IPressed = false;
        CPressed = false;
        BPressed = false;
        gravePressed = false;
        //END CHANGE

        upJustPressed = false;
        downJustPressed = false;
        rightJustPressed = false;
        leftJustPressed = false;
        actJustPressed = false;
        escJustPressed = false;
        //ASSESSMENT 3 change(11,15)
        OJustPressed = false;
        MJustPressed = false;
        shiftJustPressed = false;
        // END CHANGE
        //ASSESSMENT 4 change (S2)
        NJustPressed = false;
        IJustPressed = false;
        CJustPressed = false;
        BJustPressed = false;
        graveJustPressed = false;
        //END CHANGE
    }
    public static void enableAllInput(){
        isInputEnabled=true;
    }

}
