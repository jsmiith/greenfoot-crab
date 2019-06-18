import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Crab extends Actor
{    
    public static int score = 0;
    public static int lives = 3;
    private int round = 1;
    
    private int ammo = 1;
    private int charWidth = getImage().getWidth()-45;
    private int wait = 50;
    
    /**
     * Act - do whatever the Crab wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        controls();
        eat();
        
        if (wait > 0) wait --;
    }    
    
    
    /**
     * Controls - Logic for controlling the crab. This is called whenever
     * the 'act' method gets called.
     */
    public void controls() {
        move(4);
        
        if (Greenfoot.isKeyDown("left")) {
            turn(-3);
        }
        
        if (Greenfoot.isKeyDown("right")) {
            turn(3);
        }
        
        if (Greenfoot.isKeyDown("f")) {
            if (wait == 0) {
                wait = 50;
                shoot();
            }
        }
    }
    
    /**
     * Eat - Logic for the crab eating worms. This is called whenever the 'act' method
     * gets called.
     */
    public void eat() {
        World world;
        world = getWorld();
        
        // get the worm which is next to the crab
        Actor worm;
        worm = getOneObjectAtOffset(0, 0, Worm.class);
        
        // if the worm exists
        if (worm != null) {
            // remove the worm
            world.removeObject(worm);
            
            // play the eating sound
            Greenfoot.playSound("eating.wav");
            
            // increment the score
            score++;
            
            // show the score and round
            world.showText("Score: "+score, 50, 25);
            world.showText("Round: "+round, 300, 25);
            
            // if that was the last worm
            if(world.getObjects(Worm.class).size() == 0) {
                // move up a round and update the text
                round++;
                world.showText("Round: "+round, 300, 25);
                
                // spawn new worms
                for (int i = 0; i < 10; i++) {
                    Worm newWorm = new Worm();
                    world.addObject(newWorm, Greenfoot.getRandomNumber(540), Greenfoot.getRandomNumber(540));
                }
                
                // add a new lobster to make it harder
                Lobster lobster = new Lobster();
                world.addObject(lobster, 520, 41);
                
                ammo = world.getObjects(Lobster.class).size();
                
                // show the ammo
                world.showText("Ammo: "+ammo, 500, 525);
            }
        }
    }
    
    public void shoot() {
        Bullet bullet;
        bullet = new Bullet(getRotation());
        
        World world;
        world = getWorld();
        
        if (ammo > 0) ammo--;
        else if (ammo == 0) return;
        
        // show the ammo
        world.showText("Ammo: "+ammo, 500, 525);
            
        world.addObject(bullet, getX()+charWidth, getY());
    }
}