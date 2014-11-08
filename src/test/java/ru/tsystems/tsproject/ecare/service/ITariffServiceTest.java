package ru.tsystems.tsproject.ecare.service;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.entities.Tariff;

import java.util.List;

/**
 * Created by Selvin
 * on 08.11.2014.
 */
public class ITariffServiceTest {

    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource-test.xml"});
    private static ITariffService tariffService = (ITariffService) context.getBean("tariffService");

    private static Tariff TR1, TR2, TR3;

    @BeforeClass
    public static void beforeClass() {
        TR1 = new Tariff("tTariff1", 200);
        TR2 = new Tariff("tTariff2", 300);
        TR3 = new Tariff("tTariff3", 100);
    }

    @Before
    public void before() {
        TR2 = tariffService.saveOrUpdateTariff(TR2);
        TR3 = tariffService.saveOrUpdateTariff(TR3);
    }

    @Test
    @Transactional
    public void saveTariffTest() {
        Tariff tr = tariffService.saveOrUpdateTariff(TR1);
        Assert.assertEquals(TR1, tr);
    }

    @Test
    @Transactional
    public void loadTariffTest() {
        Assert.assertEquals(TR2, tariffService.loadTariff(TR2.getId()));
        Assert.assertEquals(TR3, tariffService.loadTariff(TR3.getId()));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void loadMissedTariffTest() {
        tariffService.loadTariff(-12l);
    }

    @Test
    @Transactional
    public void deleteTariffTest() {
        long tariffsNumber = tariffService.getNumberOfTariffs();
        tariffService.deleteTariff(TR3.getId());
        Assert.assertEquals(tariffsNumber - 1l, tariffService.getNumberOfTariffs());
        TR3 = tariffService.saveOrUpdateTariff(TR3);
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void deleteMissedTariff() {
        tariffService.deleteTariff(-12l);
    }

    @Test
    @Transactional
    public void getAllTariffsTest() {
        List<Tariff> loadedTariffs = tariffService.getAllTariffs();
        Tariff tariffs[] = {TR2, TR3};
        Assert.assertArrayEquals(tariffs, loadedTariffs.toArray());
    }

    @After
    public void after() {
        tariffService.deleteAllTariffs();
    }
}
