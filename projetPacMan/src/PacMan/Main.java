package PacMan;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



public class Main 
{
	public static void main(String[] args) 
	{
		//resolution
				int largeurAffichage = 576;
				int hauteurAffichage = 800;
				boolean siPleineEcran = false;
				
				try 
				{
					AppGameContainer app = new AppGameContainer(new Jeu("Extrem PAC-MAN"));
					app.setDisplayMode(largeurAffichage, hauteurAffichage, siPleineEcran);
					app.start();
				}
				catch(SlickException ex) 
				{
					Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				
	}
	

}
