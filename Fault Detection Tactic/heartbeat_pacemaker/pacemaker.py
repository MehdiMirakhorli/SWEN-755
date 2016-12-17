# Pacemaker:
#   Component developed to monitor and
#   regulate heart beats.

from queue import Queue
from time import sleep
from random import randint
from threading import Thread
from heart_simulator.DAQ import DAQ


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

    class PulseGenerator:
        """
            Responsible for sensing and generating
            pulsing signals as needed to keep the patient's heart
            beating.
        """

        @staticmethod
        def check_heart_signal(heartbeat_queue, pulse_queue):
            counter = 0

            while True:
                if heartbeat_queue.empty():
                    counter += 1

                    if counter == 10:
                        print("No pulse received. Generating counter pulse.")
                        counter = 0
                        pulse_queue.put(randint(0, 10))
                else:
                    heartbeat = heartbeat_queue.get()

                    if heartbeat is None:
                        print("Something is happening here...")

                    if heartbeat == -1:  # error: lack or weak heartbeat.
                        print("*** PulseGenerator:check_heart_signal Absent/Weak heartbeat ***")
                        pulse_queue.put(randint(0, 10))

                    else:
                        pulse_queue.put(None)

                sleep(0.5)

    # end PulseGenerator

# end FreeRTOS

# queue to carry messages between threads.
heartbeat_queue = Queue()
pulse_queue = Queue()

# instances of the three main components (threads).
pulse_simulator_thread = Thread(target=DAQ.pulse_simulator,
                                args=[heartbeat_queue, ])

artificial_pulse_receiver_thread = Thread(target=DAQ.get_generated_pulse,
                                          args=[pulse_queue, ])

heart_pulse_checker_thread = Thread(target=FreeRTOS.PulseGenerator.check_heart_signal,
                                    args=[
                                        heartbeat_queue,
                                        pulse_queue,
                                    ])

# start threads.
pulse_simulator_thread.start()
artificial_pulse_receiver_thread.start()
heart_pulse_checker_thread.start()
