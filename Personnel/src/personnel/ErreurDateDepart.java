package personnel;

import java.time.LocalDate;

public class ErreurDateDepart extends Exception{
	public ErreurDateDepart(LocalDate dateDebut, LocalDate dateFin)
	{
		System.out.println("La Date d'arrivée : "+ dateDebut+" est inférieur à la date de départ : " +dateFin);
	}
}