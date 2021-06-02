package shooter.entities;

import shooter.levels.Level;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {
    //arraylists to organize entity management
    private final ArrayList<Entity> enemies = new ArrayList<>();
    private final ArrayList<Entity> tempEnemies = new ArrayList<>();
    private final ArrayList<Entity> removeEnemies = new ArrayList<>();
    private final ArrayList<Entity> items = new ArrayList<>();
    private final ArrayList<Entity> tempItems = new ArrayList<>();
    private final ArrayList<Entity> removeItems = new ArrayList<>();
    private final ArrayList<Entity> bullets = new ArrayList<>();
    private final ArrayList<Entity> tempBullets = new ArrayList<>();
    private final ArrayList<Entity> removeBullets = new ArrayList<>();
    private final ArrayList<Particle> particles = new ArrayList<>();
    private final ArrayList<Particle> tempParticles = new ArrayList<>();
    private final ArrayList<Particle> removeParticles = new ArrayList<>();
    //world distributes variables
    private final Level level;

    //this constructor initializes the values
    public EntityManager(Level level) {
        this.level = level;
    }

    //ticks all entities
    public void tick(){
        level.getPlayer().tick();

        enemies.addAll(tempEnemies);
        tempEnemies.clear();
        enemies.removeAll(removeEnemies);
        removeEnemies.clear();
        for(Entity e : enemies)
            e.tick();
        items.addAll(tempItems);
        tempItems.clear();
        items.removeAll(removeItems);
        removeItems.clear();
        bullets.addAll(tempBullets);
        tempBullets.clear();
        bullets.removeAll(removeBullets);
        removeBullets.clear();
        for(Entity b : bullets)
            b.tick();
        for(Particle p : particles)
            if((p.getType() == 0 && p.getAnimParticle().lastFrame())||p.getTimeLeftToLive()==0)
                removeParticle(p);
        particles.addAll(tempParticles);
        tempParticles.clear();
        particles.removeAll(removeParticles);
        removeParticles.clear();
        for(Particle p : particles)
            p.tick();
    }
    //renders all entities
    public void render(Graphics g){
        //TODO add render order (sort entities by Z level and render on after another)
        for(Entity i : items)
            i.render(g);
        for(Entity b : bullets)
            b.render(g);
        for(Entity e : enemies)
            e.render(g);
        for(Particle p : particles)
            p.render(g);
        level.getPlayer().render(g);
    }

    //adds, removes and getters
    public void addEnemy(Entity entity) { tempEnemies.add(entity); }
    public void removeEnemy(Entity entity) { removeEnemies.add(entity); }
    public void addItem(Entity entity) { tempItems.add(entity); }
    public void removeItem(Entity entity) { removeItems.add(entity); }
    public void addBullet(Entity entity) { tempBullets.add(entity); }
    public void removeBullet(Entity entity) { removeBullets.add(entity); }
    public void addParticle(Particle particle) { tempParticles.add(particle); }
    public void removeParticle(Particle particle) { removeParticles.add(particle); }
    public Entity getClosestItem(float x, float y) {    //eine Methode um zu berechenen, welches Item am neahsten zum Spieler liegt
        float lowestDistance = 0;
        Entity closestEntity = null;
        for(Entity item : items){
            float dx = x - item.getX();
            float dy = y - item.getY();
            float distance = ((float) (Math.sqrt((dx * dx) + (dy * dy))));
            if(distance < lowestDistance || closestEntity == null){
                closestEntity = item;
                lowestDistance = distance;
            }
        }
        if(lowestDistance < 50)
            return closestEntity;
        else
            return null;
    }
    public ArrayList<Entity> getEnemies(){ return enemies; }
}
