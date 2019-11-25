package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

//enum des positions possible

public class Jeu extends BasicGame {

	// déclaration variable
	private TiledMap map;
	private TiledMap accueil;
	private int tilesSize;
	private int mur;
	private Entite pacMan;

	// tests
	//ALLO

	public Jeu(String title) {
		super(title);
	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		map.render(0, 0);
		pacMan.apparaitre();
	}

	public void init(GameContainer gc) throws SlickException {
<<<<<<< HEAD
		map = new TiledMap("./map/carte.tmx");

=======
		map = new TiledMap("./map/map.tmx");
		//accueil = new TiledMap("./map/accueil.tmx")
		
>>>>>>> branch 'master' of https://github.com/lopio4123/projetPacMan.git
		tilesSize = 32;
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 1, 2, Direction.DOWN);
	}

	public void update(GameContainer gc, int i) throws SlickException {

		int mur = map.getLayerIndex("murs");
		// logs
		System.out.println("x : " + pacMan.getPositionX());
		System.out.println("y : " + pacMan.getPositionY());
		//System.out.println( "X : " + pacMan.getPositionX());
		//System.out.println(pacMan.getDirection());

		// controles
		Input input = gc.getInput();

		// droite
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

		// s'arrete si il y a un mur.
		// verifi le coté droit
		if (pacMan.getDirection() == Direction.RIGHT
				&& map.getTileId(pacMan.getPositionXIntArret() + 1, pacMan.getPositionYIntArret(), mur) != 0) {
			System.out.println("muuuuuuuuuuuuuuuuuuur droite");
			//pour que pac man sarrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté gauche
		else if (pacMan.getDirection() == Direction.LEFT
				&& map.getTileId(pacMan.getPositionXIntArret() - 1, pacMan.getPositionYIntArret(), mur) != 0) {
			System.out.println("muuuuuuuuuuuuuuuuuuur gauche");
			//pour que pac man sarrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté haut
		else if (pacMan.getDirection() == Direction.UP
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() - 1, mur) != 0) {
			System.out.println("muuuuuuuuuuuuuuuuuuur haut");
			//pour que pac man sarrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté bas
		else if (pacMan.getDirection() == Direction.DOWN
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() + 1, mur) != 0) {
			System.out.println("muuuuuuuuuuuuuuuuuuur bas");
			//pour que pac man sarrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}

		// deplacement********************************************************************************
		// droite
		if (pacMan.getDirection() == Direction.RIGHT) {
			pacMan.deplacementX((0.1*i) / 20);
		}
		// gauche
		else if (pacMan.getDirection() == Direction.LEFT) {
			pacMan.deplacementX((-0.1*i) / 20);
		}
		// haut
		else if (pacMan.getDirection() == Direction.UP) {
			pacMan.deplacementY((-0.1*i) / 20);
		}
		// bas
		else if (pacMan.getDirection() == Direction.DOWN) {
			pacMan.deplacementY((0.1*i) / 20);
		}
		// immobile
		else if (pacMan.getDirection() == Direction.NEUTRE) {

		}

		// if (map.getTileId(pacMan.getPositionXInt() + 5, pacMan.getPositionYInt(), mur
		// ) != 0) {

		// }

	}

}
