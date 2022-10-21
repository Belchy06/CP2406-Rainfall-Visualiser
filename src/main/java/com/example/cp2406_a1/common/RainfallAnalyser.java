package com.example.cp2406_a1.common;

import com.example.cp2406_a1.common.RainRecord;
import com.example.cp2406_a1.common.RainStats;
import textio.TextIO;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RainfallAnalyser {
    private static final int PRODUCT_CODE_IDX = 0;
    private static final int STATION_NUM_IDX = 1;
    private static final int YEAR_IDX = 2;
    private static final int MONTH_IDX = 3;
    private static final int DAY_IDX = 4;
    private static final int RAIN_IDX = 5;
    private static final int PERIOD_IDX = 6;
    private static final int QUALITY_IDX = 7;
    private static final String MISSING_VAL = null;
    private static final int MISSING_INT = -1;
    private static final int KEY_IDX = 0;
    private static final int TOTAL_IDX = 1;
    private static final int MIN_IDX = 2;
    private static final int MAX_IDX = 3;

    public static void Analyse(String inputPath, String outputPath) throws IOException {
        ArrayList<RainRecord> records = loadRainRecords(inputPath);
        TreeMap<String, RainStats> monthlyStats = collectStats(records);
        saveStats(monthlyStats, outputPath);
    }

    private static void saveStats(TreeMap<String, RainStats> monthlyStats, String path) throws IOException {
        File file = new File(path);
        file.delete();
        file.createNewFile();
        TextIO.writeFile(path);
        TextIO.putln("key,total,min,max");
        for(HashMap.Entry<String, RainStats> item : monthlyStats.entrySet()) {
            RainStats stats = item.getValue();
            TextIO.putln(stats.toString());
        }
        TextIO.writeStandardOutput();
    }

    private static TreeMap<String, RainStats> collectStats(ArrayList<RainRecord> records) {
        TreeMap<String, RainStats> map = new TreeMap<>();
        for(Iterator<RainRecord> iterator = records.iterator(); iterator.hasNext(); ) {
            RainRecord record = iterator.next();
            String key = "year" + record.getYear() + "_month" + String.format("%02d", record.getMonth());
            double rain = record.getRain();
            RainStats stats = null;
            if(map.containsKey(key)) {
                stats = map.get(key);
            } else {
                stats = new RainStats(key);
            }
            stats.setMaxRain(rain);
            stats.setMinRain(rain);
            stats.updateTotal(rain);

            map.put(key, stats);
        }

        return map;
    }

    private static ArrayList<RainRecord> loadRainRecords(String path) {
        ArrayList<RainRecord> records = new ArrayList<>();

        if(path.length() == 0) {
            System.out.println("Invalid path");
            return records;
        }

        TextIO.readFile(path);
        // Read the first line into nothing as this is the header row
        TextIO.getln();
        while(!TextIO.eof()) {
            String rowText = TextIO.getln();
            String[] rowContents = rowText.split(",", -1);

            try {
                String productCode = rowContents[PRODUCT_CODE_IDX];
                String stationNumber = rowContents[STATION_NUM_IDX];
                int year = Integer.parseInt(rowContents[YEAR_IDX]);
                int month = Integer.parseInt(rowContents[MONTH_IDX]);
                int day = Integer.parseInt(rowContents[DAY_IDX]);
                double rainfall = rowContents[RAIN_IDX] != "" ? Double.parseDouble(rowContents[5]) : 0.0;
                int days = rowContents[PERIOD_IDX] != "" ? Integer.parseInt(rowContents[6]) : 1;
                String quality = rowContents[QUALITY_IDX] != "" ? rowContents[7] : "N";

                RainRecord record = new RainRecord(productCode, stationNumber, year, month, day, rainfall, days, quality);
                records.add(record);
            } catch (Exception e) {
            }
        }

        return records;
    }

    public static TreeMap<String, RainStats> loadStats(String path) {
        TreeMap<String, RainStats> map = new TreeMap<>();

        if(path.length() == 0) {
            System.out.println("Invalid path");
            return map;
        }

        TextIO.readFile(path);
        // Read the first line into nothing as this is the header row
        TextIO.getln();
        while(!TextIO.eof()) {
            String rowText = TextIO.getln();
            String[] rowContents = rowText.split(",", -1);

            try {
                String key = rowContents[KEY_IDX];
                double total = Double.parseDouble(rowContents[TOTAL_IDX]);
                double min = Double.parseDouble(rowContents[MIN_IDX]);
                double max = Double.parseDouble(rowContents[MAX_IDX]);

                RainStats stats = new RainStats(key, total, min, max);
                map.put(key, stats);
            } catch (Exception e) {

            }
        }

        return map;
    }
}
