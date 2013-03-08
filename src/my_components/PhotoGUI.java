package my_components;


import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.example.my_fragment.GUIComponent;
import com.example.firstcomponents.R;

public class PhotoGUI extends GUIComponent {

	private View view;
	private ImageView image;
	private Photo photo;
	private int nInstance = 0;
	private Long imagemAtual = new Long(1);
	private int maxImagens = 3;
	private Button proxima;
	Bundle extras;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.imageone, container, false);

		image = (ImageView) view.findViewById(R.id.imageView1);
		extras = getActivity().getIntent().getExtras();

		if (extras != null) {
			// mudou imagem, recebeu parametro
			imagemAtual = extras.getLong("nImagem");
			image.setImageResource(getId(imagemAtual));
		} else {
			image.setImageResource(getId(Long.valueOf(1)));
		}

		// image.setImageResource(R.drawable.bolapreta);

		// setContentView(R.layout.ratingone);// set content from main.xml
		// image=(ImageView) view.findViewById(R.id.imageView1);// create
		// TextView object
		// image.setText("Olaaaa!");

		// rating=(RatingBar) view.findViewById(R.id.rating);// create RatingBar
		// object

		criaFoto();

		proxima = (Button) view.findViewById(R.id.imagem_proxima);
		proxima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// extras.putInt("nInstance", proximaImagem());
				Intent trocatela = new Intent(getActivity(),
						getActivity().getClass());
				trocatela.putExtra("nImagem", proximaImagem());
				getActivity().startActivity(trocatela);
				
				
				// MainActivity.this.finish();
				//Log.e("vamos tetar", "finish!");
				
				getActivity().finish();
			}
		});

		this.view = view;
		return view;

	}

	private int getId(Long n) {
		// TODO ta bizarro ainda, tudo estatico

		if (n == 3) {
			return R.drawable.bolapreta;
		} else if (n == 2) {
			//return R.drawable.letraw;
			return R.drawable.nature;
		} else {
			return R.drawable.ic_launcher;
		}
	}

	private Long proximaImagem() {
		if (imagemAtual != maxImagens) {
			return imagemAtual + 1;
		}

		return Long.valueOf(1);

	}

	public void setImagemAtual(Long n) {
		imagemAtual = n;

		// null pointer exception
		// image = (ImageView) view.findViewById(getId(n));
	}

	private void criaFoto() {
		// TODO ainda ta estatico

		photo = new Photo();
		nInstance++;
		photo.setInstanceId(getId() + "-" + nInstance);
		addInstanceId(1);

	}

}
