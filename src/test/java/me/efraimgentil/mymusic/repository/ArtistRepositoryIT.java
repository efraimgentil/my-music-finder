package me.efraimgentil.mymusic.repository;

import me.efraimgentil.mymusic.model.Artist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by efraimgentil on 09/02/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase
public class ArtistRepositoryIT {

    @Autowired
    ArtistRepository artistRepository;

    @Test
    public void teste(){

        List<Artist> all = artistRepository.findAll();
        for (Artist artist : all) {
            System.out.println("artist = " + artist);
        }
    }

}
