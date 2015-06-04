package com.company.weather.fragements;

import java.util.ArrayList;

import com.company.weather.R;
import com.company.weather.activities.WeatherActivity;
import com.company.weather.adapter.NavDrawerListAdapter;
import com.company.weather.model.NavDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NavigationDrawerFragment extends Fragment{

	private DrawerLayout          	 drawerLayout;
	private ActionBarDrawerToggle 	 drawerToggle;
	private ListView        	  	 drawerListView;
	private NavDrawerListAdapter  	 navDrawerListAdapter;
	private WeatherActivity          weatherActivity;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavigationDrawerFragment() {
	}
	
	@Override
	public void onAttach(Activity activity) {
		weatherActivity = (WeatherActivity) activity;
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.navigation_layout_fragment,container, false);
		
		drawerListView = (ListView) view.findViewById(R.id.list_slidermenu);
		drawerListViewSetUp(drawerListView);
		drawerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				weatherActivity.setRetainedFragment(null);
				weatherActivity.displayView(position);
				drawerListView.setItemChecked(position, true);
				drawerListView.setSelection(position);
				drawerLayout.closeDrawers();
			}
		});
		
		return view;
	}

	
	
	
	private void drawerListViewSetUp(ListView drawerListView) {
		
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		String[]   navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		TypedArray navMenuIcons  = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		
		for(int i=0 ; i<navMenuTitles.length; i++)
		{
			//TODO images needs to be updated.
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(0, -1)));
		}
		navMenuIcons.recycle();
		navDrawerListAdapter = new NavDrawerListAdapter(weatherActivity, navDrawerItems);
		drawerListView.setAdapter(navDrawerListAdapter);
	}
	
	
	
	
	public void setUp(DrawerLayout drawerLayout,Toolbar toolbar) {
		
		this.drawerLayout = drawerLayout;
		this.drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed) {
			
			@Override
			public void onDrawerClosed(View drawerView) {
				Toast.makeText(weatherActivity, R.string.drawer_closed, Toast.LENGTH_SHORT).show();
				super.onDrawerClosed(drawerView);
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(weatherActivity, R.string.drawer_opened, Toast.LENGTH_SHORT).show();

				super.onDrawerOpened(drawerView);
			}
		};
		
		this.drawerLayout.setDrawerListener(drawerToggle);
	}
	
}
