# Information under roles

## System Requirements

Our project was developed using Node.js. To run this project the user needs to
have [Node.js](https://nodejs.org/en/) and [npm](https://www.npmjs.com) installed
in their machine. The latest version of Node.js (v6.9.1) comes with npm included
(v3.10.). The user needs to have access to a console or terminal to  execute the
project and also have access to the internet. The user also need a web browser.

## How to run the project

1. Make sure to have all the files in the same directory.

2. Open a terminal windows and navigate to the project location.

3. Run the folloing command to install the dependencies. It will download all 
the packages or node modules necessary to run the project

    ```
    $ npm install
    ```

4. Run the following command. It will initialize the DataBase and create the 
users. It will create two users and set their passwords: [admin, 12345],
[super, 09876]. Use these users to login to the application. You should see a
confirmation message letting you know the table was created and the users were
added.

    ```
    $ node dbinit.js
    ```

5. Run the following command to start the web server. You should see a
confirmation message letting you know the web server is running and the port the
application is running on.

    ```
    $ node server.js
    ```

6. Open a web browser and navigate to localhost and the port specified on the
console. The default port is 7760. So you would go to 
[localhost](http://localhost:7760).

## How the project works

The application allows users to sign in and performs several security checks
regarding permissions and session. We have two users with different roles. The
admin has access to a page with information that only admins can read.
Admins also have access to the supervisor's page information. Admin are like the
super user. Supervisors have access only to the supervisor page information.
They do not have access to the admin info page. Both users have access to the
dashboard which is the home page. Users that are not registered or logged in
have not access to any of this pages. Unregistered users only have access to the
login page.

We have a total of five pages that display information. Four of them can be
accessed by navigating using the URL. The pages are:
* **Login:** Users can login to the application but only if the user is not logged
in.
* **Dashboard:** Users that are logged in have access to this page. Users that are
not logged in and try to access this page will be redirected to the login page.
* **Admin info:** Only admin user has access to this page. Supervisors will be
redirected to the dashboard. Users that are not logged in will be redirected to
the login page.
* **Supervisor info:** Both admin and super users have access to this page. Users
that are not logged in will be redirected to the login page.

The other page is the error page and it cannot be access through the URL. It is
only rendered when there's an error.

The application assigns a random generated sessionid when the user logs in. This
id is stored in the database and it is associated with the user that just logged
in. Once the user logs in, the username and the roleid are stored in session.
The application checks for the username, roleid, and sessionid every time the
user makes a requests that requires to be logged in and a role to be displayed.
In case a different sessionid is found for a user, compared with the one in the
database, the application removes the sessionid from the database and destroys
the session.

The application also keeps track of user activity by saving in session the time
of the last request. We set up a time of one minute. If the user is logged in
tries to access a page after a minute of inactivity, the session is destroyed
and the sessionid is removed from the database. The application also encrypts
the password before saving it.

## Contributors

* Jairo Pavel Veloz Vidal (@jpavelw)
* Arun Gopinathan (@arung90)

## License

The underlying source code used to format and display that content is licensed 
under the [MIT license](https://github.com/jpavelw/SWEN-756/blob/master/Security/Roles/LICENSE)