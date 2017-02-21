package me.efraimgentil.mymusic.service;

import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.model.Music;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by efraimgentil on 02/02/17.
 */
@Service
public class ScanService  {

    @Autowired @Qualifier(value = "config")
    Properties appProperties;

    @Autowired ScanFileVisitor fileVisitor;
    @Autowired
    ArtistRepository artistRepository;

    @Transactional
    public void scan()  {
        System.out.println("SCANNING");
        try {
            Files.walkFileTree(Paths.get(appProperties.getProperty("baseFolder")), fileVisitor);

            artistRepository.save(fileVisitor.artists.values() );
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
