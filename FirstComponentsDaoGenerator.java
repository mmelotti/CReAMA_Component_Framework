package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GWDaoGenerator {
	static String pack = "com.gw.android.first_components.my_components.";

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "my_components");

		schema.setDefaultJavaPackageDao("com.gw.android.first_components.database");

		// componentes
		Entity com = addComment(schema);
		addPhoto(schema);
		addTag(schema);
		addBinomio(schema);
		addCoord(schema);
		addFaq(schema);
		addUser(schema);
		Entity rating = addRating(schema);
		addRatingToComment(schema, com, rating);

		// path destino
		new DaoGenerator().generateAll(schema, "./");
	}

	private static void addNewComponent(Schema schema, Entity ent) {
		ent.addIdProperty();
		ent.addLongProperty("targetId");
		ent.addLongProperty("serverId");
		ent.setSuperclass("com.gw.android.first_components.my_fragment.ComponentSimpleModel");
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

	private static void addFaq(Schema schema) {
		Entity faq = schema.addEntity("Faq");
		faq.setJavaPackage(pack + "faq");
		addNewComponent(schema, faq);
		faq.addStringProperty("pergunta");
		faq.addStringProperty("resposta");
	}
	
	private static void addUser(Schema schema) {
		Entity user = schema.addEntity("User");
		user.setJavaPackage(pack + "user");
		addNewComponent(schema, user);
		user.addStringProperty("usuario");
		user.addStringProperty("sobrenome");
		user.addStringProperty("escolaridade");
		user.addStringProperty("curso");
		user.addStringProperty("instituicao");
		user.addStringProperty("ocupacao");
		user.addStringProperty("email");
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
		binomio.addIntProperty("interna");
		binomio.addIntProperty("externa");
	}

}