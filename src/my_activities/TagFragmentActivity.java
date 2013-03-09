package my_activities;

import com.example.firstcomponents.R;

import database.DatabaseHandler;
import my_components.ComentarioGUI;
import my_components.PhotoGUI;
import my_components.RatingGUI;
import my_components.TagGUI;
import my_components.TimeBG;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class TagFragmentActivity extends FragmentActivity {

	private DatabaseHandler db;
	private PhotoGUI photo;
	private TagGUI tag;
	private ComentarioGUI comentario;
	private RatingGUI rating = new RatingGUI();
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        db = new DatabaseHandler(this);
        
        // We default to building our Fragment at runtime, but you can switch the layout here
        // to R.layout.activity_fragment_xml in order to have the Fragment added during the
        // Activity's layout inflation.
        setContentView(R.layout.activity_fragment_runtime);
        
        FragmentManager fm       = getSupportFragmentManager();
        Fragment        fragment = fm.findFragmentById(R.id.fragment_content); // You can find Fragments just like you would with a 
                                                                               // View by using FragmentManager.
        
        // If we are using activity_fragment_xml.xml then this the fragment will not be
        // null, otherwise it will be.
        if (fragment == null) {
            
            // We alter the state of Fragments in the FragmentManager using a FragmentTransaction. 
            // FragmentTransaction's have access to a Fragment back stack that is very similar to the Activity
            // back stack in your app's task. If you add a FragmentTransaction to the back stack, a user 
            // can use the back button to undo a transaction. We will cover that topic in more depth in
            // the second part of the tutorial.
        	
        	/*
        	TimeBG time = new TimeBG();        	
        	comentario = new ComentarioGUI();       	
        	comentario.setComponent(time);
        	comentario.setDb(db);*/
        	
        	//photo = new PhotoGUI();
        	tag = new TagGUI();
        	tag.setDb(db);
        	
        	
            FragmentTransaction ft = fm.beginTransaction();
            
            //ft.add(R.id.fragment_content, new Buscar());
            ft.add(R.id.fragment_content, photo);
            
            //ft.add(R.id.fragment_content6, time);
            ft.add(R.id.fragment_contentdois, tag);
            //ft.add(R.id.fragment_content3, comentario);
            //ft.add(R.id.fragment_content4, rating);
            
            
            
            Toast.makeText(this, "criado!!!", Toast.LENGTH_LONG).show();
            ft.commit(); // Make sure you call commit or your Fragment will not be added. 
                         // This is very common mistake when working with Fragments!
        }
    }
    
    
    public void configurarTargets(){
    	
    	//rating.setComponentTarget(photo);
    	tag.setComponentTarget(photo);
    	
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	db.close();
    }

}