package cityState_wether;

public class Weather {
	private int temp, temp_min, temp_max, feels_like;
	private String wind_direction=new String();
	private int humidity;
	private int precip;
	private int pressure;
	private int visibility;
	private int sky_state;
	
	public int getPrecip() {
		return precip;
	}
	public void setPrecip(int precip) {
		this.precip = precip;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getTemp_min() {
		return temp_min;
	}
	public void setTemp_min(int temp_min) {
		this.temp_min = temp_min;
	}
	public int getTemp_max() {
		return temp_max;
	}
	public void setTemp_max(int temp_max) {
		this.temp_max = temp_max;
	}
	public int getFeels_like() {
		return feels_like;
	}
	public void setFeels_like(int feels_like) {
		this.feels_like = feels_like;
	}
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public int getSky_state() {
		return sky_state;
	}
	public void setSky_state(int sky_state) {
		this.sky_state = sky_state;
	}
	
	
}

