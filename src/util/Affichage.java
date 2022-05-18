package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import sixQuiPrend.Carte;	
import sixQuiPrend.Joueur;
import sixQuiPrend.Sixquiprend;
/**
 * Classe facilitant l'utilisation de la classe jeu	contenant toutes les fonction d'entrée/sortie
 * @author Maël
 *
 */
public class Affichage {
	/**
	 * traduit une liste de carte en chaine de caractère
	 * @param cartes liste de carte 
	 * @return représente le numéro et le têtes de boeuf d'une carte 
	 */
	private static String lireCartes(LinkedList<Carte> cartes) {
		StringBuilder tmp = new StringBuilder();	
		if(!cartes.isEmpty()) {
			tmp.append(cartes.get(0).getNumero());
			if(cartes.get(0).getTeteDeBoeuf() >1)
				tmp.append(" ("+cartes.get(0).getTeteDeBoeuf()+")");
			for(int i = 1; i<cartes.size();++i) {
				tmp.append( ", "+cartes.get(i).getNumero());
				if(cartes.get(i).getTeteDeBoeuf() >1)
					tmp.append(" ("+cartes.get(i).getTeteDeBoeuf()+")");
			}
			return tmp.toString();
		}
		return tmp.toString();
	}
	/**
	 * traduit l'affichage de toutes les séries
	 * @param series liste de toutes les séries
	 * @return chaine de caractère représentant les cartes de la série
	 */
	private static String lireSeries(ArrayList<LinkedList<Carte>> series) {
		int i =0;
		StringBuilder tmp = new StringBuilder();
		for(LinkedList<Carte> c : series) {
			++i;
			tmp.append("- série n° "+i+ " : "+lireCartes(c)+'\n');
		}
		return tmp.toString();
	}
	/**
	 * fait l'introduction du jeu
	 * @param joueurs liste des joueurs
	 * @return présentation des joueurs
	 */
	private static String lirePresentation(ArrayList<Joueur> joueurs) {
		StringBuilder tmp = new StringBuilder();
		tmp.append("Les "+ joueurs.size()+" joueurs sont ");
		for(int i =0; i<joueurs.size() ; ++i) {
			if(i == joueurs.size()-1)
				tmp.append(" et ");
			tmp.append(joueurs.get(i).getNom());
			if(i < joueurs.size()-2)
				tmp.append(", ");
		}
		tmp.append(". Merci de jouer à 6 qui prend !");
		return tmp.toString();
	}
	/**
	 * lit le tours d'un jouer 
	 * @param p référence vers un joueur 
	 * @return indique au joueur de joueur 
	 */
	private static String lireTourJoueur(Joueur p) {
		return "A " + p.getNom() + " de jouer.";
	}
	/**
	 * lit les cartes à poser pendant le tours 
	 * @param personnes liste des joueurs
	 * @return carte à poser de chaque joueurs
	 */
	private static String lireCartesAPoser(ArrayList<Joueur> personnes) {
		StringBuilder tmp = new StringBuilder();
		tmp.append("Les cartes ");
		for(Joueur p : personnes) {
			tmp.append(p.getCarteAPoser().getNumero()+ " ("+ p.getNom()+"), " );
		}
		tmp.deleteCharAt(tmp.length()-2);
		return tmp.toString();
	}
	/**
	 * Saisie une carte possède par un joueur, et envoie un message sur la sortie standard si celle ci n'est pas valide
	 * @param p réference vers un joueur 
	 * @param sc réference vers un scanner
	 * @return
	 */
	private static int saisieCarteValide(Joueur p, Scanner sc) {
		boolean valide = false;
		int choix = 0;
		while(!valide) {
			if(sc.hasNextInt()) {
				choix = sc.nextInt();
				if(p.possedeCarte(new Carte(choix)))
					valide = true;
			}
			else
				sc.next();
			if(!valide)
				System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
		}
		return choix;
	}
	/**
	 * saisie une serie valide, et envoie un message sur la sortie standard si celle ci n'est pas valide
	 * @param jeu référence vers un jeu
	 * @param sc réference vers un scanner
	 * @return
	 */
	private static int saisieSerieValide(Sixquiprend jeu, Scanner sc) {
		boolean valide = false;
		int choix = 0;
		while(!valide) {
			if(sc.hasNextInt()) {
				choix = sc.nextInt();
				if(choix >0 && choix <= jeu.getNombreSerie())
					valide = true;
			}
			else
				sc.next();
			if(!valide)
				System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
		}
		return choix;
	}
	/**
	 * Affiche le tour d'un joueur sur la sortir standart 
	 * @param jeu référence vers un jeu
	 * @param p référence vers un joueur 
	 */
	private static void afficherTourJoueur(Sixquiprend jeu, Joueur p) {
		System.out.println(Affichage.lireTourJoueur(p));
		util.Console.pause();  	
		System.out.println(Affichage.lireSeries(jeu.getSeries()) + "- Vos cartes : " + Affichage.lireCartes(p.getCartes()));
		System.out.print("Saisissez votre choix : ");	
	}
	/**
	 * Affiche la fin d'un tour (points que le joueur a récupéré pendant le tour) sur la sortie standard
	 * @param jeu référence vers un jeu
	 * @param ordreBuffer liste des joueurs par points (ordre croissant)
	 */
	private static void afficherFinDuTour(Sixquiprend jeu, ArrayList<Joueur> ordreBuffer) {
			for(int i = 0; i<jeu.getNombreJoueurs(); ++i)
				if(ordreBuffer.get(i).getPoints() != 0) {
					if(ordreBuffer.get(i).getPoints() == 1) 
						System.out.println(ordreBuffer.get(i).getNom() + " a ramassé "+ ordreBuffer.get(i).getPoints()+" tête de boeufs");
					else
						System.out.println(ordreBuffer.get(i).getNom() + " a ramassé "+ ordreBuffer.get(i).getPoints()+" têtes de boeufs");
				}
	}
	/**
	 * Affiche le résultat de la partie sur la sortie standard
	 * @param jeu référence vers un jeu
	 */
	private static void afficherResultat(Sixquiprend jeu) {
		ArrayList<Joueur> ordreBuffer = jeu.getListeJoueurs();
		System.out.println("** Score final");
		Tri.triPersonnesParPoints(ordreBuffer);
		for(Joueur j : ordreBuffer)
			System.out.println(j);
	}
	/**
	 * Affiche une invatation à choisir une série sur la sortie standard 
	 * @param p
	 * @param jeu
	 */
	private static void afficherChoixSerie(Joueur p, Sixquiprend jeu) {
		System.out.println("Pour poser la carte "+p.getCarteAPoser().getNumero()+", "+p.getNom()+" doit choisir la série qu'il va ramasser.");
		System.out.print(Affichage.lireSeries(jeu.getSeries()));
		System.out.print("Saisissez votre choix : ");
	}

