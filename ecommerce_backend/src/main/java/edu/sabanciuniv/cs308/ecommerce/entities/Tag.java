package edu.sabanciuniv.cs308.ecommerce.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag_table")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    @Column(name = "tag_name")
    private String name;

    private boolean tagUsed;

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

    public boolean isTagUsed() { return tagUsed; }

    public void setTagUsed(boolean tagUsed) { this.tagUsed = tagUsed; }
}
