package utils;

import datatype.Menu;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.*;

public class XMLParseUtils {

    public static boolean updateMenu(String fileName, Menu menu){
        boolean success = false;
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = getOutputStream(menu,file);
        try {
            fos.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
    private static FileOutputStream getOutputStream(Menu menu, File file){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(menu!=null){
            Strategy strategy = new AnnotationStrategy();
            Serializer serializer = new Persister(strategy);
            try {
                serializer.write(menu,outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outputStream;
    }
}
