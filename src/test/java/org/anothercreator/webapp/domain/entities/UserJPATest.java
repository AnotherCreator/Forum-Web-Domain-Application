package org.anothercreator.webapp.domain.entities;

import org.anothercreator.webapp.domain.AbstractJPATest;
import org.anothercreator.webapp.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class UserJPATest extends AbstractJPATest {
    @Test
    public void createTest() {
        User createTestUser = new User("createTestUserName", "createtestemail@domain.com");

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(createTestUser.getID());

        // Find newly updated row
        User compareTestUser = em.find(User.class, createTestUser.getID());
        assertEquals(compareTestUser.getUserName(), createTestUser.getUserName());
        assertEquals(compareTestUser.getEmail(), createTestUser.getEmail());

        // Begin cleanup sequence
        tx.begin();
        em.remove(createTestUser);
        tx.commit();
    }

    @Test
    public void readTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        User readTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();
        assertEquals("testUsername", readTestUser.getUserName());
    }

    @Test
    public void updateTest() {  // Read for values created in AbstractJPATest.@BeforeEach
        User updateTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();

        // Begin update sequence
        tx.begin();
        updateTestUser.setEmail("newEmail@domain.com");
        updateTestUser.setDateCreated(LocalDate.of(3000, Month.JANUARY, 1));
        tx.commit();

        // Find newly updated row
        User compareTestUser = em.find(User.class, updateTestUser.getID());

        // Check if updated successfully
        assertEquals(updateTestUser.getEmail(), compareTestUser.getEmail());
        assertEquals(updateTestUser.getDateCreated(), compareTestUser.getDateCreated());
    }

    @Test
    public void deleteTest() {
        User deleteTestUser = new User("deleteTestUser", "deleteTestUser@domain.com");

        // Begin insertion sequence
        tx.begin();
        em.persist(deleteTestUser);
        tx.commit();

        // Make sure row was successfully inserted
        assertNotNull(deleteTestUser.getID());

        // Begin deletion sequence
        tx.begin();
        em.remove(deleteTestUser);
        tx.commit();

        // Attempt to read deleted object
        User deleteCheck = em.find(User.class, deleteTestUser.getID());
        assertNull(deleteCheck);
    }
}
