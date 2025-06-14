package com.example.freview.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "shared_list_media",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private List<Media> mediaList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "shared_list_user",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "sharedCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public SharedCollection(Long id, String name, List<Media> mediaList) {
        this.id = id;
        this.name = name;
        this.mediaList = mediaList;
    }


    public void addMedia(Media savedMedia) {
        mediaList.add(savedMedia);
    }
}
