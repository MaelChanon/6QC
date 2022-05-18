package sixQuiPrend;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Crée un joueur de 6 qui prend
 * @author Maël
 *
 */
public class Joueur{
	private String nom;
	private LinkedList<Carte> cartes;
	private int points;
	private Carte carteAPoser;
	/**
	 * Créé un nouveau joueur défini par son nom, les cartes qu'il possède (vide au début) 
	 * son nombre de points (initialisé à 0), et la carte à poser pendant le tours (initialisé à null)
	 * @param pseudo nom du joueur
	 */
	public Joueur(String pseudo) {
		nom = pseudo;
		cartes=new LinkedList<>();
		points = 0;
		carteAPoser = null;
	}
	/**
	 * permet de créer un clone d'un joueur 
	 * @param j référence vers un joueur 
	 */
	public Joueur(Joueur j) {
		this.nom = j.nom;
		this.points = j.points;
		this.cartes = new LinkedList<>();
		for(Carte c : j.cartes)
			this.cartes.add(new Carte(c));
		if(j.carteAPoser == null)
			this.carteAPoser = null;
		else
			this.carteAPoser = new Carte(j.carteAPoser);
	}

	/**
	 * determine si un joueur possède une carte dans sa main
	 * @param carte carte à determineur 
	 * @return true si le joueur possède la carte, sinon false
	 */
	public boolean possedeCarte(Carte carte) {
		return cartes.contains(carte);
	}
	
	/**
	 * Augmente le compteur de points d'un joueur
	 * @param points nombre de points 
	 */
	public void ajouterPoints(int points) {
		this.points += points;
	}
	
	/**
	 * Ajoute une carte à la main du joueur
	 * @param carte carte à donner 
	 */
	public void donnerCarte(Carte carte) {
		cartes.add(carte);
	}

	/**
	 * affecte une valeure null à CarteàPoser
	 * @return ancienne valeur de CarteàPoser
	 */
	public Carte enleverCarteAPoser() {
		Carte tmp = carteAPoser;
		carteAPoser =null;
		return tmp;
	}
	
	/**
	 * affecte une carte à poser de la main d'un joueur
	 * @param carte carte à poser
	 */
	public void setCarteAPoser(Carte carte) {
		assert(possedeCarte(carte));
		carteAPoser = new Carte(carte);
		cartes.remove(carte);
	}
	
	/**
	 * revoie le nombre de points d'un joueur 
	 * @return nombre de points
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * renvoi un clone de la carte à poser
	 * @return copie de CarteàPoser
	 */
	public Carte getCarteAPoser() {
		if(carteAPoser != null)
			return new Carte(carteAPoser);
		return null;
	}
	/**
	 * indique si un joueur a une carte à poser
	 * @return
	 */
	public boolean possedeCarteAPosser() {
		return carteAPoser != null;
	}
	/**
	 * retourne le nom du joueur 
	 * @return nom du joueur 
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * retourne un clone de liste des cartes d'un joueur
	 * @return cartes d'un joueur 
	 */
	public LinkedList<Carte> getCartes(){
		return new LinkedList<>(cartes);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		return Objects.equals(carteAPoser, other.carteAPoser) && Objects.equals(cartes, other.cartes)
				&& Objects.equals(nom, other.nom) && points == other.points;
	}
	
	public String toString() {
		return nom + " a ramassé "+ points +" têtes de boeufs";
	}
}
