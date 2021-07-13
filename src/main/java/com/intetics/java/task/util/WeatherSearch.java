package com.intetics.java.task.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Locale;

@Slf4j
public class WeatherSearch {

    public static String findWeatherByCountryName(String country) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/find?q=" + findCapital(country).toLowerCase(Locale.ROOT) +
                        "&cnt=1&mode=null&lon=0&type=link%2C%20accurate&lat=0&units=metric")
                .get()
                .addHeader("x-rapidapi-key", "d233a02126msh5f1402a1b04e68dp17fd6ajsnba088926bcc3")
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build();
        String weather = null;
        try {
            Response response = client.newCall(request).execute();
            weather = response.body().string();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(weather);
            JSONArray list = (JSONArray) jsonObject.get("list");
            JSONObject main = (JSONObject) list.get(0);
            JSONObject mainData = (JSONObject) main.get("main");
            weather = mainData.toJSONString();
        } catch (IOException | ParseException e) {
            log.warn(e.getMessage());
        }
        return weather;
    }

    private static String findCapital(String country) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "https://countriesnow.space/api/v0.1/countries/capital";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        String requestJson = "{\"country\": \"" + country + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        String capital = restTemplate.postForObject(resourceUrl, entity, String.class);
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(capital);
            JSONObject data = (JSONObject) jsonObject.get("data");
            capital = data.get("capital").toString();
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        return capital;
    }
}
