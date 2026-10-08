package com.example.plantcare.service;
import com.example.plantcare.dto.WeatherInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.time.LocalDate;
import java.util.*;

@Service @Slf4j
public class WeatherService {
    private static final double LAT = 31.7683, LON = 35.2137;   // Jerusalem

    record Daily(List<String> time,
                 @JsonProperty("temperature_2m_max") List<Double> max,
                 @JsonProperty("temperature_2m_min") List<Double> min,
                 @JsonProperty("precipitation_sum") List<Double> rain) {}
    record Response(Daily daily) {}

    private final RestClient client;

    public WeatherService() {
        var f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(3000);
        f.setReadTimeout(3000);
        this.client = RestClient.builder()
                .baseUrl("https://api.open-meteo.com").requestFactory(f).build();
    }

    public Optional<WeatherInfo> today() {
        try {
            Response r = client.get().uri(u -> u.path("/v1/forecast")
                            .queryParam("latitude", LAT).queryParam("longitude", LON)
                            .queryParam("daily", "temperature_2m_max,temperature_2m_min,precipitation_sum")
                            .queryParam("timezone", "Asia/Jerusalem")
                            .queryParam("forecast_days", 1).build())
                    .retrieve().body(Response.class);
            Daily d = r.daily();
            return Optional.of(new WeatherInfo(LocalDate.parse(d.time().get(0)),
                    d.max().get(0), d.min().get(0), d.rain().get(0)));
        } catch (Exception e) {
            log.warn("Weather unavailable: {}", e.getMessage());
            return Optional.empty();
        }
    }
}