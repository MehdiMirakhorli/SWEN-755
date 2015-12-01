# About the Reminders App
The purpose of this project is to practice implementing secure session management. It is built using Ruby on Rails. 
This app uses the popular Devise authentication framework to handle secure sessions.


## Dependencies
This program requires the following software to run:
Note: This project was created on a Windows machine. Dependencies may vary based on your operating system.

* Ruby 2.1.5
* Rails 4.1
* Bundler
* Git
* Sqlite
* Devkit
* TinyTDS

These dependencies can be easily installed using the [Rails Installer](http://railsinstaller.org/en)


## Start the App:
1. Clone or copy this repository to your local machine
2. Open a command line interface, and change directory so that you are at the reminders directory. Your working directory should now look something like:

	`\PATH\TO\reminders`

3. Install gem dependencies for the app by typing:

	```console
	bundle install
	```

4. Start the server by typing:

	```console
	rails server
	```

5. Access the app by opening up a web browse and navigating to [http://localhost:3000](http://localhost:3000)


## Try out the App:
By default, this app come with two pre-made users and a reminder belonging to each:


#### User 1:
	Email: user1@test.com
	Password: 12345678
	Has made a reminder called "Reminder 1"

#### User 2:
	Email: user2@test.com
	Password: 12345678
	Has made a reminder called "Reminder 2"


You can test the users by logging in with the above credentials.

To test the security of the system, perform the following checks: 

1. Confirm that each user can access their own reminders by attempting to edit the reminder belonging to themselves.

2. Confirm that a user cannot access another user's reminders by attempting to edit a reminder not belonging to the user. 
To do this, simply edit the URL and change the reminder ID to a reminder not belonging to that user.
For example, user2 has access to the reminder with an ID of 2, so visiting 
[http://localhost:3000/reminders/2/edit](http://localhost:3000/reminders/2/edit ) while logged in as user2
works. However, visiting [http://localhost:3000/reminders/1/edit](http://localhost:3000/reminders/1/edit ) would 
not work because reminder 1 does not belong to that user. The same holds true for user1.

3. Confirm that trying to access any resources while not logged in does not work.
For example, visiting [http://localhost:3000/reminders](http://localhost:3000/reminders) should not work.

