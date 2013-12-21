package com.skkl.menuapp.asynctask;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.skkl.menuapp.MainActivity.Callback;
import com.skkl.menuapp.locu.Business;
import com.skkl.menuapp.locu.LocuResult;
import com.skkl.menuapp.util.RestClient;

public class LocuAPI extends AsyncTask<Void, Void, LocuResult>{
	
	private Callback mcallback;
	private String api_key = "1a8c6f3030016f4f0d78a0e4f559312fd28aad5d";
	private ProgressDialog pd;
	private Context context;
	private double lat,lng;
	
	public LocuAPI(Callback callback, Context context, double lat, double lng) {
		mcallback = callback;
		this.context = context;
		this.lat = lat;
		this.lng = lng;
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

	@Override
	protected LocuResult doInBackground(Void... params) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String search = "https://api.locu.com/v1_0/venue/search/?location=";
		search += lat +","+ lng +"&has_menu=true&api_key=" + api_key;
		String result = RestClient.connect(search);
		JsonElement json = new JsonParser().parse(result);
		//System.out.println(json);
		LocuResult biz = gson.fromJson(json, LocuResult.class);
		//System.out.println(biz);
//		List<Objects> business = biz.getBusiness();
//		for(Objects b: business) {
//			System.out.println(b.getName());
//		}
		return biz;
	}

	@Override
	protected void onPostExecute(LocuResult result) {
		if(pd!= null) {
			pd.dismiss();
		}
		mcallback.onComplete();
	}

}
