package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GWDaoGenerator {

	static String pack = "my_components.";

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "my_components");

		schema.setDefaultJavaPackageDao("database");

		// componentes
		Entity com = addComment(schema);
		addPhoto(schema);
		addTag(schema);
		addBinomio(schema);
		addCoord(schema);
		Entity rating = addRating(schema);
		addRatingToComment(schema, com, rating);

		// agrupamentos
		// addRating2Comment2Photo(schema);

		// path destino
		new DaoGenerator().generateAll(schema, "./");
	}

	private static void addNewComponent(Schema schema, Entity ent) {
		ent.addIdProperty();
		ent.addLongProperty("targetId");
		ent.setSuperclass("com.example.my_fragment.ComponentSimpleModel");
		ent.setJavaPackageDao(ent.getJavaPackage());
	}

	private static void addPhoto(Schema schema) {
		Entity photo = schema.addEntity("Photo");
		photo.setJavaPackage(pack + "photo");
		addNewComponent(schema, photo);
		photo.addByteArrayProperty("photoBytes");
		photo.addStringProperty("text");
		photo.addDateProperty("date");

	}

	private static Entity addComment(Schema schema) {
		Entity com = schema.addEntity("Comment");
		com.setJavaPackage(pack + "comment");
		addNewComponent(schema, com);
		com.addStringProperty("text").notNull();
		com.addDateProperty("date");

		return com;
	}

	private static Entity addRating(Schema schema) {
		Entity rating = schema.addEntity("Rating");
		rating.setJavaPackage(pack + "rating");
		addNewComponent(schema, rating);

		rating.addFloatProperty("value").notNull();
		// com.addDateProperty("date");
		return rating;
	}

	private static void addRatingToComment(Schema schema, Entity com,
			Entity rating) {
		Entity ratingCom = schema.addEntity("RatingToComment");
		ratingCom.setJavaPackage(pack + "rating2comment");
		addNewComponent(schema, ratingCom);

		/*
		 * ratingCom.addFloatProperty("value").notNull(); //index 1 = target id
		 * ratingCom.addToMany(com, com.getProperties().get(1));
		 * ratingCom.addToMany(rating, rating.getProperties().get(1));
		 * //com.addDateProperty("date");
		 */
	}

	private static void addTag(Schema schema) {
		Entity tag = schema.addEntity("Tag");
		tag.setJavaPackage(pack + "tag");
		addNewComponent(schema, tag);
		tag.addStringProperty("tag").notNull();
	}

	private static void addCoord(Schema schema) {
		Entity coord = schema.addEntity("Coordinates");
		coord.setJavaPackage(pack + "gps");
		addNewComponent(schema, coord);
		coord.addDoubleProperty("latitude").notNull();
		coord.addDoubleProperty("longitude").notNull();
		coord.addStringProperty("addressLine1");
		coord.addStringProperty("addressLine2");
		coord.addStringProperty("addressLine3");
	}

	private static void addBinomio(Schema schema) {
		Entity binomio = schema.addEntity("Binomio");
		binomio.setJavaPackage(pack + "binomio");
		addNewComponent(schema, binomio);
		binomio.addIntProperty("fechada");
		binomio.addIntProperty("aberta");
		binomio.addIntProperty("simples");
		binomio.addIntProperty("complexa");
		binomio.addIntProperty("vertical");
		binomio.addIntProperty("horizontal");
		binomio.addIntProperty("simetrica");
		binomio.addIntProperty("assimetrica");
		binomio.addIntProperty("opaca");
		binomio.addIntProperty("translucida");
	}

}