# Pacemaker:
#   Component developed to monitor and
#   regulate heart beats.

import Pyro4

from random import randint
from time import sleep


class EKGTransmitter:
    """
        Connects with the EKG Real Time display in
        order to show to the cardiologist the
        behavior of the heart.
    """
    pass


# end EKGTransmitter

class FreeRTOS:
    """
        This is the Real Time Operating System
        that run on top four tasks. These tasks are:

            1. The Pulse Generator
            2. DCM Agent
            3. EKG Logger
            4. idle task
    """

    class DCMAgent:
        """
            The DCM Agent interacts with the
            DCM Manager to enable a cardiologist to reprogram
            the pacemaker parameters or select a different
            pacing mode.
        """

        pass  # empty

    class EKGLogger:
        """
            Passes data to the DCM Manager
            located at the Device Controller
            Monitor.
        """
        pass  # empty

    class IdleTask:
        """
            No mention of this within the documentation.
        """

        pass  # empty

    @Pyro4.expose
    class BasePulseGenerator:
        @staticmethod
        def pulse_receiver(pulse, generator):
            print("Pulse Generator(" + generator + ") - New pulse received", pulse)

            if pulse == -1:  # if the pulse is bad
                new_pulse_generated = randint(0, 10)

                print("*** Artificial new pulse generated ***", new_pulse_generated)

                return new_pulse_generated  # send a new pulse back...

            return None

        @staticmethod
        def message(message):
            print(message)

# end FreeRTOS
