package testUnit;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import sixQuiPrend.Carte;
import sixQuiPrend.Joueur;
import sixQuiPrend.Sixquiprend;

public class jeuTest {
	@Test
	public void test() throws FileNotFoundException {
		Sixquiprend test = new Sixquiprend("config.txt");
		//vérification de l'état de la partie
		assertFalse(test.aCommence());
		
		//vérification de la configuration
		assertTrue(test.getNombreJoueurs() == 4);
		
		//verfication du clonage des joueurs
		test.getListeJoueurs().set(0, new Joueur("Guillaume"));
		assertFalse(test.getListeJoueurs().get(0).equals(new Joueur("Guillaume")));
		test.getListeJoueurs().get(0).ajouterPoints(100);
		assertTrue(test.getListeJoueurs().get(0).getPoints() != 100);
		
		//verification de la distribution des classes
		test.distribuerCartes();
		assertTrue(test.getNombreCartesPile() == 60);
		
		//verification du clonage des series 
		test.getSeries().get(0).set(0, new Carte(105));
		assertFalse(test.getSeries().get(0).get(0).equals(new Carte(105)));
		
		//test du choix et du depos des cartes pour un joueur
		test.joueurChoisiCarteAJouer(test.getListeJoueurs().get(0), test.getListeJoueurs().get(0).getCartes().get(0).getNumero());
		assertTrue(test.getListeJoueurs().get(0).getCartes().size() <10 && test.getListeJoueurs().get(0).getCarteAPoser()!= null);
		Carte tmp = test.getListeJoueurs().get(0).getCarteAPoser();
		test.joueurRemplaceSerie(0,test.getListeJoueurs().get(0));
		assertTrue(test.getListeJoueurs().get(0).getCarteAPoser() == null &&  (test.getSeries().get(0).get(0).equals(tmp)));
		
		//difficulté de vérifier les cartes, ainsi de l'algorithme pour placer les cartes nombredetetedeboeuf
	}

}
