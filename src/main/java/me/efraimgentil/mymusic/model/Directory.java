package me.efraimgentil.mymusic.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by efraimgentil on 06/02/17.
 */
@Entity
@Table( name = "direcotry" )
public class Directory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String normalizedName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Directory parent;

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", normalizedName='" + normalizedName + '\'' +
                ", parent=" + parent +
                '}';
    }

    public Directory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getNormalizedName() {
        return normalizedName;
    }

    public void setNormalizedName(String normalizedName) {
        this.normalizedName = normalizedName;
    }
}
