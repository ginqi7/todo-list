/**
 * Md5Util.java -- Util for Md5
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    public static String getMD5(String input) {
        // Create MessageDigest instance for MD5
        MessageDigest md;
	try {
	    md = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	    return "";
	}

        // Add input bytes to digest
        md.update(input.getBytes());

        // Get the hash's bytes
        byte[] bytes = md.digest();

        // Convert bytes to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        // Return the hexadecimal string
        return sb.toString();
    }
}
