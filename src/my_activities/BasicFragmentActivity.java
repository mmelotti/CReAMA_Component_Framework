package my_activities;

import com.example.firstcomponents.R;
import com.example.my_fragment.MyActivity;

import database.DatabaseHandler;
import my_components.comment.CommentListGUI;
import my_components.photo.PhotoViewGUI;
import my_components.rating.RatingViewGUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class BasicFragmentActivity extends MyActivity {

	private DatabaseHandler db; 
	private PhotoViewGUI photo;
	private CommentListGUI comentario;
	private RatingViewGUI rating; 
	Long photoId;
	
	protected void photoNotFound() {
		Toast.makeText(this, "Foto não encontrada. Verifique se já existe alguma foto.",
				Toast.LENGTH_SHORT).show();
		finish();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
      
        
        // We default to building our Fragment at runtime, but you can switch the layout here
        // to R.layout.activity_fragment_xml in order to have the Fragment added during the
        // Activity's layout inflation.
        setContentView(R.layout.activity_fragment_runtime);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_content); // You can find Fragments just like you would with a 
                                                                               // View by using FragmentManager.
        
        // If we are using activity_fragment_xml.xml then this the fragment will not be
        // null, otherwise it will be.
        
        
		
        if (fragment == null) {
            
            // We alter the state of Fragments in the FragmentManager using a FragmentTransaction. 
            // FragmentTransaction's have access to a Fragment back stack that is very similar to the Activity
            // back stack in your app's task. If you add a FragmentTransaction to the back stack, a user 
            // can use the back button to undo a transaction. We will cover that topic in more depth in
            // the second part of the tutorial.
        	
        	//TimeBG time = new TimeBG();        	
        	      	
        	//comentario.setComponent(time);
        	
        	 
            FragmentTransaction ft = fm.beginTransaction();
            
            ft.add(R.id.fragment_content, photo);
               
            // ft.add(R.id.fragment_content6, time);
            ft.add(R.id.fragment_content3, comentario);
 
            ft.add(R.id.fragment_content4, rating);

            ft.commit(); // Make sure you call commit or your Fragment will not be added. 
                         // This is very common mistake when working with Fragments!
        }
        
        //set targets
      
    }
     
    public void configurarTargets(){
    	rating.setComponentTarget(photo);
    	
    	comentario.setComponentTarget(photo);  	    
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	
    }
   
    
	public void instanciarComponents() {
		// TODO Auto-generated method stub
		photoId = getIntent().getLongExtra("nImagem", -1L);
		if (photoId != -1L) 
			photo = new PhotoViewGUI(photoId);
		else {
			photoNotFound();
			return; 
		}
		
		photoId = photo.getImageId();
		if (photoId == -1L) {
			photoNotFound();
			return;
		}
		
		comentario = new CommentListGUI(); 
		rating = new RatingViewGUI(photoId);
		//rating.setDb(db);
		
	}

}
