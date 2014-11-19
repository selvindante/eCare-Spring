package ru.tsystems.tsproject.ecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsystems.tsproject.ecare.entities.Client;
import ru.tsystems.tsproject.ecare.entities.Contract;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.service.IContractService;

/**
 * Created by Selvin
 * on 04.10.2014.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource.xml"});
        IContractService contractService = (IContractService)context.getBean("contractService");
        IClientService clientService = (IClientService)context.getBean("clientService");
        Contract contract = contractService.loadContract(1766l);
        Client client = contract.getClient();
        boolean b = client.getContracts().remove(contract);
        //contractService.deleteContract(contract.getId());
        clientService.saveOrUpdateClient(client);



        /*IClientService clientService = (IClientService) context.getBean("clientService");
        System.out.println(clientService.saveOrUpdateClient(new Client("trololo", "lolo", new Date(0l), 12345678l, "Spb", "client", "123456", "client", 0)));*/
    }
}
