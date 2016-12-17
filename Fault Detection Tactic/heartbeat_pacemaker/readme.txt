CONTENTS OF THIS FILE
----------------------

 * QUALITY ATTRIBUTES and MORE
 * REQUIREMENTS and INSTALLATION
 * Setting Pacemaker Application 
 * Maintainers


QUALITY ATTRIBUTES and MORE
---------------------------

Name: PacemakerDomain: Health Care RQ1: The Pulse Generator shall be available 99% of the time.RQ2 (satisfied): The Pulse Generator shall sense the lack of heartbeat and generate a counter pulse 99% of the time back to the heart (DAQ).Environment: while the pacemaker listens to regular patient heartbeat.Response: Detect and generate messages to determine if the heart stopped for a minimum of time (0.5 milliseconds), generate an artificial pulse (stimulus) to the heart (DAQ).  Response measure: Failure detected within a time interval of 0.5 milliseconds and the pacemaker generates artificial pulse and continues to listen to regular “heart” pulses.


REQUIREMENTS and INSTALLATION
-----------------------------

- Python 3.4.3 
To download and install python go to:
https://www.python.org/downloads/

Setting Pacemaker Application
-----------------------------

1. Copy the application folder named heartbeat_project in your Desktop.

2. Open the command prompt.

3. Enter the heartbeat_project/

4. Type the command ‘python pacemaker.py’ without quotes and press enter. 

5. The output should be like this:
	…
	DAQ:pulse_simulator: *** New pulse generated! *** 2
	DAQ:pulse_simulator: *** New pulse generated! *** 1
	DAQ:pulse_simulator: *** New pulse generated! *** -1
	*** PulseGenerator:check_heart_signal Absent/Weak heartbeat ***
	DAQ:get_generated_pulse: *** New pulse received! *** 3
	DAQ:pulse_simulator: *** New pulse generated! *** 10
	DAQ:pulse_simulator: *** New pulse generated! *** 6
	…

In the example above, DAQ represents the source of the “message”. The line that starts with the 3 stars (***) has a source named PulseGenerator. To simulate the “normal” function of a heart random numbers (range -1-10) are generated. Values 0-10 represents the normal functionality of the heart, and -1 represents either a weak or an absent heartbeat. When -1 is received by the Pulse Generator, an artificially generated heartbeat is generated (0-10) and injected into the heart or in this case the DAQ.

Maintainers
-----------

If there is any problem in running the application, 
Please don't hesitate to contact us: 
- Cesar Armando Perez Fernandez: cap7879@rit.edu
- Ibrahim Aldosari: ima9428@rit.edu
