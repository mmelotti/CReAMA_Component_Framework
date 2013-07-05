package com.gw.android.perguntaserespostas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gw.android.R;

import com.gw.android.first_components.my_fragment.CRActivity;

public class FaqInicialActivity extends CRActivity {

	private Button buttonPrincipal, buttonOutro;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faqinicial);

		buttonPrincipal = (Button) findViewById(R.id.btnFaqUm);
		buttonOutro = (Button) findViewById(R.id.btnFaqDois);
		
		
		buttonPrincipal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Intent trocatela = new
				// Intent(MainActivity.this,TagFragmentActivity.class);
				Intent trocatela2 = new Intent(FaqInicialActivity.this,
						FaqActivity.class);
				trocatela2.putExtra("faq","4");
				FaqInicialActivity.this.startActivity(trocatela2);
				// MainActivity.this.finish();
			}
		});
		
		buttonOutro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Intent trocatela = new
				// Intent(MainActivity.this,TagFragmentActivity.class);
				Intent trocatela2 = new Intent(FaqInicialActivity.this,
						FaqActivity.class);
				trocatela2.putExtra("faq","1");
				FaqInicialActivity.this.startActivity(trocatela2);
				// MainActivity.this.finish();
			}
		});

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