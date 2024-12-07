package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json .JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HomeController {

    @FXML
    private TextField getCity;

    @FXML
    private Button showWeather;

    @FXML
    private Text temperature;

    @FXML
    private Text feelsLike;

    @FXML
    private Text maxTemperature;

    @FXML
    private Text minTemperature;

    @FXML
    private Text pressure;

    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        showWeather.setOnAction(actionEvent -> {
            String getUserCity = getCity.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" +
                        getUserCity + "&appid=YOUR API KEY&units=metric");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);

                    temperature.setText("Temperature: " + obj.getJSONObject("main").getDouble("temp"));
                    feelsLike.setText("Feels like: " + obj.getJSONObject("main").getDouble("feels_like"));
                    maxTemperature.setText("Max: " + obj.getJSONObject("main").getDouble("temp_max"));
                    minTemperature.setText("Min: " + obj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Pressure: " + obj.getJSONObject("main").getDouble("pressure"));

                    String getDescription = obj.getJSONArray("weather").getJSONObject(0).getString("description");

                    InputStream inputStream = null;
                    try {
                        if (getDescription.contains("clear sky"))
                            inputStream = new FileInputStream("PATH TO FILE CLEAR SKY.PNG");
                        else if (getDescription.contains("few clouds"))
                            inputStream = new FileInputStream("PATH TO FILE FEW CLOUDS.PNG");
                        else if (getDescription.contains("mist"))
                            inputStream = new FileInputStream("PATH TO FILE MIST.PNG");
                        else if (getDescription.contains("rain"))
                            inputStream = new FileInputStream("PATH TO FILE RAIN.PNG");
                        else if (getDescription.contains("snow"))
                            inputStream = new FileInputStream("PATH TO FILE CLEAR SNOW.PNG");
                        else if (getDescription.contains("thunder"))
                            inputStream = new FileInputStream("PATH TO FILE THUNDER.PNG");
                        else if(getDescription.contains("broken clouds"))
                            inputStream = new FileInputStream("PATH TO FILE BROKEN CLOUDS.PNG\"");
                        else if (getDescription.contains("scattered clouds"))
                            inputStream = new FileInputStream("PATH TO FILE SCATTERED CLOUDS.PNG");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    assert inputStream != null;
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("City not found!");
        }
        return content.toString();
    }
}
