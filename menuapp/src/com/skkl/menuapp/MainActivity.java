package com.skkl.menuapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	ListView listview;
	
	//Testing purpose
	String[] biz = new String[]{"Sushi Nichii", "Barchi Sushi", "Latitude"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        
        listview = (ListView)findViewById(R.id.listview);
        ((ListView)listview).setAdapter(new ItemAdapter());
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				//TODO: when clicked display menu of a biz			
			}
		});
    }
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
    public class ItemAdapter extends BaseAdapter {
        
        private class ViewHolder {
            public TextView businessName;
            public TextView descr;
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
