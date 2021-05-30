package com.mycompany.charts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

public class TitanicPassengersDao {

    //create ArrayList of TitanicPassengers
    public List<TitanicPassengers> getPassengersFromJsonFile() throws IOException {

        List<TitanicPassengers> allPassengers = new ArrayList<TitanicPassengers>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputStream input = new FileInputStream("F:\\titanic_csv.json");
        allPassengers = objectMapper.readValue(input, new TypeReference<List<TitanicPassengers>>() {
        });
        return allPassengers;
    }

    //filter the whole array to get only passengers age and draw it
    public void graphPassengerAges(List<TitanicPassengers> passengerList) {
        List<Float> pAges = passengerList.stream().map(TitanicPassengers::getAge).limit(8).collect(Collectors.toList());
        List<String> pNames = passengerList.stream().map(TitanicPassengers::getName).limit(8).collect(Collectors.toList());
        CategoryChart chart = new CategoryChartBuilder()
                .width(1024).height(768)
                .title("Age Histogram")
                .xAxisTitle("Names")
                .yAxisTitle("Age").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setStacked(true);
        chart.addSeries("Passenger's Ages", pNames, pAges);
        new SwingWrapper(chart).displayChart();
    }

    //get passengers class and draw it
    public void graphPassengerClass(List<TitanicPassengers> passengerList) {

        Map<String, Long> result = passengerList.stream().collect(Collectors.groupingBy(TitanicPassengers::getPclass, Collectors.counting()));
        PieChart chart = new PieChartBuilder()
                .width(800).height(600)
                .title("Passenger class")
                .build();
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setSeriesColors(sliceColors);
        chart.addSeries("First Class", result.get("1"));
        chart.addSeries("Second Class", result.get("2"));
        chart.addSeries("Third Class", result.get("3"));
        new SwingWrapper(chart).displayChart();
    }

    // create a map of passengers  who survived or not and draw it
    public void graphPassengerSurvived(List<TitanicPassengers> passengerList) {
        Map<String, Long> result
                = passengerList.stream().collect(Collectors.groupingBy(TitanicPassengers::getSurvived, Collectors.counting()));
        PieChart chart = new PieChartBuilder()
                .width(800).height(600)
                .title(" passengers  who survived or not")
                .build();
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.addSeries("Not survived", result.get("0"));
        chart.addSeries("survived", result.get("1"));

        new SwingWrapper(chart).displayChart();

    }

    //create a map of females and males passengers who survived and draw it
    public void graphPassengerSurvivedGender(List<TitanicPassengers> passengerList) {

        Map<String, Long> result = passengerList.stream().filter(p -> p.getSurvived().equals("1")).collect(Collectors.groupingBy(TitanicPassengers::getSex, Collectors.counting()));
        PieChart chart = new PieChartBuilder()
                .width(800).height(600)
                .title("females and males passengers who survived")
                .build();
        Color[] sliceColors = new Color[]{new Color(180, 68, 50), new Color(130, 105, 120), new Color(80, 143, 160)};
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setSeriesColors(sliceColors);
        chart.addSeries("Male", result.get("male"));
        chart.addSeries("Female", result.get("female"));
        new SwingWrapper(chart).displayChart();
    }

}
