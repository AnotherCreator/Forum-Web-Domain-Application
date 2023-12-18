package org.anothercreator.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    public User() {}

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        this.dateCreated = LocalDate.now();
        this.dateEdited = LocalDate.now();
    }

    // ========== RELATIONSHIPS ==========
    /*  ONE (user) TO MANY (posts)
        One user can make multiple posts
        1:N Relationship Bi-directional
        User(Owner) --> Post(Owned) */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> forumPostSet = new ArrayList<>();

    /*  ONE (user) TO MANY (comments)
        One user can make multiple comments
        1:N Relationship Bi-directional
        User(Owner) --> Comments(Owned) */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentSet = new ArrayList<>();

    /*  ONE (user) TO MANY (threads)
            One user can participate in multiple threads
            1:N Relationship Bi-directional
            User(Owner) --> ThreadParticpants(Owned) */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ThreadParticipants> threadParticipantsSet = new ArrayList<>();

    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank @Size(min = 2, max = 20)
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @PastOrPresent
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @PastOrPresent
    @Column(name = "date_edited", nullable = false)
    private LocalDate dateEdited;

    // ========== Getter / Setter ==========
    public Long getID() {
        return ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(LocalDate dateEdited) {
        this.dateEdited = dateEdited;
    }

    public List<Post> getForumPostSet() {
        return forumPostSet;
    }

    public List<Comment> getCommentSet() {
        return commentSet;
    }

    public List<ThreadParticipants> getThreadParticipantsSet() {
        return threadParticipantsSet;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;

        if (that == null || getClass() != that.getClass()) return false;
        User member = (User) that;

        // In a 'GeneratedValue' scenario, as a rule we can not check for persistence equality if either ID is null
        if ((this.ID == null || member.ID == null)) {
            return false;
        }

        return ID.equals(member.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
