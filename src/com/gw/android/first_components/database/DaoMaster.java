package com.gw.android.first_components.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.IdentityScopeType;

import com.gw.android.first_components.my_components.comment.CommentDao;
import com.gw.android.first_components.my_components.photo.PhotoDao;
import com.gw.android.first_components.my_components.tag.TagDao;
import com.gw.android.first_components.my_components.binomio.BinomioDao;
import com.gw.android.first_components.my_components.gps.CoordinatesDao;
import com.gw.android.first_components.my_components.faq.FaqDao;
import com.gw.android.first_components.my_components.user.UserDao;
import com.gw.android.first_components.my_components.rating.RatingDao;
import com.gw.android.first_components.my_components.rating2comment.RatingToCommentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CommentDao.createTable(db, ifNotExists);
        PhotoDao.createTable(db, ifNotExists);
        TagDao.createTable(db, ifNotExists);
        BinomioDao.createTable(db, ifNotExists);
        CoordinatesDao.createTable(db, ifNotExists);
        FaqDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        RatingDao.createTable(db, ifNotExists);
        RatingToCommentDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CommentDao.dropTable(db, ifExists);
        PhotoDao.dropTable(db, ifExists);
        TagDao.dropTable(db, ifExists);
        BinomioDao.dropTable(db, ifExists);
        CoordinatesDao.dropTable(db, ifExists);
        FaqDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        RatingDao.dropTable(db, ifExists);
        RatingToCommentDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CommentDao.class);
        registerDaoClass(PhotoDao.class);
        registerDaoClass(TagDao.class);
        registerDaoClass(BinomioDao.class);
        registerDaoClass(CoordinatesDao.class);
        registerDaoClass(FaqDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(RatingDao.class);
        registerDaoClass(RatingToCommentDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
