package sixQuiPrend;


/**
 * carte du jeu 6 qui prend 
 * @author Maël	
 *
 */
public class Carte {


	private int numero;
	private int teteDeBoeuf;
	
	/**
	 * Créé une nouvelle carte du jeu Six Qui Prend
	 * @param num numéro de la carte
	 */
	public Carte(int num) {
		numero = num;
		teteDeBoeuf = getTeteDeBoeufNumber(num);
	}
	/**
	 * Créé un clone d'une carte
	 * @param carte référence vers une carte
	 */
	public Carte(Carte carte) {
		this(carte.getNumero());
	}

	/**
	 * renvoie le numéro précis de tête de boeuf associé à un numéro de carte 
	 * @param num numéro de la carte
	 * @return nombre de tête de boeuf
	 */
	private int getTeteDeBoeufNumber(int num) {
		int tmp = 0;
		if(num%10 == 5)
			tmp += 2;
		if(num%10 == 0) 
			tmp += 3;
		if(num%11 == 0)
			tmp += 5;
		if(tmp>0)
			return tmp;
		return 1;
	}
	
	/**
	 * Indique le nombre de tête de boeuf d'une carte
	 * @return nombre de tête de boeuf
	 */
	public int getTeteDeBoeuf() {
		return teteDeBoeuf;
	}
	/**
	 * retourne le numéro de la carte
	 * @return numéro de la carte 
	 */
	public int getNumero() {
		return numero;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carte other = (Carte) obj;
		return numero == other.numero && teteDeBoeuf == other.teteDeBoeuf;
	}
}