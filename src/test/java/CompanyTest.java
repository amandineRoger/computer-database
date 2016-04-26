
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.entities.Company;

public class CompanyTest {
    Company company;

    @Before
    public void setUp() {
        company = new Company();
    }

    @After
    public void tearDown() {
        company = null;
    }

    @Test
    public void testGetId() {
        // assertNull(company.getId());
        assertEquals(0, company.getId());
    }

    @Test
    public void testSetId() {
        company.setId(7788);
        assertEquals(7788, company.getId());
    }

    @Test
    public void testSetName() {
        company.setName("A company");
        assertEquals("A company", company.getName());
    }

    @Test
    public void testGetName() {
        company.setName("A company");
        assertEquals("A company", company.getName());
    }

}
