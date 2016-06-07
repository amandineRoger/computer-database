package com.excilys.cdb.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;

@Repository("userDAO")
public class UserDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find the wanted user in database by his name.
     *
     * @param username
     *            the login of wanted user
     * @return wanted user if he exists, null if he doesn't
     */
    public User findByName(String username) {
        User user = null;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.where(builder.equal(root.get("login"), username));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);

        user = typedQuery.getSingleResult();

        return user;
    }
}
