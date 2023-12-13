package org.anothercreator.webapp.domain;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Comment {
    public Comment () {

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

    @NotBlank @Size(min = 2, max = 2000)
    @Column(name = "body", nullable = false)
    private String body;

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
