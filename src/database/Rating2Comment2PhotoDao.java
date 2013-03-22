package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import my_activities.Rating2Comment2Photo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RATING2_COMMENT2_PHOTO.
*/
public class Rating2Comment2PhotoDao extends AbstractDao<Rating2Comment2Photo, Long> {

    public static final String TABLENAME = "RATING2_COMMENT2_PHOTO";

    /**
     * Properties of entity Rating2Comment2Photo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SourceId = new Property(1, Long.class, "sourceId", false, "SOURCE_ID");
        public final static Property TargetId = new Property(2, Long.class, "targetId", false, "TARGET_ID");
    };


    public Rating2Comment2PhotoDao(DaoConfig config) {
        super(config);
    }
    
    public Rating2Comment2PhotoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RATING2_COMMENT2_PHOTO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SOURCE_ID' INTEGER," + // 1: sourceId
                "'TARGET_ID' INTEGER);"); // 2: targetId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RATING2_COMMENT2_PHOTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Rating2Comment2Photo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long sourceId = entity.getSourceId();
        if (sourceId != null) {
            stmt.bindLong(2, sourceId);
        }
 
        Long targetId = entity.getTargetId();
        if (targetId != null) {
            stmt.bindLong(3, targetId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Rating2Comment2Photo readEntity(Cursor cursor, int offset) {
        Rating2Comment2Photo entity = new Rating2Comment2Photo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // sourceId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // targetId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Rating2Comment2Photo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSourceId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTargetId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Rating2Comment2Photo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Rating2Comment2Photo entity) {
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
