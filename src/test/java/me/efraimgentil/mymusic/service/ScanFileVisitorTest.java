package me.efraimgentil.mymusic.service;

import me.efraimgentil.mymusic.model.Album;
import me.efraimgentil.mymusic.model.Artist;
import me.efraimgentil.mymusic.repository.ArtistRepository;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by efraimgentil on 08/02/17.
 */
public class ScanFileVisitorTest {

    ScanFileVisitor visitor;
    ArtistRepository artistRepository;
    Path path;

    @Before
    public void init (){
        visitor = new ScanFileVisitor();
        artistRepository = mock( ArtistRepository.class );
        visitor.artistRepository = artistRepository;
        path = mock(Path.class );
    }


    @Test
    public void returnTrueWhenThePathIsTheSameAsTheBaseFolder(){
        visitor.baseFolder =  "home/efraim/music";
        when( path.toString() ).thenReturn("home/efraim/music");

        assertThat( visitor.isBaseFolder( path ) ).isTrue();
    }

    @Test
    public void returnFalseWhenThePathIsDiferentThanTheBaseFolder(){
        visitor.baseFolder =  "home/efraim/music";
        when( path.toString() ).thenReturn("home/efraim/music/Haduken");

        assertThat( visitor.isBaseFolder( path ) ).isFalse();
    }

    @Test
    public void doesReturnThePathStringWithoutTheBaseFolder(){
        visitor.baseFolder =  "home/efraim/music";
        when( path.toString() ).thenReturn("home/efraim/music/Haduken");

        String result = visitor.pathStringWithoutBaseFolder(path);

        assertThat( result ).isEqualTo( "Haduken" );
    }

    @Test
    public void doesReturnTheAllTHePathStringWithoutTheBaseFolder(){
        visitor.baseFolder =  "home/efraim/music";
        when( path.toString() ).thenReturn("home/efraim/music/Haduken/Haha/Lole");

        String result = visitor.pathStringWithoutBaseFolder(path);

        assertThat( result ).isEqualTo( "Haduken/Haha/Lole" );
    }

    @Test
    public void createAnArtistAndSetTheCurrentArtist(){
        String artistName = "Efras Barueu";
        assertThat(visitor.artists.keySet() ).hasSize( 0 );

        visitor.handleArtist(  artistName );

        assertThat(visitor.artists.keySet() ).hasSize( 1 );
        assertThat(visitor.currentArtist.getName()).isEqualTo( artistName );
        assertThat(visitor.currentArtist.getNormalizedName() ).isEqualTo( "efrasbarueu" );
    }

    @Test
    public void ifTheArtistAlreadyExistJustGetItFromTheMapAndSetAsCurrent(){
        String artistName = "Efras Barueu";
        Artist efrasbarueu = new Artist(artistName, "efrasbarueu");
        visitor.artists.put("efrasbarueu" , efrasbarueu );

        visitor.handleArtist(  artistName );

        assertThat(visitor.currentArtist ).isSameAs( efrasbarueu );
    }

    @Test
    public void ifTheArtistAlreadyExistsInTheDataBaseItWillBePutInTheMap(){
        String artistName = "Efras Barueu";
        Artist efrasbarueu = new Artist(artistName, "efrasbarueu");
        assertThat(visitor.artists.keySet() ).hasSize( 0 );
        when( artistRepository.findByNormalizedName( "efrasbarueu" ) ).thenReturn( efrasbarueu );

        visitor.handleArtist(  artistName );

        assertThat(visitor.currentArtist ).isSameAs( efrasbarueu );
    }

    @Test
    public void doesCallPrepareNoAlbumForTheNewArtist(){
        String artistName = "Efras Barueu";
        assertThat(visitor.artists.keySet() ).hasSize( 0 );
        visitor = spy(visitor);

        visitor.handleArtist(  artistName );

        verify( visitor , times(1) ).prepareNoAlbum( any(Artist.class ) );
    }

    @Test
    public void createANoAlbumIfTheArtistDoesntAlreadyHaveOne(){
        Artist artist = spy( new Artist("AhA" , "aha") );

        visitor.prepareNoAlbum( artist );

        verify( artist , times(1) ).addAlbum( any( Album.class ) );
        assertThat( visitor.currentAlbum ).isNotNull();
        assertThat( visitor.currentAlbum.getNormalizedName() ).isEqualTo( Album.NO_ALBUM );
    }

    @Test
    public void createAnAlbumIfItDoenstExistWithTheKeyNormalizedArtistNameAndAlbumNameAndAddToTheArtist(){
        Artist efrasbarueu = new Artist("Efras Barueu", "efrasbarueu");
        visitor.currentArtist = efrasbarueu;
        assertThat( visitor.currentArtist.getAlbums()  ).hasSize( 0 );
        assertThat( visitor.currentAlbum ).isNull();

        visitor.handleAlbum("Da haduken Ryu");

        assertThat( visitor.albums.get("efrasbarueu-dahadukenryu") ).isNotNull();
        assertThat(visitor.currentAlbum ).isNotNull();
        assertThat( visitor.currentAlbum.getName() ).isEqualTo("Da haduken Ryu");
        assertThat( visitor.currentAlbum.getNormalizedName() ).isEqualTo("dahadukenryu");
        assertThat( visitor.currentArtist.getAlbums()  ).hasSize( 1 );
    }

    @Test
    public void returnTheNameNormalizedWithoutSpacesAndInLowerCase(){
        String name = "Efraim haha keke lole";

        String result = visitor.normatizeName(name);

        assertThat( result ).doesNotContain( " " );
        assertThat( result ).isEqualTo("efraimhahakekelole");
    }

    @Test
    public void returnTheNameNormalizedReplacingEspecialCharToNormalCharacters(){
        String name = "çãoéà";

        String result = visitor.normatizeName(name);

        assertThat( result ).isEqualTo( "caoea" );
    }

}
