package me.efraimgentil.mymusic.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by efraimgentil on 07/02/17.
 */
@Entity
@Table(name="album")
public class Album implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String normalizedName;

    @ManyToOne
    @JoinColumn(name="artist_id")
    private Artist artist;

    @Column(name="artist_id" , insertable = false , updatable = false)
    private Long artistId;

    @OneToMany(mappedBy = "album")
    private Set<Music> musics = new LinkedHashSet<>();

    public void addMusic(Music music) {
        music.setArtist( this.artist );
        music.setAlbum( this );
        musics.add( music );
    }

    public Album() {
    }

    public Album(String name, String normalizedName) {
        this.name = name;
        this.normalizedName = normalizedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;
        if (artistId != null ? !artistId.equals(album.artistId) : album.artistId != null) return false;
        if (id != null ? !id.equals(album.id) : album.id != null) return false;
        if (!normalizedName.equals(album.normalizedName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + normalizedName.hashCode();
        result = 31 * result + (artistId != null ? artistId.hashCode() : 0);
        return result;
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

    public Set<Music> getMusics() {
        return musics;
    }

    public void setMusics(Set<Music> musics) {
        this.musics = musics;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Long getArtistId() {
        return artistId;
    }


}
