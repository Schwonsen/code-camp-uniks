package com.uni.cc_uniapp_2015.objects;

import java.util.ArrayList;

public class Event
{

	int date;
	String appointment;
	String organizer;

	ArrayList<Event> listOfEvents = new ArrayList<Event>();

	public String getAppointment()
	{
		return appointment;
	}

	public String getOrganizer()
	{
		return organizer;
	}

	public int getDate()
	{
		return date;
	}

	public ArrayList<Event> getEventList()
	{
		return listOfEvents;
	}

	public void setEventsList(ArrayList<Event> eventList)
	{
		this.listOfEvents = eventList;
	}
}
