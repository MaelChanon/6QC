package testUnit;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import sixQuiPrend.Carte;
import sixQuiPrend.Joueur;

public class JoueurTest {

	@Test
	public void test() {
		//v�rifier l'initialisation d'un joueur
		Joueur p = new Joueur("Ma�l");
		assertTrue(p.getNom() == "Ma�l");
		assertTrue(p.getCartes().isEmpty());
		assertTrue(p.getPoints() == 0);
		assertTrue(p.getCarteAPoser() == null);
		
		//v�rifier l'initialisation d'un joueur � partir d'un nouveau joueur
		Joueur j = new Joueur(p);
		assertTrue(j != p && j.equals(p));
				
		//v�rifier qu'on ne peut pas modifier la liste des cartes d'une mani�re non voulue
		p.getCartes().add(new Carte(1));
		assertTrue(p.getCartes().isEmpty());
		
		p.ajouterPoints(10);
		assertTrue(p.getPoints() == 10);
		
		//verifier l'ajout et la suppression des cartes
		p.donnerCarte(new Carte(11));
		p.donnerCarte(new Carte(12));
		p.donnerCarte(new Carte(11));
		assertTrue(p.getCartes().size() == 3);
		assertFalse(p.possedeCarteAPosser());
		p.setCarteAPoser(new Carte(11));
		assertTrue(p.possedeCarteAPosser());
		assertTrue(p.getCartes().size() == 2);
		assertFalse(p.possedeCarte(new Carte(1000)));
	}

}
