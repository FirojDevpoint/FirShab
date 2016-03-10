package com.jpardogo.android.listbuddies.database.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "Water")
public class Water {
	
	@DatabaseField(generatedId = true)
	int WoterID;

	public int getWaterID() {
		return WoterID;
	}

	public void setWaterID(int waterID) {
		WoterID = waterID;
	}
	
	
	
}
