package me.efraimgentil.mymusic.service;

import me.efraimgentil.mymusic.model.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by efraimgentil on 02/02/17.
 */
@Service
@Scope("prototype")
public class ScanFileVisitor extends SimpleFileVisitor<Path> {

    @Autowired
    DirectoryService directoryService;

    private Directory directory;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if( !directoryService.isBaseFolder( dir.toString() ) ){
            directory = new Directory();
            directory.setNormalizedName( normatizeName( dir.toString().replace( directoryService.getBaseFolder(), "").trim() ));
        }
        return FileVisitResult.CONTINUE; //super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("FOUND FILE " +   file.getFileName() + " in " + directory );



        return super.visitFile(file, attrs);
    }

    public String normatizeName( String dirName ){
        String nfdNormalizedString = Normalizer.normalize(dirName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("\\s" , "")
                .toLowerCase();
    }

}
