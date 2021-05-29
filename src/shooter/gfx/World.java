package shooter.gfx;

import shooter.entities.*;
import shooter.Handler;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private final Tile[][] tiles;                                       //hier werden alle Tiles gespeichert, in die die Karte unterteilt wird
    private final int mapsize = 2;                                      //hier wird gespeichert, wie groß die Karte sein soll
    private Player player;                                              //hier wird der Spieler gespeichert

    private Color tileColor;                                            //hier wird die Farbe für die Tiles gespeichert

    private final Handler handler;                                      //hier wird der Handler gespeichert
    private final EntityManager entityManager;                          //hier werden alle Entitäten der Welt gespeichert und kontrolliert

    public World(Handler handler) {                                     //im Konstruktor wird die Welt mitsamt Tiles und Entitäten initialisiert
        tiles = new Tile[64 * mapsize][36 * mapsize];
        this.handler = handler;
        entityManager = new EntityManager(this);
        fillTiles();
        fillHalfSolidTiles();
    }

    public void fillWorld(){                                            //hier füllen wir die Welt mit Entitäten, deren Informationen als default gegeben sind
        this.player = new Player(100, 100, 45, handler, this);
        //entityManager.addEntity(new Enemy(200,200,135,2, handler, this));
        entityManager.addEnemy(new Enemy(700,700,0,2, handler, this));
        entityManager.addEnemy(new Enemy(1200,1800,180,2, handler, this));
    }

    public void fillWorld(Player player, ArrayList<Entity> enemies){    //hier füllen wir die Welt mit Entitäten, deren Informationen wir aus einem gespeicherten Spielstand auslesen
        this.player = player;
        for(Entity e:enemies){
            entityManager.addEnemy(e);
        }
    }

    public boolean collisionCheck(Rectangle rect){                      //hier prüfen wir, ob das übergebene Rechteck mit etwas solidem in der Welt kollidieren würde
        //System.out.println(rect.getBounds());
        for(int y = (int) (0 + rect.getY()/30); y < 4 + rect.getY()/30; y++){
            for(int x = (int) (0 + rect.getX()/30); x < 4 + rect.getX()/30; x++){
                if(x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length){
                    Tile temptile = tiles[x][y];
                    if(temptile.isSolid() && temptile.getHitbox().intersects(rect)){
                        //System.out.println(temptile.getTposX() +"  "+ temptile.getTposY());
                        return true;
                    }
                    //System.out.println(temptile.getTposX()*30 + "   " + temptile.getTposY()*30+"   ");
                }
            }
            //System.out.println();
        }
        return false;
    }
    public boolean checkEnemyCollision(Rectangle rect) {                //hier prüfen wir, ob das übergebene Rechteck mit eonem Gegener kollidiert
        for (Entity e : entityManager.getEnemies()) {
            System.out.println("start");
            System.out.println(rect);
            System.out.println(((Enemy) e).getHitbox());
            if (((Enemy) e).getHitbox() != null && ((Enemy) e).getHitbox().intersects(rect)) {
                ((Enemy) e).die();
                return true;
            }
        }
        return false;
    }

    public void tick(){                                                 //in tick wird nur der Entitymanager getickt
        entityManager.tick();
    }

    public void render(Graphics g){                                     //in render wird die Karte und die Entitäten des Entitymanagers gerendert
        g.drawImage(Assets.map_1, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
        //System.out.println((int)(0 - handler.getGameCamera().getxOffset())+"   "+(int)(0 - handler.getGameCamera().getyOffset()));

        //renderTiles(g);
        entityManager.render(g);
        //g.drawImage(Assets.Map1_walls, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
    }

    public void renderTiles(Graphics g){                                //hier werden, wenn nötig die Tiles gerendert, in die wir die Karte unterteilen
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                g.setColor(tiles[x][y].getColor());
//                if(tiles[x][y].isHalfSolid()){
//                    g.setColor(Color.red);
//                }else{
//                    g.setColor(Color.green);
//                }
                
                //g.setColor(tiles[x][y].getTileColor());
                //TODO set size of map
                //hier wird gespeichert, wie groß ein Tile sein soll
                int tilesize = 30;
                g.fillRect(((int) (tilesize * x - handler.getxOffset())), ((int) (tilesize * y - handler.getyOffset())), tilesize *x+ tilesize, tilesize *y+ tilesize);
            }
        }
    }

    public void fillHalfSolidTiles(){                                   //hier werden die halbsoliden Tiles erzeugt, die für bessere KI-Bewegung existieren
        for(int x = 0; x < 64 * mapsize; x++) {
            for (int y = 0; y < 36 * mapsize; y++) {
                if(tiles[x][y].isSolid()){
                    for(int X = -1; X < 2; X++){
                        for(int Y = -1; Y < 2; Y++){
                            if(x+X >= 0 && x+X < 64 * mapsize && y+Y >= 0 && y+Y < 36 * mapsize)
                            tiles[x+X][y+Y].setHalfSolid(true);
                        }
                    }
                }
            }
        }
    }

    public void printTiles(Graphics g){                                 //hier werden alle Tiles ausgegeben
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                System.out.print("");
            }
        }
    }

    public void fillTiles() {                                           //hier werden die aus der Karte ausgelesenen Tiles erzeugt, die bestimmen, ob etwas solide ist
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){

                Color mycolor = new Color(Assets.map_1layout.getRGB(x, y));
                boolean solid = false;
                //System.out.println(mycolor);
                int red = mycolor.getRed();
                int green = mycolor.getGreen();
                int blue = mycolor.getBlue();
                if(red < 200 && green < 200 && blue < 200){
                    solid = true;
                    tileColor = Color.cyan;
                }else{
                    tileColor = Color.green;
                }
                tiles[x][y] = new Tile(x, y, solid);
            }
        }
        //TODO find spot to make new player
    }

    //Getters und Setters
    public Tile getTiles(int x, int y) {
        if(x >= 0 && x < 64 * mapsize && y >= 0 && y < 36 * mapsize) {
            //System.out.println(x + "   " + y);
            return tiles[x][y];
        }else
            return tiles[0][0];
    }
    public Tile[][] getTiles() {
        return tiles;
    }
    public void setAllTiles(Color color){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                tiles[x][y].setColor(color);
            }
        }
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public Player getPlayer() {
        return player;
    }
    public int getMapsize(){
        return mapsize;
    }
}
