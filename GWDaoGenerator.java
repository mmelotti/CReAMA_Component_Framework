package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GWDaoGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "my_components");

		addComment(schema);
		addPhoto(schema);
		new DaoGenerator().generateAll(schema, "../MeuTeste");
	}

	private static void addNewComponent(Schema schema, Entity ent) {
		ent.addIdProperty();
		ent.addLongProperty("targetId");
		ent.setSuperclass("com.example.my_fragment.ComponentSimpleModel");
	}

	private static void addPhoto(Schema schema) {
		Entity photo = schema.addEntity("Photo");
		addNewComponent(schema, photo);
		photo.addByteArrayProperty("photoBytes");
		photo.addStringProperty("text");
		photo.addDateProperty("date");
	}

	private static void addComment(Schema schema) {
		Entity com = schema.addEntity("Comment");
		addNewComponent(schema, com);
		com.addStringProperty("text").notNull();
		com.addDateProperty("date");
	}

}
