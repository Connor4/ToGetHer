package com.running.together.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "ToGetHer";  
    private final static int DATABASE_VERSION = 1;  
    private final static String TABLE_NAME = "Recently";
  //构造函数，创建数据库  
    public SQLiteHelper(Context context) {  
           super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME   
                + " (_id integer primary key autoincrement, "   
                + " Uname varchar(50),"     
                + " Fname varchar(50)," 
                + " Fnickname varchar(50)," 
                + " Furl varchar(100),"
                + " Ftext text, "
                + " Ftime varchar(50),"
                + " State int )";
		String sql1 = "CREATE TABLE " + "Msg"   
                + " (_id integer primary key autoincrement, "   
                + " Uname varchar(50),"     
                + " Fname varchar(50)," 
                + " Frome varchar(10),"
                + " Ftext text, "
                + " Ftime varchar(50))";
		db.execSQL(sql1);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;  
        db.execSQL(sql);  
        onCreate(db); 
	}
	public Cursor select() {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);  
        return cursor;  
	}
	//插入一条记录  
    public long insert(String Uname,String Fname,String Fnickname,String Furl,String Ftext,String Ftime) {  
           SQLiteDatabase db = this.getWritableDatabase();  
           ContentValues cv = new ContentValues();  
           cv.put("Uname", Uname);  
           cv.put("Fname", Fname);  
           cv.put("Fnickname", Fnickname);  
           cv.put("Furl", Furl);
           cv.put("Ftext", Ftext);
           cv.put("Ftime", Ftime);
           cv.put("State", 1);
           long row = db.insert(TABLE_NAME, null, cv);  
           return row;  
    }  
    public long insertmsg(String Uname,String Fname,String Ftext,String Ftime,String from) {  
    	Log.d("data", Uname+Fname+Ftext+Ftime);
        SQLiteDatabase db = this.getWritableDatabase();  
        ContentValues cv = new ContentValues();  
        cv.put("Uname", Uname);  
        cv.put("Fname", Fname); 
        cv.put("Frome", from);
        cv.put("Ftext", Ftext);
        cv.put("Ftime", Ftime);
        long row = db.insert("Msg", null, cv);  
        return row;  
 }  
    public Cursor querymsg(String[] args) {  
    	Log.d("data", args[0]);
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT * FROM Msg WHERE Uname = ?", args);  
        return cursor;  
    }  
    //根据条件查询  (用户)
    public Cursor query(String[] args) {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Uname = ?", args);  
        return cursor;  
    }  
    public Cursor querymsg1(String[] args) {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT * FROM Msg WHERE Uname = ? AND Fname=? ", args);  
        return cursor;  
    }  
    //根据条件查询  （用户与特定用户聊天）
    public Cursor query1(String[] args) {  
        SQLiteDatabase db = this.getReadableDatabase();  
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Uname = ? AND Fname=? ", args);  
        return cursor;  
    }  
    //删除记录  
    public void delete(String[]arg) {  
    	//arg格式["Uanme","Fname"]
           SQLiteDatabase db = this.getWritableDatabase();  
           String where ="Uname = ? AND Fname=? ";    
           db.delete(TABLE_NAME, where,arg);  
    }  

    //更新记录  
    public void update(String[]arg,String Ftext,String Ftime,int state) {  
    	//arg格式["Uanme","Fname"]
    	   Log.d("data", Ftext);
    	   Log.d("data", arg[0]+arg[1]);
           SQLiteDatabase db = this.getWritableDatabase();  
           String where = "Uname = ? AND Fname=? ";  
           ContentValues cv = new ContentValues();    
           cv.put("Ftext", Ftext); 
           cv.put("Ftime", Ftime); 
           cv.put("State", state); 
           db.update(TABLE_NAME, cv, where, arg);  
    }  
    //更新记录  
    public void state(String[]arg) {  
    	//arg格式["Uanme","Fname"]
           SQLiteDatabase db = this.getWritableDatabase();  
           String where = "Uname = ? AND Fname=? AND Ftext=? AND Ftime=?";  
           ContentValues cv = new ContentValues();     
           cv.put("State", 0); 
           db.update(TABLE_NAME, cv, where, arg);  
    }

}
