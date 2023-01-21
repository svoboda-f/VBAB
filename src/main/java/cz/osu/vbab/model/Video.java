package cz.osu.vbab.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotNull
    private String description;
    private long views;
    @NotBlank(message = "Source url can't be empty")
    private String sourceUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user", nullable = false)
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "video")
    private List<Comment> comments;
    @JsonIgnore
    @ManyToMany(mappedBy = "videos")
    private List<Playlist> playlists;

    public Video() {}

    public Video(String title, String description, String sourceUrl, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.sourceUrl = sourceUrl;

        this.views = 0;
        this.comments = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public long getViews() {
        return this.views;
    }

    public void addView() {
        this.views++;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public List<Playlist> getPlaylists() {
        return this.playlists;
    }

}
