package com.gw.android.first_components.my_components.photo;

import java.io.File;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_components.Constants;
import com.gw.android.first_components.my_fragment.CRComponent;
import com.gw.android.first_components.my_fragment.ComponentSimpleModel;

public class PhotoSendGUI extends CRComponent {
	String url; 
	EditText titulo, descricao, autorDaObra, autorDaImagem,  tags;
	
	
	private String getUrl() {
		SharedPreferences testPrefs = getActivity()
				.getApplication()
				.getSharedPreferences("test_prefs", Context.MODE_PRIVATE);
		return testPrefs.getString("base_url", "");
	}
	
	/*TODO REQUEST - descricao abaixo
	 * collblet principal
	 * 	http://www.arquigrafia.org.br/18/
	 * 
	 * 
	 * 
	 * user.id	
photoRegister.id	
foto [APROPRIAIMAGEM CARACTERES ESCROTOS]

 photoRegister.name	maison teste
photoRegister.imageAuthor	
photoRegister.state	
photoRegister.dataCriacao	
photoRegister.country	Brasil
photoRegister.district	
photoRegister.workAuthor	
photoRegister.street	
photoRegister.workdate	
tagMgr.tags	maisonteste, maisonteste2, maisonteste3
photoRegister.description	
terms	read
photoRegister.allowCommer...	YES
photoRegister.allowModifi...	YES
enviar	


enviar aparentemente vazio, assim como os dois primeiros campos
	 * 
	 * 
	 * 
	 * 
25459152783511 Content-Disposition: form-data; name="user.id" 

	 * 25459152783511 Content-Disposition: form-data; name="photoRegister.id"
	25459152783511 Content-Disposition: form-data; name="foto"; filename="IMAG0064.jpg" Content-Type: image/jpeg
	 * AQUI VEM OS CARACTERES DA IMAGEM
	 * 
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	
	String uploadRequest(String photopath) {
		Request request = new Request(null, url, "post",
				"__tagMgr.tags--" + tags.getText() +
				"__user.id--" + "" +
				"__photoRegister.id--" + "" +
				"__photoRegister.name--" + titulo.getText() +
				"__photoRegister.imageAuthor--" + autorDaObra.getText() +
				"__photoRegister.state--" +
				"__photoRegister.country--" + "Brasil" +
				"__photoRegister.dataCriacao--" +
				"__photoRegister.district--" +
				"__photoRegister.workAuthor--" +autorDaImagem.getText() +
				"__photoRegister.street--" +
				"__photoRegister.workdate--" +
				"__photoRegister.description--" + descricao.getText()+
				"__photoRegister.allowCommercialUses--" + "YES" +
				"__photoRegister.allowModifications--" + "YES" +
				"__foto--" + "((IS_FILE_TAG))" + photopath +
				"__terms--" + "read" +
				"__enviar--" + "");
		makeRequest(request);
		return "";
	}
	
	void saveInDatabase(String filepath, Context ctx) {
		File f = new File(filepath);
		Bitmap bitmap = PhotoUtils.resizeImage(f, 1024);
		Log.e("AFTER RESIZE width e height", bitmap.getWidth() + " - " + bitmap.getHeight());
		
		byte[] bArray = PhotoUtils.bitmapToByteArray(bitmap);
		Photo photo = new Photo(ComponentSimpleModel.getUniqueId(ctx),
				null, null, bArray, null, new Date());

		PhotoDao photoDao = PhotoUtils.initPhotoDao(ctx);
		long id = photoDao.insert(photo);
		photoDao.getDatabase().close();

		Toast.makeText(ctx, "Foto salva!", Toast.LENGTH_SHORT).show();
		Log.v("Inseriu a foto no banco de dados!", "ID: " + id);
	}

	// Browse files
	public void chooseFile() {
		Intent myIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(myIntent, PhotoUtils.SELECT_PICTURE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PhotoUtils.SELECT_PICTURE) {
				Uri selectedimage = data.getData();
				String[] filepathcolumn = { MediaStore.Images.Media.DATA };

				Context ctx = getActivity();
				Cursor cursor = ctx.getContentResolver().query(selectedimage,
						filepathcolumn, null, null, null);
				cursor.moveToFirst();

				int columnindex = cursor.getColumnIndex(filepathcolumn[0]);
				String filepath = cursor.getString(columnindex);
				cursor.close();

				Toast.makeText(getActivity(), "Enviando foto.", Toast.LENGTH_SHORT).show();
				saveInDatabase(filepath, ctx);
				uploadRequest(filepath);
			}
		}
	}

	public Long getImageId() {
		preDefined();
		return getCurrentInstanceId();
	}

	public void preDefined() {
		setGeneralGUIId(Constants.PhotoViewGeneralGUIId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		url = getUrl() + "/photo/"+ getCollabletId() +"/registra"; 
		setComponentRequestCallback(new AsyncRequestHandler() {
			@Override
			public void onSuccess(String arg1, Request request) {
				new AlertDialog.Builder(getActivity())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Upload de foto bem sucedido")
						.setMessage("Foto enviada com sucesso.")
						.setNeutralButton("Ok", null).show();
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				new AlertDialog.Builder(getActivity())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("Upload de foto falhou")
						.setMessage("A foto não pode ser enviada.")
						.setNeutralButton("Ok", null).show();
			}
		});
		
		View view = inflater.inflate(R.layout.photo_send, container, false);
		titulo = (EditText) view.findViewById(R.id.edit_titulo);
		descricao = (EditText) view.findViewById(R.id.edit_descricao);
		autorDaObra = (EditText) view.findViewById(R.id.edit_autorobra);
		autorDaImagem = (EditText) view.findViewById(R.id.edit_autorimagem);
		tags = (EditText) view.findViewById(R.id.edit_tags);

		view.findViewById(R.id.btnBrowse).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseFile();
			}
		});
		
		view.findViewById(R.id.btnclear).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				titulo.setText("");
				descricao.setText("");
				autorDaObra.setText("");
				autorDaImagem.setText("");
				tags.setText("");
			}
		});

		return view;
	}
}
