package my_activities;

import my_components.photo.PhotoViewGUI;

import com.example.firstcomponents.R;

import database.DatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button bttela2, btag, btnAddPhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBotoes();

		// inicializao db
		DatabaseHandler myDb = new DatabaseHandler(this);
		myDb.dropAllTables();
		myDb.checkTablesOnDB();
		myDb.close();
		myDb = null;
	}

	private void setBotoes() {
		bttela2 = (Button) findViewById(R.id.go_comment);

		bttela2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent trocatela = new Intent(MainActivity.this,
						NewListActivity.class);
				// procura primeira imagem
				trocatela.putExtra("nImagem",
						PhotoViewGUI.searchFirstPhoto(null, arg0.getContext()));
				startActivity(trocatela);
				// MainActivity.this.finish();
			}
		});

		btag = (Button) findViewById(R.id.go_tag);
		// bttela2.setText("NEW");

		btag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent trocatela = new Intent(MainActivity.this,
						TagFragmentActivity.class);
				trocatela.putExtra("nImagem",
						PhotoViewGUI.searchFirstPhoto(null, arg0.getContext()));
				MainActivity.this.startActivity(trocatela);
				// MainActivity.this.finish();
			}
		});

		btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
		// bttela2.setText("NEW");

		btnAddPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent addPhoto = new Intent(MainActivity.this,
						SavePhotoActivity.class);
				MainActivity.this.startActivity(addPhoto);

				// Intent trocatela = new
				// Intent(MainActivity.this,NewListComposed.class);
				// trocatela.putExtra("nImagem",
				// PhotoViewGUI.searchFirstPhoto(null, arg0.getContext()));
				// MainActivity.this.startActivity(trocatela);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
