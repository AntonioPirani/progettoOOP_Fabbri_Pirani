package it.univpm.weather.WeatherApp.filters;

public class FilterByDay extends Filter {

	public FilterByDay(String cityName, int time) {
		super();
		this.filterBy = "day";
		this.cityName = cityName;
		this.time = time;
	}

	
	
}
