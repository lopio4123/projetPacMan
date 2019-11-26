package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;
//import org.newdawn.slick.state.StateBasedGame;

public class Jeu extends BasicGame {


	// variables par rapport à la map
	private TiledMap map;
	private TiledMap accueil;
	private Entite pacMan;

	private int tilesSize;
	private int mur;
	private int numNiveau;
	private int vitesse;
	
	// variable menu
	//private StateBasedGame game;

	public Jeu(String title) 
	{
		super(title);
	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		map.render(0, 0);
		pacMan.apparaitre();
	}

	public void init(GameContainer gc) throws SlickException {
		// initialisation du niveau
		numNiveau = 1;
		
		// initialisation selon le niveau
		switch (numNiveau) {
		case 1:
			vitesse = 30;
			break;
		case 2:
			vitesse = 25;
			break;
		case 3:
			vitesse = 20;
			break;
		case 4:
			vitesse = 15;
			break;
		case 5:
			vitesse = 12;
			break;

		}
		// génération de la map
		map = new TiledMap("./map/map.tmx");
		tilesSize = 32;
		
		//Menu
		//this.game = game; //menu
		//map = new TiledMap("./map/accueil.tmx");
		
		// génération des entitées
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 1, 2, Direction.DOWN);

	}

	public void update(GameContainer gc, int i) throws SlickException {

		int mur = map.getLayerIndex("murs");
		// logs
		System.out.println("x : " + pacMan.getPositionX());
		System.out.println("y : " + pacMan.getPositionY());
		// System.out.println( "X : " + pacMan.getPositionX());
		// System.out.println(pacMan.getDirection());

		// Controle
		Input input = gc.getInput();

		// Droite
		if (input.isKeyPressed(Input.KEY_D)
				&& map.getTileId(pacMan.getPositionXInt() + 1, pacMan.getPositionYInt(), mur) == 0) {
			pacMan.setDirection(Direction.RIGHT);
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
		}
		// gauche
		if (input.isKeyPressed(Input.KEY_A)
				&& map.getTileId(pacMan.getPositionXInt() - 1, pacMan.getPositionYInt(), mur) == 0) {
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.LEFT);
		}
		// haut
		if (input.isKeyPressed(Input.KEY_W)
				&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() - 1, mur) == 0) {
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.UP);
		}
		// bas
		if (input.isKeyPressed(Input.KEY_S)
				&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() + 1, mur) == 0) {
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.DOWN);
		}

		// s'arrete si il y a un mur
		// verifi le coté droit
		if (pacMan.getDirection() == Direction.RIGHT
				&& map.getTileId(pacMan.getPositionXIntArret() + 1, pacMan.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté gauche
		else if (pacMan.getDirection() == Direction.LEFT
				&& map.getTileId(pacMan.getPositionXIntArret() - 1, pacMan.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté haut
		else if (pacMan.getDirection() == Direction.UP
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() - 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté bas
		else if (pacMan.getDirection() == Direction.DOWN
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() + 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}

		// ***************** Deplacement *****************
		
		// droite
		if (pacMan.getDirection() == Direction.RIGHT) {
			pacMan.deplacementX((0.1 * i) / vitesse);
		}
		// gauche
		else if (pacMan.getDirection() == Direction.LEFT) {
			pacMan.deplacementX((-0.1 * i) / vitesse);
		}
		// haut
		else if (pacMan.getDirection() == Direction.UP) {
			pacMan.deplacementY((-0.1 * i) / vitesse);
		}
		// bas
		else if (pacMan.getDirection() == Direction.DOWN) {
			pacMan.deplacementY((0.1 * i) / vitesse);
		}
		// immobile
		else if (pacMan.getDirection() == Direction.NEUTRE) {
			// rien ?
		}

	}


}
