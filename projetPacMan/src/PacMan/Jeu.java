package PacMan;

import java.util.LinkedList;

import org.newdawn.slick.tiled.TiledMap;

import Menu.Accueil;

import org.newdawn.slick.*;

public class Jeu extends BasicGame {

	// Variables Map
	private TiledMap map;
	private Entite pacMan;
	private Accueil affiche;
	private PointsManager pointManager;
	private PointsManager pointGretta;
	
	private Animation anim_UP;
	private Animation anim_DOWN;
	private Animation anim_RIGHT;
	private Animation anim_LEFT;
	private Direction direction;
	private int img_posX;
	private int img_posY;
	
	private int numNiveau;
	private int tilesSize = 32;
	private int vitesse;

	// Variable du fog of war
	private boolean[][] fogOfWar;
	int qteLignesFOW;
	int qteColonnesFOW;
	private int visibilityDistance;

	// Variables fantomes
	private LinkedList<Fantomes> fantomes;
	boolean isTouche;

	// variables de l'audio
	private Music musique;

	// variable de vies
	private int nbVie;
	private Image heart;

	public Jeu(String title) {
		super(title);
	}
	
	
	public void init(GameContainer gc) throws SlickException {
		
		affiche = new Accueil();
		affiche.init(gc);
		
		/*anim_UP = getAnimation(2);
		anim_DOWN = getAnimation(3);
		anim_RIGHT = getAnimation(0);
		anim_LEFT = getAnimation(1);
		direction = Direction.RIGHT;*/

		isTouche = false;

		pacMan = new Entite("./image/PacMan.png", tilesSize, tilesSize, 8, 22, Direction.NEUTRE);
		fantomes = new LinkedList<>();
		
		//points
		
		pointManager = new PointsManager("./image/point.png", 10, 32, pacMan, false, fantomes);
		pointGretta = new PointsManager("./image/greta.png", 20, 32, pacMan, true, fantomes);
		
		pointManager.init(gc);
		pointGretta.init(gc);

		// Musique
		musique = new Music("./son/Music.wav");
		musique.play();
		musique.loop();
		musique.setVolume(0.2f);

		// initialisation des variables du fog of war
		qteLignesFOW = (gc.getHeight() - 2 * tilesSize) / tilesSize;
		qteColonnesFOW = (gc.getWidth() - 2 * tilesSize) / tilesSize;
		fogOfWar = new boolean[qteLignesFOW][qteColonnesFOW];

		// initialisation du niveau

		nbVie = 3;
		if(numNiveau == 0) {
			numNiveau = 1;
		}
		

		switch (numNiveau) {
		case 1:
			map = new TiledMap("./map/map.tmx");
			vitesse = 25;
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

		// variable personnage principal
		heart = new Image("./image/heart.png");

		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				fantomes.add(new Fantomes("./image/Inky.png", tilesSize, tilesSize, 9, 12, Direction.UP, vitesse, map));
			} else if (i == 1) {
				fantomes.add(
						new Fantomes("./image/Blinky.png", tilesSize, tilesSize, 9, 12, Direction.UP, vitesse, map));
			} else if (i == 2) {
				fantomes.add(
						new Fantomes("./image/Clyde.png", tilesSize, tilesSize, 8, 12, Direction.UP, vitesse, map));
			} else if (i == 3) {
				fantomes.add(
						new Fantomes("./image/Pinky.png", tilesSize, tilesSize, 8, 12, Direction.UP, vitesse, map));
			}

		}

		int mur = map.getLayerIndex("murs");

