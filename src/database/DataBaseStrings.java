package database;

public class DataBaseStrings {
	
	private String DATABASE_NAME = "comentariosManager";
	
	private String KEY_ID = "instanceId";
	private String TARGET_ID = "target";
	private String STRING_TARGET = ", "+TARGET_ID+" INTEGER";
	
	private String TABLE_COMMENT = "comments";
	private String COMENTARIO_TEXT = "comment";	
	private String CREATE_comments_TABLE = "CREATE TABLE " + TABLE_COMMENT + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + COMENTARIO_TEXT + " TEXT" + STRING_TARGET+")";
	
	
	private String TABLE_PHOTOS = "photos";
	private String PHOTO_NAME = "name";
	private String CREATE_photos_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + PHOTO_NAME + " TEXT" + STRING_TARGET+")";
	
	
	private String TABLE_TAGS = "tags";
	private String TAG_NAME = "tag";
	private String CREATE_tags_TABLE = "CREATE TABLE " + TABLE_TAGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + TAG_NAME + " TEXT" + STRING_TARGET+")";
	
	private String TABLE_TAGS_TARGET = "tags_target";
	private String TAG_ID = "tagId";
	private String KEY_ID_TARGET = "compositeId";
	private String CREATE_tags_target_TABLE = "CREATE TABLE " + TABLE_TAGS_TARGET + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + TAG_ID + " INTEGER" + STRING_TARGET+")";
	
	
	private String TABLE_RATINGS = "ratings";
	private String RATING_VALUE = "rating";
	private String CREATE_ratings_TABLE = "CREATE TABLE " + TABLE_RATINGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + RATING_VALUE + " INTEGER" + STRING_TARGET+")";
	
	private String ALL_TABLES="SELECT name FROM sqlite_master WHERE type='table'";
	
	public String getALL_TABLES() {
		return ALL_TABLES;
	}



	public void setALL_TABLES(String aLL_TABLES) {
		ALL_TABLES = aLL_TABLES;
	}



	public String[] getAllNames(){
		
		String[] names= {TABLE_COMMENT, TABLE_RATINGS, TABLE_TAGS, TABLE_PHOTOS, TABLE_TAGS_TARGET};
		
		
		return names;
	}
	
	
	public String dropTable(String table){
		String query = "DROP TABLE IF EXISTS " + table;
		
		return query;
		
	}
	
	
	
	public String getAllFrom(String table){
		String query = "SELECT  * FROM " + table;
		
		return query;
		
	}



	public String getTABLE_TAGS_TARGET() {
		return TABLE_TAGS_TARGET;
	}



	public void setTABLE_TAGS_TARGET(String tABLE_TAGS_TARGET) {
		TABLE_TAGS_TARGET = tABLE_TAGS_TARGET;
	}



	public String getTAG_ID() {
		return TAG_ID;
	}



	public void setTAG_ID(String tAG_ID) {
		TAG_ID = tAG_ID;
	}



	public String getKEY_ID_TARGET() {
		return KEY_ID_TARGET;
	}



	public void setKEY_ID_TARGET(String kEY_ID_TARGET) {
		KEY_ID_TARGET = kEY_ID_TARGET;
	}



	public String getCREATE_tags_target_TABLE() {
		return CREATE_tags_target_TABLE;
	}



	public void setCREATE_tags_target_TABLE(String cREATE_tags_target_TABLE) {
		CREATE_tags_target_TABLE = cREATE_tags_target_TABLE;
	}



	public String getDATABASE_NAME() {
		return DATABASE_NAME;
	}



	public void setDATABASE_NAME(String dATABASE_NAME) {
		DATABASE_NAME = dATABASE_NAME;
	}



	public String getKEY_ID() {
		return KEY_ID;
	}



	public void setKEY_ID(String kEY_ID) {
		KEY_ID = kEY_ID;
	}



	public String getTARGET_ID() {
		return TARGET_ID;
	}



	public void setTARGET_ID(String tARGET_ID) {
		TARGET_ID = tARGET_ID;
	}



	public String getSTRING_TARGET() {
		return STRING_TARGET;
	}



	public void setSTRING_TARGET(String sTRING_TARGET) {
		STRING_TARGET = sTRING_TARGET;
	}



	public String getTABLE_COMMENT() {
		return TABLE_COMMENT;
	}



	public void setTABLE_COMMENT(String tABLE_COMMENT) {
		TABLE_COMMENT = tABLE_COMMENT;
	}



	public String getCOMENTARIO_TEXT() {
		return COMENTARIO_TEXT;
	}



	public void setCOMENTARIO_TEXT(String cOMENTARIO_TEXT) {
		COMENTARIO_TEXT = cOMENTARIO_TEXT;
	}



	public String getCREATE_comments_TABLE() {
		return CREATE_comments_TABLE;
	}



	public void setCREATE_comments_TABLE(String cREATE_comments_TABLE) {
		CREATE_comments_TABLE = cREATE_comments_TABLE;
	}



	public String getTABLE_PHOTOS() {
		return TABLE_PHOTOS;
	}



	public void setTABLE_PHOTOS(String tABLE_PHOTOS) {
		TABLE_PHOTOS = tABLE_PHOTOS;
	}



	public String getPHOTO_NAME() {
		return PHOTO_NAME;
	}



	public void setPHOTO_NAME(String pHOTO_NAME) {
		PHOTO_NAME = pHOTO_NAME;
	}



	public String getCREATE_photos_TABLE() {
		return CREATE_photos_TABLE;
	}



	public void setCREATE_photos_TABLE(String cREATE_photos_TABLE) {
		CREATE_photos_TABLE = cREATE_photos_TABLE;
	}



	public String getTABLE_TAGS() {
		return TABLE_TAGS;
	}



	public void setTABLE_TAGS(String tABLE_TAGS) {
		TABLE_TAGS = tABLE_TAGS;
	}



	public String getTAG_NAME() {
		return TAG_NAME;
	}



	public void setTAG_NAME(String tAG_NAME) {
		TAG_NAME = tAG_NAME;
	}



	public String getCREATE_tags_TABLE() {
		return CREATE_tags_TABLE;
	}



	public void setCREATE_tags_TABLE(String cREATE_tags_TABLE) {
		CREATE_tags_TABLE = cREATE_tags_TABLE;
	}



	public String getTABLE_RATINGS() {
		return TABLE_RATINGS;
	}



	public void setTABLE_RATINGS(String tABLE_RATINGS) {
		TABLE_RATINGS = tABLE_RATINGS;
	}



	public String getRATING_VALUE() {
		return RATING_VALUE;
	}



	public void setRATING_VALUE(String rATING_VALUE) {
		RATING_VALUE = rATING_VALUE;
	}



	public String getCREATE_ratings_TABLE() {
		return CREATE_ratings_TABLE;
	}



	public void setCREATE_ratings_TABLE(String cREATE_ratings_TABLE) {
		CREATE_ratings_TABLE = cREATE_ratings_TABLE;
	}
			
			
	
}
