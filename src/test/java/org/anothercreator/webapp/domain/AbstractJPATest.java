package org.anothercreator.webapp.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class AbstractJPATest {
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;

    @BeforeAll
    public static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("forumWebAppTestPU");
    }

    @BeforeEach
    public void beforeEach() {
        em = emf.createEntityManager();
        tx = em.getTransaction();

        User createTestUser = new User("testUsername", "testemail@domain.com");

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        tx.commit();
    }

    @AfterEach
    public void afterEach() {
        User deleteTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();

        // Begin deletion sequence
        tx.begin();
        em.remove(deleteTestUser);
        tx.commit();
        em.close();
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }
}
