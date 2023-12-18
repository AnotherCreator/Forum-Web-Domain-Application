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
public class Post {
    public Post() {

    }

    public Post(String title, String description, User user) {
        this.title = title;
        this.body = description;
        this.dateCreated = LocalDate.now();
        this.dateEdited = LocalDate.now();
        this.user = user;
    }

    // ========== RELATIONSHIPS ==========
    /*  ONE (post) TO MANY (threads)
        One post can host multiple comment threads inside
        1:N Relationship Bi-Directional
        Post(Owner) --> Comment Threads(Owned)
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentThread> commentThreadSet = new ArrayList<>();

    /*  MANY (posts) TO ONE (user)
        Multiple posts can belong to a singular user
        N:1 Relationship Bi-Directional
        Post(Owned) --> User(Owner)
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank @Size(min = 1, max = 500)
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank @Size(min = 1, max = 5000)
    @Column(name = "body", nullable = false)
    private String body;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return body;
    }

    public void setDescription(String description) {
        this.body = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(LocalDate dateEdited) {
        this.dateEdited = dateEdited;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CommentThread> getCommentThreadSet() {
        return commentThreadSet;
    }

    public void setCommentThreadSet(List<CommentThread> commentThreadSet) {
        this.commentThreadSet = commentThreadSet;
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
