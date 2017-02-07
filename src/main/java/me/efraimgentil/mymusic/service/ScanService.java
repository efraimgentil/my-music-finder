package me.efraimgentil.mymusic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by efraimgentil on 02/02/17.
 */
@Service
public class ScanService  {

    @Autowired @Qualifier(value = "config")
    Properties appProperties;

    @Autowired ScanFileVisitor fileVisitor;


    public void scan()  {
        System.out.println("SCANNING");
        try {
            Files.walkFileTree(Paths.get(appProperties.getProperty("baseFolder")) , fileVisitor );
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
