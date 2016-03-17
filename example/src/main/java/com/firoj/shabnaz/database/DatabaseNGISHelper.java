package com.firoj.shabnaz.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.firoj.shabnaz.database.model.Shabnaz;

import java.io.IOException;
import java.sql.SQLException;


public class DatabaseNGISHelper extends OrmLiteSqliteOpenHelper {





	private static String DATABASE_NAME = "Shabnaz.db";
	private static int DATABASE_VERSION = 1;


	private Dao<Shabnaz, String> shabnazDao = null;
	

	public DatabaseNGISHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		DatabaseNGISInitializer initializer = new DatabaseNGISInitializer(
				context);
		try {
			// initializer.destroyDatabase();
			initializer.createDataBase();
			initializer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseNGISHelper.class.getName(), "onCreate");

			if (false) {
				TableUtils.createTable(connectionSource, Shabnaz.class);
				
			}

		} catch (SQLException e) {
			Log.e(DatabaseNGISHelper.class.getName(), "Can't create database",
					e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseNGISHelper.class.getName(), "onUpgrade");
			if (false) {
				TableUtils.dropTable(connectionSource, Shabnaz.class, true);
			}
			onCreate(db);
		} catch (SQLException e) {
			Log.e(DatabaseNGISHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	
	
	public Dao<Shabnaz, String> getshabnazDao() throws SQLException {
		if (shabnazDao == null) {
			ConnectionSource c = getConnectionSource();
			shabnazDao = DaoManager.createDao(getConnectionSource(),
					Shabnaz.class);
		}
		return shabnazDao;
	}
	
	//getshabnazDao

	

	
	@Override
	public void close() {
		super.close();
		//workOrderDao = null;
		
	}
	
	
	
	
}
