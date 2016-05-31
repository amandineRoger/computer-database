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
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;

@Repository("computerDAO")
public class ComputerDAO implements ComputerDAOInterface {
    // QUERIES
    private static final String COMPUTER_TABLE = "computer";
    private final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN company"
            + " o ON c.company_id = o.id ";
    private final String ALL_COMPUTERS_P = ALL_COMPUTERS + " LIMIT ?, ?";
    private final String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
    /*
     * private final String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE +
     * " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
     */
    private final String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE
            + " SET name = ?, introduced = ?,"
            + "discontinued = ?, company_id = ? WHERE id = ?";
    private final String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE
            + " WHERE id = ?";
    private final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM "
            + COMPUTER_TABLE;
    private final String FIND_BY_NAME = ALL_COMPUTERS
            + " WHERE c.name LIKE ? ORDER BY %s %s LIMIT ?, ?";
    private final String COUNT_SEARCH_RESULT = "SELECT COUNT(*) FROM "
            + COMPUTER_TABLE + " WHERE name LIKE ?";
    public static final String DELETE_BY_COMPANY = "DELETE FROM "
            + COMPUTER_TABLE + " WHERE company_id = ?";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerDAO.class);
    private static final String TAG = "ComputerDAO says _ ";

    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    protected EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Computer> criteriaQuery;
    private Root<Computer> rootType;
    private Metamodel metamodel;
    private EntityType<Computer> Computer_;

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
        // Define type of results
        rootType = criteriaQuery.from(Computer.class);
        metamodel = entityManager.getMetamodel();
        Computer_ = metamodel.entity(Computer.class);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

        if (id != 0) {
            criteriaQuery.where(criteriaBuilder
                    .equal(rootType.get(Computer_.getId(long.class)), id));
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
        Object[] args = { computer.getName(),
                (computer.getIntroduced() == null ? null
                        : Date.valueOf(computer.getIntroduced())),
                (computer.getDiscontinued() == null ? null
                        : Date.valueOf(computer.getDiscontinued())),
                (computer.getCompany() == null ? null
                        : computer.getCompany().getId()),
                computer.getId() };
        try {
            jdbcTemplate.update(UPDATE_COMPUTER, args);
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in updateComputer "
                    + e.getMessage());
            throw new DAOException(e);
        }
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

        LOGGER.debug(TAG + " entity manager transaction joined : "
                + entityManager.isJoinedToTransaction() + " _ entity is open : "
                + entityManager.isOpen());

        count = entityManager.createQuery(criteriaDelete).executeUpdate();

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
    public int getCount() {
        LOGGER.debug(TAG + "f_getCount");
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject(COUNT_COMPUTERS, Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error(
                    TAG + "DataAccessException in getCount " + e.getMessage());
            throw new DAOException(e);
        }
        return count;
    }

    @Override
    public List<Computer> findByName(String search, int offset, int limit,
            String order, String asc) {
        LOGGER.debug(TAG + "f_findByName");
        List<Computer> computers = null;
        // prepare query
        String request = String.format(FIND_BY_NAME, order, asc);
        // Args for query building
        Object[] args = { "%" + search + "%", offset, limit };
        try {
            // query execution
            computers = jdbcTemplate.query(request, args, computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in findByName "
                    + e.getMessage());
            throw new DAOException(e);
        }
        return computers;
    }

    @Override
    public int getSearchCount(String search) {
        LOGGER.debug(TAG + "f_getSearchCount");
        int count = 0;
        Object[] args = { "%" + search + "%" };
        try {
            count = jdbcTemplate.queryForObject(COUNT_SEARCH_RESULT, args,
                    Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in getSearchCount "
                    + e.getMessage());
            throw new DAOException(e);
        }
        return count;
    }

    @Override
    public void deleteComputersByCompany(long companyId) {
        LOGGER.debug(TAG + "f_deleteComputersByCompany");
        Object[] args = { companyId };

        try {
            int count = jdbcTemplate.update(DELETE_BY_COMPANY, args);
            if (count > 0) {
                System.out.println(count + " computers deleted !");
                LOGGER.debug(TAG + count + " computers deleted !");
            } else {
                System.out.println("Fail to delete computers with company_id = "
                        + companyId);
                LOGGER.error(TAG + "Fail to delete computers with company_id = "
                        + companyId);
            }
        } catch (DataAccessException e) {
            LOGGER.error(
                    TAG + "DataAccessException in deleteComputersByCompany "
                            + e.getMessage());
            throw new DAOException(e);
        }
    }
}
