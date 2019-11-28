package PacMan;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.*;

public class Jeu extends BasicGame 
{

	// Variables par rapport à la map
	private TiledMap map;
	private TiledMap accueil;
	private Entite pacMan;
	
	// Variable menu
	private boolean isAccueil = false;
	
	//Fantomes
	private Image fantomes;
	private int fantomes_caseX;
	private int fantomes_caseY;;
	private int fantomes_movingStep;
	private int fantomes_size;
	
	private int tilesSize;
	private int mur;
	
	// caracteristiques des entitées
	private int numNiveau;
	private int vitesse;
	
	// variable du fog of war
	private boolean[][] fogOfWar;
	int qteLignesFOW;
	int qteColonnesFOW;
	private int visibilityDistance;

	public Jeu(String title) 
	{
		super(title);
	}
	
	// Animation fantomes
	private Animation getAnimation(int rowY)
	{
		Animation anim = new Animation(false);
		for(int x = 0; x < 5; x++)
		{
			anim.addFrame(fantomes.getSubImage(x*32, rowY*32, 32, 32), 50);
		}
		return anim;
	}

	public void init(GameContainer gc) throws SlickException 
	{
	
		// Génération de la map
		if (isAccueil == false) 
		{
			map = new TiledMap("./map/map.tmx");
			tilesSize = 32;
		}
		
		// Fantomes
		fantomes = new Image("./image/hommes.png");
		fantomes_caseX = 10;
		fantomes_caseY = 10;
		fantomes_movingStep = 1;
		fantomes_size = tilesSize;
		

		// Menu
		/*if (isAccueil == false)	
		{
			map = new TiledMap("./map/accueil.tmx");
			tilesSize = 32;	
		}*/
		
		// initialisation des variables du fog of war
		qteLignesFOW = (gc.getHeight() - 2 * tilesSize) / tilesSize;
		qteColonnesFOW = (gc.getWidth() - 2 * tilesSize) / tilesSize;
		fogOfWar = new boolean[qteLignesFOW][qteColonnesFOW];

		// initialisation du niveau
		numNiveau = 1;
		
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
		pacMan = new Entite("./image/furry.jpg", tilesSize, tilesSize, 8, 22, Direction.NEUTRE);

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
		// pour enlever le brouillard
		removeFogSquare(pacMan.getPositionYInt(), pacMan.getPositionXInt());

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

	
	
	
	// Initialise le Fog Of War
	private void fillFogOfWar() {
		System.out.println("oui, ca passe dans fillFogOfWar()");
		for (int i = 0; i < qteLignesFOW; i++) {
			for (int j = 0; j < qteColonnesFOW; j++) {
				fogOfWar[i][j] = true;
			}
		}
	}

	// Fait apparaitre les cases initialisées en noir
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

	// Enleve le Fog Of War selon les déplacements du personnage
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
