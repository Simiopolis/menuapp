package com.skkl.menuapp;

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
	protected double lat, lng;
	
	//Testing purpose
	String[] biz = new String[]{"Sushi Nichii", "Barchi Sushi", "Latitude"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        context = this;
        getLocation();
        listview = (ListView)findViewById(R.id.listview);
        ((ListView)listview).setAdapter(new ItemAdapter());
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				//TODO: when clicked display menu of a biz
				startMenuActivity();
			}
		});
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		locuSearch();
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
    
    private void startMenuActivity() {
    	Intent i = new Intent(this, MenuActivity.class);
    	startActivity(i);
    }
    public class ItemAdapter extends BaseAdapter {
        
        private class ViewHolder {
            public TextView businessName;
//            public TextView descr;
        }

        @Override
        public int getCount() {
            return biz.length;
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
                //holder.descr = (TextView)view.findViewById(R.id.happyHourDesc);
                view.setTag(holder);
            } else {
                holder = (ViewHolder)view.getTag();
            }
            
            holder.businessName.setText((position + 1) + ". " + biz[position]);
            //holder.descr.setText(description[position]);
            return view;
        }
    }
}
