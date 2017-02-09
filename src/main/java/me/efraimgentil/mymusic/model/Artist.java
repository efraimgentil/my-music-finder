package me.efraimgentil.mymusic.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by efraimgentil on 07/02/17.
 */
@Entity
@Table(name = "artist" )
public class Artist implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String normalizedName;

    private String genero;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums = new LinkedHashSet<>();

    public void addAlbum(Album album ){
        album.setArtist( this );
        albums.add( album );
    }

    public Artist() {
    }

    public Artist(String name, String normalizedName) {
        this.name = name;
        this.normalizedName = normalizedName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
