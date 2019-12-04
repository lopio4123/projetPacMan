package PacMan;

import java.util.LinkedList;

import javax.swing.JButton;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

public class Jeu extends BasicGame {

	// Variables Map
	private TiledMap map;
	private TiledMap accueil;
	private Entite pacMan;

	private int tilesSize = 32;
	private int mur;
	private int vitesse;

	// Variable du fog of war
	private boolean[][] fogOfWar;
	int qteLignesFOW;
	int qteColonnesFOW;
	private int visibilityDistance;

	// Variable petit point
	private boolean[][] littlePoint;
	int qteLignesLittlePoint;
	int qteColonnesLittlePoint;

	// variables fantomes
	private LinkedList<Fantomes> fantomes;

	// variables points
	private int points;

	// variables de l'audio
	private Music musique;

	public Jeu(String title) {
		super(title);
	}

	public void init(GameContainer gc) throws SlickException {
		// musique
		musique = new Music("./son/Music.wav");
		musique.play();
		musique.loop();
		musique.setVolume(0.2f);
		// initialisation des variables du fog of war
		qteLignesFOW = (gc.getHeight() - 2 * tilesSize) / tilesSize;
		qteColonnesFOW = (gc.getWidth() - 2 * tilesSize) / tilesSize;
		fogOfWar = new boolean[qteLignesFOW][qteColonnesFOW];
		
		// initialisation des variables pour les petits points
		qteLignesLittlePoint = (gc.getHeight() - 2 * tilesSize) / tilesSize;
		qteColonnesLittlePoint = (gc.getWidth() - 2 * tilesSize) / tilesSize;
		littlePoint = new boolean[qteLignesLittlePoint][qteColonnesLittlePoint];
		
		// initialisation du niveau
	

		int numNiveau = 5; //niveau 1, il y a du bleu ne t'inquite pas c'est pour moi

		switch (numNiveau) {
		case 0:
			// map = new TiledMap("./map/accueil.tmx");
			break;
		case 1:
			map = new TiledMap("./map/map.tmx");
			fillLittlePoint();
			vitesse = 25;
			points = map.getObjectGroupCount();
			break;
		case 2:
			map = new TiledMap("./map/map.tmx");
			vitesse = 21;
			break;
		case 3:
			map = new TiledMap("./map/map.tmx");
			fillFogOfWar();
			visibilityDistance = 8;
			vitesse = 18;
			break;
		case 4:
			map = new TiledMap("./map/map.tmx");
			fillFogOfWar();
			visibilityDistance = 6;
			vitesse = 13;
			break;
		case 5:
			map = new TiledMap("./map/map.tmx");
			fillFogOfWar();
			visibilityDistance = 4;
			vitesse = 10;
			break;

		}

		pacMan = new Entite("./image/furry.png", tilesSize, tilesSize, 8, 22, Direction.NEUTRE);
		// variables fantomes
		fantomes = new LinkedList<>();
		

		
		for (int i = 0; i < 4; i++)
		{
			if (i == 0)
			{
				fantomes.add(new Fantomes("./image/shrek.png", tilesSize, tilesSize, 9 + i, 22, Direction.RIGHT,vitesse, map));
			}
			else if (i == 1) {
				fantomes.add(new Fantomes("./image/sanic.png", tilesSize, tilesSize, 9 + i, 22, Direction.RIGHT,
						vitesse, map));
			} else if (i == 2) {
				fantomes.add(new Fantomes("./image/noob.png", tilesSize, tilesSize, 9 + i, 22, Direction.RIGHT, vitesse,
						map));
			} else if (i == 3) {
				fantomes.add(new Fantomes("./image/bobshrek.png", tilesSize, tilesSize, 9 + i, 22, Direction.RIGHT,
						vitesse, map));
			}

			else if (i == 1)
			{
				for (int i1 = 0; i1 < 4; i1++) 
				{
					if (i1 == 0) 
					{
						fantomes.add(new Fantomes("./image/shrek.png", tilesSize, tilesSize, 16, 21, Direction.UP, vitesse, map));
					}
					else if (i1 == 1) 
					{
						fantomes.add(new Fantomes("./image/sanic.png", tilesSize, tilesSize, 9 + i1, 22, Direction.RIGHT, vitesse, map));
					} 
					else if (i1 == 2) 
					{
						fantomes.add(new Fantomes("./image/noob.png", tilesSize, tilesSize, 9 + i1, 22, Direction.RIGHT, vitesse, map));
					} 
					else if (i1 == 3) 
					{
						fantomes.add(new Fantomes("./image/bobshrek.png", tilesSize, tilesSize, 9 + i1, 22, Direction.RIGHT, vitesse, map));
					}
				}

			}
				

		}

	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		map.render(0, 0);
		pacMan.apparaitre();
		for (Fantomes fantome : fantomes) {
			fantome.apparaitre();
		}
		// pour les hitbox
		for (Fantomes fantome : fantomes) {
			fantome.render(grcs);
		}
		pacMan.render(grcs);
		// rendu du fog of war
		obscurcir(grcs);

		int sols = map.getLayerIndex("sols");

		// rendu petit point
		// if (map.getTileId(qteLignesLittlePoint, qteColonnesLittlePoint, sols) == 0)
		// {
		genererPoints(grcs);
		// }

	}

