package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.utils.Sound;
import shooter.utils.Timer;
import shooter.world.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends Entity {
    //provides a variable to change if Friendly Fire is active
    public static boolean friendlyFire = new shooter.utils.Writer().getSettingValue("Friendly Fire") > 0;
    //provides variables to change the speed of the bullets
    public static float playerBulletSpeed = new shooter.utils.Writer().getSettingValue("Player Bullet Speed"), enemyBulletSpeed = new shooter.utils.Writer().getSettingValue("Enemy Bullet Speed");
    //saves the bullets type and speed
    private final float speed;
    private final int type;
    //saves the bullets animation
    Animation animation;
    //times how long melee attack does damage
    private Timer meeleTimer;

    //this constructor initializes the values
    public Bullet(float posX, float posY, float dir, int speed, int type, Handler handler, Level level) {
        super(posX, posY, 3, dir, handler, level);
        this.type = type;
        this.dir = dir;
        this.speed = speed;
        switch (type) {
            case 1, 2:

                break;
            case 0:
                animation = new Animation(Assets.rocket, 20);
                break;
            case 3:
                meeleTimer = new Timer(1500);
                break;
            default:
                break;
        }
    }

    //ticks the bullets movement, collision and animation
    @Override
    public void tick() {
        if (animation != null)
            animation.tick();

        if (type == 3) {
            level.checkEnemyCollision(new Rectangle(((int) posX - 50), ((int) posY - 50), 100, 100), type);
            level.getEntityManager().removeEntity(this);
        } else {

            if (type == 2) {
                posX = posX + (float) (Math.cos(Math.toRadians(dir) + Math.PI) * speed * enemyBulletSpeed / 50);
                posY = posY + (float) (Math.sin(Math.toRadians(dir) + Math.PI) * speed * enemyBulletSpeed / 50);
            } else {
                posX = posX + (float) (Math.cos(Math.toRadians(dir) + Math.PI) * speed * playerBulletSpeed / 50);
                posY = posY + (float) (Math.sin(Math.toRadians(dir) + Math.PI) * speed * playerBulletSpeed / 50);
            }

            moveAbs(posX, posY);
            if (level.collisionCheck(new Rectangle(((int) posX), ((int) posY), 10, 10)) || level.checkEnemyCollision(new Rectangle(((int) posX), ((int) posY), 10, 10), type)) {
                if (type == 0) {
                    Sound.play("RocketExplode");
                    level.getEntityManager().addEntity(new Particle(((int) posX), ((int) posY), 80, 80, 12, Assets.explosion, handler, level));
                } else if (type > 0)
                    level.getEntityManager().addEntity(new Particle(((int) posX), ((int) posY), 20, Assets.particles1, handler, level));
                level.getEntityManager().removeEntity(this);
            }
            if (type == 2) {
                if (level.checkPlayerCollision(new Rectangle(((int) posX), ((int) posY), 10, 10)))
                    level.getEntityManager().removeEntity(this);
                if (friendlyFire)
                    level.checkEnemyCollision(new Rectangle(((int) posX), ((int) posY), 10, 10), type);
            }
        }
    }

    //renders the bullet
    @Override
    public void render(Graphics g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        Graphics2D g2 = (Graphics2D) g;
        switch (type) {
            case 1, 2:
                g2.rotate(Math.toRadians(dir), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));
                g2.drawImage(Assets.Bullet, (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()), null);
                //g.fillRect((int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()), 3, 3);
                break;
            case 0:
                g2.rotate(Math.toRadians(dir + 180), (int) (posX - handler.getGameCamera().getxOffset()), (int) (posY - handler.getGameCamera().getyOffset()));
                g.drawImage(animation.getCurrentFrame(), (int) (posX - handler.getxOffset()), (int) (posY - handler.getyOffset()), null);
                break;
            case 3:
                //g.fillRect((int)(posX - 50 - handler.getGameCamera().getxOffset()), (int)(posY - 50 - handler.getGameCamera().getyOffset()), 100, 100);
                break;
            default:
                break;
        }

        g2.setTransform(reset);
        //TODO implement rendering for the Bullet
    }

    //getters
    public String getData() {
        return "";
    }
}
