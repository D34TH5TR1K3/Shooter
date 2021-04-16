package shooter.entities;

import java.awt.*;
import java.util.ArrayList;
//test
public class EntityManager {

    private ArrayList<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void tick(){
        for(Entity e : entities) {
            e.tick();
        }
        //System.out.println(entities.size());
    }
    public void render(Graphics g){
        for(Entity e : entities) {
            e.render(g);
        }
    }

}
