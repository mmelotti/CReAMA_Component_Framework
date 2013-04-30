package my_activities;

import java.util.ArrayList;
import my_components.Constants;
import my_components.photo.PhotoViewGUI;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.example.firstcomponents.R;
import com.example.my_fragment.ComponentNaming;
import com.example.my_fragment.Dependency;
import com.example.my_fragment.CRComponent;
import com.example.my_fragment.CRActivity;

public class CoordCommentActivity extends CRActivity {

	private PhotoViewGUI photo;
	private boolean gambiarraFlag = false;
	private ComponentNaming commentView, commentSend, photoView, coordView, coordSend;
	private long idPhoto;
	private Long photoId;

	protected void photoNotFound() {
		gambiarraFlag = true;
		Toast.makeText(this, "Foto não encontrada. Verifique se já existe alguma foto.",
				Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (gambiarraFlag)
			return;

		setContentView(R.layout.activity_fragment_runtime);

		// set targets
		
		setMyList();
		addSomething();
	}

	public void configurarTargets() {
		if (gambiarraFlag)
			return;
		// rating.setComponentTarget(photo);
		idPhoto = photo.getCurrentInstanceId();

		setDependencies(new ArrayList<Dependency>());
		
		commentView = new ComponentNaming(Constants.CommentViewGUIName, Constants.CommentViewGUIName+"1");
		commentSend = new ComponentNaming(Constants.CommentSendGUIName, Constants.CommentSendGUIName+"1");
		photoView = new ComponentNaming(Constants.PhotoViewGUIName, Constants.PhotoViewGUIName+"1");
		
		coordView = new ComponentNaming(Constants.GPSViewGUIName, "GPS");
		coordSend = new ComponentNaming(Constants.GPSListenerName, "GPSsend");
		
		Log.i("adding","binomio gui");
		
		
		addDependencie(new Dependency(commentView, photoView, true));
		addDependencie(new Dependency(coordView, commentView, false));
		addDependencie(new Dependency(commentSend, photoView, false));
		addDependencie(new Dependency(coordSend, commentSend, false));
		
		
		
		
		photo.setNick(photoView.getNickName());
	}

	public void instanciarComponents() {
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

	}

	public void setMyList() {

	}

	public void addSomething() {
		startTransaction();
		addGUIComponent(R.id.menu_lin, photo);
		verDependenciaString(photoView, idPhoto);
		finishTransaction();
	}

	@Override
	public void deletarAlgo(Long target, CRComponent component) {
		callbackRemove(target, component.getNick());
	}

}