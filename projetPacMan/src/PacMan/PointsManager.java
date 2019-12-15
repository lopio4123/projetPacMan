package PacMan;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PointsManager {
	private float hauteur;
	private float largeur;

	public LinkedList<Point> listePoint;
	
	private final Entite pacMan;

	private int tilesSize = 32;
	private Image image;
	private boolean pointGretta;
	private LinkedList<Fantomes> fantomes;

	private int cptScore;

	public PointsManager(String image, int grandeur, int grandeurTiles, Entite pacMan, boolean pointGretta, LinkedList<Fantomes> fantomes2) throws SlickException {
		this.image = new Image(image);
		hauteur = grandeur;
		largeur = grandeur;
		this.tilesSize = grandeurTiles;
		this.pacMan = pacMan;
		this.pointGretta = pointGretta;
		this.fantomes = fantomes2;
	}

	public void init(GameContainer gc) throws SlickException {

		listePoint = new LinkedList<Point>();

	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
		int pX = pacMan.getPositionXInt();
		int pY = pacMan.getPositionYInt();
		
		for (int i = 0; i < listePoint.size(); i++)
		{
			Point p = listePoint.get(i);
			
			if (pX == p.getPosX() && pY == p.getPosY())
			{
				listePoint.remove(i);
				if(pointGretta) {
					for (Fantomes fantome : fantomes) {
						fantome.setPositionX(8);
						fantome.setPositionY(12);
					}
				}
				cptScore += 100;
				break;
			}

		}

	}

	public void render(Graphics grcs) throws SlickException {

		for (int i = 0; i < listePoint.size(); i++) {

			Point p = listePoint.get(i);

			int x = p.getPosX();
			int y = p.getPosY();

			image.draw((x * tilesSize) + 11, (y * tilesSize) + 11, hauteur, largeur);

		}
	}

	public void add(int x, int y) {
		listePoint.add(new Point(x, y));
	}
	
	public LinkedList<Point> getListePoint() {
		return listePoint;
	}

	public void setListePoint(LinkedList<Point> listePoint) {
		this.listePoint = listePoint;
	}

}
