package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.services.CompanyService;

public class CompanyServiceTest {

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
        service = CompanyService.INSTANCE;
    }

    @Test
    public void testGetInstance() {
        assertNull(service);
        initialize();
        assertNotNull(service);
    }

    /*
     * @Test
     * 
     * public void testGetCompanyList() { initialize(); page =
     * service.getCompanyList(); assertNotNull(page.getList());
     * assertTrue(page.getList().size() <= page.getLimit()); assertEquals(0,
     * page.getPageNumber());
     *
     * }
     */

    /*
     * @Test public void testGetNextPage() { initialize(); page =
     * service.getCompanyList();
     *
     * if (page.getNbPages() > 1) { company = page.getList().get(0); page =
     * service.getNextPage(); assertNotNull(page.getList());
     * assertTrue(page.getList().size() <= page.getLimit()); assertEquals(1,
     * page.getPageNumber()); another = page.getList().get(0);
     * assertNotEquals(company, another); }
     *
     * }
     *
     * @Test public void testGetPreviousPage() { initialize();
     * testGetNextPage(); page = service.getPreviousPage();
     * assertNotNull(page.getList()); assertEquals(0, page.getPageNumber()); if
     * (page.getNbPages() > 1) { assertNotEquals(company, another); another =
     * page.getList().get(0); assertEquals(company, another); } }
     */

    @Test
    public void testGetCompanyById() {
        initialize();
        company = service.getCompanyById(10);
        assertEquals(10, company.getId());
        // test on company name ?

    }

}
