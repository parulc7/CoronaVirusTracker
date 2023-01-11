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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Makes the class a spring service class
@Service
public class CoronaVirusDataService {
    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    List<LocationStats> allStats = new ArrayList<>();
    // Executes once the service instance is created
    @PostConstruct
    public void fetchCovidData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        StringReader csvFile = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvFile);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            String country = record.get("Country/Region");
            String covidCasesForToday = record.get(record.size()-1); // Using the last available date in the dataset
            LocationStats currentLocationStats = new LocationStats(country, state, covidCasesForToday);
            allStats.add(currentLocationStats);
        }
//        System.out.println(allStats);
    }
}
