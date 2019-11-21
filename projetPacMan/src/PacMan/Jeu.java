package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

//enum des positions possible

public class Jeu extends BasicGame {

	// déclaration variable
	private TiledMap map;
	private int tilesSize;
	private int mur;
	private Entite pacMan;

	// tests

	public Jeu(String title) {
		super(title);
	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		map.render(0, 0);
		pacMan.apparaitre();
	}

	public void init(GameContainer gc) throws SlickException {
		map = new TiledMap("./map/carte.tmx");
		int mur = map.getLayerIndex("mur");
		tilesSize = 32;
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 1, 2, Direction.DOWN);
	}

	public void update(GameContainer gc, int i) throws SlickException {
		// controles
		Input input = gc.getInput();
		// droite
		if (input.isKeyPressed(Input.KEY_D)) {
			pacMan.setDirection(Direction.RIGHT);
		}
		// gauche
		if (input.isKeyPressed(Input.KEY_A)) {
			pacMan.setDirection(Direction.LEFT);
		}
		// haut
		if (input.isKeyPressed(Input.KEY_W)) {
			pacMan.setDirection(Direction.UP);
		}
		// bas
		if (input.isKeyPressed(Input.KEY_S)) {
			pacMan.setDirection(Direction.DOWN);
		}
		// deplacement********************************************************************************
		// droite
		if (pacMan.getDirection() == Direction.RIGHT) {
			pacMan.deplacementX((0.1) / 50);
		}
		// gauche
		else if (pacMan.getDirection() == Direction.LEFT) {
			pacMan.deplacementX((-0.1) / 50);
		}
		// haut
		else if (pacMan.getDirection() == Direction.UP) {
			pacMan.deplacementY((-0.1) / 50);
		}
		// bas
		else if (pacMan.getDirection() == Direction.DOWN) {
			pacMan.deplacementY((0.1) / 50);
		}

		// if (map.getTileId(pacMan.getPositionXInt() + 5, pacMan.getPositionYInt(), mur
		// ) != 0) {

		// }

	}

}
