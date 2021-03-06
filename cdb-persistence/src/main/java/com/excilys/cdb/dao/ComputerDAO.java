package com.excilys.cdb.dao;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Computer;

@Repository("computerDAO")
public class ComputerDAO implements ComputerDAOInterface {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerDAO.class);
    private static final String TAG = "ComputerDAO says _ ";

    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    protected EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Computer> criteriaQuery;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Computer.class);
    }

    private DataSource dataSource;

    /**
     * ComputerDAO constructor with datasource injection.
     *
     * @param dataSource
     *            autowired datasource
     */
    @Autowired
    public ComputerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Computer> getComputerList(int offset, int limit) {
        LOGGER.debug(TAG + "f_getComputerList");
        List<Computer> computers = null;
        Root<Computer> rootType = criteriaQuery.from(Computer.class);
        criteriaQuery.select(rootType);
        TypedQuery<Computer> typedQuery = entityManager
                .createQuery(criteriaQuery).setFirstResult(offset)
                .setMaxResults(limit);

        computers = typedQuery.getResultList();

        return computers;
    }

    @Override
    public Computer getComputerDetail(long id) {
        LOGGER.debug(TAG + "f_getComputerDetail");
        Computer computer = null;
        criteriaQuery = criteriaBuilder.createQuery(Computer.class);
        Root<Computer> rootType = criteriaQuery.from(Computer.class);
        if (id != 0) {
            criteriaQuery.where(criteriaBuilder.equal(rootType.get("id"), id));
            TypedQuery<Computer> query = entityManager
                    .createQuery(criteriaQuery);
            computer = query.getSingleResult();
        }

        return computer;
    }

    @Override
    public Computer createComputer(Computer computer) {
        LOGGER.debug(TAG + "f_createComputer");

        entityManager.persist(computer);
        entityManager.flush();
        entityManager.clear();

        return computer;
    }

    @Override
    public Computer updateComputer(Computer computer) {
        LOGGER.debug(TAG + "f_updateComputer");

        CriteriaUpdate<Computer> update = criteriaBuilder
                .createCriteriaUpdate(Computer.class);
        Root<Computer> root = update.from(Computer.class);

        update.set("name", computer.getName())
                .set("introduced",
                        (computer.getIntroduced() == null ? null
                                : Date.valueOf(computer.getIntroduced())))
                .set("discontinued", (computer.getDiscontinued() == null ? null
                        : Date.valueOf(computer.getDiscontinued())))
                .set("company", (computer.getCompany() == null ? null
                        : computer.getCompany()));
        update.where(criteriaBuilder.equal(root.get("id"), computer.getId()));

        entityManager.createQuery(update).executeUpdate();

        return computer;
    }

    @Override
    public Computer deleteComputer(long id) {
        LOGGER.debug(TAG + "f_deleteComputer");
        Computer computer = getComputerDetail(id);

        int count = 0;

        CriteriaDelete<Computer> criteriaDelete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> root = criteriaDelete.from(Computer.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        count = entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.flush();

        if (count > 0) {
            System.out.println("Computer " + id + " was successfully deleted");
            LOGGER.debug(TAG + "computer " + id + " was successfully deleted");
        } else {
            System.out.println("Fail to delete computer " + id);
            LOGGER.error(TAG + "Fail to delete computer " + id);
        }

        return computer;
    }

    @Override
    public long getCount() {
        LOGGER.debug(TAG + "f_getCount");

        long count = 0;

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

        Root<Computer> rootType = query.from(Computer.class);
        query.select(criteriaBuilder.count(rootType));
        count = entityManager.createQuery(query).getSingleResult();

        return count;
    }

    @Override
    public List<Computer> findByName(String search, int offset, int limit,
            String order, String asc) {
        LOGGER.debug(TAG + "f_findByName");
        List<Computer> computers = null;

        CriteriaQuery<Computer> query = criteriaBuilder
                .createQuery(Computer.class);
        Root<Computer> root = query.from(Computer.class);
        query.select(root);
        query.where(criteriaBuilder.like(root.get("name"), "%" + search + "%"));

        Order ordering;
        if (order != null && !order.isEmpty()) {
            if (asc.equals("DESC")) {
                ordering = criteriaBuilder.desc(root.get(order));
            } else {
                ordering = criteriaBuilder.asc(root.get(order));
            }

        } else {
            ordering = criteriaBuilder.asc(root.get("id"));
        }
        criteriaQuery.orderBy(ordering);

        TypedQuery<Computer> typedQuery = entityManager.createQuery(query)
                .setFirstResult(offset).setMaxResults(limit);

        computers = typedQuery.getResultList();

        return computers;
    }

    @Override
    public long getSearchCount(String search) {
        LOGGER.debug(TAG + "f_getSearchCount");
        long count = 0;

        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Computer> root = query.from(Computer.class);
        query.select(criteriaBuilder.count(root)).where(
                criteriaBuilder.like(root.get("name"), "%" + search + "%"));

        count = entityManager.createQuery(query).getSingleResult();

        return count;
    }

    @Override
    public void deleteComputersByCompany(long companyId) {
        LOGGER.debug(TAG + "f_deleteComputersByCompany");
        int count = 0;

        CriteriaDelete<Computer> criteriaDelete = criteriaBuilder
                .createCriteriaDelete(Computer.class);
        Root<Computer> root = criteriaDelete.from(Computer.class);
        criteriaDelete
                .where(criteriaBuilder.equal(root.get("company"), companyId));

        count = entityManager.createQuery(criteriaDelete).executeUpdate();

        if (count > 0) {
            System.out.println(count + " computers deleted !");
            LOGGER.debug(TAG + count + " computers deleted !");
        } else {
            System.out.println(
                    "Fail to delete computers with company_id = " + companyId);
            LOGGER.error(TAG + "Fail to delete computers with company_id = "
                    + companyId);
        }

    }
}
