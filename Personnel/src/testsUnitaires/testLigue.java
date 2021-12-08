package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void deleteLigue() throws SauvegardeImpossible
	{
		Ligue ligue =gestionPersonnel.addLigue("Football");
		ligue.remove();
		assertEquals(ligue.getNom(),null);
	}
	@Test
	void modifLigue() throws SauvegardeImpossible
	{
		Ligue ligue=gestionPersonnel.addLigue("Football");
		ligue.setNom("Foot");
		assertEquals("Foot",ligue.getNom());
	}
}

