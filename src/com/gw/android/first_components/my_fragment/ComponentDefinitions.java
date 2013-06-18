package com.gw.android.first_components.my_fragment;

import java.util.ArrayList;
import java.util.List;

import com.gw.android.first_components.my_components.Constants;
import com.gw.android.first_components.my_components.binomio.BinomioAverageGUI;
import com.gw.android.first_components.my_components.binomio.BinomioSendGUI;
import com.gw.android.first_components.my_components.comment.CommentSendGUI;
import com.gw.android.first_components.my_components.comment.CommentViewGUI;
import com.gw.android.first_components.my_components.gps.GPSListener;
import com.gw.android.first_components.my_components.gps.GPSViewGUI;
import com.gw.android.first_components.my_components.rating.RatingViewGUI;
import com.gw.android.first_components.my_components.tag.TagSendGUI;
import com.gw.android.first_components.my_components.tag.TagViewGUI;

import android.app.Activity;


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

	public CRComponent getComponentToMany(ComponentSimpleModel m, String name) {
		if (name.equals(Constants.CommentViewGUIName)) {
			return getOneComment(m);

		} else if (name.equals(Constants.TagViewGUIName)) {
			return getOneTag(m);
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
		}else if (name.equals(Constants.GPSListenerName)) {
			return getGPSListener(target);
		}else if (name.equals(Constants.GPSViewGUIName)) {
			return getOneGPSViewGUI(target);
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
	
	public GPSListener getGPSListener(Long t){
		return new GPSListener(t);
	}
	

	public GPSViewGUI getOneGPSViewGUI(Long t){
		return new GPSViewGUI(t);
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
