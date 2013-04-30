package com.example.my_fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import my_components.Constants;
import my_components.binomio.BinomioAverageGUI;
import my_components.binomio.BinomioSendGUI;
import my_components.comment.CommentSendGUI;
import my_components.comment.CommentViewGUI;
import my_components.gps.GPSViewGUI;
import my_components.rating.RatingViewGUI;
import my_components.tag.TagSendGUI;
import my_components.tag.TagViewGUI;

public class ComponentDefinitions {

	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> paths = new ArrayList<String>();

	public ComponentDefinitions() {
		names.add(Constants.CommentViewGUIName);
		names.add(Constants.RatingViewGUIName);

		// paths.add("my_components.OneCommentGUI");
		// paths.add("my_components.RatingGUI");
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

	public CRComponent getComponent(ComponentSimpleModel m, String name) {
		if (name.equals(Constants.CommentViewGUIName)) {
			return getOneComment(m);

		} else if (name.equals(Constants.TagViewGUIName)) {
			return getOneTag(m);
		}else if (name.equals(Constants.GPSViewGUIName)) {
			return getOneGPSViewGUI(m);
		}
		return getOneComment(m);
	}

	public CRComponent getComponent(Long target, String name) {
		if (name.equals("OneComment")) {
			//
		} else if (name.equals(Constants.RatingViewGUIName)) {
			return getRatingGUI(target);
		} else if (name.equals(Constants.CommentSendGUIName)) {
			return getCommentSendGUI(target);
		} else if (name.equals(Constants.BinomioGUIName)) {
			return getBinomioGUI(target);
		} else if (name.equals(Constants.BinomioAverageGUIName)) {
			return getBinomioAverageGUI(target);
		} else if (name.equals(Constants.TagSendGUIName)) {
			return getTagSendGUI(target);
		} else if (name.equals(Constants.CommentViewGUIName)) {
			return getCommentViewGUI(target);

		} else if (name.equals(Constants.TagViewGUIName)) {
			return getTagViewGUI(target);
		}else if (name.equals(Constants.GPSViewGUIName)) {
			return getGPSViewGUI(target);
		}

		return getRatingGUI(target);
	}

	public CommentViewGUI getOneComment(ComponentSimpleModel m) {
		return new CommentViewGUI(m);
	}

	public TagViewGUI getOneTag(ComponentSimpleModel m) {
		return new TagViewGUI(m);
	}

	public RatingViewGUI getRatingGUI(Long target) {
		return new RatingViewGUI(target);
	}

	public CommentSendGUI getCommentSendGUI(Long t) {
		return new CommentSendGUI(t);
	}
	
	public CommentViewGUI getCommentViewGUI(Long t) {
		return new CommentViewGUI();
	}

	public TagSendGUI getTagSendGUI(Long t) {
		return new TagSendGUI(t);
	}
	
	public GPSViewGUI getGPSViewGUI(Long t){
		return new GPSViewGUI(t);
	}
	
	public GPSViewGUI getOneGPSViewGUI(ComponentSimpleModel c){
		return new GPSViewGUI(c);
	}
	
	public TagViewGUI getTagViewGUI(Long t) {
		return new TagViewGUI(t);
	}

	public BinomioSendGUI getBinomioGUI(Long t) {
		return new BinomioSendGUI(t);
	}

	public BinomioAverageGUI getBinomioAverageGUI(Long t) {
		return new BinomioAverageGUI(t);
	}

	public List<ComponentSimpleModel> listToMany(String name, Long target,
			Activity a) {
		CRComponent g = getComponent(target, name);
		return g.getListSimple(target, a);
	}

}
