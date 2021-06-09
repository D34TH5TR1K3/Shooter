package shooter.entities;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {
    //arraylists to organize entity management
    private final ArrayList<Entity> enemies = new ArrayList<>();
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> tempEntities = new ArrayList<>();
    private final ArrayList<Entity> removeEntities = new ArrayList<>();
    //saves the Player for tick and renders
    private Player player;

    //this constructor initializes the values
    public EntityManager() {

    }

    //ticks all entities
    public void tick() {
        player.tick();
        entities.addAll(tempEntities);
        tempEntities.forEach((entity) -> {
            if (entity.getClass().equals(Enemy.class)) enemies.add(entity);
        });
        tempEntities.clear();
        entities.removeAll(removeEntities);
        tempEntities.forEach((entity) -> {
            if (entity.getClass().equals(Enemy.class)) enemies.remove(entity);
        });
        removeEntities.clear();
        entities.forEach(Entity::tick);
        entities.forEach(entity -> {
            if (entity.getClass().equals(Particle.class)) {
                Particle p = (Particle) entity;
                if (p.getType() == 0 && p.getAnimParticle().lastFrame() || p.getTimeLeftToLive() < 1)
                    removeEntity(entity);
            }
        });
    }

    //renders all entities
    public void render(Graphics g) {
        //TODO add render order (sort entities by Z level and render on after another)
        entities.forEach(entity -> entity.render(g));
        player.render(g);
    }

    public Player getPlayer() {
        return player;
    }

    //adds, removes and getters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        tempEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }

    public Entity getClosestItem(float x, float y) {    //eine Methode um zu berechenen, welches Item am neahsten zum Spieler liegt
        float lowestDistance = 0;
        Entity closestEntity = null;
        ArrayList<Entity> items = new ArrayList<>();
        entities.forEach((entity) -> {
            if (entity.getClass().equals(Item.class)) items.add(entity);
        });
        for (Entity item : items) {
            float dx = x - item.getX();
            float dy = y - item.getY();
            float distance = ((float) (Math.sqrt((dx * dx) + (dy * dy))));
            if (distance < lowestDistance || closestEntity == null) {
                closestEntity = item;
                lowestDistance = distance;
            }
        }
        if (lowestDistance < 50)
            return closestEntity;
        else
            return null;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }
}