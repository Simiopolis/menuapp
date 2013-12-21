package com.skkl.menuapp.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.skkl.menuapp.MenuActivity.Callback;
import com.skkl.menuapp.locudeatail.LocuDetailResult;

public class LocuDetailAPI extends AsyncTask<Void, Void, LocuDetailResult> {
	
	private Callback mCallback;
	private String id;
	private Context context;
	private ProgressDialog pd;
	
	public LocuDetailAPI(Callback callback, String id, Context context) {
		mCallback = callback;
		this.id = id;
		this.context = context;
	}

	@Override
	protected LocuDetailResult doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onPostExecute(LocuDetailResult result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setTitle("Processing");
		pd.setMessage("Please wait...");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}
	
}
