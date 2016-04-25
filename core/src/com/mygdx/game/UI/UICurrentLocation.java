package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;

/**
 * NEW CLASS ASSESSMENT 3 (change 13)
 * Component for rendering the current location at the bottom right of the screen.
 */
public class UICurrentLocation extends UIComponent {

    UIMessageBox messageBox;
    String messagePrepend="Current Location: ";
    //ASSESSMENT 4 CHANGE (S1)
    String location = "Somewhere on Heslington East";
    //END ASSESSMENT 4 CHANGE

    public UICurrentLocation(){
        super(0,0,0,0);
        this.width=500;
        this.height=20;
        this.x= Gdx.graphics.getWidth()-width;
        this.y= 0;
        getLocation();
        this.messageBox = new UIMessageBox(messagePrepend+location,this.x,this.y,this.width,this.height,10,10);
    }

    /**
     * Assessment 4 New Method (S1)
     * Checks the current location and sets the location string to the appropriate description
     */
    private void getLocation(){
        if (!(Game.currentLocation == null)){
            switch (Game.currentLocation) {
                case CATALYST:
                    location = "The Catalyst";
                    break;
                case CS:
                    location = "Computer Science";
                    break;
                case TFTV:
                    location = "Theatre, Film and Television";
                    break;
                case LMB:
                    location = "Law and Management School";
                    break;
                case RCH:
                    location = "Ron Cooke Hub";
                    break;
                case GOODRICKE:
                    location = "Goodricke College";
                    break;
                case LANGWITH:
                    location = "Langwith College";
                    break;
                case CONSTANTINE:
                    location = "Constantine College";
                    break;
                case LAKE:
                    location = "The Lake";
                    break;
                case SOMEWHERE:
                    location = "Somewhere on Heslington East";
                    break;
                case SPORT_CENTRE:
                    location = "Sport Centre";
                    break;
            }
        }else{
                location="NULL FOR SOME REASON?";
        }
    }

    /**
     * Called once per frame to render the location.
     * Assessment 4 change S1: gets current location and sets the message appropriately
     */
    public void render(SpriteBatch batch, NinePatch patch){
        //ASSESSMENT 4 CHANGE (S1)
        getLocation();
        messageBox.setMessage(messagePrepend+location);
        //END ASSESSMENT 4 CHANGE
        messageBox.render(batch,patch);
    }
}
