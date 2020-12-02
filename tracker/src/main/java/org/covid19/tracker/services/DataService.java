package org.covid19.tracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.covid19.tracker.model.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class DataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
	private List<LocationStats> allStats = new ArrayList<>();
	private List<String> allStates = new ArrayList<>();
	private List<LocationStats> eachState = new ArrayList<>();


	@PostConstruct
	@Scheduled(cron = "* * * 1 * *")
	public void fetchCovidData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<>();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader csvBodyReader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		
		for (CSVRecord record : records) {
			LocationStats locationStat = new LocationStats();
			locationStat.setCountry(record.get("Country_Region"));
			locationStat.setState(record.get("Province_State"));
			locationStat.setCounty(record.get("Admin2"));
			int latestCases = Integer.parseInt(record.get(record.size() - 1));
			int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
			locationStat.setLatestCases(latestCases);
			locationStat.setRecentCases(latestCases - prevDayCases);
			newStats.add(locationStat);
		}
		this.allStats = newStats;
		
	}
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	public List<String> getAllStates() {
		this.allStates = this.allStats
					.stream()
					.map(s -> s.getState())
					.distinct()
					.collect(Collectors.toList());
		return allStates;
	}
	
	public List<LocationStats> getEachState(String state) {
		this.eachState = this.allStats
					.stream()
					.filter(s -> s.getState().equalsIgnoreCase(state))
					.collect(Collectors.toList());
		return eachState;
	}
}
