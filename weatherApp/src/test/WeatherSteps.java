package test;

import com.gdn.qa.automation.core.context.StepDefinition;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@StepDefinition
public class WeatherSteps {
    @Given("^the user gives the city name '(.*)' to find the latitude and longitude$")
    public void theUserGivesTheCityNameBangaloreChennaiMumbaiKolkataDelhiToFindTheLatitudeAndLongitude(String cityList) {
        String list[]=cityList.split(",");
        try (FileWriter fileWriter = new FileWriter("city.csv");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Write data
            for (String row : list) {
                printWriter.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        try{
            FileWriter fileWriter1 = new FileWriter("citystats.csv");
            PrintWriter printWriter1 = new PrintWriter(fileWriter1);

            BufferedReader br = new BufferedReader(new FileReader("city.csv"));
            while ((line = br.readLine()) != null) {
                Response response=masterSkuReviewController.getLatLogOfCity(line);
                String latitude=response.jsonPath().getString("[0].lat");
                System.out.println(latitude);
                String longitude=response.jsonPath().getString("[0].lon");
                System.out.println(longitude);
                Response weatherResponse=masterSkuReviewController.getWeatherOfCity(latitude,longitude);
                String humidity=weatherResponse.jsonPath().getString("current.humidity");
                String temperature=weatherResponse.jsonPath().getString("current.temp");
                String finalValue[]=new String[3];
                finalValue[0]=line;
                finalValue[1]=humidity;
                finalValue[2]=temperature;
                printWriter1.println(String.join(",", finalValue));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("^the user finds the top three humid cities from by comparing the humid values from excel$")
    public void theUserFindsTheTopThreeHumidCitiesFromByComparingTheHumidValuesFromExcel() {
        String line;
        HashMap<String,Float> humidityReport=new HashMap<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("city_stats.csv"));
            while ((line = br.readLine()) != null) {
                String a[]=line.split(",");
                humidityReport.put(a[0], Float.valueOf(a[1]));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        List<Map.Entry<String,Float>> humidityReportList=new ArrayList<>(humidityReport.entrySet());
        humidityReportList.sort(Map.Entry.comparingByValue());
        System.out.println("Top three humid cities");
        for(int i=0;i<3;i++){
            System.out.println(humidityReportList.get(i).getKey());
        }
    }

    @Then("^the user finds the top three cold cities from by comparing the humid values from excel$")
    public void theUserFindsTheTopThreeColdCitiesFromByComparingTheHumidValuesFromExcel() {
        String line;
        HashMap<String,Float> coldestReport=new HashMap<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("city_stats.csv"));
            while ((line = br.readLine()) != null) {
                String a[]=line.split(",");
                coldestReport.put(a[0], Float.valueOf(a[1]));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        List<Map.Entry<String,Float>> coldestReportList=new ArrayList<>(coldestReport.entrySet());
        coldestReportList.sort(Map.Entry.comparingByValue());
        System.out.println("Top three coldest cities");
        for(int i=coldestReportList.size()-1 ; i>= coldestReportList.size()-3 ; i--){
            System.out.println(coldestReportList.get(i).getKey());
        }
    }
}