	public void update(GameContainer gc, int i) throws SlickException {

		// logs

		// Controle
		Input input = gc.getInput();
		int mur = map.getLayerIndex("murs");

		// Droite
		if ((input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT))
				&& map.getTileId(pacMan.getPositionXInt() + 1, pacMan.getPositionYInt(), mur) == 0) {
			pacMan.setDirection(Direction.RIGHT);
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
		}
		// Gauche
		if ((input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT))
				&& map.getTileId(pacMan.getPositionXInt() - 1, pacMan.getPositionYInt(), mur) == 0) {
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.LEFT);
		}
		// Haut
		if ((input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP))
				&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() - 1, mur) == 0) {
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.UP);
		}
		// Bas
		if ((input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN))
				&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() + 1, mur) == 0) {
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.DOWN);
		}

		// S'arrete s'il y a un mur

		// Verifie le cot� droit
		if (pacMan.getDirection() == Direction.RIGHT
				&& map.getTileId(pacMan.getPositionXIntArret() + 1, pacMan.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// Verifie le cot� gauche
		else if (pacMan.getDirection() == Direction.LEFT
				&& map.getTileId(pacMan.getPositionXIntArret() - 1, pacMan.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// Verifie le cot� haut
		else if (pacMan.getDirection() == Direction.UP
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() - 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// Verifie le cot� bas
		else if (pacMan.getDirection() == Direction.DOWN
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() + 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// Pour enlever le brouillard
		removeFogSquare(pacMan.getPositionYInt(), pacMan.getPositionXInt());

		// ***************** D�placement *****************

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

		}

		// fantomes

		for (Fantomes fantome : fantomes) {
			fantome.update(i);
		}
		// intersection
		for (Fantomes fantome : fantomes) {
			if (pacMan.hitBox.intersects(fantome.hitBox)) {
				System.out.println("toucheeeeee");
			}
		}
	}

	// Initialise le Fog Of War
	private void fillFogOfWar() {
		System.out.println("oui, ca passe dans fillFogOfWar()");
		for (int i = 0; i < qteLignesFOW; i++) {
			for (int j = 0; j < qteColonnesFOW; j++) {
				fogOfWar[i][j] = true;
			}
		}
	}

	// Fait apparaitre les cases initialis�es en noir
	private void obscurcir(Graphics grphcs) {
		int qteLignes = fogOfWar.length - 1;
		int qteColonnes = fogOfWar[0].length;
		int posX = tilesSize;
		int posY = tilesSize;
		grphcs.setColor(Color.darkGray);
		for (int i = 0; i < qteLignes; i++) {
			for (int j = 0; j < qteColonnes; j++) {
				if (fogOfWar[i][j]) {
					grphcs.fillRect(posX, posY, tilesSize, tilesSize);
				}
				posX += tilesSize;
			}
			posX = tilesSize;
			posY += tilesSize;
		}
	}

	// Enleve le Fog Of War selon les d�placements du personnage
	private void removeFogSquare(int row, int column) {
		int rowIndex = row - 1;
		int columnIndex = column - 1;
		fogOfWar[rowIndex][columnIndex] = false;

		int min;
		int max;
		for (int i = 1; i < visibilityDistance; i++) {
			switch (pacMan.getDirection()) {
			case UP:
				min = column - 1 - visibilityDistance / 2;
				max = min + visibilityDistance;
				if (min < 0) {
					min = 0;
				}
				if (max > qteColonnesFOW) {
					max = qteColonnesFOW;
				}
				rowIndex--;
				for (int j = min; j < max; j++) {
					if (rowIndex >= 0) {
						fogOfWar[rowIndex][j] = false;
					}
				}
				break;
			case DOWN:
				min = column - 1 - visibilityDistance / 2;
				max = min + visibilityDistance;
				if (min < 0) {
					min = 0;
				}
				if (max > qteColonnesFOW) {
					max = qteColonnesFOW;
				}
				rowIndex++;
				for (int j = min; j < max; j++) {
					if (rowIndex < qteLignesFOW) {
						fogOfWar[rowIndex][j] = false;
					}
				}
				break;
			case RIGHT:
				min = row - 1 - visibilityDistance / 2;
				max = min + visibilityDistance;
				if (min < 0) {
					min = 0;
				}
				if (max > qteLignesFOW) {
					max = qteLignesFOW;
				}
				columnIndex++;
				for (int j = min; j < max; j++) {
					if (columnIndex < qteColonnesFOW) {
						fogOfWar[j][columnIndex] = false;
					}
				}
				break;
			case LEFT:
				min = row - 1 - visibilityDistance / 2;
				max = min + visibilityDistance;
				if (min < 0) {
					min = 0;
				}
				if (max > qteLignesFOW) {
					max = qteLignesFOW;
				}
				columnIndex--;
				for (int j = min; j < max; j++) {
					if (columnIndex >= 0) {
						fogOfWar[j][columnIndex] = false;
					}
				}
				break;
			}
		}
	}

	// ************************** PETITS POINTS ******************************

	// Initialise les petits points
	private void fillLittlePoint() {
		System.out.println("yes");
		for (int i = 0; i < qteLignesLittlePoint; i++) {
			for (int j = 0; j < qteColonnesLittlePoint; j++) {
				littlePoint[i][j] = true;
			}
		}
	}

	private void genererPoints(Graphics grphcs) {
		int qteLignes = littlePoint.length - 1;
		int qteColonnes = littlePoint[0].length;
		int posX = tilesSize;
		int posY = tilesSize;
		// grphcs.draw(petitPoint);
		// if (map.getTileId(qteLignesLittlePoint, qteColonnesLittlePoint, mur) != 0)

		grphcs.setColor(Color.orange);

		for (int i = 0; i < qteLignes; i++) {
			for (int j = 0; j < qteColonnes; j++) {
				if (littlePoint[i][j]) {
					grphcs.fillRect(posX, posY, tilesSize, tilesSize);
				}
				posX += tilesSize;
			}
			posX = tilesSize;
			posY += tilesSize;
		}

	}

}