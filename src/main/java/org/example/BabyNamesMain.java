package main.java.org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class BabyNamesMain {

  public BabyNamesMain() {
  }

  public static void main(String[] args) throws Exception {
    BabyNamesMain babyNamesMain = new BabyNamesMain();
    System.out.println(babyNamesMain.yearOfBestRank("Anton", "M"));
    System.out.println(babyNamesMain.averageRank("Anton", "M"));
    System.out.println(babyNamesMain.whatIsThisNameInYear(1999, 1880, "Anton", "M"));
    // Iterable<CSVRecord> records = babyNamesMain.readFileOfYear(1880);
    // Long total = babyNamesMain.getTotalOfPeopleBorn(records);
    // int rank = babyNamesMain.getRank(2010, "Marco", "M");
    // System.out.println(babyNamesMain.whatIsThisNameInYear(1994, 1885, "Marco", "M"));
    // System.out.println(babyNamesMain.yearOfBestRank("Marco", "M"));
    // System.out.println(babyNamesMain.averageRank("Marco", "M"));
    // System.out.println(babyNamesMain.getTotalBornRankedHigher(1994, "Marco", "M"));
    // System.out.println(babyNamesMain.getRank(1971, "Frank","M"));
    // System.out.println(babyNamesMain.getNameOfRank(450, 1982, "M"));
    // System.out.println(babyNamesMain.whatIsThisNameInYear(1974, 2014, "Owen", "M"));
    // System.out.println(babyNamesMain.yearOfBestRank("Mich", "M"));
    // System.out.println(babyNamesMain.averageRank("Robert", "M"));
    //System.out.println(babyNamesMain.getTotalBornRankedHigher(1990, "Drew", "M"));
  }

  public Iterable<CSVRecord> readFileOfYear(int year) throws IOException {
    if (year < 1880 || year > 2014) {
      System.out.println("Please enter a year between 1880 and 2014.");
    }
    String filePath = "res/us_babynames_by_year/yob" + year + ".csv";
    Reader in = new FileReader(filePath);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
    for (CSVRecord record : records) {
      //Name
      //System.out.print(record.get(0) + ", ");
      //Gender
      //System.out.print(record.get(1) + ", ");
      //Number of Babies
      //System.out.println(record.get(2));
    }
    Reader in2 = new FileReader(filePath);
    Iterable<CSVRecord> records2 = CSVFormat.RFC4180.parse(in2);
    //System.out.println(filePath + " loaded looks is " + records2);
    return records2;

  }

  public Long getTotalOfPeopleBorn(Iterable<CSVRecord> records) {
    Long total = 0L;
    Long totalBoys = 0L;
    Long totalGirls = 0L;
    Integer numberOfBoysNames = 0;
    Integer numberOfGirlsNames = 0;
    Integer numberOfAllNames = 0;
    for (CSVRecord record : records) {
      total += Long.parseLong(record.get(2));
      numberOfAllNames++;
      if (record.get(1).equals("M")) {
        totalBoys += Long.parseLong(record.get(2));
        numberOfBoysNames++;
      }
      if (record.get(1).equals("F")) {
        totalGirls += Long.parseLong(record.get(2));
        numberOfGirlsNames++;
      }
    }
    System.out.println("We have " + numberOfBoysNames + " different boys names.");
    System.out.println("We have " + numberOfGirlsNames + " different girls names.");
    System.out.println("We have " + numberOfAllNames + " different names.");
    System.out.println("Total number of Boys born is " + totalBoys);
    System.out.println("Total number of Girls born is " + totalGirls);
    System.out.println("Total number of Babies born is " + total);
    return total;
  }

  public int getRank(int year, String name, String gender) throws IOException {
    int rank = 0;
    String currentName;
    Iterable<CSVRecord> records = readFileOfYear(year);
    for (CSVRecord record : records) {
      if (record.get(1).equals(gender)) {
        rank++;
        currentName = record.get(0);
        if (currentName.equals(name)) {
          System.out.println("Rank of " + name + " as a " + gender + " name is " + rank + ".");
          return rank;
        }
      }
    }
    rank = -1;
    System.out.println("Rank of " + name + " as a " + gender + " name was not found.");
    return rank;
  }

  public String whatIsThisNameInYear(int actualYear, int newYear, String name, String gender)
      throws IOException {
    int rankInActualYear = getRank(actualYear, name, gender);
    Iterable<CSVRecord> recordsForNewYear = readFileOfYear(newYear);
    int rankCount = 0;
    String currentName = "";
    for (CSVRecord record : recordsForNewYear) {
      if (record.get(1).equals(gender)) {
        rankCount++;
        currentName = record.get(0);
        if (rankCount == rankInActualYear) {
          System.out.println(name + ", if you would have been born in " + newYear + " your name would have been " + currentName + ".");
          return currentName;
        }
      }
    }
    System.out.println(name + ", if you would have been born in " + newYear + " your name would have been " + currentName + ".");
    // returning the last name if there are not enough names in the new year
    return currentName;
  }

  public int yearOfBestRank(String name, String gender) throws IOException {
    File folder = new File("res/us_babynames_by_year");
    File[] listOfFiles = folder.listFiles();
    int bestRank = 2147483645;
    int currentRank;
    int year;
    int bestYear = 0;
    for (File file : listOfFiles) {
      String fileName = file.getName();
      fileName = fileName.replace("yob", "");
      fileName = fileName.replace(".csv", "");
      year = Integer.parseInt(fileName);
      Iterable<CSVRecord> recordsOfGivenYear = readFileOfYear(year);
      currentRank = getRank(year, name, gender);
      if (currentRank < bestRank && currentRank > 0) {
        bestRank = currentRank;
        bestYear = year;
      }
    }
    System.out.println(name + ", your name has ranked most used, that is " + bestRank + ", in the year " + bestYear + ".");
    return bestRank;
  }

  public double averageRank(String name, String gender) throws IOException {
    File folder = new File("res/us_babynames_by_year");
    File[] listOfFiles = folder.listFiles();
    int currentRank;
    int year;
    double total = 0.0;
    double count = 0.0;
    for (File file : listOfFiles) {
      String fileName = file.getName();
      fileName = fileName.replace("yob", "");
      fileName = fileName.replace(".csv", "");
      year = Integer.parseInt(fileName);
      Iterable<CSVRecord> recordsOfGivenYear = readFileOfYear(year);
      currentRank = getRank(year, name, gender);
      if (currentRank > 0) {
        count++;
        total += currentRank;
      }
    }
    if (count == 0) {
      System.out.println("Name never found.");
      return -1;
    }
    double average = total / count;
    System.out.println(name + ", the average rank of your name is: " + average + ".");
    return average;
  }

  public Long getTotalBornRankedHigher(int year, String name, String gender) throws IOException {
    Long total = 0L;
    int count = 0;
    Iterable<CSVRecord> records = readFileOfYear(year);
    int rankThisYear = getRank(year, name, gender);
    for (CSVRecord record : records) {
      if (record.get(1).equals(gender)) {
        count++;
        if (count >= rankThisYear) {
          break;
        }
        total += Long.parseLong(record.get(2));
      }
    }
    System.out.println("Total of people born with more common names than you is: " + total);
    return total;

  }

  public String getNameOfRank(int rank, int year, String gender) throws IOException {
    int count = 0;
    Iterable<CSVRecord> records = readFileOfYear(year);
    String name = "";
    for (CSVRecord record : records) {
      if (record.get(1).equals(gender)) {
        count++;
        if (count == rank) {
          name = record.get(0);
          System.out.println("The " + gender + " name of the year " + year + " for rank " + rank + " was " + name + ".");
          return name;
        }
      }
    }
    System.out.println("Not so many names in this year.");
    return name;
  }

}

