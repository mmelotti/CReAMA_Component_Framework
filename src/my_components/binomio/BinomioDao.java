package my_components.binomio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import database.DaoSession;

import my_components.binomio.Binomio;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BINOMIO.
*/
public class BinomioDao extends AbstractDao<Binomio, Long> {

    public static final String TABLENAME = "BINOMIO";

    /**
     * Properties of entity Binomio.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TargetId = new Property(1, Long.class, "targetId", false, "TARGET_ID");
        public final static Property Fechada = new Property(2, Integer.class, "fechada", false, "FECHADA");
        public final static Property Aberta = new Property(3, Integer.class, "aberta", false, "ABERTA");
        public final static Property Simples = new Property(4, Integer.class, "simples", false, "SIMPLES");
        public final static Property Complexa = new Property(5, Integer.class, "complexa", false, "COMPLEXA");
        public final static Property Vertical = new Property(6, Integer.class, "vertical", false, "VERTICAL");
        public final static Property Horizontal = new Property(7, Integer.class, "horizontal", false, "HORIZONTAL");
        public final static Property Simetrica = new Property(8, Integer.class, "simetrica", false, "SIMETRICA");
        public final static Property Assimetrica = new Property(9, Integer.class, "assimetrica", false, "ASSIMETRICA");
        public final static Property Opaca = new Property(10, Integer.class, "opaca", false, "OPACA");
        public final static Property Translucida = new Property(11, Integer.class, "translucida", false, "TRANSLUCIDA");
    };


    public BinomioDao(DaoConfig config) {
        super(config);
    }
    
    public BinomioDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BINOMIO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TARGET_ID' INTEGER," + // 1: targetId
                "'FECHADA' INTEGER," + // 2: fechada
                "'ABERTA' INTEGER," + // 3: aberta
                "'SIMPLES' INTEGER," + // 4: simples
                "'COMPLEXA' INTEGER," + // 5: complexa
                "'VERTICAL' INTEGER," + // 6: vertical
                "'HORIZONTAL' INTEGER," + // 7: horizontal
                "'SIMETRICA' INTEGER," + // 8: simetrica
                "'ASSIMETRICA' INTEGER," + // 9: assimetrica
                "'OPACA' INTEGER," + // 10: opaca
                "'TRANSLUCIDA' INTEGER);"); // 11: translucida
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BINOMIO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Binomio entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long targetId = entity.getTargetId();
        if (targetId != null) {
            stmt.bindLong(2, targetId);
        }
 
        Integer fechada = entity.getFechada();
        if (fechada != null) {
            stmt.bindLong(3, fechada);
        }
 
        Integer aberta = entity.getAberta();
        if (aberta != null) {
            stmt.bindLong(4, aberta);
        }
 
        Integer simples = entity.getSimples();
        if (simples != null) {
            stmt.bindLong(5, simples);
        }
 
        Integer complexa = entity.getComplexa();
        if (complexa != null) {
            stmt.bindLong(6, complexa);
        }
 
        Integer vertical = entity.getVertical();
        if (vertical != null) {
            stmt.bindLong(7, vertical);
        }
 
        Integer horizontal = entity.getHorizontal();
        if (horizontal != null) {
            stmt.bindLong(8, horizontal);
        }
 
        Integer simetrica = entity.getSimetrica();
        if (simetrica != null) {
            stmt.bindLong(9, simetrica);
        }
 
        Integer assimetrica = entity.getAssimetrica();
        if (assimetrica != null) {
            stmt.bindLong(10, assimetrica);
        }
 
        Integer opaca = entity.getOpaca();
        if (opaca != null) {
            stmt.bindLong(11, opaca);
        }
 
        Integer translucida = entity.getTranslucida();
        if (translucida != null) {
            stmt.bindLong(12, translucida);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Binomio readEntity(Cursor cursor, int offset) {
        Binomio entity = new Binomio( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // targetId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // fechada
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // aberta
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // simples
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // complexa
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // vertical
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // horizontal
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // simetrica
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // assimetrica
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // opaca
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11) // translucida
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Binomio entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTargetId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setFechada(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setAberta(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setSimples(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setComplexa(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setVertical(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setHorizontal(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setSimetrica(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setAssimetrica(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setOpaca(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setTranslucida(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Binomio entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Binomio entity) {
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