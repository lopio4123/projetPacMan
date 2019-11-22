package PacMan;

public enum Direction {
	RIGHT("Droit"),
	LEFT("Gauche"),
	UP("Haut"),
	DOWN("Bas"),
	NEUTRE("Neutre");
	
	private String nom = "";
	
	//Constructeur
	Direction(String name){
	    this.nom = name;
	}
	   
	public String toString(){
		return nom;
	}

}
