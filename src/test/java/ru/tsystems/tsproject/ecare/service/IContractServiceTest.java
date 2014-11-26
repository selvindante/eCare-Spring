package ru.tsystems.tsproject.ecare.service;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.entities.Tariff;
import ru.tsystems.tsproject.ecare.util.Util;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Selvin
 * on 08.11.2014.
 */
public class IContractServiceTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource-test.xml"});
    private static IClientService clientService = (IClientService) context.getBean("clientService");
    private static IContractService contractService = (IContractService) context.getBean("contractService");
    private static ITariffService tariffService = (ITariffService) context.getBean("tariffService");

    private static Client CL1;
    private static Contract CN11, CN12;
    private static Tariff TR1;
    private static Option OP11, OP12, OP13, OP14, OP15;

    @BeforeClass
    public static void beforeClass() {
        Util.setClientService(clientService);

        CL1 = clientService.saveOrUpdateClient(new Client("Ivan", null, null, 9234132135l, "SPB", "ivanov@mail.ru", "password", "ROLE_USER", 1000));

        CN11 = new Contract(CL1, 12345643l, null, false, false);
        CN12 = new Contract(CL1, 89652345090l, null, false, false);

        TR1 = tariffService.saveOrUpdateTariff(new Tariff("tTariff1", 300));

        OP11 = new Option(TR1, "tOption11", 5, 120);

        TR1 = tariffService.saveOrUpdateTariff(TR1);
    }

    @Before
    public void before() {
        CN12 = contractService.saveOrUpdateContract(CN12);
    }

    @Test
    @Transactional
    public void saveContractTest() {
        Contract cn = contractService.saveOrUpdateContract(CN11);
        CL1.setAmount(1000);
        clientService.saveOrUpdateClient(CL1);
        CN11.setId(cn.getId());
        assertEquals(CN11, cn);
    }

    @Test
    @Transactional
    public void loadContractTest() {
        Assert.assertEquals(CN12, contractService.loadContract(CN12.getId()));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void loadMissedContractTest() {
        contractService.loadContract(-12l);
    }

    @Test
    @Transactional
    public void fondContractByNumberTest() {
        Assert.assertEquals(CN12, contractService.findContractByNumber(CN12.getNumber()));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void fondContractByNotExistingNumberTest() {
        contractService.findContractByNumber(-12l);
    }

    @Test
    @Transactional
    public void deleteContractTest() {
        long contractsNumber = contractService.getNumberOfContracts();
        CL1.getContracts().remove(CN12);
        clientService.saveOrUpdateClient(CL1);
        Assert.assertEquals(contractsNumber - 1l, contractService.getNumberOfContracts());
        CN12 = contractService.saveOrUpdateContract(CN12);
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void deleteMissedContractTest() {
        contractService.loadContract(-12l);
    }

    @Test
    @Transactional
    public void getAllContractsTest() {
        List<Contract> loadedContracts = contractService.getAllContracts();
        Contract contracts[] = {CN12};
        assertArrayEquals(contracts, loadedContracts.toArray());
    }

    @Test
    @Transactional
    public void getAllContractsForClientTest() {
        List<Contract> loadedContracts = contractService.getAllContractsForClient(CL1.getId());
        Contract contracts[] = {CN12};
        assertArrayEquals(contracts, loadedContracts.toArray());
    }

    @Test
    public void checkOnBlockingTest() {
        assertEquals(false, contractService.isBlockedByClient(CN11));
        assertEquals(false, contractService.isBlockedByClient(CN12));
        assertEquals(false, contractService.isBlockedByOperator(CN11));
        assertEquals(false, contractService.isBlockedByOperator(CN12));
    }

    @Test(expected = ECareException.class)
    public void unblockAlreadyUnblockedContractTest() {
        contractService.unblockByClient(CN12);
    }

    @Test
    public void enableOptionTest() {
        CN12 = contractService.enableOption(CN12, OP11);
        assertEquals(true, CN12.getOptions().contains(OP11));
        CN12 = contractService.disableOption(CN12, OP11);
        assertEquals(0, CN12.getOptions().size());
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void enableOptionTwiceTest() {
        CN12 = contractService.enableOption(CN12, OP11);
        try{
            CN12 = contractService.enableOption(CN12, OP11);
        } catch (ECareException ecx) {
            CN12 = contractService.disableOption(CN12, OP11);
            throw ecx;
        }
    }

    @AfterClass
    public static void afterClass() {
        clientService.deleteAllClients();
        tariffService.deleteAllTariffs();
    }
}
