package it.univpm.weather.WeatherApp.testStats;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import it.univpm.weather.WeatherApp.stats.Statistics;


public class TestStatistics 
{
	
	private Statistics stats;
	
	@Before
	public void setUp()
	{
		stats = new Statistics(2.55,3.66,4.44,5.44);
	}
	
	@Test
	public void testJSON()
	{
		String json="";
		assertEquals(stats.toJson().toString(),json);
	}
	
}
