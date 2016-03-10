package com.jpardogo.android.listbuddies.database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jpardogo.android.listbuddies.database.model.Water;

import java.sql.SQLException;
import java.util.List;


public class RepoWater {

	Dao<Water, String> waterDao;
	
	public RepoWater(DatabaseNGISHelper db) throws SQLException
	{
		waterDao = db.getwaterDao();
	}
	public int create(Water water)
	{
		try {
			
			return waterDao.create(water);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	public int update(Water water)
	{
		try {
			return waterDao.update(water);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	public int delete(Water water)
	{
		try {
			return waterDao.delete(water);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	public int delete_soft(Water water)
	{
		//water.setRowStatus("D");
		return update(water);

	}
	
	public List<Water> getAllWorkOrders()
	{		
		try {
            QueryBuilder<Water, String> qb = waterDao.queryBuilder();
			/*Where<Water, String> where = qb.where();
			where.eq("RowStatus", "A");*/
			PreparedQuery<Water> pq = qb.prepare();
			return waterDao.query(pq);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}
	
}
