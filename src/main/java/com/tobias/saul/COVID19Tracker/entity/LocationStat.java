package com.tobias.saul.COVID19Tracker.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LocationStat {
	
	public String provinceState;
	public String countryRegion;
	private int latestTotalCases;
	
	public LocationStat() {}

	public String getProvinceState() {
		return provinceState;
	}

	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	@Override
	public String toString() {
		return "LocationStat [provinceState=" + provinceState + ", countryRegion=" + countryRegion
				+ ", latestTotalCases=" + latestTotalCases + "]";
	};
	
	
	
	
	
}
