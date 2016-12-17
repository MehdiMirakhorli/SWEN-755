CONTENTS OF THIS FILE
----------------------
 * REQUIREMENTS and INSTALLATION
 * SETTING THE APPLICATION
 * USING THE APPLICATION
 * ACCESSING THE ADMIN PAGE:
 * MAINTAINERS


REQUIREMENTS and INSTALLATION
-----------------------------
- Python 3.4.3 
To download and install python go to:
https://www.python.org/downloads/


- Django 1.8.3
to download django:
https://www.djangoproject.com/download/

- SQLite version 3.7.11 2012-03-20 11:35:50


SETTING THE APPLICATION
-----------------------------

1. Copy the application folder named “Secure Session Management” in your Desktop.

2. Open the command prompt. How to? go to: http://windows.microsoft.com/en-us/windows-vista/open-a-command-prompt-window

3. Change directory to “Secure Session Management” application by typing on the command prompt cd C:\Users\[whatever your username is]\Desktop\Secure Session Management

4. Type the command 've\Scripts\python.exe SSM_project\manage.py runserver' without quotes and press enter. The server runs.
 
5. Once the server is successfully running, open the application by typing:
	Internet Explorer: http://localhost:8000/SSM_app/authenticate/
	Google Chrome and Safari: 127.0.0.1:8000/SSM_app/authenticate/
	Firefox: localhost:8000/SSM_app/authenticate/

USING THE APPLICATION
-----------------------------

We have set up three users with three different roles which you can use to login into the application:

User#1:
Username: user1 
Password: something1234
Role: manager
Description: This user can login into the application but cannot get the secret info (the secret key is the task which the user can perform when logging in into the application). 

User#2:
Username: admin 
Password: something1234
Role: admin
Description: This user can login into the application and can request the secret info. 

User#3:
Username: user2 
Password: something1234
Role: regular_user
Description: This user not authenticated to login and can’t request the secret info. 

The time out for the application is 60 second, after that that the session will ends and the user has to login again. You can modify that by going to “settings.py” and modify “SESSION_TIMEOUT = 60” in line 47 to any other time you like.


ACCESSING THE ADMIN PAGE:
-----------------------------

You can login to the admin page by typing: http://127.0.0.1:8000/admin and then using the following credentials:
Username: root
Password: something123

By logging in into the admin page you will be able to register new users, assign roles or create new roles, and delete existing users. 


MAINTAINERS
-----------

If there is any problem in running the application, 
Please don't hesitate to contact us: 
- Cesar Armando Perez Fernandez: cap7879@rit.edu
- Ibrahim Aldosari: ima9428@rit.edu
