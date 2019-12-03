package PacMan;

import java.awt.Font;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;


public class Fantomes extends Entite {
	// declaration variable
	private int vitesse;
	private TiledMap map;
	private int randomNum;
	
	/*int w = original_image.getWidth() nbCols; int h = original_image.getHeight()
	nbRows; int x = thisCol*w; int y = thisRow*h; subImage = new getSubImage(x,y, w, h);
	private int playersChoice = 1; //private int choice = 0; private boolean exit
	= false; private boolean play = false; private boolean propos = false;
	private TiledMap map; private Image buttonPlay; private Image buttonExit;
	*/
	

	// Constructeur
	public Fantomes(String image, int grandeur, int grandeurTiles, int positionX, int positionY, Direction direction,
			int vitesse, TiledMap map) throws SlickException {
		super(image, grandeur, grandeurTiles, positionX, positionY, direction);
		this.vitesse = vitesse;
		this.map = map;
	}

	public void update(int i) 
	{
		// Verifi si sa direction est neutre et en choisi une nouvelle
		if (this.getDirection() == Direction.NEUTRE) {
			this.setDirection(this.decideDirection());
		}
		// Deplacement
		if (this.getDirection() == Direction.RIGHT) {
			this.deplacementX((0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.LEFT) {
			this.deplacementX((-0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.UP) {
			this.deplacementY((-0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.DOWN) {
			this.deplacementY((0.1 * i) / vitesse);
		}
		
		//verifi si il peux tourner.
		int sol = map.getLayerIndex("sols");
		//verifi le coter gauche
		
		if ((this.getDirection() == Direction.UP || this.getDirection() == Direction.DOWN) && map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(), sol) == 0) {
			//System.out.println("mur a gauche");
		}
		// arret vers un mur
		int mur = map.getLayerIndex("murs");
		//verifi le coter droit
		if (this.getDirection() == Direction.RIGHT
				&& map.getTileId(this.getPositionXIntArret() + 1, this.getPositionYIntArret(), mur) != 0) {
			// pour que le fantome s'arrete a un chiffre rond
			this.setPositionX(Math.round(this.getPositionX()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté gauche
		else if (this.getDirection() == Direction.LEFT
				&& map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionX(Math.round(this.getPositionX()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté haut
		else if (this.getDirection() == Direction.UP
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() - 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionY(Math.round(this.getPositionY()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté bas
		else if (this.getDirection() == Direction.DOWN
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() + 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionY(Math.round(this.getPositionY()));
			this.setDirection(Direction.NEUTRE);
		}
	}
	

	// decide de sa direction quand il est neutre.
	private Direction decideDirection() {

		Direction direction = null;
		Random rand = new Random();
		int random = rand.nextInt(4);
		if(random == 0) {direction = Direction.RIGHT;}
		else if(random == 1) {direction = Direction.LEFT;}
		else if(random == 2) {direction = Direction.UP;}
		else if(random == 3) {direction = Direction.DOWN;}
		return direction;	

	}

	private Direction siTourne(Direction direction) {
		return Direction.UP;
	}
}
