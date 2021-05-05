package shooter.entities;

import shooter.gfx.World;

import java.awt.*;
import java.util.ArrayList;
//test
public class EntityManager {

    private ArrayList<Entity> enemies;
    private ArrayList<Entity> tempEnemies;
    private ArrayList<Entity> removeEnemies;

    private ArrayList<Entity> items;
    private ArrayList<Entity> tempItems;
    private ArrayList<Entity> removeItems;

    private ArrayList<Entity> bullets;
    private ArrayList<Entity> tempBullets;
    private ArrayList<Entity> removeBullets;

    private ArrayList<Particle> particles;
    private ArrayList<Particle> tempParticles;
    private ArrayList<Particle> removeParticles;

    private World world;

    public EntityManager(World world) {
        this.world = world;
        enemies = new ArrayList<Entity>();
        tempEnemies = new ArrayList<Entity>();
        removeEnemies = new ArrayList<Entity>();

        items = new ArrayList<Entity>();
        tempItems = new ArrayList<Entity>();
        removeItems = new ArrayList<Entity>();

        bullets = new ArrayList<Entity>();
        tempBullets = new ArrayList<Entity>();
        removeBullets = new ArrayList<Entity>();

        particles = new ArrayList<Particle>();
        tempParticles = new ArrayList<Particle>();
        removeParticles = new ArrayList<Particle>();
    }

    public void addEnemy(Entity entity) {
        tempEnemies.add(entity);
    }

    public void removeEnemy(Entity entity) {
        removeEnemies.add(entity);
    }

    public void addItem(Entity entity) {
        tempItems.add(entity);
    }

    public void removeItem(Entity entity) {
        removeItems.add(entity);
    }

    public void addBullet(Entity entity) {
        tempBullets.add(entity);
    }

    public void removeBullet(Entity entity) {
        removeBullets.add(entity);
    }

    public void addParticle(Particle particle) {
        tempParticles.add(particle);
    }

    public void removeParticle(Particle particle) {
        removeParticles.add(particle);
    }

    public Entity getClosestItem(float x, float y) {
        float lowestDistance = 0;
        Entity closestEntity = null;
        for(Entity item : items){
            float dx = x - item.getX();                      //pythagoras
            float dy = y - item.getY();                      //pythagoras
            float distance = ((float) (Math.sqrt((dx * dx) + (dy * dy))));  //pythagoras
            //System.out.println(distance);
            if(distance < lowestDistance || closestEntity == null){
                closestEntity = item;
                lowestDistance = distance;
            }
        }
        if(lowestDistance < 50) {
            //System.out.println("lowestdistance: "+lowestDistance);
            return closestEntity;
        } else {
            return null;
        }
    }

    public void tick(){
        world.getPlayer().tick();

        enemies.addAll(tempEnemies);
        tempEnemies.clear();
        enemies.removeAll(removeEnemies);
        removeEnemies.clear();
        for(Entity e : enemies) {
            //System.out.println(((Enemy)e).getHitbox());
            e.tick();
        }
        //System.out.println(enemies.size());
        items.addAll(tempItems);
        tempItems.clear();
        items.removeAll(removeItems);
        removeItems.clear();
        for(Entity i : items) {
            i.tick();
        }
        bullets.addAll(tempBullets);
        tempBullets.clear();
        bullets.removeAll(removeBullets);
        removeBullets.clear();
        for(Entity b : bullets) {
            b.tick();
        }
        for(Particle p : particles) {
            if((p.getType() == 0 && p.getAnimParticle().lastFrame())||p.getTimeLeftToLive()==0){
                removeParticle(p);
            }
        }
        particles.addAll(tempParticles);
        tempParticles.clear();
        particles.removeAll(removeParticles);
        removeParticles.clear();
        for(Particle p : particles) {
            p.tick();
        }
    }
    public void render(Graphics g){
        //TODO add render order (sort entities by Z level and render on after another)


        for(Entity i : items){
            i.render(g);
        }
        for(Entity b : bullets){
            b.render(g);
        }
        for(Entity e : enemies) {
            e.render(g);
        }
        for(Particle p : particles) {
            p.render(g);
        }
        world.getPlayer().render(g);
    }
    public ArrayList<Entity> getEnemies(){
        return enemies;
    }
}
