package org.anothercreator.webapp.domain.relationships;

import org.anothercreator.webapp.domain.AbstractJPATest;
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
    }
}
