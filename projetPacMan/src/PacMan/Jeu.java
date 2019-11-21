package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

//enum des positions possible
enum Direction {
	UP, DOWN, RIGHT, LEFT
}

public class Jeu extends BasicGame {
	
	//déclaration variable
	private TiledMap map;
	private int tilesSize;
	private int mur;
	private Entite pacMan;
	
	//tests


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
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 1, 2);
	}

	public void update(GameContainer gc, int i) throws SlickException {
		System.out.println(i);
		if (map.getTileId(pacMan.getPositionXInt() + 5, pacMan.getPositionYInt(), mur ) != 0) {
			pacMan.deplacementX((0.1*i) / 50);
		}
		
		
	}



}
