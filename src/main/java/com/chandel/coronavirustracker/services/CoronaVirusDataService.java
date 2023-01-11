package com.chandel.coronavirustracker.services;

import com.chandel.coronavirustracker.models.LocationStats;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// Makes the class a spring service class
@Service
public class CoronaVirusDataService {
    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getCurrentDayStats() {
        return currentDayStats;
    }

    List<LocationStats> currentDayStats = new ArrayList<>();

    public List<LocationStats> getPreviousDayStats() {
        return previousDayStats;
    }

    List<LocationStats> previousDayStats = new ArrayList<>();
    // Executes once the service instance is created
    @PostConstruct
    public void fetchCurrentDayCovidData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvFile = new StringReader(response.body());
        Iterable<CSVRecord> locationStats = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvFile);
        for (CSVRecord statRecord : locationStats) {
            String state = statRecord.get("Province/State");
            String country = statRecord.get("Country/Region");
            String covidCasesForToday = statRecord.get(statRecord.size()-1); // Using the last available date in the dataset
            LocationStats currentLocationStats = new LocationStats(country, state, covidCasesForToday);
            currentDayStats.add(currentLocationStats);
        }
    }
    @PostConstruct
    public void fetchPreviousDayCovidData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvFile = new StringReader(response.body());
        Iterable<CSVRecord> locationStats = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvFile);
        for (CSVRecord statRecord : locationStats) {
            String state = statRecord.get("Province/State");
            String country = statRecord.get("Country/Region");
            String covidCasesForToday = statRecord.get(statRecord.size()-2); // Using the last available date in the dataset
            LocationStats currentLocationStats = new LocationStats(country, state, covidCasesForToday);
            previousDayStats.add(currentLocationStats);
        }
    }
}
