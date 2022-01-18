package it.univpm.weather.WeatherApp.testStats;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.univpm.weather.WeatherApp.stats.Statistics;

/** Classe per testare i metodi della classe Statistics.
 * 
 * @author Matteo
 */

public class TestStatistics 
{
	
	private Statistics stats;
	
	/** Setup dei parametri utilizzati nei vari metodi dell classe TestStatics.
	 */
	@Before
	public void setUp()
	{
		stats = new Statistics(4.54,-3.07,6.819,8.266, "Ancona");
	}
	
	/** Test del metodo toJSON.	
	 */
	@Test
	public void testJSON()
	{
		String json="{\"min\":-3.07,\"avg\":6.819,\"max\":4.54,\"var\":8.266}";
		assertEquals(stats.toJson().toString(),json);//TODO
	}

	@After
	public void tearDown() throws Exception {
	}
}
