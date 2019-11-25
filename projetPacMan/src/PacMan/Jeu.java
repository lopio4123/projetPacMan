package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

public class Jeu extends BasicGame {

	// déclaration variable
	// variables par rapport à la map
	private TiledMap map;
	private TiledMap accueil;
	private int tilesSize;
	// caracteristiques des entitées
	private Entite pacMan;
	private int numNiveau;
	private int vitesse;
	// variable du fog of war
	private boolean[][] fogOfWar;
	int qteLignesFOW;
	int qteColonnesFOW;
	private int visibilityDistance;

	public Jeu(String title) {
		super(title);
	}

	public void init(GameContainer gc) throws SlickException {

		// génération de la map
		map = new TiledMap("./map/carte.tmx");
		tilesSize = 32;
		// initialisation des variables du fog of war
		qteLignesFOW = (gc.getHeight() - 2 * tilesSize) / tilesSize;
		qteColonnesFOW = (gc.getWidth() - 2 * tilesSize) / tilesSize;
		fogOfWar = new boolean[qteLignesFOW][qteColonnesFOW];

		// initialisation du niveau
		numNiveau = 5;
		switch (numNiveau) {
		case 1:
			vitesse = 25;
			break;
		case 2:
			vitesse = 21;
			break;
		case 3:
			fillFogOfWar();
			visibilityDistance = 8;
			vitesse = 18;
			break;
		case 4:
			fillFogOfWar();
			visibilityDistance = 6;
			vitesse = 13;
			break;
		case 5:
			fillFogOfWar();
			visibilityDistance = 4;
			vitesse = 10;
			break;

		}

		// génération des entitées
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 1, 2, Direction.DOWN);

	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		map.render(0, 0);
		pacMan.apparaitre();
		// rendu du fog of war
		obscurcir(grcs);
	}

	public void update(GameContainer gc, int i) throws SlickException {

		int mur = map.getLayerIndex("murs");
		// logs
		// System.out.println("fog of war : " + fogOfWar[1][1]);
		// System.out.println("y : " + pacMan.getPositionY());
		// System.out.println( "X : " + pacMan.getPositionX());
		// System.out.println(pacMan.getDirection());

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
			// pour que pac man sarrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté gauche
		else if (pacMan.getDirection() == Direction.LEFT
				&& map.getTileId(pacMan.getPositionXIntArret() - 1, pacMan.getPositionYIntArret(), mur) != 0) {
			// pour que pac man sarrete a un chiffre rond
			pacMan.setPositionX(Math.round(pacMan.getPositionX()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté haut
		else if (pacMan.getDirection() == Direction.UP
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() - 1, mur) != 0) {
			// pour que pac man sarrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// verifi le coté bas
		else if (pacMan.getDirection() == Direction.DOWN
				&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() + 1, mur) != 0) {
			// pour que pac man sarrete a un chiffre rond
			pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			pacMan.setDirection(Direction.NEUTRE);
		}
		// pour enlever le brouillard
		removeFogSquare(pacMan.getPositionYInt(), pacMan.getPositionXInt());

		// deplacement********************************************************************************
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

	}

	// initialise le fog of war
	private void fillFogOfWar() {
		System.out.println("oui, ca passe dans fillFogOfWar()");
		for (int i = 0; i < qteLignesFOW; i++) {
			for (int j = 0; j < qteColonnesFOW; j++) {
				fogOfWar[i][j] = true;
			}
		}
	}

	// fait apparaitre les cases initialisées en noir.
	private void obscurcir(Graphics grphcs) {
		int qteLignes = fogOfWar.length;
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

	// Enleve le fog of war selon les déplacements du personnage
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

}
