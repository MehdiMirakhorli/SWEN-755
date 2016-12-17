# DAQ:
#   Component in the architecture
#   that simulated heart pulses.

from time import sleep
from random import randint


class DAQ:
    """
        Simulates a heart.
    """

    @staticmethod
    def pulse_simulator(heartbeat_queue):
        """
            Generates actual heart pulses.
        """
        while True:
            heartbeat = randint(-1, 10)

            if heartbeat == 5:
                sleep(0.5 * randint(20, 40)) # sleep for ten or twenty seconds

            else:
                print("DAQ:pulse_simulator: *** New pulse generated! ***", heartbeat)

                heartbeat_queue.put(heartbeat)

                sleep(0.5)

    # end pulse_simulator

    @staticmethod
    def get_generated_pulse(pulse_queue):
        """
            Receptor of an artificially generated heart pulse.
        """
        while True:
            pulse_queue_genValue = pulse_queue.get()

            if pulse_queue_genValue is not None:
                print("DAQ:get_generated_pulse: *** New pulse received! ***",
                      pulse_queue_genValue)

    # end get_generated_pulse

# end Heart
