package com.creama.conecte.components.test;

import com.creama.conecte.components.comment.CommentListGUI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class IdeaPagerAdapter extends FragmentPagerAdapter {

	private final String[] TITLES = { "Ideia", "Envolvidos", "Comentários", "Imagens" };

	public IdeaPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) { 
		switch (position) {
		case 0:
			return new CommentListGUI(0L,1300L);
		case 1:
			return new CommentListGUI(0L,1300L);
		case 2:
			return new CommentListGUI(0L,1300L);
		case 3:
			return new CommentListGUI(0L,1300L);
		
		default:
			return null;
		}
	}

}
