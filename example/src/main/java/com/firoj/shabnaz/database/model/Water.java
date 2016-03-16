package com.firoj.shabnaz.database.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "Water")
public class Water {
	
	@DatabaseField(generatedId = true)
	int WoterID;
	@DatabaseField
	String senderNum;
	@DatabaseField
	String message;



	public String getSenderNum() {
		return senderNum;
	}

	public void setSenderNum(String senderNum) {
		this.senderNum = senderNum;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	public int getWaterID() {
		return WoterID;
	}

	public void setWaterID(int waterID) {
		WoterID = waterID;
	}
	
	
	
}
