package PacMan;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Petit_Point 
{
	private Image image;
	private float hauteur;
	private float largeur;
	private int grandeurTiles;
	private float positionX;
	private float positionY;

	private int score;
	private int pointEat;
	
	public Petit_Point(String image, int grandeur, int grandeurTiles) throws SlickException 
	{
		this.image = new Image(image);
		hauteur = grandeur;
		largeur = grandeur;
		this.grandeurTiles = grandeurTiles;
	}
	
	public void eat()
	{
		pointEat++;
		score += 100;
		
	}


}
