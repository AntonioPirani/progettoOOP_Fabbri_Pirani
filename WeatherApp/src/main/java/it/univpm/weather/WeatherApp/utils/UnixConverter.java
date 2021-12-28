package it.univpm.weather.WeatherApp.utils;

import java.util.*;
import java.time.Instant;
import java.text.*;

// UNIX-->Epoca Epoca--->UNIX
public class UnixConverter
{
	public int dec;
	
	public UnixConverter(int dec) 
	{
		super();
		this.dec = dec;
	}
	
	public String UnixtoDate(long unix)
	{
		//converto i secondi in millisecondi
		Date date = new Date(unix*1000L);
		//setto il formato desiderato per la data
		SimpleDateFormat forma = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
		//setto il fusorario desiderato 
		forma.setTimeZone(TimeZone.getTimeZone("GMT"));
		String java_date = forma.format(date);
		return java_date;
	}
	
	public long previousHour(int d)
	{  
		long now = Instant.now().getEpochSecond();
		return now - d*3600;
	}
}
