package com.example.cp2406_a1.v1;

import com.example.cp2406_a1.common.RainStats;
import com.example.cp2406_a1.common.RainfallAnalyser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.TreeMap;

public class RainfallVisualiser extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        int width = 218 * 6 + 40;
        int height = 500;
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Rainfall Analyser");
        xAxis.setLabel("");
        yAxis.setLabel("Rainfall (mm)");

        RainfallAnalyser.Analyse("resources/MountSheridanStationCNS.csv", "resources/MountSheridanStationCNS_analysed.csv");
        TreeMap<String, RainStats> stats = RainfallAnalyser.loadStats("resources/MountSheridanStationCNS_analysed.csv");
        XYChart.Series series = new XYChart.Series();
        series.setName("Mount Sheridan");
        for(HashMap.Entry<String, RainStats> item : stats.entrySet()) {
            String key = item.getKey();
            RainStats rain = item.getValue();
            double total = rain.getTotal();
            String[] keyContents = key.split("_", -1);
            String formattedKey = keyContents[1].substring(5) + "/" + keyContents[0].substring(4);
            series.getData().add(new XYChart.Data(formattedKey, total));
        }
        bc.getData().addAll(series);
        stage.setTitle("Rainfall Visualiser");
        stage.show();
        Scene scene = new Scene(bc, width, height);
        stage.setScene(scene);
        stage.setResizable(false);
    }
}
