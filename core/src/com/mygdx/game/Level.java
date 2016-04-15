/*
gameworld refernces removed in assessment 3 - change 5
*/
package com.mygdx.game;

import java.util.ArrayList;
import java.util.Locale;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

/**
 * The level class contains the map used by the game and stores all characters.
 */
public class Level {

    public static final int TILE_SIZE = 16;

    public TiledMap map;
    public boolean[][] collisionMap;
    // Assessment 3 Change (15)
    public boolean[][] waterMap;
    public Location[][] locationMap;
    // Assessment 3 End
    public Player player;
    public ArrayList<Character> characters;
    public boolean stopInput;

    public int mapWidth;
    public int mapHeight;
    public int tileWidth;
    public int tileHeight;
    public Vector2 mapBounds;

    /**
     * The constructor loads the map and creates a new player in the appropriate position.
     * Modified for ASSESSMENT 3 (change 15)
     */
    public Level(GameWorld gameWorld) {
        map = new TmxMapLoader().load("newMap.tmx");

        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tileWidth = prop.get("tilewidth", Integer.class);
        tileHeight = prop.get("tileheight", Integer.class);
        mapBounds = new Vector2(mapWidth * tileWidth, mapHeight * tileHeight);
        
        collisionMap = new boolean[mapWidth][mapHeight];
        // Assessment 3 Change (15)
        locationMap = new Location[mapWidth][mapHeight];
        waterMap = new boolean[mapWidth][mapHeight];
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        // Assessment 3 Change (15)
        TiledMapTileLayer locationLayer = (TiledMapTileLayer) map.getLayers().get(1);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                // For collisionMap
            	collisionMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("blocked");
                
            	// For waterMap
            	waterMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("WaterFrame");

            	// Assessment 4 Change (S1)
                // For current location
                MapProperties mapLocationProperties = locationLayer.getCell(x, y).getTile().getProperties();
            	if(mapLocationProperties.containsKey("Location")){
                    if(mapLocationProperties.get("Location").equals("Computer Science")){
                        locationMap[x][y]= Location.CS;
                    }else if(mapLocationProperties.get("Location").equals("Theatre, Film + Television")){
                        locationMap[x][y]= Location.TFTV;
                    }else if(mapLocationProperties.get("Location").equals("The Catalyst")){
                        locationMap[x][y]= Location.CATALYST;
                    }else if(mapLocationProperties.get("Location").equals("Law and Management School")){
                        locationMap[x][y]= Location.LMB;
                    }else if(mapLocationProperties.get("Location").equals("Ron Cooke Hub")){
                        locationMap[x][y]= Location.RCH;
                    }else if(mapLocationProperties.get("Location").equals("Goodricke College")){
                        locationMap[x][y]= Location.GOODRICKE;
                    }else if(mapLocationProperties.get("Location").equals("Langwith College")){
                        locationMap[x][y]= Location.LANGWITH;
                    }else if(mapLocationProperties.get("Location").equals("Constantine College")){
                        locationMap[x][y]= Location.CONSTANTINE;
                    }else if(mapLocationProperties.get("Location").equals("The Lake")){
                        locationMap[x][y]= Location.LAKE;
                    }
            		//locationMap[x][y] = (String) mapLocationProperties.get("Location");
            		//System.out.println(mapLocationProperties.get("Location"));
            	}else{
            		locationMap[x][y] = Location.SOMEWHERE;
            	}
            }
            
            
        }

        player = new Player(this, new Vector2(115, 94));
        characters = new ArrayList<Character>();
        characters.add(player);
        stopInput = false;
    }

    /**
     * This method is called once per frame and updates each character in the level.
     */
    public void update(float delta) {
        characters.sort(new Character.CharacterComparator());
        updateCollisionMap();
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).update(delta);
        }
    }

    /**
     * The CollisionMap allows characters to know if their path is blocked by a player or a blocked tile.
     */
    private void updateCollisionMap() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                collisionMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("blocked");
            }
        }
        for (int i = 0; i < characters.size(); i++) {
            collisionMap[(int) characters.get(i).getCurrentTile().x][(int) characters.get(i).getCurrentTile().y] = true;
            collisionMap[(int) characters.get(i).targetTile.x][(int) characters.get(i).targetTile.y] = true;
        }
        collisionMap[(int) player.targetTile.x][(int) player.targetTile.y] = true;
        collisionMap[(int) player.getCurrentTile().x][(int) player.getCurrentTile().y] = true;
    }
    

    
    /**
     * @return Returns null if no character exists at x, y.
     */
    public Character getCharacterAt(float tileX, float tileY){
        if (characters != null) {
            for (Character c : characters) {
                if (c.getCurrentTile().equals(new Vector2(tileX, tileY)))
                    return c;
            }
        }
        return null;
    }


}
