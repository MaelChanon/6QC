package appli;
import sixQuiPrend.Sixquiprend;
import util.Affichage;

public class Application {
	public static void main(String[] args){
		Sixquiprend jeu = new Sixquiprend("config.txt");
		Affichage.sixQuiPrend(jeu);

		
	}
}
