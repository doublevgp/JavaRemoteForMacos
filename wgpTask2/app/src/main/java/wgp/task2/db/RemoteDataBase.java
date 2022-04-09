package wgp.task2.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import wgp.task2.data.LinkData;

public class RemoteDataBase {
    public static final String KEY_ID = "mid";
    public static final String KEY_LINK_NAME = "link_name";
    public static final String KEY_LINK_IP = "link_ip";
    public static final String KEY_LINK_PORT = "link_port";
    public static final String KEY_HOT_KEY_NAME = "hot_key_name";
    public static final String KEY_HOT_KEY_CMD = "hot_key_cmd";
    public static final String KEY_COMBO_KEY_NAME = "combo_key_name";
    public static final String KEY_COMBO_KEY_CMD = "combo_key_cmd";
    public static final String KEY_FILE_TYPE = "file_type"; // default 默认 pdf ppt doc excel ...
    public static final String KEY_LEVEL = "level";
    public static final String KEY_LOOK_UP = "key_look_up";
    private static final String DB_NAME = "remote.db";
    public static final String LINK_TABLE = "link";
    public static final String HOT_KEY_TABLE = "hot_key";
    public static final String COMBO_KEY_TABLE = "combo_key";


    private int version = 1;
    Activity activity;
    private SQLiteDatabase db;
    DatabaseHelper databaseHelper;

    public RemoteDataBase(Activity activity) {
        this.activity = activity;
    }

    public void open() {
        if (db == null || !db.isOpen()) {
            databaseHelper = new DatabaseHelper();
            db = databaseHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private ContentValues enCodeCotentValues(LinkData data) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_LINK_NAME, data.getLinkName());
        cv.put(KEY_LINK_IP, data.getLinkIp());
        cv.put(KEY_LINK_PORT, data.getLinkPort());
        return cv;
    }

    public LinkData queryLinkDataById(int id) {
        String sql = String.format("select * from %s where _id=%d", LINK_TABLE, id);
        ArrayList<LinkData> list = getCityListBySql(sql, null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

//    private String generateLookup(City city) {
//        String name = city.getName();
//        String enName = city.getEnName();
//        String initialName = city.getInitialName();
//        String[] enNameArray = enName.split("\\s");
//        StringBuilder sb = new StringBuilder();
//        sb.append(name + " ");
//        sb.append(enName + " ");
//        sb.append(initialName + " ");
//        sb.append(enName.replaceAll("\\s", "") + " ");
//        for (int i = 1; i < enNameArray.length; i++) {
//            sb.append(initialName.substring(0, i));
//            for (int j = i; j < enNameArray.length; j++) {
//                sb.append(enNameArray[j]);
//            }
//            sb.append(" ");
//        }
//        return sb.toString();
//    }
//
    private LinkData getCityFromCursor(Cursor c) {
        @SuppressLint("Range") String name = c.getString(c.getColumnIndex(KEY_LINK_NAME));
        @SuppressLint("Range") String link_ip = c.getString(c.getColumnIndex(KEY_LINK_IP));
        @SuppressLint("Range") int link_port = c.getInt(c.getColumnIndex(KEY_LINK_PORT));
        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
        LinkData data = new LinkData(name, link_ip, link_port);
        data.setId(id);
        return data;
    }

    public long insertData(LinkData data) {
        ContentValues cv = enCodeCotentValues(data);
        return db.insert(LINK_TABLE, KEY_LINK_NAME, cv);
    }
    public int deleteLinkData(LinkData data) {
        return db.delete(LINK_TABLE, "_id=?", new String[]{String.valueOf(data.getId())});
    }

    public int insertList(ArrayList<LinkData> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            LinkData data = list.get(i);
            if (insertData(data) > 0) {
                count += 1;
            }
        }
        return count;
    }

    public void clearDatabase() {
        if (db != null && db.isOpen()) {
            databaseHelper.resetData(db);
        }
    }

    public ArrayList<LinkData> getCityListFromCursor(Cursor c) {
        ArrayList<LinkData> list = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            LinkData linkData = getCityFromCursor(c);
            list.add(linkData);
        }
        return list;
    }

    public ArrayList<LinkData> getLinkDataList() {
        String sql = String.format("select * from %s", LINK_TABLE);
        return getCityListBySql(sql, null);
    }

    private ArrayList<LinkData> getCityListBySql(String sql, String[] args) {
        Cursor c = db.rawQuery(sql, args);
        ArrayList<LinkData> list = getCityListFromCursor(c);
        c.close();
        return list;
    }


    class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper() {
            super(activity, DB_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // LINK_TABLE KEY_LINK_NAME text KEY_IP text KEY_PORT int
            String sql = String.format("create table if not exists %s " +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT,%s text, %s text, %s int)",
                    LINK_TABLE, KEY_LINK_NAME, KEY_LINK_IP, KEY_LINK_PORT);
            db.execSQL(sql);
            // HOT_KEY_TABLE HOT_KEY_NAME text HOT_KEY_CMD text
            sql = String.format("create table if not exists %s " +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s text)",
                    HOT_KEY_TABLE, KEY_HOT_KEY_NAME, KEY_HOT_KEY_CMD, KEY_FILE_TYPE);
            db.execSQL(sql);
            // COMBO_KEY_TABLE COMBO_KEY_NAME text COMBO_KEY_CMD text
            sql = String.format("create table if not exists %s " +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text)",
                    COMBO_KEY_TABLE, KEY_COMBO_KEY_NAME, KEY_COMBO_KEY_CMD);
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetData(db);
        }

        public void resetData(SQLiteDatabase db) {
            String sql = String.format("drop table if exists %s", LINK_TABLE);
            db.execSQL(sql);
            sql = String.format("drop table if exists %s", HOT_KEY_TABLE);
            db.execSQL(sql);
            sql = String.format("drop table if exists %s", COMBO_KEY_TABLE);
            db.execSQL(sql);
            onCreate(db);
        }

    }
}