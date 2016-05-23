package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.services.CompanyService;

@ContextConfiguration("/home/excilys/workspace/computer-database/src/main/webapp/WEB-INF/applicationContext.xml")
public class CompanyServiceTest {

    @Autowired
    @Qualifier("companyService")
    CompanyService service;
    Page<Company> page;
    Company company;
    Company another;

    @After
    public void tearDown() {
        service = null;
        page = null;
        company = null;
        another = null;
    }

    private void initialize() {
        // service = CompanyService.INSTANCE;
    }

    @Test
    public void testGetInstance() {
        assertNull(service);
        initialize();
        assertNotNull(service);
    }

    @Test
    public void testGetCompanyById() {
        initialize();
        company = service.getCompanyById(10);
        assertEquals(10, company.getId());
        // test on company name ?

    }

    /*
     * TODO tests: - getAllCompanies - getCount - deleteCompany
     */

}
