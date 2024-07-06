package com.qiqijin.todoList.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

/**
 * Util for handling CSV file.
 */
public class CsvUtil {
    
    /** Append item to csv file
     * @param fileName csv file name
     * @param row String[] a csv row
     */
    public static void append(String fileName, String[] row) {
	FileUtil.createFileIfNotExists(fileName);
	try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) {
            writer.writeNext(row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /** Load all csv file content.
     * @param fileName csv file name.
     * @return List<String[]> all rows
     */
    public static List<String[]> load(String fileName) {
	FileUtil.createFileIfNotExists(fileName);
	try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            return reader.readAll();
	} catch (IOException | CsvException e) {
            e.printStackTrace();
        }
	return null;
    }
}
