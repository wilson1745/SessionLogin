package e.wilso.sessionlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{

   public static final String TAG = DbHelper.class.getSimpleName();
   public static final String DB_NAME = "myapp.db";
   public static final int DB_VERSION = 1;

   public static final String USER_TABLE = "users";
   public static final String COLUMN_ID = "_id";
   public static final String COLUMN_EMAIL = "email";
   public static final String COLUMN_PASS = "password";

   private static DbHelper instance;

   /*
    create table users(
        id integer primary key autoincrement,
        email text,
        password text);
     */
   public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
           + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
           + COLUMN_EMAIL + " TEXT,"
           + COLUMN_PASS + " TEXT);";

   public static DbHelper getInstance(Context ctx) {
      if(instance == null) {
         instance = new DbHelper(ctx, DB_NAME, null, DB_VERSION);
      }
      return instance;
   }

   public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
      super(context, name, factory, version);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL(CREATE_TABLE_USERS);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
      onCreate(db);
   }

   /**
    * Storing user details in database
    * */
   public void addUser(String email, String password) {
      SQLiteDatabase db = this.getWritableDatabase();

      ContentValues values = new ContentValues();
      values.put(COLUMN_EMAIL, email);
      values.put(COLUMN_PASS, password);

      long id = db.insert(USER_TABLE, null, values);
      db.close();

      Log.d(TAG, "user inserted" + id);
   }

   public boolean getUser(String email, String pass){
      //HashMap<String, String> user = new HashMap<String, String>();
      String selectQuery = "SELECT * FROM  " + USER_TABLE + " WHERE " +
              COLUMN_EMAIL + " = " + "'" + email + "'" + " AND " + COLUMN_PASS + " = " + "'" + pass +"'";

      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);
      // Move to first row
      cursor.moveToFirst();
      if (cursor.getCount() > 0) {
         return true;
      }
      cursor.close();
      db.close();

      return false;
   }
}
