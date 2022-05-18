package util;
import java.util.ArrayList;

import sixQuiPrend.Joueur;
/**
 * methodes permettant de trier des listes de joueurs 
 * @author Maël
 *
 */
public class Tri {
	/**
	 * tri une liste de personne selon dans l'ordre croissant des cartes à poser
	 * @param p liste des joueurs
	 */
	public static void triPersonnesParCarteAPoser(ArrayList<Joueur> p) {
		for(Joueur tmp : p)
			assert(tmp.possedeCarteAPosser());
		int min = 0;
		Joueur tmp;
		for(int i =0; i<=p.size()-2;++i) {
			min = i;
			for(int j = i+1; j<=p.size()-1;++j) {
				if(p.get(j).getCarteAPoser().getNumero()<p.get(min).getCarteAPoser().getNumero())
					min = j;
			}
			tmp = p.get(i);
			p.set(i, p.get(min));
			p.set(min, tmp);
		}
	}
	/**
	 * tri une liste de personne dans l'ordre croissant des joueurs puis dans l'ordre lexicographique
	 * @param p liste de joueurs
	 */
	public static void triPersonnesParPoints(ArrayList<Joueur> p) {
		int min = 0;
		Joueur tmp;
		for(int i =0; i<=p.size()-2;++i) {
			min = i;
			for(int j = i+1; j<=p.size()-1;++j) {
				if(p.get(j).getPoints()<p.get(min).getPoints() || (p.get(j).getPoints()==p.get(min).getPoints() && p.get(j).getNom().compareTo(p.get(min).getNom())<0))
					min = j;
			}

			tmp = p.get(i);
			p.set(i, p.get(min));
			p.set(min, tmp);
		}
	}
}
