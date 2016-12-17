# Cardiopulmonary bypass (CPB)

## System Requirements

Our project was developed using Java Standard Edition (JSE). To run this 
project the user needs to have at least Java Development Kit 7 (JDK7) 
installed in their machine. The user needs to have access to a console 
or terminal to execute the project.

## How to run the project

1. Make sure to have all the files in the same directory

2. Open a terminal windows and navigate to the project location

3. Type the following command to compile the application. If this step 
works properly you shall not see any message

    ```
    $ javac *.java
    ```

4. Type the following commands. Ignore the warning if it shows up in 
the terminal and leave this terminal window open

    ```
    $ rmic MonitoringModule
    $ rmiregistry 5000
    ```

5. Type the following command in a new terminal window

    ```
    $ java MonitoringModule
    ```

6. Type the following command in a new terminal window

    ```
    $ java BloodPump
    ```

7. Type the following command in a new terminal window 

    ```
    $ java BloodPumpR
    ```

8. Type the following command in a new terminal window

    ```
    $ java Oxygenator
    ```

9. Type the following command in a new terminal window 

    ```
    $ java OxygenatorR
    ```

## How the project works

We created separate processes for the blood pump and the oxygenator and for
their backups. The monitoring module class will print a message to let the user
know that it started running. After the first message you shall see a message
saying that the MonitoringModule is receiving the heart beats sent by the
BloodPump and Oxygenator. If any of these components (oxygenator and blood pump)
fails, you shall see a message in the MonitoringModule terminal window saying
that they are not beeping and that it switched to the backup.

Our failure generator generates a random number every time after a heart beat is
sent to the MonitoringModule, if one of those numbers is between a predefined
range it will stop one of those components. The monitoring module will notice
that the component stopped "beating", then it shall print a failure message on
the screen and it will switched to the backup. Both the BloodPump and Oxygenator
make use of the random failure generator, the backups do not make use of this.
However, if you terminate the terminal window of one of the backups and the main
component is down, the MonitoringModule will notice and it shall print a message
in the screen letting the user know that the main component and its backup are
both down.

Both BloodPump and Oxygenator shall print a message in their terminal windows
letting the user know that they are working properly. This message is printed
every time they send a heart beat. They also shall print a message when the
"fail", letting the user know that the component is going down.

## Contributors

* Jairo Pavel Veloz Vidal (@jpavelw)
* Arun Gopinathan (@arung90)

## License

The underlying source code used to format and display that content is licensed 
under the [MIT license](https://github.com/jpavelw/SWEN-756/blob/master/Fault%20Recovery/CPB/LICENSE)