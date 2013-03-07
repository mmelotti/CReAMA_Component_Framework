package database;

import java.util.ArrayList;
import java.util.List;

import my_components.Comentario;
import my_components.Rating;
import my_components.Tag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private DataBaseStrings stringDb = new DataBaseStrings();
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "comentariosManager";

	// Comentarios table name
	private static final String TABLE_COMENTARIOS = "comentarios";

	// Comentarios Table Columns names
	private static final String KEY_ID = "instanceId";
	private static final String KEY_NAME = "text";

	// private static final String KEY_PH_NO = "phone_number";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_ComentarioS_TABLE = "CREATE TABLE " + TABLE_COMENTARIOS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
				+ ")";
		db.execSQL(CREATE_ComentarioS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMENTARIOS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Comentario
	public void addComentario(Comentario comentario) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(stringDb.getCOMENTARIO_TEXT(), comentario.getText()); // Comentario
																			// Name
		values.put(stringDb.getTARGET_ID(), comentario.getTargetId());

		// values.put(KEY_PH_NO, Comentario.getPhoneNumber()); // Comentario
		// Phone

		// Inserting Row
		db.insert(stringDb.getTABLE_COMMENT(), null, values);
		db.close(); // Closing database connection
	}

	public void addTag(Tag tag) {
		SQLiteDatabase db = this.getWritableDatabase();

		// TODO negocio ta feio por aqui, melhorar essas querys ridiculas depois

		String queryTagNew = "SELECT * FROM " + stringDb.getTABLE_TAGS()
				+ " WHERE " + stringDb.getTAG_NAME() + "='" + tag.getTag()
				+ "'";

		int tt = 0;
		Cursor c = db.rawQuery(queryTagNew, null);// primeira query

		// nao tem essa tag inserida
		if (c.getCount() < 1) {
			ContentValues values = new ContentValues();
			values.put(stringDb.getTAG_NAME(), tag.getTag()); // tag Name
			values.put(stringDb.getTARGET_ID(), tag.getTargetId());

			Log.i("uou!", "botou primeiro");

			// Inserting Row
			int newId = (int) db.insert(stringDb.getTABLE_TAGS(), null, values);

			// insert row para ligacao
			values = new ContentValues();
			values.put(stringDb.getTAG_ID(), newId); // tag Name
			values.put(stringDb.getTARGET_ID(), tag.getTargetId());
			db.insert(stringDb.getTABLE_TAGS_TARGET(), null, values);

		} else { // tem a tag, entao soh vai fazer ligacao soh com outra tabela

			if (c.moveToFirst()) {
				do {
					tt = c.getInt(0);
					break;
				} while (c.moveToNext());
			}

			String queryTargetTag = "SELECT * FROM "
					+ stringDb.getTABLE_TAGS_TARGET() + " WHERE "
					+ stringDb.getTARGET_ID() + "=" + tag.getTargetId()
					+ " AND " + stringDb.getTAG_ID() + "=" + tt;

			Cursor dois = db.rawQuery(queryTargetTag, null);
			// nao tem a tag para novo target
			if (dois.getCount() < 1) {
				ContentValues values = new ContentValues();

				// need this to get the first value

				Log.i("uou!", "botou de novo");

				values.put(stringDb.getTAG_ID(), tt); // poe tag id pra fazer
														// ligacao
				values.put(stringDb.getTARGET_ID(), tag.getTargetId());

				// Inserting Row
				db.insert(stringDb.getTABLE_TAGS_TARGET(), null, values);

			}
			dois.close();
		}

		c.close();

		db.close(); // Closing database connection
	}

	public void addRating(Rating rat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(stringDb.getRATING_VALUE(), rat.getQuantidade()); // Comentario
																		// Name
		values.put(stringDb.getTARGET_ID(), rat.getTargetId());

		// values.put(KEY_PH_NO, Comentario.getPhoneNumber()); // Comentario
		// Phone

		// Inserting Row
		db.insert(stringDb.getTABLE_RATINGS(), null, values);
		db.close(); // Closing database connection
	}

	// Getting single Comentario
	Comentario getComentario(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_COMENTARIOS, new String[] { KEY_ID,
				KEY_NAME }, KEY_ID + "=?", new String[] { String.valueOf(id) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Comentario Comentario = new Comentario();
		Comentario.setText(cursor.getString(1));

		cursor.close();
		// return Comentario
		return Comentario;
	}

	// Getting All Comentarios
	public List<Comentario> getAllComentarios() {
		List<Comentario> ComentarioList = new ArrayList<Comentario>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COMENTARIOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Comentario Comentario = new Comentario();
				// Comentario.setID(Integer.parseInt(cursor.getString(0)));
				Comentario.setText(cursor.getString(1));
				// Comentario.setPhoneNumber(cursor.getString(2));
				// Adding Comentario to list
				ComentarioList.add(Comentario);
			} while (cursor.moveToNext());
		}

		// return Comentario list
		return ComentarioList;
	}

	// Updating single Comentario
	public int updateComentario(Comentario Comentario) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, Comentario.getText());

		// values.put(KEY_PH_NO, Comentario.getPhoneNumber());

		// updating row
		return db.update(TABLE_COMENTARIOS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Comentario.getInstanceId()) });
	}

	// Deleting single Comentario
	public void deleteComentario(Comentario Comentario) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COMENTARIOS, KEY_ID + " = ?",
				new String[] { String.valueOf(Comentario.getInstanceId()) });
		db.close();
	}

	// Getting Comentarios Count
	public int getComentariosCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COMENTARIOS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
 
		// return count   
		return cursor.getCount(); 
	}

	public String[] getCommentsFrom(int target) {
		String[] result;
		int i = 0;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + stringDb.getTABLE_COMMENT()
				+ " WHERE " + stringDb.getTARGET_ID() + "=" + target, null);
		if (c == null) {
			return null;
		}
		
		result = new String[c.getCount()];

		String con = "";
		// con += "id, comentario, targetId \n";
		/*
		 * con += "id, comentario, targetId = "+ c.getColumnName(0)+" \n"; con
		 * += "comentario "+c.getColumnName(1)+" \n"; con += c.getCount()+
		 * " <- count ";
		 */

		if (c.moveToFirst()) {
			do {
				result[i] = "\nID: " + c.getString(0) 
						+ "\nTarget ID: " + c.getString(2) 
						+ "\nComentário: " + c.getString(1) + "\n";
				
				/*con += "\nID: " + c.getString(0) 
						+ "\nTarget ID: " + c.getString(2) 
						+ "\nComentário: " + c.getString(1) + "\n"; */
				i++;
			} while (c.moveToNext());
		}

		c.close();

		return result;
	}

	public String getTagsFrom(int target) {
		SQLiteDatabase db = this.getReadableDatabase();

		// essa pega pela tabela tags_target
		String queryTargetTag = "SELECT distinct " + stringDb.getTABLE_TAGS()
				+ "." + stringDb.getTAG_NAME() + " FROM "
				+ stringDb.getTABLE_TAGS_TARGET() + " JOIN "
				+ stringDb.getTABLE_TAGS() + " ON "
				+ stringDb.getTABLE_TAGS_TARGET() + "." + stringDb.getTAG_ID()
				+ "=" + stringDb.getTABLE_TAGS() + "." + stringDb.getKEY_ID()
				+ " WHERE " + stringDb.getTABLE_TAGS_TARGET() + "."
				+ stringDb.getTARGET_ID() + "=" + target;

		Cursor c = db.rawQuery(queryTargetTag, null);

		/*
		 * if(dois.getCount()>0){ //essa pega pela tabela tags c =
		 * db.rawQuery("SELECT * FROM " + stringDb.getTABLE_TAGS() + " WHERE " +
		 * stringDb.getTARGET_ID() + "=" + target, null);
		 * 
		 * }
		 * 
		 * 
		 * /* if (c == null) { return "nada"; }
		 */
		String con = "";
		// con += "id, tag, targetId \n";

		if (c.moveToFirst()) {
			do {
				con += c.getString(0) + " \n";
				/*
				 * con += c.getString(1) + ", "; con += c.getString(2) + ", ";
				 * con += c.getString(3) + ", "; con += c.getString(4) + ", ";
				 * con += c.getString(5) + " \n";
				 */

			} while (c.moveToNext());
		}

		c.close();

		return con;
	}

	public float getAverageRatingFrom(int target) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + stringDb.getTABLE_RATINGS()
				+ " WHERE " + stringDb.getTARGET_ID() + "=" + target, null);
		
		if (c == null) {
			return 0;
		}

		float soma = 0;
		int cont = 0;

		if (c.moveToFirst()) {
			do {
				soma += c.getInt(1);
				cont++;
			} while (c.moveToNext());
		}

		soma = soma / cont;
		c.close();

		return soma;
	}

	public void dropAllTables() {

		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL(stringDb.dropTable(stringDb.getTABLE_COMMENT()));
		db.execSQL(stringDb.dropTable(stringDb.getTABLE_PHOTOS()));
		db.execSQL(stringDb.dropTable(stringDb.getTABLE_TAGS()));

		db.execSQL(stringDb.dropTable(stringDb.getTABLE_RATINGS()));
		db.execSQL(stringDb.dropTable(stringDb.getTABLE_TAGS_TARGET()));

	}

	public void checkTablesOnDB() {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(stringDb.getALL_TABLES(), null);

		String[] names = stringDb.getAllNames();

		int[] exist = new int[names.length];
		for (int i = 0; i < names.length; i++) {

			exist[i] = 0;

		}

		// ver se tabela ja ta criada
		if (c.moveToFirst()) {
			do {
				for (int i = 0; i < names.length; i++) {
					if (c.getString(0).equals(names[i])) {
						exist[i] = 1;
					}

				}

			} while (c.moveToNext());
		}

		// toskera soh por enquanto,,, sobre criacao das tables
		// se tiver metade que nao estao criados, cria

		int cont = 0;
		for (int i = 0; i < names.length; i++) {

			if (exist[i] == 0) {
				cont++;
				// criar table para cada que nao existe
			}

		}

		if (cont > names.length / 2) {
			db.execSQL(stringDb.getCREATE_comments_TABLE());
			db.execSQL(stringDb.getCREATE_tags_TABLE());
			db.execSQL(stringDb.getCREATE_ratings_TABLE());
			db.execSQL(stringDb.getCREATE_photos_TABLE());
			db.execSQL(stringDb.getCREATE_tags_target_TABLE());
		}

		c.close();

	}

}