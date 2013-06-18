package com.gw.android.first_components.my_components.rating;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.gw.android.first_components.database.DaoSession;

import com.gw.android.first_components.my_components.rating.Rating;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RATING.
*/
public class RatingDao extends AbstractDao<Rating, Long> {

    public static final String TABLENAME = "RATING";

    /**
     * Properties of entity Rating.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TargetId = new Property(1, Long.class, "targetId", false, "TARGET_ID");
        public final static Property ServerId = new Property(2, Long.class, "serverId", false, "SERVER_ID");
        public final static Property Value = new Property(3, float.class, "value", false, "VALUE");
    };


    public RatingDao(DaoConfig config) {
        super(config);
    }
    
    public RatingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RATING' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TARGET_ID' INTEGER," + // 1: targetId
                "'SERVER_ID' INTEGER," + // 2: serverId
                "'VALUE' REAL NOT NULL );"); // 3: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RATING'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Rating entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long targetId = entity.getTargetId();
        if (targetId != null) {
            stmt.bindLong(2, targetId);
        }
 
        Long serverId = entity.getServerId();
        if (serverId != null) {
            stmt.bindLong(3, serverId);
        }
        stmt.bindDouble(4, entity.getValue());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Rating readEntity(Cursor cursor, int offset) {
        Rating entity = new Rating( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // targetId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // serverId
            cursor.getFloat(offset + 3) // value
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Rating entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTargetId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setServerId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setValue(cursor.getFloat(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Rating entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Rating entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
