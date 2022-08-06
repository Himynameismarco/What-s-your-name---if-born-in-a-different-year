package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CSVprepWeekIII3 {

  public CSVprepWeekIII3() throws IOException {
  }

  public static void main(String[] args) throws Exception {
    CSVprepWeekIII3 csVprepWeekIII3 = new CSVprepWeekIII3();




    File folder = new File("src/main/java/src/2013");
    File[] listOfFiles = folder.listFiles();

    Double coldestSoFar = null;
    Double smallesHumiditySoFar = null;
    Double currentHumidity = null;
    Double currentTemperature = null;
    File fileWithSmallestTemp = null;
    File fileWithSmallestHum = null;

    for (File file : listOfFiles) {
      if (file.isFile()) {
        System.out.println(file.getName());
        Reader inII = new FileReader("src/main/java/src/2013/" + file.getName());
        Iterable<CSVRecord> recordsII = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(inII);
        //Reader inII2 = new FileReader("src/main/java/src/2013/" + file.getName());
        //Iterable<CSVRecord> recordsII2 = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(inII2);
        /*
        if (smallesHumiditySoFar == null) {
          smallesHumiditySoFar = csVprepWeekIII3.findLowestHumidityTemperaturInFile(recordsII2);
          fileWithSmallestHum = file;
        } else {
          currentHumidity = csVprepWeekIII3.findLowestHumidityTemperaturInFile(recordsII2);
          if (currentHumidity < smallesHumiditySoFar) {
            smallesHumiditySoFar = currentHumidity;
            fileWithSmallestHum = file;
          }
        }
        */

        if (coldestSoFar == null) {
          coldestSoFar = csVprepWeekIII3.findColdestTemperaturInFile(recordsII);
          fileWithSmallestTemp = file;
        } else {
          currentTemperature = csVprepWeekIII3.findColdestTemperaturInFile(recordsII);
          if (currentTemperature < coldestSoFar) {
            coldestSoFar = currentTemperature;
            fileWithSmallestTemp = file;
          }
        }
      }
    }
    System.out.println("Coldest all year: " + coldestSoFar + " in file: " + fileWithSmallestTemp.getName() + ".");
    //System.out.println("Smallest humindity all year: " + smallesHumiditySoFar + " in file: " + fileWithSmallestHum.getName() + ".");



    /*
    String filePath = "src/main/java/src/weather/2014/weather-2014-05-01.csv";
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    Double coldest = csVprepWeekIII3.findColdestTemperaturInFile(records);
     */

    /*
    String filePath = "src/main/java/src/weather/2014/weather-2014-07-22.csv";
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    Double coldest = csVprepWeekIII3.findLowestHumidityTemperaturInFile(records);
    */


    /*
    String filePath = "src/main/java/src/2013/weather-2013-08-10.csv";
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    Double avg = csVprepWeekIII3.findAverageTemperaturInFile(records);

     */



    String filePath = "src/main/java/src/2013/weather-2013-09-02.csv";
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    ArrayList<CSVRecord> above = csVprepWeekIII3.findRecordsWithHumidityAboveThreshhold(records, 80.0);
    Double avg = csVprepWeekIII3.findAverageTemperaturInFile(above);


  }

  public Iterable<CSVRecord> readFileOfYear(int year) {
    String filePath;
    Reader in = new FileReader(filePath);



  }

  public Double findColdestTemperaturInFile(Iterable<CSVRecord> records) {
    Double smallestSoFar = null;
    Double currentTemperature;
    CSVRecord largest = null;
    for (CSVRecord record : records) {
      if (smallestSoFar == null) {
        smallestSoFar = Double.parseDouble(record.get("TemperatureF"));
      } else {
        currentTemperature = Double.parseDouble(record.get("TemperatureF"));
        System.out.println("current: " + currentTemperature);
        System.out.println("smallest: " + smallestSoFar);
        System.out.println("current cmaller then smallest? " + (currentTemperature < smallestSoFar));

        if (currentTemperature < smallestSoFar) {
          smallestSoFar = currentTemperature;
          largest = record;
        }
      }
    }
    System.out.println("Smallest temperature is " + smallestSoFar + ".");
    return  smallestSoFar;
  }

  public Double findAverageTemperaturInFile(Iterable<CSVRecord> records) {
    Double total = null;
    Double count = 0.0;
    CSVRecord largest = null;
    for (CSVRecord record : records) {
      count += 1.0;
      if (total == null) {
        total = Double.parseDouble(record.get("TemperatureF"));
      } else {
        total += Double.parseDouble(record.get("TemperatureF"));
      }
    }
    Double avg = total / count;
    System.out.println("Average temperature is " + avg + ".");
    return  avg;
  }

  public Double findLowestHumidityInFile(Iterable<CSVRecord> records) {
    Double smallestSoFar = null;
    Double currentHumidity;
    CSVRecord smallest = null;
    for (CSVRecord record : records) {
      if (smallestSoFar == null) {
        smallestSoFar = Double.parseDouble(record.get("Humidity"));
        smallest = record;

      } else {
        try {
          currentHumidity = Double.parseDouble(record.get("Humidity"));
        } catch (Exception e) {
          e.printStackTrace();
          currentHumidity = smallestSoFar + 1;
        }
        if (currentHumidity < smallestSoFar) {
          smallestSoFar = currentHumidity;
          smallest = record;
        }
      }
    }
    System.out.println("Smallest humidity is " + smallestSoFar + " at " + smallest.get("DateUTC"));
    return  smallestSoFar;
  }

  public ArrayList<CSVRecord> findRecordsWithHumidityAboveThreshhold(Iterable<CSVRecord> records, Double threshold) {
    ArrayList<CSVRecord> recordsBelow = new ArrayList<>();
    Double currentHumidity;
    CSVRecord smallest = null;
    for (CSVRecord record : records) {
        try {
          currentHumidity = Double.parseDouble(record.get("Humidity"));
        } catch (Exception e) {
          e.printStackTrace();
          currentHumidity = -1.0;
        }
        if (currentHumidity >= threshold) {
          recordsBelow.add(record);
        }
    }
    return recordsBelow;
  }

}

