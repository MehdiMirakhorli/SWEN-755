#Heartbeat

A hearbeat implementation on a drone.

#Quality Attributes
- RQ1: The system shall be able to detect obstacles in order to prevent it from crashing.
- RQ2: The system shall be able to switch a backup obstacle detector when the main detector fails in order to prevent failure of the system.

#Features
- The drone sends its location to the controller
- The drone detects obstacles
- Switching to backup process when the main detector process is dead.

#Installation & Running
- Clone the repository
- Navigate to src directory
- javac *.java
- java RemoteController

######Open a new terminal navigate to src directory
- java MainObstacleDetector

######Open a new terminal navigate to src directory
- java DroneStarter

#Usage:

- Use the arrow keys to move the drone towards the yellow zone(Warning zone).
- The drone should stop moving when reaching the yellow zone.
- Use arrow keys to move the drone again.
- The MainObstacleDetectore shows it is crash time.

#Contributors:
- Rebaz Saleh
- Wajdi Aljeddani
