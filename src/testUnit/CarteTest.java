package testUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import sixQuiPrend.Carte;

public class CarteTest {

	@Test
	public void test() {
		Carte c1 = new Carte(1);
		Carte c2 = new Carte(5);
		Carte c3 = new Carte(10);
		Carte c4 = new Carte(11);
		Carte c5 = new Carte(55);
		//vérification des têtes de boeuf 
		assertTrue(c1.getNumero() == 1 && c2.getNumero() == 5);
		assertTrue(c1.getTeteDeBoeuf() == 1 && c2.getTeteDeBoeuf() ==2 && c3.getTeteDeBoeuf() ==3 && c4.getTeteDeBoeuf() ==5 && c5.getTeteDeBoeuf() == 7);
		//vérification de la copie d'une carte
		Carte c6 = new Carte(c5);
		assertTrue(c6.equals(c6));
	}

}