		for (int i = 0; i < map.getWidth(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				if (map.getTileId(i, j, mur) == 0) // si pas de murs afficher point
				{
					if (!((i >= 8 && i <= 10) && (j >= 11 && j <= 12))) {
						pointManager.add(i, j);
					}
				}
			}

		}
		//initialisation pointgretta
		pointGretta.add(1, 7);
		pointGretta.add(16, 7);

	}

	public void render(GameContainer gc, Graphics grcs) throws SlickException {
		
		map.render(0, 0);
		
		if (!affiche.isOpenMenu()) {
			for (int i = 0; i < nbVie; i++) {
				heart.draw((3 + i) * tilesSize, 24 * tilesSize, 32, 32);
			}
		}

		pointManager.render(grcs);
		pointGretta.render(grcs);
		pacMan.render(grcs);

		for (Fantomes fantome : fantomes) {
			fantome.render(grcs);
		}

		// Rendu du fog of war
		obscurcir(grcs);

		// Rendu accueil et pause
		affiche.render();
		
		/*int posX = img_posX * tilesSize;
		int posY = img_posY * tilesSize;
		
		switch(direction) {
		case UP :
			anim_UP.draw(posX, posY);
		case DOWN :
			anim_DOWN.draw(posX, posY);
			break;
		case RIGHT :
			anim_RIGHT.draw(posX, posY);
			break;
		case LEFT :
			anim_LEFT.draw(posX, posY);
			break;
		}*/

	}

	public void update(GameContainer gc, int i) throws SlickException {

		// faire réapparaitre le fog of war
		if (numNiveau == 5) {
			fillFogOfWar();
		}
		
		if(pointManager.listePoint.size() == 0) {
			numNiveau ++;
			init(gc);
		}
		
		//UPDATE PERSONNAGE
		/*anim_RIGHT.update(i);
		anim_LEFT.update(i);
		anim_UP.update(i);
		anim_DOWN.update(i);*/

		Input input = gc.getInput();
		
		affiche.update(gc, i, this);
		pointManager.update(gc, i);
		pointGretta.update(gc, i);

		// aller a l'accueil et recommencer les niveaux
		if (input.isKeyPressed(Input.KEY_ENTER) && affiche.isOpenPause()) {
			init(gc);
		}

		if (!affiche.isOpenMenu() && !affiche.isOpenPause()) {

			// intersection
			for (Fantomes fantome : fantomes) {
				if (pacMan.hitBox.intersects(fantome.hitBox)) {
					nbVie--;
					// reset le nb de poritions

					isTouche = true;

					pacMan.setPositionX(8);
					pacMan.setPositionY(22);

					// pour etre sure que le vies ne sois jamais en dessous de 0
					if (nbVie <= 0) {
						nbVie = 0;
						System.out.println("GAME OVER ! TRY AGAIN");
						numNiveau = 1;
						init(gc);
					}
				}
			}
			if (isTouche) {
				for (Fantomes fantome : fantomes) {
					fantome.setPositionX(8);
					fantome.setPositionY(12);
					isTouche = false;
				}
			}

			for (Fantomes fantome : fantomes) {
				fantome.update(i);
				
			}

			// Controle

			int mur = map.getLayerIndex("murs");

			// Droite
			if ((input.isKeyPressed(Input.KEY_D))
					&& map.getTileId(pacMan.getPositionXInt() + 1, pacMan.getPositionYInt(), mur) == 0) {
	        	//direction = Direction.RIGHT;
	        	pacMan.setDirection(Direction.RIGHT);
				pacMan.setPositionY(Math.round(pacMan.getPositionY()));
			}
			// Gauche
			if ((input.isKeyPressed(Input.KEY_A))
					&& map.getTileId(pacMan.getPositionXInt() - 1, pacMan.getPositionYInt(), mur) == 0) {
	        	//direction = Direction.LEFT;
	        	pacMan.setPositionY(Math.round(pacMan.getPositionY()));
				pacMan.setDirection(Direction.LEFT);
			}
			// Haut
			if ((input.isKeyPressed(Input.KEY_W))
					&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() - 1, mur) == 0) {
	        	//direction = Direction.UP;
	        	pacMan.setPositionX(Math.round(pacMan.getPositionX()));
				pacMan.setDirection(Direction.UP);
			}
			// Bas
			if ((input.isKeyPressed(Input.KEY_S))
					&& map.getTileId(pacMan.getPositionXInt(), pacMan.getPositionYInt() + 1, mur) == 0) {
	        	//direction = Direction.DOWN;
	        	pacMan.setPositionX(Math.round(pacMan.getPositionX()));
				pacMan.setDirection(Direction.DOWN);
			}

			// Verifie le cotï¿½ droit
			if (pacMan.getDirection() == Direction.RIGHT
					&& map.getTileId(pacMan.getPositionXIntArret() + 1, pacMan.getPositionYIntArret(), mur) != 0) {
				// pour que pac man s'arrete a un chiffre rond
				pacMan.setPositionX(Math.round(pacMan.getPositionX()));
				pacMan.setDirection(Direction.NEUTRE);

			}
			// Verifie le cotï¿½ gauche
			else if (pacMan.getDirection() == Direction.LEFT
					&& map.getTileId(pacMan.getPositionXIntArret() - 1, pacMan.getPositionYIntArret(), mur) != 0) {
				// pour que pac man s'arrete a un chiffre rond
				pacMan.setPositionX(Math.round(pacMan.getPositionX()));
				pacMan.setDirection(Direction.NEUTRE);
			}
			// Verifie le cotï¿½ haut
			else if (pacMan.getDirection() == Direction.UP
					&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() - 1, mur) != 0) {
				// pour que pac man s'arrete a un chiffre rond
				pacMan.setPositionY(Math.round(pacMan.getPositionY()));
				pacMan.setDirection(Direction.NEUTRE);
			}
			// Verifie le cotï¿½ bas
			else if (pacMan.getDirection() == Direction.DOWN
					&& map.getTileId(pacMan.getPositionXIntArret(), pacMan.getPositionYIntArret() + 1, mur) != 0) {
				// pour que pac man s'arrete a un chiffre rond
				pacMan.setPositionY(Math.round(pacMan.getPositionY()));
				pacMan.setDirection(Direction.NEUTRE);
			}

			// Pour enlever le brouillard
			removeFogSquare(pacMan.getPositionYInt(), pacMan.getPositionXInt());

			// ***************** Dï¿½placement *****************

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

	}

	// ************************** FOG OF WAR ******************************

	// Initialise le Fog Of War
	private void fillFogOfWar() {
		System.out.println("oui, ca passe dans fillFogOfWar()");
		for (int i = 0; i < qteLignesFOW; i++) {
			for (int j = 0; j < qteColonnesFOW; j++) {
				fogOfWar[i][j] = true;
			}
		}
	}

	// Fait apparaitre les cases initialisï¿½es en noir
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

	// Enleve le Fog Of War selon les dï¿½placements du personnage
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