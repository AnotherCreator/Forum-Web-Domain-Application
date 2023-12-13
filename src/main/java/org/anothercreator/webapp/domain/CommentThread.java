package org.anothercreator.webapp.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CommentThread {
    public CommentThread() {

    }

    // ========== RELATIONSHIPS ==========
    /*  MANY (comment_threads) TO ONE (post)
        Multiple comment threads can belong to a singular post
        N:1 Relationship Bi-Directional
        Comment Thread(Owned) --> Post(Owner)
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    /*  ONE (user) TO MANY (posts)
        One user can make multiple posts
        1:N Relationship Bi-directional
        User(Owner) --> Post(Owned) */

    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    // ========== Getter / Setter ==========
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        CommentThread commentThread = (CommentThread) that;
        if ((this.ID == null || commentThread.ID == null)) {
            return false;
        }
        return ID.equals(commentThread.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
