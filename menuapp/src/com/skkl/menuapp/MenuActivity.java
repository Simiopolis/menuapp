package com.skkl.menuapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	public interface Callback {
		public void onComplete();
		public void onFail();
	}
	
	private String id, name;
	private Context context;
	TextView tv;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		name = bundle.getString("name");
//		tv = (TextView)findViewById(R.id.menu);
//		tv.setText(id);
		context = this;
		
	}
	
	

}
