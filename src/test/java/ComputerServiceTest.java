import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Test;

import com.excilys.cdb.control.Page;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.services.ComputerService;

public class ComputerServiceTest {
    ComputerService service;
    Page<Computer> page;
    Computer computer;
    Computer another;

    private void initialize() {
        service = ComputerService.getInstance();
    }

    @After
    public void tearDown() {
        service = null;
        page = null;
        computer = null;
        another = null;
    }

    @Test
    public void testGetInstance() {
        assertNull(service);
        initialize();
        assertNotNull(service);
    }

    @Test
    public void testGetComputerList() {
        initialize();
        page = service.getComputerList();
        assertNotNull(page.getList());
        assertEquals(10, page.getList().size());
        assertEquals(0, page.getPageNumber());
    }

    @Test
    public void testGetNextPage() {
        initialize();
        page = service.getComputerList();
        computer = page.getList().get(0);

        page = service.getNextPage();
        assertNotNull(page.getList());
        assertEquals(10, page.getList().size());
        assertEquals(1, page.getPageNumber());
        another = page.getList().get(0);
        assertNotEquals(computer, another);
    }

    @Test
    public void testGetPreviousPage() {
        initialize();
        testGetNextPage();
        page = service.getPreviousPage();
        assertNotNull(page.getList());
        assertEquals(0, page.getPageNumber());
        assertNotEquals(computer, another);
        another = page.getList().get(0);
        assertEquals(computer, another);
    }

    @Test
    public void testGetComputerDetail() {
        // In DB :
        // ID: 5, name: CM-5, introduced on: 1991-01-01, discontinued on: null,
        // provided by Thinking Machines (2)
        initialize();
        computer = service.getComputerDetail(-1);
        assertNull(computer);

        computer = service.getComputerDetail(5);
        assertNotNull(computer);
        assertEquals(5, computer.getId());
        assertEquals("CM-5", computer.getName());
        assertEquals(1991, computer.getIntroduced().getYear());
        assertEquals(1, computer.getIntroduced().getMonthValue());
        assertEquals(1, computer.getIntroduced().getDayOfMonth());
        assertNull(computer.getDiscontinued());
        assertEquals(2, computer.getCompany().getId());
        assertEquals("Thinking Machines", computer.getCompany().getName());
    }

    @Test
    public void testCreateComputer() {
        LocalDate intro = LocalDate.of(2016, 4, 4);
        LocalDate disco = LocalDate.of(2016, 9, 30);
        computer = new Computer.Builder("testComputer").introduced(intro)
                .discontinued(disco).build();
        // Impossible to test service because of create method depend of user
        // typing _ refactoring required !
    }

    @Test
    public void testUpdateComputer() {
        // Impossible to test service because of create method depend of user
        // typing_ refactoring required !
    }

    @Test
    public void testDeleteComputer() {
        // fail("Not yet implemented");
        // Impossible to test service because of create method depend of user
        // typing_ refactoring required !
    }

}
