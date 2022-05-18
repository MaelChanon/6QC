package sixQuiPrend;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe permettant de joueur à Six qui prend
 * @author Maël
 *
 */
public class Sixquiprend{
	private ArrayList<Joueur> joueurs;
	private LinkedList<Carte> pile;
	ArrayList<LinkedList<Carte>> series;
	private static final int NOMBRECARTES = 104;
	private static final int MAXJOUEURS = 10;
	private static final int MINJOUEURS = 2;
	private static final int DISTRIBUTION = 10;
	private static final int NOMBRESERIES = 4;
	private static final int LONGUEURMAXSERIE = 5;
	
	/**
	 * déclare un jeu (6 qui prend) avec un liste de joueurs indiquée dans le fichier de configuration
	 * la pile des cartes, et la liste des series (vides)
	 * @param config nom du fichier de configuration
	 * @throws FileNotFoundException si le fichier de configuration n'est pas trouvé
	 */
	public Sixquiprend(String config){
		pile = new LinkedList<>();
		initialiserCartes();
		joueurs = new ArrayList<>();
		declarerJoueurs(config);
		series  = new ArrayList<>();
	}
	//---------------initialisation de la partie---------------------------
	/**
	 * initialise de nouveaux joueurs à partir du fichier de configuration
	 * @param nomFichier nom du fichier de configuration
	 * @throws FileNotFoundException
	 */
	private void declarerJoueurs(String nomFichier){
		Scanner in;
		try {
			in = new Scanner(new FileInputStream(nomFichier));
			while(in.hasNextLine()) {
				ajouterJoueur((new Joueur(in.nextLine())));
			}
			assert(joueurs.size()>= MINJOUEURS);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Ajoute des joueurs dans dans la partie
	 * @param p une référence vers un joueur
	 */
	private void ajouterJoueur(Joueur p) {
		assert (joueurs.size() <= MAXJOUEURS && !aCommence() && p.getCartes().isEmpty());
		joueurs.add(p);
	}
	/**
	 * initialise la pile de cartes et la mélange aléatoirement 
	 */
	private void initialiserCartes() {
		for(int i =1 ; i<=NOMBRECARTES ; ++i) 
			pile.add(new Carte(i));
		Collections.shuffle(pile);
	}
	/**
	 * initialise une serie et ajoute la première carte 
	 */
	private void ajouterSerie() {
		LinkedList<Carte> tmp = new LinkedList<>();
		tmp.add(pile.pop());
		series.add(tmp);
	}
	

	/**
	 * Distribue des cartes à tous les joueurs et place les cartes sur les series
	 */
	public void distribuerCartes() {
		assert(!aCommence()); //se fait en début de partie uniquement
		for(Joueur p : joueurs) 
			for(int i =0 ; i<DISTRIBUTION; ++i)
				p.donnerCarte(pile.pop());
		for(int i =0 ; i<NOMBRESERIES; ++i)
			ajouterSerie();

	}
	
	//----------------Jouer le tour------------------------------
	/**
	 * place la carte qu'un joueur doit poser dans une des serie 
	 * @param p référence vers le joueur 
	 * @return -1 si la carte ne peut pas être placée sinon le nombre de tête de boeuf que le joueur a reçu
	 */
	public int placerCarte(Joueur p) {
		assert(joueurDansListe(p) && p.possedeCarteAPosser());	
		Joueur tmp = getjoueurDansListe(p);
		int max = 0;
		int idx = -1;
		//recherche de l'endroit pour poser la carte
		for(int i = 0 ; i<series.size(); ++i) 
			if(series.get(i).getLast().getNumero()<tmp.getCarteAPoser().getNumero() && max<series.get(i).getLast().getNumero()) {
				max = series.get(i).getLast().getNumero();
				idx = i;
			}
		if(idx >=0) {
			//si on a un endroit ou placer la carte et que la série n'est pas pleine 
			if(series.get(idx).size() <LONGUEURMAXSERIE) {
				series.get(idx).add(tmp.enleverCarteAPoser());
				return 0;
			}
			else {
				max = nombreTeteDeBoeufSerie(idx);
				tmp.ajouterPoints(max);
				remplacerSerie(idx, tmp.enleverCarteAPoser());
				return max;
				}
			}
		return -1;
	}

	
	//-------------------- Interface Series--------------------------
	/**
	 * retourne un clone des series du jeu
	 * @return liste des différentrs series 
	 */
	public ArrayList<LinkedList<Carte>> getSeries(){
		ArrayList<LinkedList<Carte>> tmp = new ArrayList<>();
		for(LinkedList<Carte> l : series)
			tmp.add(new LinkedList<>(l));
		return tmp;
	}

	/**
	 * remplace une serie avec la carte à poser du joueur 
	 * @param idx index de la liste 
	 * @param p joueur 
	 * @return nombre de tête de boeuf récoltées 
	 */
	public int joueurRemplaceSerie(int idx,Joueur p) {
		assert(joueurDansListe(p) && p.possedeCarteAPosser());
		Joueur tmp = getjoueurDansListe(p);
		int ret =0;
		ret = nombreTeteDeBoeufSerie(idx);
		tmp.ajouterPoints(ret);
		remplacerSerie(idx, tmp.enleverCarteAPoser());
		return ret;
	}
	/**
	 * remplace une serie par une nouvelle serie de une carte 
	 */
	private void remplacerSerie(int idx, Carte carte) {
		LinkedList<Carte> l = new LinkedList<>();
		l.add(new Carte(carte));
		series.remove(idx);
		series.add(idx, l);
	}
	
	/**
	 * compte le nombre de tête de boeuf d'une serie
	 * @param number numéro de la série (commence à 0)
	 * @return nombre de tête de boeuf 
	 */
	public int nombreTeteDeBoeufSerie(int number) {
		assert(number <NOMBRESERIES);
		int nbTeteSerie = 0;
		for(Carte c : series.get(number))
			nbTeteSerie += c.getTeteDeBoeuf();
		return nbTeteSerie;
	}


	//-----------------interface joueur-----------------------------
	/**
	 * indique si un joueur se trouve dans la liste des joueur 
	 * @param p référence vers un joueur 
	 * @return true si il est présent sinon false 
	 */
	public boolean joueurDansListe(Joueur p) {
		return joueurs.contains(p);
	}
	/**
	 * retourne la référence d'un joueur
	 * @param p clone d'un joueur 
	 * @return référence du joueur 
	 */
	private Joueur getjoueurDansListe(Joueur p) {
		assert(joueurDansListe(p));
		for(Joueur j : joueurs)
			if(j.equals(p)) 
				return j;
			return null;
	}
	/**
	 * retourne un clone de la liste des joueurs
	 * @return liste des joueurs 
	 */
	public ArrayList<Joueur> getListeJoueurs(){
		ArrayList<Joueur> tmp = new ArrayList<>();
		for(Joueur j : joueurs)
			tmp.add(new Joueur(j));
		return tmp;
	}
	
	/**
	 * initialiser la carte à jouer d'un joueur
	 * @param p copie d'un joueur 
	 * @param nb numéro de la carte 
	 */
	public void joueurChoisiCarteAJouer(Joueur p, int nb) {
		assert(joueurDansListe(p) && p != null && nb >0 && nb<=NOMBRECARTES);
		Joueur tmp = getjoueurDansListe(p);
		tmp.setCarteAPoser(new Carte(nb));
	}
	
	//---------------Etat de la partie-------------------------------
	
	/**
	 * indique si la game est commencée 
	 * @return true si la pile de carte est pleine sinon false
	 */
	public boolean aCommence() {
		return pile.size() != NOMBRECARTES;
	}

	/**
	 * Donne le nombre de joueurs
	 * @return taille de la pile 
	 */
	public int getNombreJoueurs() {
		return joueurs.size();
	}
	/**
	 * donne le nombre de tours total d'une partie
	 * @return le nombre de cartes d'une main
	 */
	public int getNombreTours() {
		return DISTRIBUTION;
	}
	/**
	 * donne le nombre de series d'une partie
	 * @return NOMBRESERIES
	 */
	public int getNombreSerie() {
		return NOMBRESERIES;
	}
	/**
	 * donne le nombre maximum de joueurs
	 * @return MAXJOUEURS
	 */
	public int getMaxJoueurs() {
		return MAXJOUEURS;
	}
	/**
	 * donne la taille actuelle de la pile de cartes 
	 * @return taile de pile 
	 */
	public int getNombreCartesPile() {
		return pile.size();
	}
}
