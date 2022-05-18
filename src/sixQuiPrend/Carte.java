package sixQuiPrend;


/**
 * carte du jeu 6 qui prend 
 * @author Ma�l	
 *
 */
public class Carte {


	private int numero;
	private int teteDeBoeuf;
	
	/**
	 * Cr�� une nouvelle carte du jeu Six Qui Prend
	 * @param num num�ro de la carte
	 */
	public Carte(int num) {
		numero = num;
		teteDeBoeuf = getTeteDeBoeufNumber(num);
	}
	/**
	 * Cr�� un clone d'une carte
	 * @param carte r�f�rence vers une carte
	 */
	public Carte(Carte carte) {
		this(carte.getNumero());
	}

	/**
	 * renvoie le num�ro pr�cis de t�te de boeuf associ� � un num�ro de carte 
	 * @param num num�ro de la carte
	 * @return nombre de t�te de boeuf
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
	 * Indique le nombre de t�te de boeuf d'une carte
	 * @return nombre de t�te de boeuf
	 */
	public int getTeteDeBoeuf() {
		return teteDeBoeuf;
	}
	/**
	 * retourne le num�ro de la carte
	 * @return num�ro de la carte 
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