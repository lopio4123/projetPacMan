package PacMan;

import java.awt.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Accueil extends BasicGame {

	private int playersChoice = 1;
	//private int choice = 0;
    private boolean exit = false;
	private boolean play = false;
	private boolean propos = false;
    
    private TiledMap map;
    private Image buttonPlay;
    private Image buttonExit;

    public Accueil() 
    {
        super("Main Menu");
    }

    @Override
    public void init(GameContainer gc) throws SlickException 
    {
    	map = new TiledMap("./map/accueil.tmx");
    	buttonPlay = new Image("./image/play.png");
    	buttonExit = new Image("./image/exit - Copie.png");
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException 
    {
        Input input = gc.getInput();
                
        if (input.isKeyPressed(Input.KEY_DOWN)) // Descendre / exit
        {
        	if (playersChoice == 0) 
            {
               System.out.println("Down");
            } 
            else 
            {
                playersChoice++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) // Monter / play
        {
        	if (playersChoice == 0) 
            {
               System.out.println("Up");
            } 
            else 
            {
                playersChoice--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) 
        {
            switch (playersChoice) 
            {
                case 0:
                    this.play = true;
                    break;
                case 1:
                	this.exit = true;
                	break;
                case 2:
                	this.propos = true;
                	break;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException 
    {
        map.render(0, 0);
        buttonPlay.draw(190, 250, 200, 100);
        buttonExit.draw(195, 400, 200, 100);
        //g.drawString("Hello", 50, 50);
        
        if (exit) 
        {
            gc.exit();
        }
        if (play)
        {
        	gc.pause();
        }
    }
    
 

    
}