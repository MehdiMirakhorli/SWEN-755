Requirements
=======================
- JDK/JRE 1.7+
- JAVA_HOME variable properly configured

How to Run/Test (If you have windows, there are 2 .bat scripts for convenience)
=======================
- Execute the compile.bat (compile classes into .jar files in the bin folder)
- Execute run.bat
	- it will open three windows: the Client UI, the monitor process and the judge process
	- provide some values to the client (in such a way that you were submitting a source code file to the judge) and wait for the response. At this point you could either get a response or an injected failure can happen.

Outputs Shown
=======================
- Monitor: 
	- It prints the time that a new heartbeat message arrived
	- It prints the time that the health check is done by the receiver
	- It prints a detection of failure if Sender stops sending messages
		--> NOTICE: You should wait about 15 seconds after the crash happens, since the checkingInterval was defined to 15 seconds.
- Judge:
	- Show its current state (if it is compiling a code, executing, verifying, etc)
	- If a random failure happens, it prints the exception stack trace and it will close when you press any button
- Client:
	- Shows the feedback from the server in the text area 



Running without .bat scripts (java Commands)
=======================
+ Compilation Commands
	- Classes for communication protocols
	javac -d bin src/edu/rit/swen755/communication/*.java 
	jar cf bin/communication.jar -C bin edu/rit/swen755/communication

	- Client code
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/client/*.java 
	jar cf bin/client.jar -C bin edu/rit/swen755/client 
	echo JAR for client generated & echo.


	- Compiling judge code (server)
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/judge/*.java 
	jar cf bin/judge.jar -C bin edu/rit/swen755/judge

	- Compiling fault monitor code 
	javac -cp bin/communication.jar -d bin src/edu/rit/swen755/faultmonitor/*.java 
	jar cf bin/faultmonitor.jar -C bin edu/rit/swen755/faultmonitor

+ Execution Commands (should be done in the order below, since the Monitor should be able to bind the receiver into the RMI Registry before the Judge attempts to retrieve it)
	start javaw -cp bin/communication.jar;bin/client.jar edu/rit/swen755/client/MainFrame
	start java -cp bin/communication.jar;bin/faultmonitor.jar edu/rit/swen755/faultmonitor/MonitorMain
	start java -cp bin/communication.jar;bin/judge.jar edu/rit/swen755/judge/JudgeMain