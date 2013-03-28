package my_components.photo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import database.DaoSession;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PHOTO.
*/
public class PhotoDao extends AbstractDao<Photo, Long> {

    public static final String TABLENAME = "PHOTO";

    /**
     * Properties of entity Photo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TargetId = new Property(1, Long.class, "targetId", false, "TARGET_ID");
        public final static Property PhotoBytes = new Property(2, byte[].class, "photoBytes", false, "PHOTO_BYTES");
        public final static Property Text = new Property(3, String.class, "text", false, "TEXT");
        public final static Property Date = new Property(4, java.util.Date.class, "date", false, "DATE");
    };


    public PhotoDao(DaoConfig config) {
        super(config);
    }
    
    public PhotoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PHOTO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TARGET_ID' INTEGER," + // 1: targetId
                "'PHOTO_BYTES' BLOB," + // 2: photoBytes
                "'TEXT' TEXT," + // 3: text
                "'DATE' INTEGER);"); // 4: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PHOTO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Photo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long targetId = entity.getTargetId();
        if (targetId != null) {
            stmt.bindLong(2, targetId);
        }
 
        byte[] photoBytes = entity.getPhotoBytes();
        if (photoBytes != null) {
            stmt.bindBlob(3, photoBytes);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(4, text);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(5, date.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Photo readEntity(Cursor cursor, int offset) {
        Photo entity = new Photo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // targetId
            cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2), // photoBytes
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // text
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Photo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTargetId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setPhotoBytes(cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2));
        entity.setText(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Photo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Photo entity) {
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
