package my_activities;

import my_components.photo.PhotoGalleryGUI;
import my_components.photo.PhotoSendGUI;
import my_components.photo.PhotoViewGUI;

import com.example.firstcomponents.R; 
import com.example.my_fragment.MyActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends MyActivity {

	private Button bttela2, btag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBotoes();

		startTransaction();
		PhotoSendGUI photoSend = new PhotoSendGUI();
		addGUIComponent(R.id.root, photoSend); 
		
		PhotoGalleryGUI gallery = new PhotoGalleryGUI();
		addGUIComponent(R.id.root, gallery);
		finishTransaction();
	}

	private void setBotoes() {
		bttela2 = (Button) findViewById(R.id.go_comment);

		bttela2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent trocatela = new Intent(MainActivity.this,
						NewListActivity.class);
				// procura primeira imagem
				trocatela.putExtra("nImagem",
						PhotoViewGUI.searchFirstPhoto(null, v.getContext()));
				startActivity(trocatela);
				// MainActivity.this.finish();
			}
		});

		btag = (Button) findViewById(R.id.go_tag);
		// bttela2.setText("NEW");

		btag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent trocatela = new Intent(MainActivity.this,TagFragmentActivity.class);
				Intent trocatela2 = new Intent(MainActivity.this,
						Rating2Comment2Photo.class);
				trocatela2.putExtra("nImagem",
						PhotoViewGUI.searchFirstPhoto(null, arg0.getContext()));
				MainActivity.this.startActivity(trocatela2);
				// MainActivity.this.finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void configurarTargets() {
		// TODO Auto-generated method stub

	}

	@Override
	public void instanciarComponents() {
		// TODO Auto-generated method stub

	}

}
