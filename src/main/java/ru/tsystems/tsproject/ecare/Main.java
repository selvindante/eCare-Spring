package ru.tsystems.tsproject.ecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsystems.tsproject.ecare.service.IClientService;
import ru.tsystems.tsproject.ecare.util.Util;

/**
 * Created by Selvin
 * on 04.10.2014.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource.xml"});
        IClientService clientService = (IClientService)context.getBean("clientService");
        /*Client c1 = clientService.findClientByNumber(9210001122l);
        Client c2 = clientService.findClientByNumber(9210001123l);*/
        long num1 = Util.checkNumberOnExisting("9210001122");
        long num2 = Util.checkNumberOnExisting("9210001123");
        //boolean b = clientService.existLogin("client@mail.com");
        //long l = Util.checkNumberOnExisting("9210001122");


        /*try {
            long number = Util.checkLong("9210001122");
            try {
                clientService.findClientByNumber(number);
            } catch (ECareException ecx) {
                System.out.println("Not EXIST!!!");
            }
            ECareException ecx = new ECareException("Entered telephone number already exist.");
            throw ecx;
        } catch (NumberFormatException nfx) {
            ECareException ecx = new ECareException("Wrong format of entered telephone number.", nfx);
            throw ecx;
        }*/
    }
}
