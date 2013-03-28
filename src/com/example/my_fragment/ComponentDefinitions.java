package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import my_components.comment.CommentSendGUI;
import my_components.comment.CommentViewGUI;
import my_components.rating.RatingViewGUI;

public class ComponentDefinitions {

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> paths = new ArrayList<String>();

	public ComponentDefinitions() {
		names.add("OneComment");
		names.add("Rating");

		paths.add("my_components.OneCommentGUI");
		paths.add("my_components.RatingGUI");
	}

	public String findByName(String name) {
		String r = "";

		for (int i = 0; i < names.size(); i++) {
			String l = names.get(i);
			if (l.equals(name)) {
				return paths.get(i);
			}

		}

		return r;
	}

	public GUIComponent getComponent(ComponentSimpleModel m, String name) {
		if (name.equals("OneComment")) {
			return getOne(m);
		} else if (name.equals("Rating")) {
			return getRatingGUI(1L);
		}
		return getOne(m);
	}

	public GUIComponent getComponent(Long target, String name) {
		if (name.equals("OneComment")) {
			//
		} else if (name.equals("Rating")) {
			return getRatingGUI(target);
		} else if (name.equals("CommentSend")) {
			return getCommentSendGUI(target);
		}
		return getRatingGUI(target);
	}

	public CommentViewGUI getOne(ComponentSimpleModel m) {
		return new CommentViewGUI(m);
	}

	public RatingViewGUI getRatingGUI(Long target) {
		return new RatingViewGUI(target);
	}

	public CommentSendGUI getCommentSendGUI(Long t) {
		return new CommentSendGUI(t);
	}

}
