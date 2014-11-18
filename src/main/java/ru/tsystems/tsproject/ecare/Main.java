package ru.tsystems.tsproject.ecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsystems.tsproject.ecare.entities.Option;
import ru.tsystems.tsproject.ecare.service.IOptionService;

import java.util.List;

/**
 * Created by Selvin
 * on 04.10.2014.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource.xml"});
        IOptionService optionService = (IOptionService)context.getBean("optionService");
        List<Option> options = optionService.getAllOptionsForTariff(2047l);
        Option option = optionService.loadOption(7131l);


        /*IClientService clientService = (IClientService) context.getBean("clientService");
        System.out.println(clientService.saveOrUpdateClient(new Client("trololo", "lolo", new Date(0l), 12345678l, "Spb", "client", "123456", "client", 0)));*/
    }
}
