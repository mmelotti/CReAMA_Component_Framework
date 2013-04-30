package my_activities;

import my_components.photo.PhotoGalleryGUI;
import my_components.photo.PhotoSendGUI;
import my_components.photo.PhotoViewGUI;

import com.example.firstcomponents.R; 
import com.example.my_fragment.CRActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends CRActivity {

	private Button buttonBinomioComentario, buttonComposto, buttonTag, buttonSensor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBotoes();

		startTransaction();
		PhotoGalleryGUI gallery = new PhotoGalleryGUI();
		addGUIComponent(R.id.gallery, gallery);
		
		PhotoSendGUI photoSend = new PhotoSendGUI();
		addGUIComponent(R.id.root, photoSend); 
		finishTransaction();
	}

	private void setBotoes() {
		buttonBinomioComentario = (Button) findViewById(R.id.go_comment);

		buttonBinomioComentario.setOnClickListener(new View.OnClickListener() {
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

		buttonComposto = (Button) findViewById(R.id.go_composto);
		// bttela2.setText("NEW");

		buttonComposto.setOnClickListener(new View.OnClickListener() {
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
		
		buttonSensor = (Button) findViewById(R.id.go_sensor);
		// bttela2.setText("NEW");

		buttonSensor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent trocatela = new Intent(MainActivity.this,TagFragmentActivity.class);
				Intent trocatela2 = new Intent(MainActivity.this,
						SensoresActivity.class);
				
				MainActivity.this.startActivity(trocatela2);
				// MainActivity.this.finish();
			}
		});
		
		buttonTag = (Button) findViewById(R.id.go_tag);
		// bttela2.setText("NEW");

		buttonTag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent trocatela = new Intent(MainActivity.this,TagFragmentActivity.class);
				Intent trocatela2 = new Intent(MainActivity.this,
						TagActivity.class);
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
