package com.tobias.saul.COVID19Tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tobias.saul.COVID19Tracker.entity.LocationStat;

@Service
public class LocationStatService {

	private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<LocationStat> locationStats;
	
	private Map<String, Integer> tcp;

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ApplicationContext context;

	public List<LocationStat> getLocationStats() {
		return locationStats;
	}

	public void setLocationStats(List<LocationStat> locationStats) {
		this.locationStats = locationStats;
	}

	@PostConstruct
	public void fetchCoronaData() throws IOException {
		ResponseEntity<String> coronaData = restTemplate.getForEntity(COVID_DATA_URL, String.class);

		StringReader in = new StringReader(coronaData.getBody());

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		List<LocationStat> newLocationStats = new ArrayList<LocationStat>();

		for (CSVRecord record : records) {
			LocationStat locationStat = context.getBean(LocationStat.class);

			locationStat.setProvinceState(record.get("Province/State"));
			locationStat.setCountryRegion(record.get("Country/Region"));

			locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));

			newLocationStats.add(locationStat);
		}

		this.locationStats = newLocationStats;

		Map<String, Integer> totalCasesPerCountryRegion = locationStats.stream().collect(Collectors
				.groupingBy(LocationStat::getCountryRegion, Collectors.summingInt(LocationStat::getLatestTotalCases)));
		
		LinkedHashMap<String, Integer> sortedTotalCasesPerCountryRegion = new LinkedHashMap<String, Integer>();
		
		totalCasesPerCountryRegion.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		    .forEachOrdered(x -> sortedTotalCasesPerCountryRegion.put(x.getKey(), x.getValue()));
		
		this.setTcp(sortedTotalCasesPerCountryRegion);
		
	}

	public Map<String, Integer> getTcp() {
		return tcp;
	}

	public void setTcp(Map<String, Integer> tcp) {
		this.tcp = tcp;
	}

}
