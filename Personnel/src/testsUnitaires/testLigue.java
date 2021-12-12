package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
	void deleteLigue() throws SauvegardeImpossible
	{
		Ligue ligue =gestionPersonnel.addLigue("Football");
		ligue.remove();
		assertEquals(null,ligue.getNom());
	}
	@Test
	void modifLigue() throws SauvegardeImpossible
	{
		Ligue ligue=gestionPersonnel.addLigue("Football");
		ligue.setNom("Foot");
		assertEquals("Foot",ligue.getNom());
	}
	
	@Test
	void addEmploye() throws SauvegardeImpossible,ErreurDateDepart, ErreurDateFin
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com",LocalDate.parse("2021-09-09"),LocalDate.parse("2022-09-09"), "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void deleteEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		Employe employe = ligue.addEmploye("Afonso","Jason","jason@gmail.com",LocalDate.parse("2021-01-08"),LocalDate.parse("2022-06-06"),"test");
		employe.remove();
		assertEquals(null,employe);
		
	}
	
	@Test
	void modifEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		Employe employe = ligue.addEmploye("Afonso","Jason","jason@gmail.com",LocalDate.parse("2021-01-08"),LocalDate.parse("2022-06-06"),"test");
		Employe employe2 = ligue.addEmploye("Afonso2","Jason2","jason2@gmail.com",LocalDate.parse("2021-01-08"),LocalDate.parse("2022-06-06"),"test2");
		
		employe.setNom("Afonso2");
		employe.setPrenom("Jason2");
		employe.setMail("jason2@gmail.com");
		employe.setPassword("test2");
		assertEquals(employe2.getNom(),employe.getNom());
		assertEquals(employe2.getPrenom(),employe.getPrenom());
		
	}
	
	@Test
	void changeAdmin() throws SauvegardeImpossible, ErreurDateDepart, ErreurDateFin
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com",LocalDate.parse("2010-09-09"), LocalDate.parse("2020-10-01"), "azerty");
		ligue.setAdministrateur(employe);
		assertEquals(null,ligue.getAdministrateur().toString());
	}
}

