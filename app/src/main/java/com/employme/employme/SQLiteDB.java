package com.employme.employme;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB extends SQLiteOpenHelper {

    ///////////////////////////////////////////////////////////
    private static final String TABLE_SESSION = "session";

    private static final String KEY_SESSION_ID = "id";
    private static final String KEY_SESSION_USER_ID = "user_id";

    private static final String[] SESSION_COLUMNS = {KEY_SESSION_ID, KEY_SESSION_USER_ID};

    public List<String> getAllSessions() {
        List<String> users = new LinkedList<String>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SESSION;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        if (cursor.moveToFirst()) {
            do {

                users.add(cursor.getString(0) + " " + cursor.getString(1));

            } while (cursor.moveToNext());
        }

        return users;
    }

    public String getSessionUser() {
        //IF 0 FRI
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_SESSION, // a. table
                        SESSION_COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(1) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            String userID = cursor.getString(1);
            return userID;
        }
        else {
            createSessionEntry();
            return "";
        }
    }

    private void createSessionEntry() {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_SESSION_USER_ID, "");

        // 3. insert
        db.insert(TABLE_SESSION, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public int updateSession(String newSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SESSION_USER_ID, newSession);

        int i = db.update(TABLE_SESSION, //table
                values, // column/value
                KEY_SESSION_ID + " = ?", // selections
                new String[] { String.valueOf(1) }); //selection args

        db.close();
        return i;
    }
    ///////////////////////////////////////////////////////////
    private static final String TABLE_JOBS = "joblisting";

    private static final String KEY_JOB_ID = "id";
    private static final String KEY_EMPLOYER_ID = "employer_id"; //0
    private static final String KEY_BUSINESS_NAME = "business_name"; //1
    private static final String KEY_LOGO_URL = "logo_url"; //2
    private static final String KEY_JOB_DESCRIPTION = "job_description"; //3
    private static final String KEY_BUSINESS_NUMBER = "business_number"; //4
    private static final String KEY_LOCATION = "location"; //5
    private static final String KEY_JOB_CATEGORY = "job_category"; //6
    private static final String KEY_MIN_AGE = "min_age"; //7
    private static final String KEY_MAX_AGE = "max_age"; //8

    private static final String[] JOBLISTING_COLUMNS = {KEY_JOB_ID, KEY_EMPLOYER_ID, KEY_BUSINESS_NAME, KEY_LOGO_URL, KEY_JOB_DESCRIPTION, KEY_BUSINESS_NUMBER, KEY_LOCATION, KEY_JOB_CATEGORY, KEY_MIN_AGE, KEY_MAX_AGE};

    public List<Integer> getAllJobs() {
        List<Integer> jobs = new LinkedList<Integer>();

        String query = "SELECT  * FROM " + TABLE_JOBS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                jobs.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        return jobs;
    }

    public List<String> getJobListing(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_JOBS, // a. table
                        JOBLISTING_COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        List<String> jobInfo = new LinkedList<String>();
        if (cursor.getCount() > 0) {
            jobInfo.add(String.valueOf(cursor.getInt(1)));
            jobInfo.add(cursor.getString(2));
            jobInfo.add(cursor.getString(3));
            jobInfo.add(cursor.getString(4));
            jobInfo.add(cursor.getString(5));
            jobInfo.add(cursor.getString(6));
            jobInfo.add(cursor.getString(7));
            jobInfo.add(cursor.getString(8));
            jobInfo.add(cursor.getString(9));
        } else return null;

        return jobInfo;
    }

    public void addJobListing(int employer_id, String business_name, String logo_url, String job_description, String business_number, String location, String job_category, String min_age, String max_age) {
        Log.d("addJobListing", business_name);
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYER_ID, employer_id);
        values.put(KEY_BUSINESS_NAME, business_name);
        values.put(KEY_LOGO_URL, logo_url);
        values.put(KEY_JOB_DESCRIPTION, job_description);
        values.put(KEY_BUSINESS_NUMBER, business_number);
        values.put(KEY_LOCATION, location);
        values.put(KEY_JOB_CATEGORY, job_category);
        values.put(KEY_MIN_AGE, min_age);
        values.put(KEY_MAX_AGE, max_age);

        // 3. insert
        db.insert(TABLE_JOBS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }
    ///////////////////////////////////////////////////////////

    private static final SQLiteDB db = new SQLiteDB(MyApplication.getAppContext());

    public static SQLiteDB getInstance() {
        return db;
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SQLiteDB";

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE user ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "email TEXT, "+
                "password TEXT )";

        String CREATE_JOBS_TABLE = "CREATE TABLE joblisting ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "employer_id INTEGER, " +
                "business_name TEXT, " +
                "logo_url TEXT, " +
                "job_description TEXT, " +
                "business_number TEXT, " +
                "location TEXT, " +
                "job_category TEXT, " +
                "min_age INTEGER, " +
                "max_age INTEGER )";

        String CREATE_SESSION_TABLE = "CREATE TABLE session ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT )";

        //Create the tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_JOBS_TABLE);
        db.execSQL(CREATE_SESSION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS joblisting");
        db.execSQL("DROP TABLE IF EXISTS session");

        // create fresh users table
        this.onCreate(db);
    }

    // users table name
    private static final String TABLE_USER = "user";

    // users Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final String[] USER_COLUMNS = {KEY_ID,KEY_NAME,KEY_EMAIL,KEY_PASSWORD};

    public void addUser(User user){
        Log.d("addUser", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // get title
        values.put(KEY_EMAIL, user.getEmail()); // get title
        values.put(KEY_PASSWORD, user.getPassword()); // get title

        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public User getUser(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        USER_COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build user object
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setName(cursor.getString(1));
        user.setEmail(cursor.getString(2));
        user.setPassword(cursor.getString(3));

        Log.d("getUser(" + id + ")", user.toString());

        // 5. return user
        return user;
    }

    public User getUser(String email){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        USER_COLUMNS, // b. column names
                        " email = ?", // c. selections
                        new String[] { String.valueOf(email) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build user object

        if (cursor.getCount() > 0) {
            User user = new User();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            Log.d("getUser(" + email + ")", user.toString());
            return user;
        }
        else {
            Log.d("getUser(" + email + ")", "null");
            return null;
        }
        // 5. return user
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_USER;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));

                // Add user to users
                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers()", users.toString());

        // return users
        return users;
    }

    // Updating single user
    public int updateUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", user.getName()); // get title
        values.put("email", user.getEmail()); // get author
        values.put("password", user.getPassword());

        // 3. updating row
        int i = db.update(TABLE_USER, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single user
    public void deleteUser(User user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USER,
                KEY_ID+" = ?",
                new String[] { String.valueOf(user.getId()) });

        // 3. close
        db.close();

        Log.d("deleteUser", user.toString());
    }
}