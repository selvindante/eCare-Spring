eCare mobile operator. Author: Starostin Konstantin, 2014.

============

Welcome to the implementation of the T-Systems Java School student's project eCare, simulating the application for the mobile operator and its clients.
The application is written on the Spring Framework, used a local MySql database with JPA and Maven.

More information about project you can find in specification PDF file: https://github.com/selvindante/eCare-Spring/blob/master/eCare%20specification/ECare%20specification.pdf

MySql database scripts: https://github.com/selvindante/eCare-Spring/tree/master/database
(ecare-test only for jUnit tests in project)

============

Task description:

In the task required to write an application that simulates the operation of mobile network operator information system. Below is a more detailed description of subject area and technical requirements.
There are following kinds of entities:
-	Tariff (Title, Price, List of available options);
-	Option (Title, Price , Cost of connection);
-	Client (Name, Last name, Birth date, Passport data, Address, List of contracts, E-mail, Password);
-	Contract (Telephone number, Tariff, Connected options for tariff).

The application must provide the following functionality:

For clients:
-	Browse of the contract in a personal cabinet;
-	Browse of all available tariffs and changing of tariff;
-	Browse of all available options for tariff, adding new options, disable the existing ones;
-	Lock / Unlock of number (if number is locked, you cannot change tariff and options; if number is not locked by client, he can’t unlock it);

For employees:
-	Conclusion of a contract with a new client: the choice of a new telephone number with the tariff and options. The phone number should be unique.
-	Browse of all clients and contracts;
-	Lock / Unlock of clients contract;
-	Search of client by phone number;
-	Changing tariff, adding and removing of options to contract;
-	Adding new tariffs, removing of old;
-	Adding / removing option for tariff;
-	Option management: some options may not be compatible with each other or could be added to certain options, employee adds and removes these rules.

When performing operations with contracts before saving the changes on each page will be displayed basket, which displays the selected client's positions.

============

Have fun!
