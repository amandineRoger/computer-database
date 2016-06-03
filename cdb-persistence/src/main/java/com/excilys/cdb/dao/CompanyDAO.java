package com.excilys.cdb.dao;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.mappers.CompanyMapper;

/**
 * Data Access Object for Company objects and requests Singleton pattern.
 *
 * @author Amandine Roger
 *
 */
@Repository("companyDAO")
@Scope("singleton")
public class CompanyDAO {

    // Queries
    String COMPANY_TABLE = "company";
    String ALL_COMPANIES = "SELECT id, name FROM " + COMPANY_TABLE;
    String ALL_COMPANIES_P = ALL_COMPANIES + " LIMIT ?, ?";
    String COMPANY_BY_ID = ALL_COMPANIES + " WHERE id = ?";
    String COUNT_COMPANIES = "SELECT COUNT(*) FROM " + COMPANY_TABLE;
    String DELETE_COMPANY = "DELETE FROM " + COMPANY_TABLE + " WHERE id = ?";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyDAO.class);
    private static final String TAG = "CompanyDAO says _ ";

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;
    private DataSource dataSource;

    protected EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Company> criteriaQuery;
    //private Root<Company> rootType;
    private Metamodel metamodel;
    private EntityType<Company> Company_;

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
        criteriaQuery = criteriaBuilder.createQuery(Company.class);
        // Define type of results
       // rootType = criteriaQuery.from(Company.class);
        //metamodel = entityManager.getMetamodel();
        //Company_ = metamodel.entity(Company.class);
    }

    /**
     * CompanyDAO constructor with datasource injection.
     *
     * @param dataSource
     *            autowired datasource
     */
    @Autowired
    public CompanyDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get companies from select * request.
     *
     * @return List of all companies
     */
    public List<Company> getAllCompanyList() {
        LOGGER.debug(TAG + "f_getAllCompanyList");
        LOGGER.error(TAG + " entity manager transaction joined : "
                + entityManager.isJoinedToTransaction());
        List<Company> list = null;
        criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> rootType = criteriaQuery.from(Company.class);
        // Set the query root
        criteriaQuery.select(rootType);

        TypedQuery<Company> typedQuery = entityManager
                .createQuery(criteriaQuery);
        list = typedQuery.getResultList();

        return list;
    }

    /**
     * Find a company by its id.
     *
     * @param id
     *            id of the wanted company
     * @return wanted company
     */
    public Company getCompanyById(long id) {
        LOGGER.debug(TAG + "f_getCompanyById");
        Company company = null;
        criteriaQuery = criteriaBuilder.createQuery(Company.class);
        Root<Company> rootType = criteriaQuery.from(Company.class);
        if (id != 0) {
            criteriaQuery.where(criteriaBuilder
                    .equal(rootType.get("id"), id));
            TypedQuery<Company> query = entityManager
                    .createQuery(criteriaQuery);
            company = query.getSingleResult();
        }
        return company;
    }

    /**
     * Get the number of companies in database.
     *
     * @return number of companies in database
     */
    public long getCount() {
        LOGGER.debug(TAG + "f_getCount");
        long count = 0;

        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        cq.select(criteriaBuilder.count(cq.from(Company.class)));
        count = entityManager.createQuery(cq).getSingleResult();

        return count;
    }

    /**
     * delete a company by its id and delete all computers from this company.
     * Transaction handling !
     *
     * @param id
     *            id of the company to delete
     */
    public void deleteCompany(long id) {
        LOGGER.debug(TAG + "f_deleteCompany");
        int count = 0;

        if (!entityManager.isJoinedToTransaction()) {
            entityManager.joinTransaction();
            System.out.println(TAG + " Join transaction");
        }
        Root<Company> rootType = criteriaQuery.from(Company.class);
        CriteriaDelete<Company> criteriaDelete = criteriaBuilder
                .createCriteriaDelete(Company.class);
        criteriaDelete.from(Company.class);
        criteriaDelete.where(criteriaBuilder
                .equal(rootType.get("id"), id));

        count = entityManager.createQuery(criteriaDelete).executeUpdate();

        if (count > 0) {
            System.out.println("Company " + id + " was successfully deleted");
            LOGGER.debug(TAG + "company " + id + " was successfully deleted");
        } else {
            System.out.println("Fail to delete company " + id);
            LOGGER.error(TAG + "Fail to delete company " + id);
        }
    }
}
