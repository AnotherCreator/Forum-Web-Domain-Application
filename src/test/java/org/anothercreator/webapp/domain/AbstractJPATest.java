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

    // Create these entities before each test case
    @BeforeEach
    public void beforeEach() {
        em = emf.createEntityManager();
        tx = em.getTransaction();

        User createTestUser = new User("testUsername", "testemail@domain.com");
        Post createTestPost1 = new Post("TestPost1","testDescription", createTestUser);
        Post createTestPost2 = new Post("TestPost2","testDescription", createTestUser);
        Post createTestPost3 = new Post("TestPost3","testDescription", createTestUser);

        // Begin insertion sequence
        tx.begin();
        em.persist(createTestUser);
        em.persist(createTestPost1);
        em.persist(createTestPost2);
        em.persist(createTestPost3);
        tx.commit();
    }

    // What to do with created entities after each test case
    @AfterEach
    public void afterEach() {
        User deleteTestUser = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = 'testUsername'", User.class).getSingleResult();

        Post deleteTestPost1 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost1'", Post.class).getSingleResult();
        Post deleteTestPost2 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost2'", Post.class).getSingleResult();
        Post deleteTestPost3 = em.createQuery(
                "SELECT p FROM Post p WHERE p.title = 'TestPost3'", Post.class).getSingleResult();

        // Begin deletion sequence
        tx.begin();
        em.remove(deleteTestUser);
        em.remove(deleteTestPost1);
        em.remove(deleteTestPost2);
        em.remove(deleteTestPost3);
        tx.commit();
        em.close();
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }
}
