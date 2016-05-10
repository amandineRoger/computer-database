package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;

public class ComputerTest {
    Computer computer;

    @Before
    public void setUp() {
        computer = new Computer.Builder("test").build();
    }

    @After
    public void tearDown() {
        computer = null;
    }

    @Test
    public void testGetId() {
        assertEquals(0, computer.getId());
    }

    @Test
    public void testSetId() {
        long id = 25468;
        computer.setId(id);
        assertEquals(id, computer.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("test", computer.getName());
    }

    @Test
    public void testSetName() {
        computer.setName("other");
        assertEquals("other", computer.getName());
    }

    @Test
    public void testGetIntroduced() {
        LocalDate date = LocalDate.of(2016, 4, 4);
        computer.setIntroduced(date);
        assertEquals(2016, computer.getIntroduced().getYear());
        assertEquals(4, computer.getIntroduced().getMonthValue());
        assertEquals(4, computer.getIntroduced().getDayOfMonth());
    }

    @Test
    public void testSetIntroduced() {
        LocalDate date = LocalDate.of(2016, 4, 4);
        computer.setIntroduced(date);
        assertEquals(2016, computer.getIntroduced().getYear());
        assertEquals(4, computer.getIntroduced().getMonthValue());
        assertEquals(4, computer.getIntroduced().getDayOfMonth());
    }

    @Test
    public void testGetDiscontinued() {
        LocalDate date = LocalDate.of(2016, 4, 4);
        computer.setDiscontinued(date);
        assertEquals(2016, computer.getDiscontinued().getYear());
        assertEquals(4, computer.getDiscontinued().getMonthValue());
        assertEquals(4, computer.getDiscontinued().getDayOfMonth());
    }

    @Test
    public void testSetDiscontinued() {
        LocalDate date = LocalDate.of(2016, 4, 4);
        computer.setDiscontinued(date);
        assertEquals(2016, computer.getDiscontinued().getYear());
        assertEquals(4, computer.getDiscontinued().getMonthValue());
        assertEquals(4, computer.getDiscontinued().getDayOfMonth());
    }

    @Test
    public void testGetCompany() {
        Company company = new Company();
        company.setName("A company");
        company.setId(7788);
        computer.setCompany(company);

        assertEquals(company, computer.getCompany());
    }

    @Test
    public void testSetCompany() {
        Company company = new Company();
        company.setName("A company");
        company.setId(7788);
        computer.setCompany(company);

        assertEquals(company, computer.getCompany());
    }

}
