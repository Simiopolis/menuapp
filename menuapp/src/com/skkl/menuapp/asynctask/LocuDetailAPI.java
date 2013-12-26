package com.skkl.menuapp.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skkl.menuapp.MenuActivity.Callback;
import com.skkl.menuapp.locudeatail.Content;
import com.skkl.menuapp.locudeatail.LocuDetailResult;
import com.skkl.menuapp.locudeatail.Menu;
import com.skkl.menuapp.locudeatail.Option;
import com.skkl.menuapp.locudeatail.OptionGroups;
import com.skkl.menuapp.locudeatail.Section;
import com.skkl.menuapp.locudeatail.Subsection;
import com.skkl.menuapp.locudeatail.Venue;
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
		search += id + "/?api_key=" + api_key;
		String result = RestClient.connect(search);
		JSONObject json = null;
		try {
			json = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LocuDetailResult locuDetialResult = makeLocuDetailResult(json);
		return locuDetialResult;
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
	
	private LocuDetailResult makeLocuDetailResult(JSONObject json) {
		LocuDetailResult result = new LocuDetailResult();
		try {
			JSONArray objects = json.getJSONArray("objects");
			int numOfVenues = objects.length();
			List<Venue> venues = new ArrayList<Venue>();
			for(int i=0; i < numOfVenues; i++) {
				JSONObject object = (JSONObject) objects.get(i);
				Venue venue = makeVenue(object);
				venues.add(venue);
			}
			result.setObjects(venues);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//TODO: additional attributes might be needed for future
	private Venue makeVenue(JSONObject json) {
		Venue venue = new Venue();
		try {
			JSONArray jArray = json.getJSONArray("menus");
			int numOfMenus = jArray.length();
			List<Menu> menus = new ArrayList<Menu>();
			for(int i =0; i< numOfMenus; i++) {
				JSONObject j = (JSONObject)jArray.get(i);
				Menu menu = makeMenu(j);
				menus.add(menu);
			}
			if(json.has("phone")) {
				String phone = json.getString("phone");
				venue.setPhone(phone);
			}
			String name = json.getString("name");
			venue.setName(name);
			venue.setMenus(menus);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return venue;
	}
	
	private Menu makeMenu(JSONObject json) {
		Menu menu = new Menu();
		try {
			JSONArray jArray = json.getJSONArray("sections");
			int numOfSections = jArray.length();
			List<Section> sections = new ArrayList<Section>();
			for(int i=0; i<numOfSections; i++) {
				JSONObject j = (JSONObject)jArray.get(i);
				Section section = makeSection(j);
				sections.add(section);
			}
			if(json.has("menu_name")) {
				String menu_name = json.getString("menu_name");
				menu.setMenu_name(menu_name);
			}
			
			if(json.has("currency_symbol")) {
				String currency_symbol = json.getString("currency_symbol");
				menu.setCurrency_symbol(currency_symbol);
			}
			menu.setSections(sections);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return menu;
	}
	
	private Section makeSection(JSONObject json) {
		Section section = new Section();
		try {
			JSONArray jArray = json.getJSONArray("subsections");
			int numOfSubsections = jArray.length();
			List<Subsection> subsections = new ArrayList<Subsection>();
			for(int i=0; i<numOfSubsections; i++) {
				JSONObject j = (JSONObject)jArray.get(i);
				Subsection subsection = makeSubsection(j);
				subsections.add(subsection);
			}
			if(json.has("section_name")) {
				String section_name = json.getString("section_name");
				section.setSection_name(section_name);
			}
			section.setSubsections(subsections);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return section;
	}
	
	private Subsection makeSubsection(JSONObject json) {
		Subsection subsection = new Subsection();
		try {
			JSONArray jArray = json.getJSONArray("contents");
			int numOfContents = jArray.length();
			List<Content> contents = new ArrayList<Content>();
			for(int i=0; i< numOfContents; i++) {
				JSONObject j = (JSONObject)jArray.get(i);
				Content content = makeContent(j);
				contents.add(content);
			}
			if(json.has("section_name")) {
				String subsection_name = json.getString("section_name");
				subsection.setSubsection_name(subsection_name);
			}
			subsection.setContents(contents);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return subsection;
	}
	
	private Content makeContent(JSONObject json) {
		Content content = new Content();
		
		try {
			if(json.has("option_groups")) {
				JSONArray jArray = json.getJSONArray("option_groups");
				int numOfOptionGroups = jArray.length();
				List<OptionGroups> optionGroups = new ArrayList<OptionGroups>();
				for(int i=0; i< numOfOptionGroups; i++) {
					JSONObject j = (JSONObject)jArray.get(i);
					OptionGroups oGroups = makeOptionGroups(j);
					optionGroups.add(oGroups);
				}
				content.setOptionGroups(optionGroups);
			}
			
			if(json.has("price")) {
				String price = json.getString("price");
				content.setPrice(price);
			}
			
			if(json.has("type")) {
				String type = json.getString("type");
				content.setType(type);
			}
			
			if(json.has("description")) {
				String description = json.getString("description");
				content.setDescription(description);
			}
			
			if(json.has("name")) {
				String name = json.getString("name");
				content.setName(name);
			}
			
			if(json.has("text")) {
				String text = json.getString("text");
				content.setText(text);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	private OptionGroups makeOptionGroups(JSONObject json) {
		OptionGroups optionGroup = new OptionGroups();
		try {
			JSONArray jArray = json.getJSONArray("options");
			int numOfOptions = jArray.length();
			List<Option> options = new ArrayList<Option>();
			for(int i=0; i< numOfOptions; i++) {
				JSONObject j = (JSONObject)jArray.get(i);
				Option option = makeOption(j);
				options.add(option);
			}
			optionGroup.setOptions(options);
			
			if(json.has("text") && json.getString("text") != "") {
				optionGroup.setText(json.getString("text"));
			}
			
			if(json.has("type")) {
				optionGroup.setType(json.getString("type"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionGroup;
	}
	
	private Option makeOption(JSONObject json) {
		Option option = new Option();
		
		try {
			if(json.has("name")) {
				option.setName(json.getString("name"));
			}
			
			if(json.has("price")) {
				option.setPrice(json.getString("price"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return option;
	}
	
}
