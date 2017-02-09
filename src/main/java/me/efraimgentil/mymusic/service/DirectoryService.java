package me.efraimgentil.mymusic.service;

import me.efraimgentil.mymusic.model.Directory;
import me.efraimgentil.mymusic.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by efraimgentil on 06/02/17.
 */
@Service
public class DirectoryService {

    @Autowired
    @Qualifier(value = "config")
    Properties appProperties;

    @Autowired
    DirectoryRepository repository;

    public String getBaseFolder(){
        return appProperties.getProperty("baseFolder");
    }



    public Directory findDirectoryByNormalizedName(String normalizedName) {
        return repository.findByNormalizedName( normalizedName );
    }
}
