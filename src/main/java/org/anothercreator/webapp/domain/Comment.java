package org.anothercreator.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Comment {
    public Comment () {

    }

    public Comment (String body, User user) {
        this.body = body;
        this.user = user;
        this.dateCreated = LocalDate.now();
        this.dateEdited = LocalDate.now();
        this.num_likes = 0;
        this.num_dislikes = 0;
    }

    // ========== RELATIONSHIPS ==========
    /*  MANY (comments) TO ONE (user)
        Multiple comments can belong to a singular user
        N:1 Relationship Bi-Directional
        Comments(Owned) --> User(Owner)
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotBlank @Size(min = 1, max = 2000)
    @Column(name = "body", nullable = false)
    private String body;

    @PastOrPresent
    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @PastOrPresent
    @Column(name = "date_edited", nullable = false)
    private LocalDate dateEdited;

    @NotNull
    @Column(name = "num_likes", nullable = false)
    private Integer num_likes;

    @NotNull
    @Column(name = "num_dislikes", nullable = false)
    private Integer num_dislikes;

    // ========== Getter / Setter ==========
    public Long getID() {
        return ID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public Integer getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(Integer num_likes) {
        this.num_likes = num_likes;
    }

    public Integer getNum_dislikes() {
        return num_dislikes;
    }

    public void setNum_dislikes(Integer num_dislikes) {
        this.num_dislikes = num_dislikes;
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
        Comment comment = (Comment) that;

        // In a 'GeneratedValue' scenario, as a rule we can not check for persistence equality if either ID is null
        if ((this.ID == null || comment.ID == null)) {
            return false;
        }

        return ID.equals(comment.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
