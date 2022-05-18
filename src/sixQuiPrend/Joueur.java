package sixQuiPrend;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Cr�e un joueur de 6 qui prend
 * @author Ma�l
 *
 */
public class Joueur{
	private String nom;
	private LinkedList<Carte> cartes;
	private int points;
	private Carte carteAPoser;
	/**
	 * Cr�� un nouveau joueur d�fini par son nom, les cartes qu'il poss�de (vide au d�but) 
	 * son nombre de points (initialis� � 0), et la carte � poser pendant le tours (initialis� � null)
	 * @param pseudo nom du joueur
	 */
	public Joueur(String pseudo) {
		nom = pseudo;
		cartes=new LinkedList<>();
		points = 0;
		carteAPoser = null;
	}
	/**
	 * permet de cr�er un clone d'un joueur 
	 * @param j r�f�rence vers un joueur 
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
	 * determine si un joueur poss�de une carte dans sa main
	 * @param carte carte � determineur 
	 * @return true si le joueur poss�de la carte, sinon false
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
	 * Ajoute une carte � la main du joueur
	 * @param carte carte � donner 
	 */
	public void donnerCarte(Carte carte) {
		cartes.add(carte);
	}

	/**
	 * affecte une valeure null � Carte�Poser
	 * @return ancienne valeur de Carte�Poser
	 */
	public Carte enleverCarteAPoser() {
		Carte tmp = carteAPoser;
		carteAPoser =null;
		return tmp;
	}
	
	/**
	 * affecte une carte � poser de la main d'un joueur
	 * @param carte carte � poser
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
	 * renvoi un clone de la carte � poser
	 * @return copie de Carte�Poser
	 */
	public Carte getCarteAPoser() {
		if(carteAPoser != null)
			return new Carte(carteAPoser);
		return null;
	}
	/**
	 * indique si un joueur a une carte � poser
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
		return nom + " a ramass� "+ points +" t�tes de boeufs";
	}
}
