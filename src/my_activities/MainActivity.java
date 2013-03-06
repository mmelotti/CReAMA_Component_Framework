package my_activities;

import com.example.firstcomponents.R;


import database.DatabaseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button bttela2, btag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBotoes();
		
		//inicializao db
		DatabaseHandler myDb=new DatabaseHandler(this);
		myDb.dropAllTables();
		myDb.checkTablesOnDB();
		myDb.close();
		myDb=null;
		
		
	}

	private void setBotoes() {
		// TODO Auto-generated method stub
		bttela2 = (Button) findViewById(R.id.go_comment);
		//bttela2.setText("NEW");
		
		
		
		
		bttela2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent trocatela = new Intent(MainActivity.this,
						BasicFragmentActivity.class);
				trocatela.putExtra("nImagem", 1);
				MainActivity.this.startActivity(trocatela);
				//MainActivity.this.finish();

			}
		}); //*/

		
		
		btag = (Button) findViewById(R.id.go_tag);
		//bttela2.setText("NEW");
		
		
		
		
		btag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent trocatela = new Intent(MainActivity.this,
						TagFragmentActivity.class);
				trocatela.putExtra("nImagem", 1);
				MainActivity.this.startActivity(trocatela);
				//MainActivity.this.finish();

			}
		}); //*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