	/**
	 * récupère les cartes (+ affichage)
	 * @param jeu référence vers un jeu
	 * @param sc référence vers un scanner 
	 */
	private static void recupererCartes(Sixquiprend jeu, Scanner sc) {
		for(Joueur p : jeu.getListeJoueurs()) {
			afficherTourJoueur(jeu,p);
			jeu.joueurChoisiCarteAJouer(p, saisieCarteValide(p,sc));
			Console.clearScreen();
		}
	}
	/**
	 * pose les cartes dans une serie (+ affichage de la fin du tour)
	 * @param jeu référence vers un jeu
	 * @param sc référence vers un scanner 
	 */
	private static void poserCartes(Sixquiprend jeu, Scanner sc) {
		boolean annonce =false;
		boolean joueurPerdPoints =false;
		int tmp = 0;
		ArrayList<Joueur> ordreBuffer = jeu.getListeJoueurs();
		Tri.triPersonnesParCarteAPoser(ordreBuffer);
		String carteAPoser = lireCartesAPoser(ordreBuffer);		
		//Placement des cartes 
		for(Joueur p : ordreBuffer) {
			tmp = jeu.placerCarte(p);
			//si la carte n'a pas eté placée
			if(tmp == -1) {
				if(!annonce) {
					System.out.println(carteAPoser+"vont être posées.");
					annonce = true;
				}
				afficherChoixSerie(p, jeu);
				tmp = jeu.joueurRemplaceSerie(saisieSerieValide(jeu, sc)-1,p);
			}
			if(tmp >0)
				joueurPerdPoints = true;
			p.ajouterPoints(-p.getPoints() + tmp);
		}
		//fin du tour
		System.out.println(carteAPoser+"ont été posées.");
		System.out.print(Affichage.lireSeries(jeu.getSeries()));
		if(!joueurPerdPoints)
			System.out.println("Aucun joueur ne ramasse de tête de boeufs.");
		else
			afficherFinDuTour(jeu, ordreBuffer);
	}

	/**
	 * permet de jouer à 6 qui prend en ligne de commande
	 * @param jeu référence vers un jeu 
	 */
	public static void sixQuiPrend(Sixquiprend jeu) {
		jeu.distribuerCartes();
		Scanner sc = new Scanner(System.in);
		//présentation des joueurs
		System.out.println(Affichage.lirePresentation(jeu.getListeJoueurs()));
		//le tours complet
		for(int o =0; o<jeu.getNombreTours();++o) {
			recupererCartes(jeu, sc);
			poserCartes(jeu, sc);
		}
		sc.close();
		afficherResultat(jeu);
	}

}

