package org.anothercreator.webapp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class CommentThread {
    public CommentThread() {

    }

    public CommentThread(Post post, Comment comment, ThreadParticipants threadParticipants) {
        this.post = post;
        this.commentSet.add(comment);
        this.threadParticipantsSet.add(threadParticipants);
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

    /*  ONE (commentThread) TO MANY (threadParticipants)
        One comment thread can have multiple thread participants (users)
        1:N Relationship Bi-directional
        User(commentThread) --> ThreadParticpants(Owned) */
    @OneToMany(mappedBy = "commentThread", cascade = CascadeType.ALL)
    private List<ThreadParticipants> threadParticipantsSet = new ArrayList<>();

    /*  ONE (commentThread) TO MANY (comments)
            One comment thread can have multiple comments
            1:N Relationship Bi-directional
            User(commentThread) --> Comments(Owned) */
    @OneToMany(mappedBy = "commentThread", cascade = CascadeType.ALL)
    private List<Comment> commentSet = new ArrayList<>();

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

    public List<ThreadParticipants> getThreadParticipantsSet() {
        return threadParticipantsSet;
    }

    public List<Comment> getCommentSet() {
        return commentSet;
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
