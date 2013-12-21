package com.skkl.menuapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.skkl.menuapp.asynctask.LocuAPI;
import com.skkl.menuapp.locu.Business;
import com.skkl.menuapp.locu.LocuResult;

public class MainActivity extends Activity {
	
	public interface Callback{
		void onComplete();
		void onFail();
	}
	
	ListView listview;
	private LocuAPI locu;
	private Context context;
	private LocuResult result;
	private List<String> names;
	private List<String> ids;
	protected double lat, lng, currentTime;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        context = this;
        names = new ArrayList<String>();
        ids = new ArrayList<String>();
        getLocation();
        listview = (ListView)findViewById(R.id.listview);
        ((ListView)listview).setAdapter(new ItemAdapter());
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				//TODO: when clicked display menu of a biz
				startMenuActivity(pos);
			}
		});
        locuSearch();
        currentTime = System.currentTimeMillis();
    }
    
	@Override
	protected void onStart() {
		super.onStart();
//		double time = System.currentTimeMillis();
//		if(time - currentTime >= 300000) {
//			locuSearch();
//		}
		locuSearch();
	}

	@Override
	protected void onResume() {
		super.onResume();
		names.clear();
		ids.clear();
	}

	private void locuSearch() {
    	locu = new LocuAPI(new Callback() {
			
			@Override
			public void onFail() {
			}
			
			@Override
			public void onComplete() {
				try {
					result = locu.get(1000, TimeUnit.MILLISECONDS);
					List<Business> businesses = result.getObjects();					
					for(Business b: businesses) {
						names.add(b.getName());
						ids.add(b.getId());
					}
					listview.invalidateViews();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
		}, context, lat, lng);
    	locu.execute();
    }
    
    private void getLocation() {
    	LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    	Criteria criteria = new Criteria();
    	String provider = locationManager.getBestProvider(criteria, true);
    	Location myLocation = locationManager.getLastKnownLocation(provider);
    	lat = myLocation.getLatitude();
    	lng = myLocation.getLongitude();
    }
    
    private void startMenuActivity(int pos) {
    	Intent i = new Intent(this, MenuActivity.class);
    	i.putExtra("id", ids.get(pos));
    	i.putExtra("name", names.get(pos));
    	startActivity(i);
    }
    
    public class ItemAdapter extends BaseAdapter {
        
        private class ViewHolder {
            public TextView businessName;
        }

        @Override
        public int getCount() {
            return names.size();
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
            
            holder.businessName.setText((position + 1) + ". " + names.get(position));
            return view;
        }
    }
}
