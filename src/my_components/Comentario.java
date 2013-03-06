package my_components;

import com.example.my_fragment.ComponentSimpleModel;

import database.DatabaseHandler;

public class Comentario extends ComponentSimpleModel{
	
	private String text="";
	
	
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	@Override
	public void save(){
		
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		
	}
	
}
