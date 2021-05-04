package shooter.entities;

import java.awt.*;
import java.util.ArrayList;
public class ParticleManager {

    private ArrayList<Particle> particles;
    private ArrayList<Particle> tempParticles;
    private ArrayList<Particle> removeParticles;

    public ParticleManager() {
        particles = new ArrayList<Particle>();
        tempParticles = new ArrayList<Particle>();
        removeParticles = new ArrayList<Particle>();
    }

    public void addParticle(Particle particle) {
        tempParticles.add(particle);
    }

    public void removeParticle(Particle particle) {
        removeParticles.add(particle);
    }

    public void tick(){
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
        for(Particle p : particles) {
            p.render(g);
        }
    }
}
