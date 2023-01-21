package cz.osu.vbab.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import cz.osu.vbab.model.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Email can't be empty")
    private String email;
    @NotBlank(message = "Username can't be empty")
    private String username;
    @Length(min = 8, message = "Password has to be at least 8 characters long")
    private String password;
    private String firstName;
    private String lastName;
    @Past(message = "Date of birth has to be in the past")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<Video> uploadedVideos;
    @OneToMany(mappedBy = "user")
    private List<Playlist> playlists;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User() {
    }

    public User(String email, String username, String password, String firstName, String lastName,
            LocalDate dateOfBirth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;

        this.uploadedVideos = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Video> getUploadedVideos() {
        return uploadedVideos;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this.email, this.username, this.firstName, this.lastName, this.dateOfBirth, this.playlists,
                this.uploadedVideos, this.comments);

    }

}
