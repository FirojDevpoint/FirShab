package com.jpardogo.android.listbuddies.database;


import android.annotation.TargetApi;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseNGISInitializer extends SQLiteOpenHelper{

	//Path to the device folder with databases



	private static String DATABASE_NAME = "Shabnaz.db";
	private static int DATABASE_VERSION = 1;
    private static String DB_PATH = "";

//    public static String getDB_PATH() {
//		return DB_PATH;
//	}
//
//	public static void setDB_PATH(String dB_PATH) {
//		DB_PATH = dB_PATH;
//	}
    
	private SQLiteDatabase nGISDatabase; 
	private final Context myContext;
    
    
	public DatabaseNGISInitializer(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/"; 
	    this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	public void createDataBase() throws IOException{
		 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("static-access")
//	public void destroyDatabase()
//	{
//		if(checkDataBase())
//		{
//		String myPath = DB_PATH + DATABASE_NAME;
//		nGISDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//		if(nGISDatabase!=null)
//		{
//		File outFile = new File(DB_PATH + DATABASE_NAME);
//		Boolean x = nGISDatabase.deleteDatabase(outFile);
//		}
//		}
//
//	}
	
	private boolean checkDataBase(){
		 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
     		//database does't exist yet.
     	}
     	if(checkDB != null){
 
    		checkDB.close();
     	}
 
    	return checkDB != null ? true : false;
    }
	private void copyDataBase() throws IOException{
		 
    	try {
			//Open your local db as the input stream
			InputStream InputBuf = myContext.getAssets().open(DATABASE_NAME);
 
			// Path to the just created empty db
			String outFileName = DB_PATH + DATABASE_NAME;
 
			//Open the empty db as the output stream
			File outFile = new File(DB_PATH + DATABASE_NAME); 
			OutputStream outputDBfile = new FileOutputStream(outFileName);
 
			//transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = InputBuf.read(buffer))>0){
				outputDBfile.write(buffer, 0, length);
			}
 
			//Close the streams
			outputDBfile.flush();
			outputDBfile.close();
			InputBuf.close();
			openDataBase();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
	public void openDataBase() throws SQLException{
		 
    	//Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        nGISDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
         
    }

	

}
