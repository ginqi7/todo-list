/**
 * FileUtil.java -- Util for handling file.
 *
 * Author: Qiqi Jin
 * Created: Saturday, 29 June 2024.
 */

package com.qiqijin.todoList.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    
    public static void createFileIfNotExists(String fileName) {
	File file = new File(fileName);

        // Check if file exists.
        if (!file.exists()) {
            // Create directory unless it not exists
            File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try {
                // Create file.
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
    }
}
