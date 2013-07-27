package it.ecosw.dudo.historical;

/**
 * This file is part of Dudo for Android software.
 *
 *  Dudo is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Dudo is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Dudo.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to interface with historical database
 * @author Enrico Strocchi
 */
public class SqlHelper extends SQLiteOpenHelper {

    private SQLiteDatabase myDB;
	
	private final static int DB_VERSION = 1;
    private final static String DB_NAME = "dudoroll_db.s3db";
    
    private final String ROLLSDB_TABLE = "dudo_rolls_db";
    private final String ID = "_id";
    private final String ROLL_PLAYER = "player";
    private final String ROLL_VALUE = "roll";
    private final String ROLL_DATE = "date";
    
    private ArrayList<RollData> data;
    
    private final String DUDO_ROLLS_TABLE_CREATE = 
    		"CREATE TABLE IF NOT EXISTS " +
			ROLLSDB_TABLE + "( " +
			ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			ROLL_PLAYER + " TEXT NOT NULL, " +
			ROLL_VALUE + " TEXT NOT NULL, " +
			ROLL_DATE	+ " INTEGER NOT NULL);";
    
	public SqlHelper(Context context) {
	       super(context, DB_NAME, null, DB_VERSION);
	        // Store the context for later use
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DUDO_ROLLS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Insert one element in the database
	 * @param roll value of roll in string
	 * @param date date of roll
	 * @param maximum number of rows allowed in the database
	 */
    public void insertRoll(String player, String roll, Date date, int maxrow){
    	myDB = getWritableDatabase();
        ContentValues cv=new ContentValues();
        boolean del = false;
    	while (fetchRolls().getCount()>=maxrow) {
    		myDB.delete(ROLLSDB_TABLE, ROLL_DATE+"= (SELECT MIN("+ROLL_DATE+") FROM "+ROLLSDB_TABLE+")", null);
    		del = true;
    	} 
    	if(del) myDB.execSQL("VACUUM");
    	cv.put(ROLL_PLAYER, player);
        cv.put(ROLL_VALUE, roll);
        cv.put(ROLL_DATE, date.getTime());
        myDB.insert(ROLLSDB_TABLE, null, cv);
        myDB.close();
    }
	
    /**
     * Delete all the element of database
     */
    public void reset(){
    	myDB = getWritableDatabase();
    	myDB.delete(ROLLSDB_TABLE, null, null);
    	myDB.close();
    }
    
    /**
     * Return the number of row inside the database
     * @return number of row
     */
    public int countRow(){
    	myDB = getWritableDatabase();
    	Cursor c = fetchRolls();
    	int num = c.getCount();
    	myDB.close();
    	return num;
    }
    
    /**
     * Return array list with database elements
     * @return arraylist with all elements of database
     */
    public ArrayList<RollData> getRollData(){
    	myDB = getWritableDatabase();
    	data = new ArrayList<RollData>();
    	Cursor c = fetchRolls();
    	// Column index
    	int playerCol=c.getColumnIndex(ROLL_PLAYER);
    	int rollCol=c.getColumnIndex(ROLL_VALUE);
        int dateCol=c.getColumnIndex(ROLL_DATE);
        if(c.moveToFirst()){
            do {
                    data.add(new RollData(c.getString(playerCol),c.getString(rollCol), new Date(c.getLong(dateCol)))); 
            } while (c.moveToNext()); //iter to next element
        }
        myDB.close();
        Collections.sort(data);
        return data;
    }
    
    private Cursor fetchRolls(){
    	return myDB.query(ROLLSDB_TABLE, null,null,null,null,null,null);
    }
    
}
