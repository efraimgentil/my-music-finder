package me.efraimgentil.mymusic.repository;

import me.efraimgentil.mymusic.model.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by efraimgentil on 06/02/17.
 */
public interface DirectoryRepository extends JpaRepository<Directory , Integer> {

    Directory findByNormalizedName(String normalizedName );

}
