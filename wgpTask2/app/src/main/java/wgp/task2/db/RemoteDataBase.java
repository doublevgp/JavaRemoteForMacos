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

import wgp.task2.data.DlfData;
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
//    public static final String KEY_LEVEL = "level";
//    public static final String KEY_LOOK_UP = "key_look_up";
    public static final String KEY_FILE_NAME = "file_name";
    public static final String KEY_FILE_SIZE = "file_size";
    public static final String KEY_FILE_PATH = "file_path";
    public static final String KEY_DOWN_SIZE = "down_size";
    public static final String KEY_DOWN_IP = "down_ip";
    public static final String KEY_DOWN_PORT = "down_port";
//    private static final String DB_NAME = "filedb.db";
    public static final String FILE_TABLE = "download";
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

    private ContentValues enCodeCotentValues(DlfData data) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_FILE_NAME, data.getFile_name());
        cv.put(KEY_FILE_PATH, data.getFile_path());
        cv.put(KEY_FILE_SIZE, data.getFile_size());
        cv.put(KEY_DOWN_SIZE, data.getFile_down_size());
        cv.put(KEY_DOWN_IP, data.getIp());
        cv.put(KEY_DOWN_PORT, data.getPort());
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

    // region getFromCursor
    private LinkData getCityFromCursor(Cursor c) {
        @SuppressLint("Range") String name = c.getString(c.getColumnIndex(KEY_LINK_NAME));
        @SuppressLint("Range") String link_ip = c.getString(c.getColumnIndex(KEY_LINK_IP));
        @SuppressLint("Range") int link_port = c.getInt(c.getColumnIndex(KEY_LINK_PORT));
        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
        LinkData data = new LinkData(name, link_ip, link_port);
        data.setId(id);
        return data;
    }
    private DlfData getDlfDataFromCursor(Cursor c) {
        @SuppressLint("Range") String name = c.getString(c.getColumnIndex(KEY_FILE_NAME));
        @SuppressLint("Range") String path = c.getString(c.getColumnIndex(KEY_FILE_PATH));
        @SuppressLint("Range") long filesz = c.getLong(c.getColumnIndex(KEY_FILE_SIZE));
        @SuppressLint("Range") long downsz = c.getLong(c.getColumnIndex(KEY_DOWN_SIZE));
        @SuppressLint("Range") String ip = c.getString(c.getColumnIndex(KEY_DOWN_IP));
        @SuppressLint("Range") int port = c.getInt(c.getColumnIndex(KEY_DOWN_PORT));
        DlfData data = new DlfData(name, path, filesz, downsz, ip, port);
        return data;
    }
    // endregion
    // region insertData
    public long insertData(LinkData data) {
        ContentValues cv = enCodeCotentValues(data);
        return db.insert(LINK_TABLE, KEY_LINK_NAME, cv);
    }
    public long insertData(DlfData data) {
        ContentValues cv = enCodeCotentValues(data);
        return db.insert(FILE_TABLE, KEY_FILE_NAME, cv);
    }
    // endregion
    // region deleteData
    public int deleteLinkData(DlfData data) {
        return db.delete(FILE_TABLE, "file_name=? and file_path=?", new String[]{data.getFile_name(), data.getFile_path()});
    }
    public int deleteLinkData(LinkData data) {
        return db.delete(LINK_TABLE, "_id=?", new String[]{String.valueOf(data.getId())});
    }
    // endregion
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
    // region getCityListFromCursor
    public ArrayList<LinkData> getCityListFromCursor(Cursor c) {
        ArrayList<LinkData> list = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            LinkData linkData = getCityFromCursor(c);
            list.add(linkData);
        }
        return list;
    }
    public ArrayList<DlfData> getDownFileListFromCursor(Cursor c) {
        ArrayList<DlfData> list = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            DlfData dlfData = getDlfDataFromCursor(c);
            list.add(dlfData);
        }
        return list;
    }
    // endregion
    public void updateFileData() {

    }
    // region getDataList
    public ArrayList<LinkData> getLinkDataList() {
        String sql = String.format("select * from %s", LINK_TABLE);
        return getCityListBySql(sql, null);
    }
    public ArrayList<DlfData> getDownloadFileList() {
        String sql = String.format("select * from %s", FILE_TABLE);
        return getDownloadFileListBySql(sql, null);
    }

    private ArrayList<LinkData> getCityListBySql(String sql, String[] args) {
        Cursor c = db.rawQuery(sql, args);
        ArrayList<LinkData> list = getCityListFromCursor(c);
        c.close();
        return list;
    }
    private ArrayList<DlfData> getDownloadFileListBySql(String sql, String[] args) {
        Cursor c = db.rawQuery(sql, args);
        ArrayList<DlfData> list = getDownFileListFromCursor(c);
        c.close();
        return list;
    }
    // endregion

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
            sql = String.format("create table if not exists %s " +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s long, %s long, %s text, %s int)",
                    FILE_TABLE, KEY_FILE_NAME, KEY_FILE_PATH, KEY_FILE_SIZE, KEY_DOWN_SIZE, KEY_DOWN_IP, KEY_DOWN_PORT);
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
            sql = String.format("drop table if exists %s", FILE_TABLE);
            db.execSQL(sql);
            onCreate(db);
        }
        public void resetDataByTableName(SQLiteDatabase db, String table_name) {
            String sql = String.format("drop table if exists %s", table_name);
            db.execSQL(sql);
            onCreate(db);
        }
    }
}