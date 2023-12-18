package org.anothercreator.webapp.domain.entities;

import org.anothercreator.webapp.domain.AbstractJPATest;
import org.anothercreator.webapp.domain.Comment;
import org.anothercreator.webapp.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentJPATest extends AbstractJPATest {
    @Test
    public void createTest() {
        User createTestUser = new User("createTestUserName", "createpostemail@domain.com");
        Comment createTestComment = new Comment("createTestComment", createTestUser);

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        em.persist(createTestComment);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(createTestUser.getID());
        assertNotNull(createTestComment.getID());

        // Find newly updated row
        Comment compareTestComment = em.find(Comment.class, createTestComment.getID());

        assertEquals(compareTestComment.getBody(), createTestComment.getBody());
        assertEquals(compareTestComment.getUser().getID(), createTestComment.getUser().getID());

        // Begin cleanup sequence
        tx.begin();
        em.remove(createTestUser);
        em.remove(createTestComment);
        tx.commit();
    }

    @Test
    public void readTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        User readTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();
        assertEquals("testUsername", readTestUser.getUserName());

        Comment readTestComment1 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment1'", Comment.class).getSingleResult();
        assertEquals("TestComment1", readTestComment1.getBody());
        Comment readTestComment2 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment2'", Comment.class).getSingleResult();
        assertEquals("TestComment2", readTestComment2.getBody());
        Comment readTestComment3 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment3'", Comment.class).getSingleResult();
        assertEquals("TestComment3", readTestComment3.getBody());
    }

    @Test
    public void updateTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        Comment updateTestComment1 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment1'", Comment.class).getSingleResult();
        assertEquals("TestComment1", updateTestComment1.getBody());
        Comment updateTestComment2 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment2'", Comment.class).getSingleResult();
        assertEquals("TestComment2", updateTestComment2.getBody());
        Comment updateTestComment3 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment3'", Comment.class).getSingleResult();
        assertEquals("TestComment3", updateTestComment3.getBody());

        // Begin update sequence
        tx.begin();
        updateTestComment1.setNum_likes(2);
        updateTestComment2.setNum_likes(2);
        updateTestComment3.setNum_likes(2);
        tx.commit();

        // Find newly updated row
        Comment compareTestComment1 = em.find(Comment.class, updateTestComment1.getID());
        Comment compareTestComment2 = em.find(Comment.class, updateTestComment2.getID());
        Comment compareTestComment3 = em.find(Comment.class, updateTestComment3.getID());

        // Check if updated successfully
        assertEquals(compareTestComment1.getNum_likes(), updateTestComment1.getNum_likes());
        assertEquals(compareTestComment2.getNum_likes(), updateTestComment2.getNum_likes());
        assertEquals(compareTestComment3.getNum_likes(), updateTestComment3.getNum_likes());
    }

    @Test
    public void deleteTest() {
        User deleteTestUser = new User("deleteTestUserName", "deletecommentemail@domain.com");
        Comment deleteTestComment = new Comment("deleteTestComment", deleteTestUser);

        // Begin insertion sequence
        tx.begin();
        em.persist(deleteTestUser);
        em.persist(deleteTestComment);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(deleteTestUser.getID());
        assertNotNull(deleteTestComment.getID());

        // Begin deletion sequence
        tx.begin();
        em.remove(deleteTestUser);
        em.remove(deleteTestComment);
        tx.commit();

        // Attempt to read deleted object
        User userDeleteCheck = em.find(User.class, deleteTestUser.getID());
        Comment commentDeleteCheck = em.find(Comment.class, deleteTestComment.getID());
        assertNull(userDeleteCheck);
        assertNull(commentDeleteCheck);
    }
}
