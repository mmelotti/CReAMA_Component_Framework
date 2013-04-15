package com.example.my_fragment;

import java.util.ArrayList;
import my_components.Constants;
import my_components.binomio.BinomioAverageGUI;
import my_components.binomio.BinomioSendGUI;
import my_components.comment.CommentSendGUI;
import my_components.comment.CommentViewGUI;
import my_components.rating.RatingViewGUI;

public class ComponentDefinitions {

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> paths = new ArrayList<String>();

	public ComponentDefinitions() {
		names.add(Constants.CommentViewGUIName);
		names.add(Constants.RatingViewGUIName);

		//paths.add("my_components.OneCommentGUI");
		//paths.add("my_components.RatingGUI");
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
		if (name.equals(Constants.CommentViewGUIName)) {
			return getOne(m);
		} else if (name.equals(Constants.RatingViewGUIName)) {
			return getRatingGUI(1L);
		}
		return getOne(m);
	}

	public GUIComponent getComponent(Long target, String name) {
		if (name.equals("OneComment")) {
			//
		} else if (name.equals(Constants.RatingViewGUIName)) {
			return getRatingGUI(target);
		} else if (name.equals(Constants.CommentSendGUIName)) {
			return getCommentSendGUI(target);
		} else if (name.equals(Constants.BinomioGUIName)){
			return getBinomioGUI(target);
		} else if (name.equals(Constants.BinomioAverageGUIName)){
			return getBinomioAverageGUI(target);
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
	
	public BinomioSendGUI getBinomioGUI(Long t){
		return new BinomioSendGUI(t);
	}
	
	public BinomioAverageGUI getBinomioAverageGUI(Long t){
		return new BinomioAverageGUI(t);
	}

}
