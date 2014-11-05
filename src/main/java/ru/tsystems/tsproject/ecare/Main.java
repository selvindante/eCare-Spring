package ru.tsystems.tsproject.ecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.service.IClientService;

/**
 * Created by Selvin
 * on 04.10.2014.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource.xml"});
        /*ITariffService tariffService = (ITariffService)context.getBean("tariffService");
        Tariff tr = tariffService.loadTariff(2042l);
        System.out.println(tr);

        IContractService contractService = (IContractService)context.getBean("contractService");
        Contract cn = contractService.findContractByNumber(9080006754l);
        System.out.println(cn);

        contractService.setTariff(cn, tr);*/

        IClientService clientService = (IClientService) context.getBean("clientService");
        Client cl = clientService.loadClient(3316l);
        //System.out.println(cl);
        //cl.addAmount(100);
        clientService.deleteClient(3316l);
        //System.out.println(cl);
    }
}
