package org.anothercreator.webapp.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ThreadParticipants {
    public ThreadParticipants () {

    }

    public  ThreadParticipants (User user) {
        this.user = user;
    }

    // ========== RELATIONSHIPS ==========
    /*  MANY (threads) TO ONE (user)
        Multiple threads can include the same user
        N:1 Relationship Bi-Directional
        ThreadParticipants(Owned) --> User(Owner)
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    /*  MANY (threadParticipants) TO ONE (commentThread)
            Multiple participants can belong to a singular comment thread
            N:1 Relationship Bi-Directional
            ThreadParticipants(Owned) --> commentThread(Owner)
         */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentThread_id")
    private CommentThread commentThread;

    // ========== Variables ==========
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    // ========== Getter / Setter ==========
    public Long getID() {
        return ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommentThread getCommentThread() {
        return commentThread;
    }

    public void setCommentThread(CommentThread commentThread) {
        this.commentThread = commentThread;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        ThreadParticipants threadParticipants = (ThreadParticipants) that;
        if ((this.ID == null || threadParticipants.ID == null)) {
            return false;
        }
        return ID.equals(threadParticipants.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}