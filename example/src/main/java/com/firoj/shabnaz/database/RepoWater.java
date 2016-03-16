package com.firoj.shabnaz.database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.firoj.shabnaz.database.model.Water;

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


	public Water getByMobWorkOrderID2( int mobWorkOrderID)
	{
		try {
			QueryBuilder<Water, String> qb = waterDao.queryBuilder();
			Where<Water, String> where = qb.where();
			where.eq("WoterID", mobWorkOrderID);
			PreparedQuery<Water> pq = qb.prepare();
			return waterDao.queryForFirst(pq);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

	public int save(Water obj)
	{
		Water queryObj = this.getByMobWorkOrderID2(obj.getWaterID());

		if(queryObj == null)
		{
			return this.create(obj);
		}
		else
		{
			return this.update(obj);
		}

	}
	
}
