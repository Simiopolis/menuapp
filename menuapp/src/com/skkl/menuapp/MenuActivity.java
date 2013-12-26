package com.skkl.menuapp;

import java.util.ArrayList;
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
import com.skkl.menuapp.locudeatail.Content;
import com.skkl.menuapp.locudeatail.LocuDetailResult;
import com.skkl.menuapp.locudeatail.Menu;
import com.skkl.menuapp.locudeatail.MenuDisplay;
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
	private List<MenuDisplay> menu_display;
	TextView businessName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_item_list);
		
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		name = bundle.getString("name");
		context = this;
		menu_display = new ArrayList<MenuDisplay>();
		menuItem = (ListView)findViewById(R.id.menu_item_list);
		((ListView)menuItem).setAdapter(new ItemAdapter());
		menuItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO: do something when item is clicked.
			}
		});
		businessName = (TextView)findViewById(R.id.busines_name);
		businessName.setText(name);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		LocuDetailSearch();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		menu_display.clear();
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
					//this should be only one
					venues = (List<Venue>) result.getObjects();
					Venue venue = venues.get(0);
					menus = venue.getMenus();
					// 1 or more menus
					for(Menu m: menus) {
						// adds a menu object to menu_display
						MenuDisplay menu = new MenuDisplay();
						menu.setMenu(m);
						menu_display.add(menu);
						List<Section> sections = m.getSections();
						for(Section s: sections) {
							// adding a section instance to menu_display
							MenuDisplay section = new MenuDisplay();
							section.setSection(s);
							menu_display.add(section);
							List<Subsection> subsections = s.getSubsections();
							for(Subsection sub: subsections) {
								MenuDisplay subsection = new MenuDisplay();
								subsection.setSubsection(sub);
								menu_display.add(subsection);
								List<Content> contents = sub.getContents();
								for(Content c: contents) {
									MenuDisplay content = new MenuDisplay();
									content.setContent(c);
									menu_display.add(content);	
								}
							}							
						}
					}
					menuItem.invalidateViews();
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
            public TextView menuName;
            public TextView sectionName;
            public TextView subsectionName;
            public TextView contentName;
            public TextView contentPrice;
            public TextView contentDesc;
            public TextView contentText;
        }

        @Override
        public int getCount() {
        	return menu_display.size();
        }

        @Override
        public Object getItem(int position) {
            return menu_display.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
		public int getViewTypeCount() {
			return 4;
		}
        
		@Override
		public int getItemViewType(int position) {
//			Menu menu = menu_display.get(position).getMenu();
//			if(menu instanceof Menu) {
//				
//			}
			
			if(menu_display.get(position).getMenu() != null) {
				return 0;
			} else if(menu_display.get(position).getSection() != null) {
				return 1;
			} else if(menu_display.get(position).getSubsection() != null) {
				return 2;
			} else {
				return 3;
			}
			
		}

		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            String dollarSign = "$";
            
            //get menu display instance
            MenuDisplay menuDisplay = menu_display.get(position);
            
            if(menuDisplay.getMenu() != null) {	
            	if(convertView == null) {
            		view = getLayoutInflater().inflate(R.layout.menu, parent, false);
            		holder = new ViewHolder();
            		holder.menuName  = (TextView)view.findViewById(R.id.menuName);
            		view.setTag(holder);
            	} else {
            		holder = (ViewHolder)view.getTag();
            	}
            	if(menuDisplay.getMenu().getMenu_name() != null) {
            		holder.menuName.setText(menuDisplay.getMenu().getMenu_name());
            	} else {
            		holder.menuName.setText("");
            	}
            	
            } else if(menuDisplay.getSection() != null) {
            	if(convertView == null) {
            		view = getLayoutInflater().inflate(R.layout.section, parent, false);
            		holder = new ViewHolder();
            		holder.sectionName = (TextView)view.findViewById(R.id.section_name);
            		view.setTag(holder);
            	} else {
            		holder = (ViewHolder)view.getTag();
            	}
            	if(menuDisplay.getSection().getSection_name() != null) {
            		holder.sectionName.setText(menuDisplay.getSection().getSection_name());
            	} else {
            		holder.sectionName.setText("");
            	}
            	
            } else if(menuDisplay.getSubsection() != null) {
            	if(convertView == null) {
            		view = getLayoutInflater().inflate(R.layout.subsection, parent, false);
            		holder = new ViewHolder();
            		holder.sectionName = (TextView)view.findViewById(R.id.subsectionName);
            		view.setTag(holder);
            	} else {
            		holder = (ViewHolder)view.getTag();
            	}
            	if(menuDisplay.getSubsection().getSubsection_name() != null) {
            		holder.subsectionName.setText(menuDisplay.getSubsection().getSubsection_name());
            	} else {
//            		holder.subsectionName.setText("No Subsection Name");
            	}            	
            } else {
            	if(convertView == null) {
            		view = getLayoutInflater().inflate(R.layout.content, parent, false);
            		holder = new ViewHolder();
            		holder.contentName = (TextView)view.findViewById(R.id.content_name);
            		holder.contentPrice = (TextView)view.findViewById(R.id.content_price);
            		holder.contentDesc = (TextView)view.findViewById(R.id.content_desc);
            		holder.contentText = (TextView)view.findViewById(R.id.content_text);
            		view.setTag(holder);
            	} else {
            		holder = (ViewHolder)view.getTag();
            	}
            	
            	if(menuDisplay.getContent().getText() != null) {
            		holder.contentText.setText(menuDisplay.getContent().getText());
            	} else {
            		holder.contentText.setText("");
            	}
            	
            	if(menuDisplay.getContent().getName() != null) {
            		holder.contentName.setText(menuDisplay.getContent().getName());
            	} else {
            		holder.contentName.setText("");
            	}
            	
            	if(menuDisplay.getContent().getPrice() != null) {
            		holder.contentPrice.setText(dollarSign + menuDisplay.getContent().getPrice());            
            	} else {
            		holder.contentPrice.setText("");
            	}
            	
            	if(menuDisplay.getContent().getDescription() != null) {
            		holder.contentDesc.setText(menuDisplay.getContent().getDescription());
            	} else {
            		holder.contentDesc.setText("");
            	}
            }
            return view;
        }
    }
	
}
