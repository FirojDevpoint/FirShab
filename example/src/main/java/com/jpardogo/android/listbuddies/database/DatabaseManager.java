package com.jpardogo.android.listbuddies.database;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;




public class DatabaseManager<H extends OrmLiteSqliteOpenHelper> {
	
	private H helper;
	
		public H getNGISHelper(Context context)
	{
		if(helper == null)
		{
			DatabaseNGISHelper helperCls = new DatabaseNGISHelper(context);
			helper = (H) OpenHelperManager.getHelper(context, helperCls.getClass());
		  
		}
		return helper;
	}
	

//	public void releaseHelper(H helper)
//	{
//		if (helper != null) {
//			OpenHelperManager.releaseHelper();
//			helper = null;
//		}
//	}
	
}
