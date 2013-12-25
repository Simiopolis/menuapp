package com.skkl.menuapp;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.skkl.menuapp.asynctask.LocuDetailAPI;
import com.skkl.menuapp.locudeatail.LocuDetailResult;
import com.skkl.menuapp.locudeatail.Menu;
import com.skkl.menuapp.locudeatail.Section;
import com.skkl.menuapp.locudeatail.Subsection;
import com.skkl.menuapp.locudeatail.Venue;

public class MenuActivity extends Activity {
	
	public interface Callback {
		public void onComplete();
		public void onFail();
	}
	
	private ListView menuItem;
	private String id, name;
	private Context context;
	private LocuDetailResult result;
	private LocuDetailAPI detail;
	private List<Venue> venues;
	private List<Menu> menus;
	private List<Section> sections;
	private List<Subsection> subsections;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_item_list);
		
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		name = bundle.getString("name");
		context = this;
		menuItem = (ListView)findViewById(R.id.menu_item_list);
		((ListView)menuItem).setAdapter(new ItemAdapter());
		menuItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO: do something when item is clicked.
			}
		});
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		LocuDetailSearch();
	}
	
	private void LocuDetailSearch() {
		detail = new LocuDetailAPI(new Callback() {
			
			@Override
			public void onFail() {
			}
			
			@Override
			public void onComplete() {
				try {
					result = detail.get(1000, TimeUnit.MILLISECONDS);
					venues = (List<Venue>) result.getObjects();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				
			}
		}, id, context);
		detail.execute();
	}
	
	public class ItemAdapter extends BaseAdapter {
        
        private class ViewHolder {
            public TextView businessName;
        }

        @Override
        public int getCount() {
        	// this number should sum up menu name and its items
            //return names.size();
        	return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if(convertView == null) {
                view = getLayoutInflater().inflate(R.layout.business, parent, false);
                holder = new ViewHolder();
                holder.businessName = (TextView)view.findViewById(R.id.businessName);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            
            //holder.businessName.setText((position + 1) + ". " + names.get(position));
            return view;
        }
    }
	
}
