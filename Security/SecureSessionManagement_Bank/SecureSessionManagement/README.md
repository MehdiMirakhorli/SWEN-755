# SecureSessionManagement
The architecture breaker is in the method closeSession in the class MbSession. Testing was performed using Mockito and JUnit.
The project was built in netbeans using JSF and Glashfish. No database was used for this implementation.

The following users are created in the system:

username, password, role 

ivanC, 123, Cashier

ivanBM,123, Bank Manager

ivanAM,123,Account Manager

In a real life environment, the website must only be access through an HTTPS connection.


################################
Assignment 5: Secure session management testing

Leonardo Matos
Ivan Taktuk

1.identify and discuss four architecture breakers in the secure session tactic.
•	An Unauthenticated user is able to access a resource only authenticated users can access.
(Unauthenticated users shall not access any resources authenticated users can access.)

•	An authorized user is able to access resources she is not authorized to access.
(An authorized user may not access resources she is not authorized to access.)

•	An unauthenticated user enters an incorrect password for the specified user and it’s able to log in.
(An unauthenticated user must enter the password to log in as the desired user.)

•	A user logs out but the session is not destroyed
(Once a user logs out the session must be destroyed.)

2.Using a testing framework develop test cases fro two of these breakers
<See attached source code>

3. Refactor your code so you will have one failed test, and one successful test. In case of the failed test, your code should have the architecture breaker you discussed earlier.
<See attached source code>


