package PacMan;

import org.newdawn.slick.Image;

import org.newdawn.slick.SlickException;

public class Entite {
	
	private Image skin;
	private float hauteur;
	private float largeur;
	private float positionX;
	private float positionY;
	private int grandeurTiles;
	private Direction direction;
	private double positionXX;
	private double positionYY;

	public Entite(String image, int grandeur, int grandeurTiles, int positionX, int positionY, Direction direction) throws SlickException {
		this.skin = new Image(image);
		this.positionX = positionX;
		this.positionY = positionY;
		hauteur = grandeur;
		largeur = grandeur;
		this.grandeurTiles = grandeurTiles;
		this.direction = direction;
		System.out.println(direction);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void apparaitre() {
		skin.draw(positionX * grandeurTiles, positionY * grandeurTiles, hauteur, largeur);
	}
	
	public void deplacementX(double ajout) {
		this.positionX += ajout;
	}
	
	public void deplacementY(double ajout) {
		this.positionY += ajout;
	}
	
	public Image getSkin() {
		return skin;
	}
	//transforme les positions en entier (surtout utilisé pour les collisions
	public int getPositionXIntArret() {
		if(getDirection() == Direction.RIGHT) {
			positionXX = Math.floor(positionX);
		}
		
		else  {
			positionXX = Math.ceil(positionX);
		}
		
		int positionXInt = (int) Math.round(positionXX);
		
		return positionXInt;
	}
	
	public int getPositionYIntArret() {
		if(getDirection() == Direction.DOWN) {
			positionYY = Math.floor(positionY);
		}
		else {
			positionYY = Math.ceil(positionY);
		}
		
		
		int positionYInt = (int) Math.round(positionYY);
		return positionYInt;
	}
	//sert à s'arreter à une bonne distance du mur...
	
	public int getPositionXArret(){
		int positionXInt = Math.round(positionX);
		int i = new Float(positionX).intValue();
		
		float decimale = positionX-(new Float(i).floatValue());
		
		System.out.println(decimale);
		if (decimale > 0.1) {
			return Math.round(positionX);
		}
		
		else {
			return (Math.round(positionX) - 1);
		}
		
	}
	
	public int getPositionXInt() {
		return Math.round(positionX);
	}
	
	public int getPositionYInt() {
		return Math.round(positionY);
	}
	
	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setSkin(Image skin) {
		this.skin = skin;
	}

	public float getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public float getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getGrandeurTiles() {
		return grandeurTiles;
	}

	public void setGrandeurTiles(int grandeurTiles) {
		this.grandeurTiles = grandeurTiles;
	}



}
