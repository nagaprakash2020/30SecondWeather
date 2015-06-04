package com.company.weather.adapter;

import java.util.List;

import com.company.weather.R;
import com.company.weather.model.Hourly;
import com.company.weather.utils.WeatherUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HourlyWeatherListAdapter extends BaseAdapter{

	Context             context;
	List<Hourly>        hourly;
	ImageLoader         imageLoader;
	DisplayImageOptions imageOptions;
	
	public HourlyWeatherListAdapter(Context context,List<Hourly> hourly) {
		this.context = context;
		this.hourly  = hourly;
		imageLoader  = ImageLoader.getInstance();
		imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
							.cacheOnDisk(true)
							.resetViewBeforeLoading(true)
							.build();
	}
	
	@Override
	public int getCount() {
		return hourly.size();
	}

	@Override
	public Object getItem(int position) {
		return hourly.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView == null)
		{
			LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflator.inflate(R.layout.hourly_list_item, parent,false);
			holder      = new ViewHolder();
			holder.desc = (TextView) convertView.findViewById(R.id.descrTxt);
			holder.icon = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.temp = (TextView) convertView.findViewById(R.id.tempTxt);
			holder.time = (TextView) convertView.findViewById(R.id.timeTxt);

			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		Hourly hourlyItem  = (Hourly) getItem(position);
		
		if(hourlyItem != null)
		{
			holder.desc.setText(hourlyItem.getDesc());
			//holder.icon.set(hourlyItem.getDesc());
			holder.temp.setText(hourlyItem.getTemp());
			holder.time.setText(WeatherUtils.getHourlyTime(hourlyItem.getTime()));
			imageLoader.displayImage(hourlyItem.getIcon(), holder.icon,imageOptions);
		}
		
		return convertView;
	}

	class ViewHolder
	{
		TextView  time;
		ImageView icon;
		TextView  temp;
		TextView  desc;
	}
}