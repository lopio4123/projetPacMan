package Menu;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Accueil {

	int accueilChoixPlayer = 0;

	// Variable Menu d'accueil
	public boolean isOpenMenu = true;
	private boolean accueilPlay = false;
	private boolean accueilExit = false;
	private TiledMap mapMenu;
	private Button buttonPlay;
	private Button buttonExit;

	// Variable de pause
	public boolean isOpenPause = false;
	private boolean pauseReprendre = false;
	private boolean pauseMenu = false;
	private TiledMap mapPause;
	private Button buttonPause;
	private Button buttonReprendre;
	private Button buttonMenu;

	public Accueil() {

	}

	public void init(GameContainer gc) throws SlickException {
		mapMenu = new TiledMap("./map/accueil.tmx");
		buttonPlay = new Button("./image/play2.png", 250, 200, 100, 50);
		buttonExit = new Button("./image/exit.png", 250, 260, 100, 50);
		buttonPlay.init(gc);
		buttonExit.init(gc);
		buttonPlay.setValeurActive(0);
		buttonExit.setValeurActive(1);

		mapPause = new TiledMap("./map/pause.tmx");
		buttonReprendre = new Button("./image/reprendre.png", 230, 380, 150, 50);
		buttonMenu = new Button("./image/menu.png", 225, 430, 150, 50);
		buttonReprendre.init(gc);
		buttonMenu.init(gc);
		buttonReprendre.setValeurActive(2);		//0
		buttonMenu.setValeurActive(3);			//1
		
		buttonPause = new Button("/image/pause.png", 240, 305, 90, 30);
		buttonPause.init(gc);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		buttonPlay.update(gc, delta);
		buttonExit.update(gc, delta);
		buttonReprendre.update(gc, delta);
		buttonMenu.update(gc, delta);
		buttonPause.update(gc, delta);

		if (isOpenMenu == true) // menu est affiché
		{
			if (input.isKeyPressed(Input.KEY_DOWN)) {
				accueilChoixPlayer++;
			}
			if (input.isKeyPressed(Input.KEY_UP)) {
				accueilChoixPlayer--;
			}
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				isOpenMenu = false;
			}
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				isOpenPause = false;
			}
		}
		else // jeux en cours
		{
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				isOpenPause = true;
			}

			if (isOpenPause == true) {
				if (input.isKeyPressed(Input.KEY_DOWN)) {
					accueilChoixPlayer++;
				}
				if (input.isKeyPressed(Input.KEY_UP)) {
					accueilChoixPlayer--;
				}
				if (input.isKeyPressed(Input.KEY_ENTER)) {

					isOpenPause = false;
				}
			}

		}

	}

	public void render() throws SlickException {
		if (isOpenMenu == true) {
			mapMenu.render(0, 0);
			buttonPlay.render();
			buttonExit.render();
		}
		if (isOpenPause == true && isOpenMenu == false) {
			mapPause.render(0, 0);
			buttonReprendre.render();
			buttonMenu.render();
			buttonPause.render();
		}

	}

	/**** SETTERS AND GETTERS ****/

	public boolean isAccueilPlay() {
		return accueilPlay;
	}

	public void setAccueilPlay(boolean accueilPlay) {
		this.accueilPlay = accueilPlay;
	}

	public boolean isAccueilExit() {
		return accueilExit;
	}

	public void setAccueilExit(boolean accueilExit) {
		this.accueilExit = accueilExit;
	}

	public int getAccueilChoixPlayer() {
		return accueilChoixPlayer;
	}

	public void setAccueilChoixPlayer(int accueilChoixPlayer) {
		this.accueilChoixPlayer = accueilChoixPlayer;
	}

}
