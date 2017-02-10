package me.efraimgentil.mymusic.repository;

import me.efraimgentil.mymusic.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by efraimgentil on 09/02/17.
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Artist findByNormalizedName( String normalizedName );

}
