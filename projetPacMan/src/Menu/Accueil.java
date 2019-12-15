package Menu;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import PacMan.Jeu;

public class Accueil {

	int accueilChoixPlayer = 0;

	// Variable Menu d'accueil
	private boolean isOpenMenu;
	private boolean accueilPlay;
	private boolean accueilExit;
	private TiledMap mapMenu;
	private Button buttonPlay;
	private Button buttonExit;

	// Variable de pause
	private boolean isOpenPause;
	//private boolean pauseReprendre;
	//private boolean pauseMenu;
	private TiledMap mapPause;
	private Button buttonPause;
	private Button buttonReprendre;
	private Button buttonMenu;

	public Accueil() {

	}

	public void init(GameContainer gc) throws SlickException {
		isOpenPause = false;
		isOpenMenu = true;
		
		
		mapMenu = new TiledMap("./map/accueil.tmx");
		buttonPlay = new Button("./image/accueil/jouer.png", 200, 200, 400, 100);
		buttonExit = new Button("./image/accueil/quitter.png", 190, 300, 400, 100);
		buttonPlay.init(gc);
		buttonExit.init(gc);
		//buttonPlay.setValeurActive(0);
		//buttonExit.setValeurActive(1);

		mapPause = new TiledMap("./map/pause.tmx");
		buttonPause = new Button("/image/accueil/pause.png", 225, 300, 120, 40);
		buttonReprendre = new Button("./image/accueil/reprendre.png", 235, 390, 170, 40);
		buttonMenu = new Button("./image/accueil/menu.png", 255, 445, 170, 40);
		buttonPause.init(gc);
		buttonReprendre.init(gc);
		buttonMenu.init(gc);
		//buttonReprendre.setValeurActive(2);		//0
		//buttonMenu.setValeurActive(3);			//1
		
		
	}

	public void update(GameContainer gc, int delta,Jeu jeu) throws SlickException {
		Input input = gc.getInput();

		//buttonPlay.update(gc, delta);
		//buttonExit.update(gc, delta);
		//buttonReprendre.update(gc, delta);
		//buttonMenu.update(gc, delta);
		//buttonPause.update(gc, delta);

		if (isOpenMenu && !isOpenPause) // menu est affiché (début)
		{
			
			if (input.isKeyPressed(Input.KEY_ENTER)) 
			{
				isOpenMenu = false;
			}
			if (input.isKeyPressed(Input.KEY_ESCAPE))
			{
				System.exit(delta);
			}
			if (input.isKeyPressed(Input.KEY_SPACE)) 
			{
				isOpenPause = false;
			}
			
		}
		else if (isOpenPause && !isOpenMenu) //jeu fonctionne et en pause
		{			
			if (input.isKeyPressed(Input.KEY_SPACE)) 
			{
				isOpenPause = false;
			}
			if (input.isKeyPressed(Input.KEY_ENTER)) 
			{
				jeu.init(gc);
				isOpenMenu = true;
			}
		}
		else // jeux en cours
		{
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				isOpenPause = true;
			}

		}

	}

	public void render() throws SlickException {
		if (isOpenMenu) {
			mapMenu.render(0, 0);
			buttonPlay.render();
			buttonExit.render();
		}
		else if (isOpenPause ) {
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
	
	public boolean isOpenMenu() {
		return isOpenMenu;
	}

	public void setOpenMenu(boolean isOpenMenu) {
		this.isOpenMenu = isOpenMenu;
	}

	public boolean isOpenPause() {
		return isOpenPause;
	}

	public void setOpenPause(boolean isOpenPause) {
		this.isOpenPause = isOpenPause;
	}


}
