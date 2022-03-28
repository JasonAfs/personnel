package personnel;
import java.sql.SQLException;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public void updateEmploye(Employe employe) throws SauvegardeImpossible, SQLException;
	public int insertEmploye(Employe employe) throws SauvegardeImpossible, SQLException;
}
