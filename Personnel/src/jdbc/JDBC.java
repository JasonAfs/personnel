package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nomLigue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	@Override
	public int insertEmploye(Employe employe) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("insert into employe(nomEe,prenomE,DateDebut,DateFin,mailE,passwordE,ligue) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, employe.getNom());
		instruction.setString(2, employe.getPrenom());
		instruction.setString(3, employe.getDateDebut().toString());
		instruction.setString(4, employe.getDateFin().toString());
		instruction.setString(5, employe.getMail());
		instruction.setString(6, employe.getPassword());
		instruction.setInt(7, employe.getLigue().getId());
		instruction.executeUpdate();
		ResultSet id = instruction.getGeneratedKeys();
		id.next();
		return id.getInt(1);
	}
	@Override
	public void updateEmploye(Employe employe) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("update employe set nomE = ? , prenomE = ? , DateDebut = ? , DateFin = ? , mailE= ? , passwordE= ? where idEmploye = ? ", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, employe.getNom());
		instruction.setString(2, employe.getPrenom());
		instruction.setString(3, employe.getDateDebut().toString());
		instruction.setString(4, employe.getDateFin().toString());
		instruction.setString(5, employe.getMail());
		instruction.setString(6, employe.getPassword());
		instruction.setInt(7, employe.getId());
		instruction.executeUpdate();
	}
	
}
