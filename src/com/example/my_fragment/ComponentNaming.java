package com.example.my_fragment;

public class ComponentNaming {

	
	private String guiName,nickName;
	
	public ComponentNaming(String a,String b){
		guiName=a;
		nickName=b;
	}
	
	public String getGuiName() {
		return guiName;
	}

	public void setGuiName(String guiName) {
		this.guiName = guiName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
