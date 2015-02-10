package com.uni.cc_uniapp_2015.activitys;

import java.util.ArrayList;
import java.util.Calendar;

import com.uni.cc_uniapp_2015.MainActivity;
import com.uni.cc_uniapp_2015.R;
import com.uni.cc_uniapp_2015.crawler.CrawlValue;
import com.uni.cc_uniapp_2015.crawler.Crawler;
import com.uni.cc_uniapp_2015.crawler.Crawls;
import com.uni.cc_uniapp_2015.crawler.NvvCrawler;
import com.uni.cc_uniapp_2015.root.Root;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NvvActivity extends ActionBarActivity implements Crawls
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nvv);

		Calendar c = Calendar.getInstance();
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		
		String time = "";
		time = setTimer(hours, minutes, time);

		createThis(hours, minutes);

		if (Root.getInsRoot().getNvvInfos() == null)
		{
			Object[] parameters = { time };
			NvvCrawler nvv = new NvvCrawler(this);
			if (!MainActivity.online)
			{
				createInterface();
				Toast.makeText(getApplicationContext(),
						"Keine Internetverbindung.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				nvv.fetch(parameters);
			}
		}
		else
		{
			createInterface();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nvv, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void createInterface()
	{
		if (!MainActivity.online)
		{
			return;
		}

		CrawlValue crawVal = Root.getInsRoot().getNvvInfos();
		ListView listView = (ListView) findViewById(R.id.listViewNvv);
		ArrayAdapter<String> itemAdapter;
		ArrayList<String> listItems = new ArrayList<String>();

		for (String input : crawVal.getKeys())
		{
			String tramInfos = crawVal.getValue(input).get(0) + " "
					+ " " + crawVal.getValue(input).get(1) + " "
					+ crawVal.getValue(input).get(2);

			if (listItems.size() == 0)
			{
				listItems.add(tramInfos);
			}
			else
			{
				int currentTime = getTime(crawVal.getValue(input).get(0));
				int listPosition = 0;

				for (String listItem : listItems)
				{
					if (currentTime < getTime(listItem))
					{
						break;
					}
					listPosition++;
				}
				listItems.add(listPosition, tramInfos);
			}
		}
		itemAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		listView.setAdapter(itemAdapter);
	}

	private int getTime(String string)
	{
		int hour = Integer.parseInt(string.substring(0, 2).trim());
		int minute = Integer.parseInt(string.substring(3, 5).trim());

		return hour * 60 + minute;
	}

	@Override
	public void onCrawlFinish(CrawlValue result)
	{
		Root.getInsRoot().setNvvInfos(result);
		createInterface();
	}

	public void createThis(int hours, int minutes)
	{
		setContentView(R.layout.activity_nvv);
		TimePicker picker = (TimePicker) findViewById(R.id.timePickerNvv);
		picker.setIs24HourView(true);
		picker.setCurrentHour(hours);

		final NvvActivity nvvAvtivity = this;
		Button searchButton = (Button) findViewById(R.id.buttonSearchTram);

		searchButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				ListView listView = (ListView) findViewById(R.id.listViewNvv);
				listView.setAdapter(null);

				NvvCrawler nvvCrawl = new NvvCrawler(nvvAvtivity);
				TimePicker picker = (TimePicker) findViewById(R.id.timePickerNvv);
				int hours = picker.getCurrentHour();
				int minutes = picker.getCurrentMinute();
				
				String time = "";
				time = setTimer(hours, minutes, time);

				Object[] parameters = { time };

				nvvCrawl.fetch(parameters);
			}
		});
	}

	public String setTimer(int hour, int minute, String timer)
	{
		if (hour < 10)
		{
			timer += "0";
		}

		timer += hour + ":";

		if (minute < 10)
		{
			timer += "0";
		}

		return timer += minute;

	}

	public void test(CrawlValue crv)
	{
		Crawler asd = new NvvCrawler(this);
		Object[] parms = { "10:10" }; // <-- time
		asd.fetch(parms);
	}
}