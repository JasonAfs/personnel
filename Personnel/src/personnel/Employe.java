package personnel;

import java.io.Serializable;

import java.time.LocalDate;

/**
 * EmployÃ© d'une ligue hÃ©bergÃ©e par la M2L. Certains peuvent 
 * Ãªtre administrateurs des employÃ©s de leur ligue.
 * Un seul employÃ©, rattachÃ© Ã  aucune ligue, est le root.
 * Il est impossible d'instancier directement un employÃ©, 
 * il faut passer la mÃ©thode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private int id;
	private LocalDate dateDebut, dateFin;
	private GestionPersonnel gestionPersonnel;
	
	public Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
	}
	
	public Employe(GestionPersonnel gestionPersonnel, Ligue ligue,int id, String nom, String prenom, String mail, LocalDate dateDebut, LocalDate dateFin,String password)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	/**
	 * Retourne vrai ssi l'employÃ© est administrateur de la ligue 
	 * passÃ©e en paramÃ¨tre.
	 * @return vrai ssi l'employÃ© est administrateur de la ligue 
	 * passÃ©e en paramÃ¨tre.
	 * @param ligue la ligue pour laquelle on souhaite vÃ©rifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employÃ© est le root.
	 * @return vrai ssi l'employÃ© est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employÃ©.
	 * @return le nom de l'employÃ©. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employÃ©.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne le prÃ©nom de l'employÃ©.
	 * @return le prÃ©nom de l'employÃ©.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prÃ©nom de l'employÃ©.
	 * @param prenom le nouveau prÃ©nom de l'employÃ©. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Retourne le mail de l'employÃ©.
	 * @return le mail de l'employÃ©.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employÃ©.
	 * @param mail le nouveau mail de l'employÃ©.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retourne vrai ssi le password passÃ© en paramÃ¨tre est bien celui
	 * de l'employÃ©.
	 * @return vrai ssi le password passÃ© en paramÃ¨tre est bien celui
	 * de l'employÃ©.
	 * @param password le password auquel comparer celui de l'employÃ©.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employÃ©.
	 * @param password le nouveau password de l'employÃ©. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}

	/**
	 * Retourne la ligue Ã  laquelle l'employÃ© est affectÃ©.
	 * @return la ligue Ã  laquelle l'employÃ© est affectÃ©.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	public LocalDate getDateFin() {
		return dateFin;
	}
	
	
	public void setDateDebut(LocalDate datedebut) throws ErreurDateDepart, SauvegardeImpossible{
		if(dateFin == null)
			this.dateDebut = datedebut;
		else if(datedebut.isAfter(dateFin))
			throw new ErreurDateDepart(datedebut, dateFin);
		else {
			this.dateDebut = datedebut;
			gestionPersonnel.updateEmploye(this);
		}
			
	}
	
	public void setDateFin(LocalDate datefin) throws ErreurDateFin, SauvegardeImpossible{
		if(dateDebut ==null )
			this.dateFin = datefin;
		else if(datefin.isBefore(dateDebut))
			throw new ErreurDateFin(datefin, dateDebut);
		else {
			this.dateFin = datefin;
			gestionPersonnel.updateEmploye(this);
		}
	}

	/**
	 * Supprime l'employÃ©. Si celui-ci est un administrateur, le root
	 * rÃ©cupÃ¨re les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail +" "+dateDebut+" "+dateFin+" (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
