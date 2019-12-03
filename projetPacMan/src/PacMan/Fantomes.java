package PacMan;

import java.awt.Font;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Fantomes extends Entite {
	// declaration variable
	private int vitesse;
	private TiledMap map;
	private int randomNum;
	private int compteur = 0;

	/*
	 * int w = original_image.getWidth() nbCols; int h = original_image.getHeight()
	 * nbRows; int x = thisCol*w; int y = thisRow*h; subImage = new getSubImage(x,
	 * y, w, h); **
	 * 
	 * private int playersChoice = 1; //private int choice = 0; private boolean exit
	 * = false; private boolean play = false; private boolean propos = false;
	 * 
	 * private TiledMap map; private Image buttonPlay; private Image buttonExit;
	 * 
	 */
	// constructeur
	public Fantomes(String image, int grandeur, int grandeurTiles, int positionX, int positionY, Direction direction,
			int vitesse, TiledMap map) throws SlickException {
		super(image, grandeur, grandeurTiles, positionX, positionY, direction);
		this.vitesse = vitesse;
		this.map = map;
	}

	public void update(int i) {

		// verifi si sa direction est neutre et en choisi une nouvelle
		/*
		 * if(this.getPositionX()== (int)this.getPositionX() && this.getPositionY()==
		 * (int)this.getPositionY()) { System.out.println("case");
		 * this.setDirection(this.decideDirection()); }
		 */
		if (this.getDirection() == Direction.NEUTRE) {
			this.setDirection(this.decidationDirection());
		}
		// deplacement
		if (this.getDirection() == Direction.RIGHT) {
			this.deplacementX((0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.LEFT) {
			this.deplacementX((-0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.UP) {
			this.deplacementY((-0.1 * i) / vitesse);
		} else if (this.getDirection() == Direction.DOWN) {
			this.deplacementY((0.1 * i) / vitesse);
		}
		// verifi si il peux tourner.
		int sol = map.getLayerIndex("sols");
		// System.out.println((this.getDirection() == Direction.UP ||
		// this.getDirection() == Direction.DOWN)
		// && map.getTileId(this.getPositionXIntArret() - 1,
		// this.getPositionYIntArret(), sol) != 0);
		if (compteur <= 10) {
			compteur++;
		}
		else {
			this.siTourne(this.getDirection(), map);
			//compteur = 0;
		}

		// test
		/*
		 * Random rand = new Random(); int virage = rand.nextInt(2); int sol =
		 * map.getLayerIndex("sols"); if (dejaFait == false) { if ((this.getDirection()
		 * == Direction.UP || this.getDirection() == Direction.DOWN) &&
		 * map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(),
		 * sol) != 0) { dejaFait = true; // System.out.println("detection"); if (virage
		 * == 1) { this.setPositionY(Math.round(this.getPositionY()));
		 * this.setDirection(Direction.LEFT);
		 * 
		 * } } }
		 */

		// arret vers un mur
		int mur = map.getLayerIndex("murs");
		// verifi le coter droit
		if (this.getDirection() == Direction.RIGHT
				&& map.getTileId(this.getPositionXIntArret() + 1, this.getPositionYIntArret(), mur) != 0) {
			// pour que le fantome s'arrete a un chiffre rond
			this.setPositionX(Math.round(this.getPositionX()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté gauche
		else if (this.getDirection() == Direction.LEFT
				&& map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(), mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionX(Math.round(this.getPositionX()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté haut
		else if (this.getDirection() == Direction.UP
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() - 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionY(Math.round(this.getPositionY()));
			this.setDirection(Direction.NEUTRE);
		}
		// Verifie le coté bas
		else if (this.getDirection() == Direction.DOWN
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() + 1, mur) != 0) {
			// pour que pac man s'arrete a un chiffre rond
			this.setPositionY(Math.round(this.getPositionY()));
			this.setDirection(Direction.NEUTRE);
		}
	}

	private int scanDirection() {
		/*
		 * Direction droit + 1 direction gauche + 2 direction haut + 4 direction bas + 8
		 */
		int codeDirection = 0;
		int mur = map.getLayerIndex("murs");
		// droit
		if (map.getTileId(this.getPositionXIntArret() + 1, this.getPositionYIntArret(), mur) == 0) {
			codeDirection++;
		}
		// gauche
		if (map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(), mur) == 0) {
			codeDirection += 2;
		}
		// haut
		if (map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() - 1, mur) == 0) {
			codeDirection += 4;
		}
		// bas
		if (map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() + 1, mur) == 0) {
			codeDirection += 8;
		}

		return codeDirection;

	}

	private Direction decidationDirection() {
		Random rand = new Random();
		int random = rand.nextInt(4);
		Direction direction = null;

		if (random == 0) {
			direction = Direction.RIGHT;
		} else if (random == 1) {
			direction = Direction.LEFT;
		} else if (random == 2) {
			direction = Direction.UP;
		} else if (random == 3) {
			direction = Direction.DOWN;
		}
		return direction;
	}

	// decide de sa direction quand il est neutre.
	private Direction decideDirection() {
		Direction direction = null;
		int codeDirection = scanDirection();
		boolean isRight = false;
		boolean isLeft = false;
		boolean isUp = false;
		boolean isDown = false;
		// bas
		if (codeDirection > 8) {
			isDown = true;
			codeDirection -= 8;

		}
		// haut
		if (codeDirection > 4) {
			isUp = true;
			codeDirection -= 4;

		}
		// gauche
		if (codeDirection > 2) {
			System.out.println("gauche est mis a true");
			isLeft = true;
			codeDirection -= 2;

		}
		// droite
		if (codeDirection == 1) {
			isRight = true;
			codeDirection -= 1;

		}

		do {
			Random rand = new Random();
			int random = rand.nextInt(4);
			System.out.println(random);
			System.out.println(isLeft);
			if (random == 0 && isRight == true) {
				direction = Direction.RIGHT;
			} else if (random == 1 && isLeft == true) {
				System.out.println("suppose tourne");
				direction = Direction.LEFT;
			} else if (random == 2 && isUp == true) {
				direction = Direction.UP;
			} else if (random == 3 && isDown == true) {
				direction = Direction.DOWN;
			}
		} while (direction == null);

		return direction;
	}

	private void siTourne(Direction direction, TiledMap map) {
		Random rand = new Random();
		int virage = rand.nextInt(30);
		int sol = map.getLayerIndex("sols");
		if ((this.getDirection() == Direction.UP || this.getDirection() == Direction.DOWN)
				&& map.getTileId(this.getPositionXIntArret() - 1, this.getPositionYIntArret(), sol) != 0) {
			System.out.println("detection");
			System.out.println(virage);
			if (virage == 1) {
				this.setPositionY(Math.round(this.getPositionY()));
				this.setDirection(Direction.LEFT);
				compteur = 0;

			}
		}

		else if ((this.getDirection() == Direction.UP || this.getDirection() == Direction.DOWN)
				&& map.getTileId(this.getPositionXIntArret() + 1, this.getPositionYIntArret(), sol) != 0) { // System.out.println("detection");
			if (virage == 2) {
				this.setPositionY(Math.round(this.getPositionY()));
				this.setDirection(Direction.RIGHT);
				compteur = 0;
			}
		}

		else if ((this.getDirection() == Direction.RIGHT || this.getDirection() == Direction.LEFT)
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() - 1, sol) != 0) { //
			System.out.println("detection");
			if (virage == 3) {
				System.out.println("il tourne");
				this.setDirection(Direction.UP);
				compteur = 0;
			}
			
		} else if ((this.getDirection() == Direction.RIGHT || this.getDirection() == Direction.LEFT)
				&& map.getTileId(this.getPositionXIntArret(), this.getPositionYIntArret() + 1, sol) != 0) { //
			System.out.println("detection");
			if (virage == 4) {
				System.out.println("il tourne");
				this.setDirection(Direction.DOWN);
				compteur = 0;
			}
		}

	}
}
