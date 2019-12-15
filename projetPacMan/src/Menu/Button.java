package Menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Button {

	private Image button;
	private Image curseurImg;
	String image;
	int posX;
	int posY;
	int hauteur;
	int largeur;
	int valeurActive;
	int curseur = 0;

	public Button(String image, int posX, int posY, int largeur, int hauteur) {
		this.image = image;
		this.posX = posX;
		this.posY = posY;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public void init(GameContainer gc) throws SlickException {

		button = new Image(image);
		//curseurImg = new Image("./image/shrek.png");
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		/*if (input.isKeyPressed(Input.KEY_DOWN)) {
			curseur++;
			curseurImg.draw(posX+50, posY);
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			curseur--;
		}*/

	}

	public void render() throws SlickException {
		button.draw(posX, posY, largeur, hauteur);

		/*if (valeurActive == curseur) {
			curseurImg.draw(200, 215, 20, 20);
		}*/

	}

	public int getValeurActive() {
		return valeurActive;
	}

	public void setValeurActive(int valeurActive) {
		this.valeurActive = valeurActive;
	}

}
