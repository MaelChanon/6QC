package testUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;
import sixQuiPrend.Carte;
import sixQuiPrend.Joueur;
import util.Tri;

public class TriTest {

	@Test
	public void test() {
		Joueur j1 = new Joueur("Guillaume");
		j1.ajouterPoints(10);
		j1.donnerCarte(new Carte(5));
		j1.setCarteAPoser(new Carte(5));
		
		Joueur j2 = new Joueur("Martin");
		j2.ajouterPoints(14);
		j2.donnerCarte(new Carte(18));
		j2.setCarteAPoser(new Carte(18));
		Joueur j3 = new Joueur("Alexi");
		
		j3.ajouterPoints(2);
		j3.donnerCarte(new Carte(6));
		j3.setCarteAPoser(new Carte(6));
		
		Joueur j4 = new Joueur("Mathis");
		j4.ajouterPoints(10);
		j4.donnerCarte(new Carte(19));
		j4.setCarteAPoser(new Carte(19));
		
		ArrayList<Joueur> lst1 = new ArrayList<>();
		lst1.add(j1);
		lst1.add(j2);
		lst1.add(j3);
		lst1.add(j4);
		ArrayList<Joueur> lst2 = new ArrayList<>(lst1);
		Tri.triPersonnesParCarteAPoser(lst1);
		Tri.triPersonnesParPoints(lst2);
		
		for(int i =0; i<lst1.size()-1 ; ++i) {
			assertTrue(lst1.get(i).getCarteAPoser().getNumero()<lst1.get(i+1).getCarteAPoser().getNumero());
			if(lst2.get(i).getPoints() ==lst2.get(i+1).getPoints())
				assertTrue(lst2.get(i).getNom().compareTo(lst2.get(i+1).getNom())<0);
			else
				assertTrue(lst2.get(i).getPoints() < lst2.get(i+1).getPoints() );
		}
	}

}
