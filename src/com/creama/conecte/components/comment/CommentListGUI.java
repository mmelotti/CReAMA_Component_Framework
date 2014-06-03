package com.creama.conecte.components.comment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class CommentListGUI extends CRComponent {

	private ListView listview;
	private final static String urlBase = "http://apiconecteideias.azurewebsites.net";
	private final static  String urlFinal = "/comentarios/searchByIdea?idIdeia=";
	private List<Comment> lista = new ArrayList<Comment>();
	private Long targetId;
	private CommentListAdapter myAdapter;

	public CommentListGUI(Long serverId) {

		Log.e("Request??", "after construtor");
	}

	public CommentListGUI(Long serverId, Long targetId) {

		this.targetId = targetId;
		Log.e("Request??", "after construtor");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.conecte_list_comments_comp,
				container, false);
		myAdapter = new CommentListAdapter(lista) {
			@Override
			public void onClickOneItensTitleComponent(View v, Long id) {
				Log.e("ONNon clic test", "dentro component comment");
			}
		};
		listview = (ListView) view.findViewById(R.id.listComments);
		listview.setAdapter(myAdapter);
		AsyncRequestHandler mHandler = new AsyncRequestHandler() {
			@Override
			public void onSuccess(String response, Request request) {
				atualizarAfterSucces(response);
			}
			@Override
			public void onFailure(Throwable arg0, String arg1, Request request) {
				// atualizarAfterSucces("erro");
			}
		};
		setComponentRequestCallback(mHandler);
		return view;
	}

	// http://conecteideias.azurewebsites.net/Ideia/Create
	// http://apiconecteideias.azurewebsites.net/ideias/searchById?id=1067
	// "X-ApiKey" e o value dele é 257F1D3C-57A0-4F34-A937-1538104E97FE

	@Override
	protected void onBind() {
		testRequest();
	}

	private void testRequest() {
		Request request = new Request(null, urlBase + urlFinal + targetId,
				"get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		makeRequest(request);

	}

	private void atualizarAfterSucces(String r) {
		try {

			lista.clear();
			JSONArray commentsArray;
			commentsArray = new JSONArray(r);
			for (int j = 0; j < commentsArray.length(); j++) {
				Log.i("Parseando primeiro for comemnt", " ...= "
						+ commentsArray.get(j).toString());
				JSONObject commentObject = (JSONObject) commentsArray.get(j);
				JSONArray namesArray = commentObject.names();
				Comment comment = new Comment();
				comment.setNome(commentObject.getString("nome"));
				comment.setTexto(commentObject.getString("texto"));
				Log.i("vai add na lista", " ...= " + comment.getNome());
				lista.add(comment);
				for (int i = 0; i < namesArray.length(); i++) {
					String string = (String) namesArray.get(i);
					Log.i("dentro names = ", string + " ...= ");
				}
			}
			myAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
	}

}