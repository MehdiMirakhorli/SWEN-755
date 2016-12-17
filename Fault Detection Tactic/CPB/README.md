# Cardiopulmonary bypass (CPB)

## System Requirements

Our project was developed using Java Standard Edition (JSE). To run this 
project the user needs to have at least Java Development Kit 7 (JDK7) 
installed in their machine. The user needs to have access to a console 
or terminal to execute the project.

## How to run the project

1. Make sure to have all the files in the same directory

2. Open a terminal windows and navigate to the project location

3. Type the next command to compile the application. If this step works 
properly you shall not see any message

    ```
    $ javac CPB.java
    ```

4. Type the following command to run the application

    ```
    $ java CPB
    ```

## How the project works

We created two threads for the blood pump and the oxygenator. Both plus the
monitoring module class will print a message to let the user know that they
started running. After the first message you shall not see any other message.

If any of these components (oxygenator and blood pump) fails, you shall see a
message in the terminal saying that they are not beeping.

Our failure generator generates a random number every certain amount of seconds,
if one of those numbers is between a predefined range it will stop one of those
components. The monitoring module will notice that the component stopped
"beating", then will print a failure message on the screen.

## Contributors

* Jairo Pavel Veloz Vidal (@jpavelw)
* Arun Gopinathan (@arung90)

## License

The underlying source code used to format and display that content is licensed 
under the [MIT license](https://github.com/jpavelw/SWEN-756/blob/master/Fault%20Detection%20Tactic/CPB/LICENSE)