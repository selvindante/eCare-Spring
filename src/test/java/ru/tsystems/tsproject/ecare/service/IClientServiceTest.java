package ru.tsystems.tsproject.ecare.service;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.entities.Client;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Selvin
 * on 08.11.2014.
 */

public class IClientServiceTest {

    private ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource-test.xml"});
    private IClientService clientService = (IClientService) context.getBean("clientService");

    private static Client CL1, CL2, CL3;

    @BeforeClass
    public static void beforeClass() {
        CL1 = new Client("Ivan", null, null, 9234132135l, "SPB", "ivanov@mail.ru", "password", "client", 1000);
        CL2 = new Client("Semen", "Semenov", new Date(), 98274560923l, "Moscow", "semenov@mail.ru", "Qwerty123", "client", 1000);
        CL3 = new Client("Petr", "Petrov", new Date(), 9582450345l, "Sankt-Peterburg", "petrov@mail.ru", "petrov51spb", "client", 2000);
    }

    @Before
    public void before() {
        CL2 = clientService.saveOrUpdateClient(CL2);
        CL3 = clientService.saveOrUpdateClient(CL3);
    }

    @Test
    @Transactional
    public void saveClientTest() {
        Client cl = clientService.saveOrUpdateClient(CL1);
        Assert.assertEquals(CL1, cl);
    }

    @Test
    @Transactional
    public void loadClientTest() {
        Assert.assertEquals(CL2, clientService.loadClient(CL2.getId()));
        Assert.assertEquals(CL3, clientService.loadClient(CL3.getId()));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void loadMissedClientTest() {
        clientService.loadClient(-12l);
    }

    @Test
    @Transactional
    public void findClientTest() {
        Assert.assertEquals(CL2, clientService.findClient(CL2.getEmail(), CL2.getPassword()));
        Assert.assertEquals(CL3, clientService.findClient(CL3.getEmail(), CL3.getPassword()));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void findMissedClientTest() {
        clientService.findClient(CL1.getEmail(), CL1.getPassword());
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void findClientByNotExistedNumberTest() {
        clientService.findClientByNumber(-12345l);
    }

    @Test
    @Transactional
    public void deleteClientTest() {
        long clientsNumber = clientService.getNumberOfClients();
        clientService.deleteClient(CL2.getId());
        Assert.assertEquals(clientsNumber - 1l, clientService.getNumberOfClients());
        clientService.deleteClient(CL3.getId());
        Assert.assertEquals(clientsNumber - 2l, clientService.getNumberOfClients());
    }

    @Test(expected = ECareException.class)
    public void deleteMissedClientTest() {
        clientService.deleteClient(-12l);
    }

    @Test
    @Transactional
    public void getAllClients() {
        List<Client> loadedClients = clientService.getAllClients();
        Client clients[] = {CL2, CL3};
        assertArrayEquals(clients, loadedClients.toArray());
    }

    @Test
    @Transactional
    public void existLoginTest() {
        assertEquals(false, clientService.existLogin(CL1.getEmail()));
        assertEquals(true, clientService.existLogin(CL2.getEmail()));
        assertEquals(true, clientService.existLogin(CL3.getEmail()));
    }

    @After
    public void after() {
        clientService.deleteAllClients();
    }
}
