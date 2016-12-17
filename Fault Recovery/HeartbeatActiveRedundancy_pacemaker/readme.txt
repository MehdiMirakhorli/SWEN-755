CONTENTS OF THIS FILE
----------------------

 * QUALITY ATTRIBUTES and MORE
 * REQUIREMENTS and INSTALLATION
 * Setting Pacemaker Application 
 * Maintainers


QUALITY ATTRIBUTES and MORE
---------------------------

Name: Pacemaker

Domain: Health Care 

RQ1: The Pulse Generator shall be available 99% of the time.

RQ2: The Pulse Generator shall sense the lack of heartbeat and generate a counter pulse 99% of the time back to the heart (DAQ).

Environment: while the pacemaker listens to regular patient heartbeat.

Response: Detect and generate messages to determine if the heart stopped for a minimum of time (0.5 milliseconds), generate an artificial pulse (stimulus) to the heart (DAQ).  

Response measure: Failure detected within a time interval of 0.5 milliseconds and the pacemaker generates artificial pulse and continues to listen to regular “heart” pulses.


REQUIREMENTS and INSTALLATION
-----------------------------

- Python 3.4.3 
To download and install python go to:
https://www.python.org/downloads/

- PYRO4
To download and install PYRO4 go to:
https://pythonhosted.org/Pyro4/install.html


SETTING PACEMAKER APPLICATION
-----------------------------

1. Copy the application folder named HeartbeatProjectWithActiveRedundancy in your Desktop.

2. Open the command prompt.

3. Enter the HeartbeatProjectWithActiveRedundancy/

4. Type the following commands in any order in different consoles:
    prompt$ pyro4-ns
    prompt$ python3 pacemaker_active_node.py
    prompt$ python3 pacemaker_redundant_node.py
    prompt$ python3 heart_simulator/DAQ.py

Note: for the first time running the files, run the two pacemaker files first (pacemaker_active_node.py & pacemaker_redundant_node.py), and then you will be able to initialize the pacemakers, and the DAQ in any order.

MAINTAINERS
-----------

If there is any problem in running the application, 
Please don't hesitate to contact us: 
- Cesar Armando Perez Fernandez: cap7879@rit.edu
- Ibrahim Aldosari: ima9428@rit.edu
