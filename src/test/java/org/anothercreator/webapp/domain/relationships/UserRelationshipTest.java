package org.anothercreator.webapp.domain.relationships;

import org.anothercreator.webapp.domain.AbstractJPATest;
import org.anothercreator.webapp.domain.Comment;
import org.anothercreator.webapp.domain.Post;
import org.anothercreator.webapp.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserRelationshipTest extends AbstractJPATest {
    @Test
    public void oneToMany_BiDirectional_UserToPost_Test() {
        /*  ONE (user) TO MANY (posts)
            One user can make multiple posts
            1:N Relationship Uni-directional
            User(Owner) --> Post(Owned)
        */

        User createTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();
        assertEquals("testUsername", createTestUser.getUserName());

        // Posts will be automatically related to the user but user will have to add it to their 'forumPostSet'
        Post createTestPost1 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost1'", Post.class).getSingleResult();
        assertEquals("TestPost1", createTestPost1.getTitle());
        Post createTestPost2 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost2'", Post.class).getSingleResult();
        assertEquals("TestPost2", createTestPost2.getTitle());
        Post createTestPost3 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost3'", Post.class).getSingleResult();
        assertEquals("TestPost3", createTestPost3.getTitle());

        // Add posts to user
        createTestUser.getForumPostSet().add(createTestPost1);
        createTestUser.getForumPostSet().add(createTestPost2);
        createTestUser.getForumPostSet().add(createTestPost3);

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        em.persist(createTestPost1);
        em.persist(createTestPost2);
        em.persist(createTestPost3);
        tx.commit();

        // Make sure rows were successfully inserted
        assertNotNull(createTestUser.getID());
        assertNotNull(createTestPost1.getID());
        assertNotNull(createTestPost2.getID());
        assertNotNull(createTestPost3.getID());

        // Check relationships
        assertEquals(3, createTestUser.getForumPostSet().size());  // User should have 3 posts
        assertEquals(createTestPost1.getUser(), createTestUser);
        assertEquals(createTestPost2.getUser(), createTestUser);
        assertEquals(createTestPost3.getUser(), createTestUser);

        assertEquals(createTestPost1.getUser().getID(), createTestUser.getID());
        assertEquals(createTestPost2.getUser().getID(), createTestUser.getID());
        assertEquals(createTestPost3.getUser().getID(), createTestUser.getID());
    }

    @Test
    public void oneToMany_BiDirectional_UserToComment_Test() {
        /*  ONE (user) TO MANY (comments)
            One user can make multiple comments
            1:N Relationship Bi-directional
            User(Owner) --> Comments(Owned)
        */

        User readTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();
        assertEquals("testUsername", readTestUser.getUserName());

        // Comments will be automatically related to the user but user will have to add it to their 'commentSet'
        Comment readTestComment1 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment1'", Comment.class).getSingleResult();
        assertEquals("TestComment1", readTestComment1.getBody());
        Comment readTestComment2 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment2'", Comment.class).getSingleResult();
        assertEquals("TestComment2", readTestComment2.getBody());
        Comment readTestComment3 = em.createQuery(
                "SELECT c FROM Comment c WHERE c.body = 'TestComment3'", Comment.class).getSingleResult();
        assertEquals("TestComment3", readTestComment3.getBody());

        // Add comments to user
        readTestUser.getCommentSet().add(readTestComment1);
        readTestUser.getCommentSet().add(readTestComment2);
        readTestUser.getCommentSet().add(readTestComment3);

        // Begin insertion sequence
        tx.begin();
        em.persist(readTestUser);
        em.persist(readTestComment1);
        em.persist(readTestComment2);
        em.persist(readTestComment3);
        tx.commit();

        // Make sure rows were successfully inserted
        assertNotNull(readTestUser.getID());
        assertNotNull(readTestComment1.getID());
        assertNotNull(readTestComment2.getID());
        assertNotNull(readTestComment3.getID());

        // Check relationships
        assertEquals(3, readTestUser.getCommentSet().size());  // User should have 3 comments
        assertEquals(readTestComment1.getUser(), readTestUser);
        assertEquals(readTestComment2.getUser(), readTestUser);
        assertEquals(readTestComment3.getUser(), readTestUser);

        assertEquals(readTestComment1.getUser().getID(), readTestUser.getID());
        assertEquals(readTestComment2.getUser().getID(), readTestUser.getID());
        assertEquals(readTestComment3.getUser().getID(), readTestUser.getID());
    }

    @Test
    public void oneToMany_BiDirectional_UserToThreadParticipant_Test() {
        /*  ONE (user) TO MANY (Threads)
            One user can partake in multiple threads
            1:N Relationship Bi-directional
            User(Owner) --> Thread Participants(Owned)
        */
    }
}
