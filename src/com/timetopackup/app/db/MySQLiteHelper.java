package com.timetopackup.app.db;

import java.util.LinkedList;
import java.util.List;

import com.timetopackup.app.obj.Categorie;
import com.timetopackup.app.obj.Element;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper
{	 
    /* database version & name */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PACKUPdb";
 
    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db)
    {
    	/* =========================== CATEGORIE =========================== */
        /* SQL statement to create categorie table */
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE CATEGORIES ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "catname TEXT, "+
                "catdesc TEXT, "+
                "catcolo TEXT, "+
                "caticon TEXT )";
        /* command to create it. */
        db.execSQL(CREATE_CATEGORIES_TABLE);
        
        /* =========================== ELEMENTS =========================== */
        /* SQL statement to create element table */
        String CREATE_ELEMENTS_TABLE = "CREATE TABLE ELEMENTS ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "catname TEXT, "+
                "elename TEXT, "+
                "elestat TEXT, "+
                "eleicon TEXT )";
        /* command to create it. */
        db.execSQL(CREATE_ELEMENTS_TABLE);

        /* =========================== INSTALLATION =========================== */
        /* SQL statement to create element table */
        String CREATE_INSTALLATION_TABLE = "CREATE TABLE INSTALLATION ( " +
        		"first TEXT )";
        /* command to create it. */
        db.execSQL(CREATE_INSTALLATION_TABLE);
        initializeAppDemo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
        db.execSQL("DROP TABLE IF EXISTS ELEMENTS");
 
        // create fresh books table
        this.onCreate(db);
    }
    
    /* #################################################### */
    /* #################### CATEGORIES #################### */
    /* #################################################### */
    // Table configuration
    private static final String TABLE_CATEGORIES = "CATEGORIES"; 	// table name
    private static final String KEY_ID = "id";						// column 1
    private static final String KEY_CATNAME = "catname";			// column 2
    private static final String KEY_CATDESC = "catdesc";			// column 3
    private static final String KEY_CATCOLO = "catcolo";			// column 4
    private static final String KEY_CATICON = "caticon";			// column 5
    
    /* #################################################### */
    /* #################### ELEMENTS ###################### */
    /* #################################################### */
    // Table configuration
    private static final String TABLE_ELEMENTS = "ELEMENTS"; 		// table name
  //private static final String KEY_ID = "id";						// declared before
  //private static final String KEY_CATNAME = "catname";			// declared before
    private static final String KEY_ELENAME = "elename";			// column 3
    private static final String KEY_ELESTAT = "elestat";			// column 4
    private static final String KEY_ELEICON = "eleicon";			// column 5
    
    /* #################################################### */
    /* #################### INSTALLATION ################## */
    /* #################################################### */
    // Table configuration
    private static final String TABLE_INSTALLATION = "INSTALLATION"; // table name
    private static final String KEY_FIRST = "first";				 // column 1 (unique)
    
    /* bellow, not used for the moment (just in comment because of a probably future use
    private static final String[] COLUMNS = {KEY_ID,KEY_CATNAME,KEY_CATDESC,KEY_CATCOLO, KEY_CATICON};
    */
    // End configuration
    
    /* ### METHODS ### */
    
    public void initializeAppDemo(SQLiteDatabase db)
    {		
		// 1. get reference to writable DB
		//SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_FIRST, "YES"); // set the first installation at YES 
		
		// 3. insert
		db.insert(TABLE_INSTALLATION,null,values); 
		
		// 4. close
		//db.close(); 
	}
    
    public void addCategorie(Categorie cat)
    {		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_CATNAME, cat.getName()); // get title 
		values.put(KEY_CATDESC, cat.getDesc()); // get author
		values.put(KEY_CATCOLO, cat.getColo()); // get color
		values.put(KEY_CATICON, cat.getIcon()); // get icon
		
		// 3. insert
		db.insert(TABLE_CATEGORIES,null,values); 
		
		// 4. close
		db.close(); 
	} 
    
    public void addElement(Element ele)
    {		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_CATNAME, ele.getCatN()); // get categorie name 
		values.put(KEY_ELENAME, ele.getName()); // get element name
		values.put(KEY_ELESTAT, ele.getStat()); // get element status
		values.put(KEY_ELEICON, ele.getIcon()); // get element icon (name)
		
		// 3. insert
		db.insert(TABLE_ELEMENTS,null,values); 
		
		// 4. close
		db.close(); 
	}
   
    public List<Categorie> getAllCategories()
    {
        List<Categorie> cats = new LinkedList<Categorie>();
 
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Categorie cat = null;
        if (cursor.moveToFirst())
        {
            do {
                cat = new Categorie();
                cat.setId(Integer.parseInt(cursor.getString(0)));
                cat.setName(cursor.getString(1));
                cat.setDesc(cursor.getString(2));
                cat.setColo(cursor.getString(3));
                cat.setIcon(cursor.getString(4));
 
                // Add book to books
                cats.add(cat);
            } while (cursor.moveToNext());
        }
 
        // return books
        return cats;
    }   
    
    public boolean uniqueCategorie(String nom)
    {
    	String query = 	"SELECT * FROM " + TABLE_CATEGORIES + 
    					" WHERE " + KEY_CATNAME + " = \"" + nom + "\"";
    	
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        
        if (cursor.moveToFirst())
        {
        	return false;
        }
        
        return true;
    }
    
    public boolean uniqueElement(String cat, String ele)
    {
    	String query = 	"SELECT * FROM " + TABLE_ELEMENTS + 
    			" WHERE " 	+ KEY_CATNAME + " = \"" + cat + "\"" +
    			" AND "		+ KEY_ELENAME + " = \"" + ele + "\"";

    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(query, null);

    	if (cursor.moveToFirst())
    	{
    		return false;
    	}

    	return true;
    }
    
    public List<Element> getAllElements(String cat)
    {
        List<Element> eles = new LinkedList<Element>();
 
        // 1. build the query
        String query = 	"SELECT * FROM " 	+ TABLE_ELEMENTS 	+ 
        				" WHERE " 			+ KEY_CATNAME 		+ " = \"" + cat + "\"" + 
        				" ORDER BY " 		+ KEY_ID 			+ " DESC";;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        Element ele = null;
        if (cursor.moveToFirst())
        {
            do {
                ele = new Element();
                ele.setCatN(cursor.getString(1));
                ele.setName(cursor.getString(2));
                ele.setStat(cursor.getString(3));
                ele.setIcon(cursor.getString(4));
 
                // Add book to books
                eles.add(ele);
            } while (cursor.moveToNext());
        }
 
        // return books
        return eles;
    }    

    public void updateElementStatus(String eleid, String stat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_ELESTAT, stat);
        db.update(TABLE_ELEMENTS, values, KEY_ID + " = ?", new String[] { String.valueOf(eleid) });
   
    }
    
    public String getElementStatus(String cat, String ele)
    {
        // 1. build the query
        String query = 	"SELECT " 	+ KEY_ELESTAT 		+	 
        				" FROM " 	+ TABLE_ELEMENTS 	+ 
        				" WHERE " 	+ KEY_CATNAME 		+ " = \"" + cat + "\"" + 
        				" AND " 	+ KEY_ELENAME 		+ " = \"" + ele + "\"";
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0)
        {
        	cursor.moveToFirst();
        	return cursor.getString(0);
        }
        else
        {
        	return "";
        }
    }
    
    public int getAppDemo()
    {
        // 1. build the query
        String query = 	"SELECT " 	+ KEY_FIRST 		+	 
        				" FROM " 	+ TABLE_INSTALLATION;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0)
        {
        	cursor.moveToFirst();
        	Log.w("TTPU", "JOEY | key_first= " + cursor.getString(0));
            if (cursor.getString(0).equals("YES"))
            {
            	return 1;
            }
            else
            {
            	return 0;
            }
        }
        else
        {
        	return 0;
        }
    }
    
    public void setAppDemo()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_FIRST, "NO");
    	db.update(TABLE_INSTALLATION, values, KEY_FIRST + " = ?", new String[] { "YES" });
    }

    public String getElementID(String cat, String ele)
    {
        // 1. build the query
        String query = 	"SELECT " 	+ KEY_ID 		+	 
        				" FROM " 	+ TABLE_ELEMENTS 	+ 
        				" WHERE " 	+ KEY_CATNAME 		+ " = \"" + cat + "\"" + 
        				" AND " 	+ KEY_ELENAME 		+ " = \"" + ele + "\"";
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0)
        {
        	cursor.moveToFirst();
        	return cursor.getString(0);
        }
        else
        {
        	return "";
        }
    }
    
    public void deleteAllCategories()
    {
    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_CATEGORIES,
        		KEY_ID + " >= ?",
                new String[] { "0" });
 
        // 3. close
        db.close();
    }
    
    public void deleteCategorie(String n)
    {
    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_CATEGORIES,
        		KEY_CATNAME + " = ?",
                new String[] { n });
 
        // 3. close
        db.close();
    }
    
    public void deleteElement(String c, String e)
    {
    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_ELEMENTS,
        		KEY_CATNAME + " = ? AND " + KEY_ELENAME + " = \"" + e + "\"",
                new String[] { c });
 
        // 3. close
        db.close();
    }
}