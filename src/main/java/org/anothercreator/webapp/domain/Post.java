package org.anothercreator.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Post {
    public Post() {

    }

    public Post(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.dateCreated = LocalDate.now();
        this.user = user;
    }

    // ========== RELATIONSHIPS ==========
    /*  MANY (posts) TO ONE (user)
        Multiple posts can belong to a singular user
        N:1 Relationship Uni-directional
        Post(Owned) --> User(Owner)
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank @Size(min = 2, max = 300)
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @FutureOrPresent
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    // ========== Getter / Setter ==========
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Post post = (Post) that;
        if ((this.ID == null || post.ID == null)) {
            return false;
        }
        return ID.equals(post.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Post{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
