package com.firoj.shabnaz.database;


import java.sql.SQLException;


import android.content.Context;

public class Repo {
	public DatabaseNGISHelper db;

	public RepoWater rWater;





	public Repo(Context context) throws SQLException
	{
		DatabaseManager<DatabaseNGISHelper> manager = new DatabaseManager<DatabaseNGISHelper>();
		db = manager.getNGISHelper(context);
	}	
	


	public void repoWater() throws SQLException {
		rWater = new RepoWater(db);
	}
	
}
