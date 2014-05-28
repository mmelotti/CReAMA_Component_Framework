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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.creama.conecte.components.idea.Idea;
import com.creama.conecte.components.idea.IdeaListAdapter;
import com.gw.android.R;
import com.gw.android.components.connection_manager.AsyncRequestHandler;
import com.gw.android.components.request.Request;
import com.gw.android.first_components.my_fragment.CRComponent;

@SuppressLint("ValidFragment")
public class CommentListGUI extends CRComponent {

	TextView title, descricao;
	private ListView listview;
	private String urlBase = "http://apiconecteideias.azurewebsites.net";

	private String urlFinal = "/comentarios/searchByIdea?idIdeia=";
	private List<Comment> lista = new ArrayList<Comment>();
	private Long serverId, targetId;
	CommentListAdapter myAdapter;

	public CommentListGUI(Long serverId) {
		this.serverId = serverId;
		Log.e("Request??", "after construtor");
	}

	public CommentListGUI(Long serverId, Long targetId) {
		this.serverId = serverId;
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
				// TODO Auto-generated method stub
				Log.e("ONNon clic test", "dentro component comment");
			}
		};
		

		// title=(TextView) view.findViewById(R.id.idea_titulo_one);
		// descricao=(TextView)view.findViewById(R.id.idea_body_one);
		listview = (ListView) view.findViewById(R.id.listComments);
		listview.setAdapter(myAdapter);
		//listview.setScrollContainer(false);
		/*
		 * listview.setOnTouchListener(new ListView.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { switch
		 * (event.getAction()) { case MotionEvent.ACTION_DOWN: // Disallow
		 * ScrollView to intercept touch events.
		 * v.getParent().requestDisallowInterceptTouchEvent(true); break;
		 * 
		 * case MotionEvent.ACTION_UP: // Allow ScrollView to intercept touch
		 * events. v.getParent().requestDisallowInterceptTouchEvent(false);
		 * break; }
		 * 
		 * // Handle ListView touch events. v.onTouchEvent(event); return true;
		 * } });
		 */

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
		// getConnectionManager().getCookiesInfo();
		testRequest();

	}

	void testRequest() {

		Request request = new Request(null, urlBase + urlFinal + targetId,
				"get", null);
		String header[] = new String[2];
		header[0] = "X-ApiKey";
		header[1] = "257F1D3C-57A0-4F34-A937-1538104E97FE";
		request.onlyOneHeader(header);
		// request.setKeyValuePairs(keyValuePairs);

		makeRequest(request);

	}

	public void atualizarAfterSucces(String r) {
		try {

			lista.clear();

			JSONArray commentsArray;
			commentsArray = new JSONArray(r);

			for (int j = 0; j < commentsArray.length(); j++) {
				Log.i("Parseando primeiro for comemnt", " ...= "
						+ commentsArray.get(j).toString());
				JSONObject commentObject = (JSONObject) commentsArray.get(j);
				JSONArray namesArray = commentObject.names();

				String titulo, descricao;
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
			//listview.setScrollContainer(false);
			// setComponentGUI(idea);
			// listview.setAdapter(new CommentAdapter(getActivity(), lista));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("TEST R LOGIN",r);

	}

	private void setComponentGUI(Idea idea) {

		title.setText(idea.getTitle());
		descricao.setText(idea.getText());
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		int a=1;
	}

}