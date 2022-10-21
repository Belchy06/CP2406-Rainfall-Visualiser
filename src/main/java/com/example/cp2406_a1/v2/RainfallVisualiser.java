package com.example.cp2406_a1.v2;

import com.example.cp2406_a1.common.RainStats;
import com.example.cp2406_a1.common.RainfallAnalyser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import textio.TextIO;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class RainfallVisualiser extends Application {
    private BarChart<String, Number> bc = null;

    @Override
    public void start(Stage stage) {
        int width = 218 * 6 + 40;
        int height = 500;

        Button selectFile = new Button("Select File:");
        Label label = new Label("No file selected");
        HBox hBox = new HBox(selectFile, label);
        BorderPane root = new BorderPane();
        root.setTop(hBox);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./resources"));
        EventHandler<ActionEvent> event = actionEvent -> {
            File file = fileChooser.showOpenDialog(stage);
            if(file != null) {
                String path = file.getAbsolutePath();
                label.setText(" " + path + " selected");
                try {
                    visualiseData(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        selectFile.setOnAction(event);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Rainfall Visualiser");
        xAxis.setLabel("");
        yAxis.setLabel("Rainfall (mm)");
        root.setCenter(bc);

        stage.setTitle("Rainfall Visualiser");
        stage.show();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    private void visualiseData(String path) throws IOException {
        bc.getData().clear();

        TextIO.readFile(path);
        String headerText = TextIO.getln();
        String[] headerContents = headerText.split(",", -1);
        TreeMap<String, RainStats> stats = new TreeMap<>();
        String stationName = "";
        if(headerContents.length == 8) {
            // 8 items indicates raw file
            String[] pathComponents = path.split("\\.", -1);
            String outputPath = pathComponents[0] + "_analysed." + pathComponents[1];
            RainfallAnalyser.Analyse(path, outputPath);
            stats = RainfallAnalyser.loadStats(outputPath);
            String[] temp = pathComponents[0].split(Pattern.quote(File.separator), -1);
            String fileName = temp[temp.length - 1].replace("_", "");
            stationName = fileName.replaceAll("([^_])([A-Z])", "$1 $2");
        } else if (headerContents.length == 4) {
            // 4 items indicates processed file
            stats = RainfallAnalyser.loadStats(path);
            String[] temp = path.split("\\.", -1)[0].split(Pattern.quote(File.separator), -1);
            String fileName = temp[temp.length - 1].replace("_", "").replace("analysed", "");
            stationName = fileName.replaceAll("([^_])([A-Z])", "$1 $2");
        }

        XYChart.Series series = new XYChart.Series();
        series.setName(stationName);
        for(HashMap.Entry<String, RainStats> item : stats.entrySet()) {
            String key = item.getKey();
            RainStats rain = item.getValue();
            double total = rain.getTotal();
            String[] keyContents = key.split("_", -1);
            String formattedKey = keyContents[1].substring(5) + "/" + keyContents[0].substring(4);
            series.getData().add(new XYChart.Data<>(formattedKey, total));
        }
        bc.getData().addAll(series);
    }
}
