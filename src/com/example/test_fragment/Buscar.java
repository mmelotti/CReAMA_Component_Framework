package com.example.test_fragment;

import com.example.firstcomponents.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Buscar extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
     View view = inflater.inflate(R.layout.search, container, false);
     
     EditText edit = (EditText) view.findViewById(R.id.edit_find);
     
    Button button = (Button) view.findViewById(R.id.button_find);
        
         button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                
                if (activity != null) {
                    Toast.makeText(activity, "do you found it?", Toast.LENGTH_LONG).show();
                }
            }
            
        });
        
        return view;
    }
}
