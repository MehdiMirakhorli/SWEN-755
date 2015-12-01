+------------------------------------------------------------+
| NOTICE: Check the Documentation.pdf for more explanations. |
+------------------------------------------------------------+


ENVIRONMENT REQUIREMENTS
=======================
- JDK/JRE 1.7+
- JAVA_HOME variable properly configured

HOW TO RUN/TEST APPLICATION (WINDOWS-ONLY)
=======================
- Execute the start.bat (compile classes into .jar files in the bin folder)
	- it will open two windows: the Client UI and a Server UI that shows the outputs of the monitor process,  the active judge process and the passive one.
	- provide some values to the client (in such a way that you were submitting a source code file to the judge) and wait for the response. At this point you could either get a response or an injected failure can happen.

OUTPUTS SHOWN
=======================
- Monitor: 
	- It prints an initialization success message
	- It prints the time that the health check is done by the receiver
	- It prints a detection of failure if Sender stops sending messages
		--> NOTICE: You should wait about 15 seconds after the crash happens, since the checkingInterval was defined to 15 seconds.
- Judge:
	- Show its current state (if it is compiling a code, executing, verifying, etc)
	- If a random failure happens, it prints the exception stack trace and it will closes itself
- Client:
	- Shows the feedback from the server in the text area 


HOW TO RUN/TEST APPLICATION USING COMMANDS (FOR OTHER OS's RATHER THAN WINDOWS)
=======================
+ Compilation Commands
	- Compiling common classes for IPC protocols
	javac -d bin src/edu/rit/swen755/communication/*.java 
	jar cf bin/communication.jar -C bin edu/rit/swen755/communication
	
	- Compiling client code
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/client/*.java 
	jar cf bin/client.jar -C bin edu/rit/swen755/client 

	- Compiling judge code (server)
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/judge/*.java 
	jar cf bin/judge.jar -C bin edu/rit/swen755/judge

	- Compiling fault monitor code 
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/faultmonitor/*.java 
	jar cf bin/faultmonitor.jar -C bin edu/rit/swen755/faultmonitor

+ Execution Commands (should be done in the order below, since the Monitor should be able to bind the receiver into the RMI Registry before the Judge attempts to retrieve it)
	start javaw -cp bin/communication.jar;bin/client.jar edu/rit/swen755/client/MainFrame
	start javaw -cp bin/communication.jar;bin/faultmonitor.jar;lib/sqlite-jdbc-3.8.11.2.jar edu/rit/swen755/faultmonitor/MonitorMain
