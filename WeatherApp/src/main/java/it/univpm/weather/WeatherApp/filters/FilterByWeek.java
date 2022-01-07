package it.univpm.weather.WeatherApp.filters;

public class FilterByWeek extends Filter {

	public FilterByWeek(String cityName, int time) {
		super();
		this.filterBy = "week";
		this.cityName = cityName;
		this.time = time;
	}

	
	
}
