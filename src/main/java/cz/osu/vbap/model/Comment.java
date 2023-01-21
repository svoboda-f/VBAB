package cz.osu.vbap.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Text can't empty")
    private String text;
    private LocalDateTime postedOn;
    private boolean edited;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    public Comment() {}

    public Comment(String text, User user, Video video) {
        this.text = text;
        this.user = user;
        this.video = video;

        this.postedOn = LocalDateTime.now();
        this.edited = false;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public boolean isEdited() {
        return edited;
    }

    public User getUser() {
        return user;
    }

    public Video getVideo() {
        return video;
    }

    public void setText(String text) {
        this.text = text;
        this.edited = true;
    }
}
