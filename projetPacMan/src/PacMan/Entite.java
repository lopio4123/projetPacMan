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
	public int getPositionXInt() {
		int positionXInt = Math.round(positionX);
		return positionXInt;
	}
	
	public int getPositionYInt() {
		int positionYInt = Math.round(positionY);
		return positionYInt;
	}
	//sert à s'arreter à une bonne distance du mur...
	/*
	public int getPositionXArret(){
		int positionXInt = Math.round(positionX);
		int i = new Float(positionX).intValue();
		float decimale = positionX-(new Float(i).floatValue());
		return decimale;
	}
	*/
	
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
