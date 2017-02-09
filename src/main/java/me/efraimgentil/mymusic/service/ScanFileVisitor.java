package me.efraimgentil.mymusic.service;

import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.model.Directory;
import me.efraimgentil.mymusic.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by efraimgentil on 02/02/17.
 */
@Service
@Scope("prototype")
public class ScanFileVisitor extends SimpleFileVisitor<Path> {

    @Autowired
    @Qualifier("baseFolder") String baseFolder;

    @Autowired
    @Qualifier("folderLayout") String folderLayout;

    private Directory directory;

    protected Map<String,Artist> artists = new HashMap<>();
    protected Map<String,Album> albums = new HashMap<>();

    protected Artist currentArtist;
    protected Album currentAlbum;

    protected int ARTIST_IDX;
    protected int ALBUM_IDX;
    protected int MUSIC_IDX;


    @PostConstruct
    public void prepareVisitor(){
        String[] split = folderLayout.split("/");
        for( int i = 0 ;  i< split.length ; i++ ){
            if( "{artist}".equalsIgnoreCase( split[i] ) ){
                ARTIST_IDX = i;
            }
            if( "{album}".equalsIgnoreCase( split[i] ) ){
                ALBUM_IDX = i;
            }
        }
    }


    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if( !isBaseFolder(dir ) ){
            String path = pathStringWithoutBaseFolder(dir);
            String[] split = path.split(File.separator);
            if( split.length == ARTIST_IDX + 1 ){
                handleArtist(split[ARTIST_IDX]);
            }else if( split.length == ALBUM_IDX + 1 ){
                handleAlbum(split[ARTIST_IDX]);
            }else{
                throw new RuntimeException("Layout(" + dir.toString() + ") not supported");
            }
        }
        return FileVisitResult.CONTINUE; //super.preVisitDirectory(dir, attrs);
    }

    protected void handleAlbum(String albumName) {
        String normatizedName = normatizeName(albumName);
        String albumKey = currentArtist.getNormalizedName()+"-"+normatizedName;
        if( !albums.containsKey( albumKey ) ){
            Album album = new Album( albumName , normatizedName );
            currentArtist.addAlbum(album);
            albums.put( albumKey , album );
        }
        currentAlbum =  albums.get( albumKey );
    }

    protected void handleArtist(String artistName) {
        String normalizedName = normatizeName(artistName);
        if( !artists.containsKey( normalizedName ) ) {
            Artist artist = new Artist(artistName, normalizedName);
            artists.put(normalizedName, artist );
        }
        currentArtist = artists.get( normalizedName );
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String path = pathStringWithoutBaseFolder( file );
        String[] split = path.split(File.separator);
        String musicName = split[split.length - 1];
        String normalizedName = normatizeName( musicName );
        Music music = new Music(musicName, normalizedName, file.toString());
        currentAlbum.addMusic( music );
        return FileVisitResult.CONTINUE;
    }

    public String pathStringWithoutBaseFolder(Path path){
        String pathString = path.toString().replace(baseFolder, "").trim();
        if( pathString.startsWith(File.separator ) ) {
            pathString = pathString.replaceFirst( File.separator , "" );
        }
        return pathString;
    }

    public boolean isBaseFolder( Path path ){
        return path.toString().replace( baseFolder  , "").trim().isEmpty();
    }

    public String normatizeName( String dirName ){
        String nfdNormalizedString = Normalizer.normalize(dirName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("")
                .replaceAll("\\s" , "")
                .toLowerCase();
    }

}
