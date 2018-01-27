package com.qa.test.automation.utils;

import java.io.BufferedWriter;

/**
 *
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ResourceLoader {

	 private ResourceLoader() {
	    }

	 /**
     *
     * @param resourceName
     * @return
     * @throws IOException
     */

	    public static InputStream loadResource(String resourceName) throws IOException {
	        ClassLoader classLoader = ResourceLoader.class.getClassLoader();

	        InputStream inputStream = null;

	        if (classLoader != null) {
	            inputStream = classLoader.getResourceAsStream(resourceName);
	        }

	        if (inputStream == null) {
	            classLoader = ClassLoader.getSystemClassLoader();
	            if (classLoader != null) {
	                inputStream = classLoader.getResourceAsStream(resourceName);
	            }
	        }

	        if (inputStream == null) {
	            File file = new File(resourceName);
	            if (file.exists()) {
	                inputStream = new FileInputStream(file);
	            }
	        }

	        return inputStream;
	    }//end loadResource

	    /**
	     *
	     * @param resourceName
	     * @return
	     * @throws IOException
	     */
	    public static Properties loadProperties(String resourceName) throws IOException {
	        Properties properties = null;
	        InputStream inputStream = null;
	        try {
	            inputStream = loadResource(resourceName);
	            if (inputStream != null) {
	                properties = new Properties();
	                properties.load(inputStream);
	            }
	        } finally {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        }
	        return properties;
	    }
	    

}
