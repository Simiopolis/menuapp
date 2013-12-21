package com.skkl.menuapp.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.skkl.menuapp.MenuActivity.Callback;
import com.skkl.menuapp.locudeatail.LocuDetailResult;
import com.skkl.menuapp.util.RestClient;

public class LocuDetailAPI extends AsyncTask<Void, Void, LocuDetailResult> {
	
	private Callback mCallback;
	private String id, api_key;
	private Context context;
	private ProgressDialog pd;
	
	public LocuDetailAPI(Callback callback, String id, Context context) {
		mCallback = callback;
		this.id = id;
		this.context = context;
		api_key = "1a8c6f3030016f4f0d78a0e4f559312fd28aad5d";
	}

	@Override
	protected LocuDetailResult doInBackground(Void... params) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String search = "https://api.locu.com/v1_0/venue/";
		search += id + "/?api_key" + api_key;
		String result = RestClient.connect(search);
		JsonElement json = new JsonParser().parse(result);
		LocuDetailResult detailResult = gson.fromJson(json, LocuDetailResult.class);
		return detailResult;
	}

	@Override
	protected void onPostExecute(LocuDetailResult result) {
		if(pd != null) {
			pd.dismiss();
		}
		mCallback.onComplete();
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
