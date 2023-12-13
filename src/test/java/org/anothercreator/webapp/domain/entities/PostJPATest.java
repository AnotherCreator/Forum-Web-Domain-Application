package org.anothercreator.webapp.domain.entities;

import org.anothercreator.webapp.domain.AbstractJPATest;
import org.anothercreator.webapp.domain.Post;
import org.anothercreator.webapp.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostJPATest extends AbstractJPATest {
    @Test
    public void createTest() {
        User createTestUser = new User("createTestUserName", "createtestemail@domain.com");
        Post createTestPost = new Post("createTestPost", "testDescription", createTestUser);

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        em.persist(createTestPost);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(createTestUser.getID());
        assertNotNull(createTestPost.getID());

        // Find newly updated row
        User compareTestUser = em.find(User.class, createTestUser.getID());
        Post compareTestPost = em.find(Post.class, createTestPost.getID());
        assertEquals(compareTestUser.getUserName(), createTestUser.getUserName());
        assertEquals(compareTestUser.getEmail(), createTestUser.getEmail());
        assertEquals(compareTestPost.getTitle(), createTestPost.getTitle());
        assertEquals(compareTestPost.getUser().getID(), createTestPost.getUser().getID());

        // Begin cleanup sequence
        tx.begin();
        em.remove(createTestUser);
        em.remove(createTestPost);
        tx.commit();
    }

    @Test
    public void readTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        User readTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();
        assertEquals("testUsername", readTestUser.getUserName());

        Post readTestPost1 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost1'", Post.class).getSingleResult();
        assertEquals("TestPost1", readTestPost1.getTitle());
        Post readTestPost2 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost2'", Post.class).getSingleResult();
        assertEquals("TestPost2", readTestPost2.getTitle());
        Post readTestPost3 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost3'", Post.class).getSingleResult();
        assertEquals("TestPost3", readTestPost3.getTitle());
    }

    @Test
    public void updateTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        Post updateTestPost1 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost1'", Post.class).getSingleResult();
        Post updateTestPost2 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost2'", Post.class).getSingleResult();
        Post updateTestPost3 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost3'", Post.class).getSingleResult();

        // Begin update sequence
        tx.begin();
        updateTestPost1.setDescription("1");
        updateTestPost2.setDescription("2");
        updateTestPost3.setDescription("3");
        tx.commit();

        // Find newly updated row
        Post compareTestPost1 = em.find(Post.class, updateTestPost1.getID());
        Post compareTestPost2 = em.find(Post.class, updateTestPost2.getID());
        Post compareTestPost3 = em.find(Post.class, updateTestPost3.getID());

        // Check if updated successfully
        assertEquals(compareTestPost1.getDescription(), updateTestPost1.getDescription());
        assertEquals(compareTestPost2.getDescription(), updateTestPost2.getDescription());
        assertEquals(compareTestPost3.getDescription(), updateTestPost3.getDescription());
    }

    @Test
    public void deleteTest() {
        User deleteTestUser = new User("deleteTestUserName", "createtestemail@domain.com");
        Post deleteTestPost = new Post("deleteTestPost", "testDescription", deleteTestUser);

        // Begin insertion sequence
        tx.begin();
        em.persist(deleteTestUser);
        em.persist(deleteTestPost);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(deleteTestUser.getID());
        assertNotNull(deleteTestPost.getID());

        // Begin deletion sequence
        tx.begin();
        em.remove(deleteTestUser);
        em.remove(deleteTestPost);
        tx.commit();

        // Attempt to read deleted object
        User userDeleteCheck = em.find(User.class, deleteTestUser.getID());
        Post postDeleteCheck = em.find(Post.class, deleteTestPost.getID());
        assertNull(userDeleteCheck);
        assertNull(postDeleteCheck);
    }
}
