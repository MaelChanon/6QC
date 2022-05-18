package sixQuiPrend;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe permettant de joueur � Six qui prend
 * @author Ma�l
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
	 * d�clare un jeu (6 qui prend) avec un liste de joueurs indiqu�e dans le fichier de configuration
	 * la pile des cartes, et la liste des series (vides)
	 * @param config nom du fichier de configuration
	 * @throws FileNotFoundException si le fichier de configuration n'est pas trouv�
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
	 * initialise de nouveaux joueurs � partir du fichier de configuration
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
	 * @param p une r�f�rence vers un joueur
	 */
	private void ajouterJoueur(Joueur p) {
		assert (joueurs.size() <= MAXJOUEURS && !aCommence() && p.getCartes().isEmpty());
		joueurs.add(p);
	}
	/**
	 * initialise la pile de cartes et la m�lange al�atoirement 
	 */
	private void initialiserCartes() {
		for(int i =1 ; i<=NOMBRECARTES ; ++i) 
			pile.add(new Carte(i));
		Collections.shuffle(pile);
	}
	/**
	 * initialise une serie et ajoute la premi�re carte 
	 */
	private void ajouterSerie() {
		LinkedList<Carte> tmp = new LinkedList<>();
		tmp.add(pile.pop());
		series.add(tmp);
	}
	

	/**
	 * Distribue des cartes � tous les joueurs et place les cartes sur les series
	 */
	public void distribuerCartes() {
		assert(!aCommence()); //se fait en d�but de partie uniquement
		for(Joueur p : joueurs) 
			for(int i =0 ; i<DISTRIBUTION; ++i)
				p.donnerCarte(pile.pop());
		for(int i =0 ; i<NOMBRESERIES; ++i)
			ajouterSerie();

	}
	
	//----------------Jouer le tour------------------------------
	/**
	 * place la carte qu'un joueur doit poser dans une des serie 
	 * @param p r�f�rence vers le joueur 
	 * @return -1 si la carte ne peut pas �tre plac�e sinon le nombre de t�te de boeuf que le joueur a re�u
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
			//si on a un endroit ou placer la carte et que la s�rie n'est pas pleine 
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
	 * @return liste des diff�rentrs series 
	 */
	public ArrayList<LinkedList<Carte>> getSeries(){
		ArrayList<LinkedList<Carte>> tmp = new ArrayList<>();
		for(LinkedList<Carte> l : series)
			tmp.add(new LinkedList<>(l));
		return tmp;
	}

	/**
	 * remplace une serie avec la carte � poser du joueur 
	 * @param idx index de la liste 
	 * @param p joueur 
	 * @return nombre de t�te de boeuf r�colt�es 
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
	 * compte le nombre de t�te de boeuf d'une serie
	 * @param number num�ro de la s�rie (commence � 0)
	 * @return nombre de t�te de boeuf 
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
	 * @param p r�f�rence vers un joueur 
	 * @return true si il est pr�sent sinon false 
	 */
	public boolean joueurDansListe(Joueur p) {
		return joueurs.contains(p);
	}
	/**
	 * retourne la r�f�rence d'un joueur
	 * @param p clone d'un joueur 
	 * @return r�f�rence du joueur 
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
	 * initialiser la carte � jouer d'un joueur
	 * @param p copie d'un joueur 
	 * @param nb num�ro de la carte 
	 */
	public void joueurChoisiCarteAJouer(Joueur p, int nb) {
		assert(joueurDansListe(p) && p != null && nb >0 && nb<=NOMBRECARTES);
		Joueur tmp = getjoueurDansListe(p);
		tmp.setCarteAPoser(new Carte(nb));
	}
	
	//---------------Etat de la partie-------------------------------
	
	/**
	 * indique si la game est commenc�e 
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
