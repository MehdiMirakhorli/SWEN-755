# DAQ:
#   Component in the architecture
#   that simulated heart pulses.

import Pyro4

from time import sleep
from random import randint


class PulseGeneratorError(Exception):
    """
        Exception used in order to take action
        when a connection problem arises.
    """

    def __init__(self, origin):
        self.origin = origin

    def get_error_origin(self):
        return self.origin

# end PulseGeneratorError


class DAQ:
    """
        Simulates a heart.
    """

    heartbeat = None

    # connection to the pulse generator...
    pulse_generator_01 = None
    pulse_generator_02 = None

    @staticmethod
    def main_pulse_generator_connection():  # pushes heart pulses
        """
            Generates actual heart pulses.
        """

        pulse_generator_01_name = "PulseGenerator01"
        pulse_generator_02_name = "PulseGenerator02"

        while True:
            try:
                DAQ.heartbeat = randint(-1, 10)  # heart pulse...

                print("DAQ - New pulse generated:", DAQ.heartbeat)

                try:
                    # connect and send heartbeat to pacemaker 01
                    pulse_generator_result_01 = DAQ.connect_and_send_heartbeat_pg(True, pulse_generator_01_name)

                except Exception:
                    raise PulseGeneratorError("2") # connect to pacemaker 02 instead

                try:
                    # connect and send heartbeat to pacemaker 02
                    pulse_generator_result_02 = DAQ.connect_and_send_heartbeat_pg(False, pulse_generator_02_name)

                except Exception:
                    raise PulseGeneratorError("1")  # manage received data from pacemaker

                # if counter pulse is generated from any of the pacemakers...
                if pulse_generator_result_01 is not None and pulse_generator_result_02 is not None:

                    # decide which pacemaker response to use...
                    if randint(0, 1) == 1:
                        DAQ.send_generated_pulse(pulse_generator_01_name, pulse_generator_result_01)

                    else:
                        DAQ.send_generated_pulse(pulse_generator_02_name, pulse_generator_result_02)

            except PulseGeneratorError as error:
                if error.get_error_origin() == "1":
                    if pulse_generator_result_01 is not None:
                        DAQ.send_generated_pulse(pulse_generator_01_name, pulse_generator_result_01)

                else:
                    try:
                        pulse_generator_result_02 = DAQ.connect_and_send_heartbeat_pg(False, pulse_generator_02_name)

                        if pulse_generator_result_02 is not None:
                            DAQ.send_generated_pulse(pulse_generator_02_name, pulse_generator_result_02)
                    except:
                        print("*** No pacemaker active ***")

            sleep(0.5)

    # end main_pulse_generator_connection

    @staticmethod
    def connect_and_send_heartbeat_pg(pulse_generator_flag, pulse_generator_name):
        # connect to pacemaker
        if pulse_generator_flag:
            DAQ.pulse_generator_01 = Pyro4.Proxy(Pyro4.locateNS().lookup(pulse_generator_name))

            return DAQ.pulse_generator_01.pulse_receiver(DAQ.heartbeat, pulse_generator_name)

        else:
            DAQ.pulse_generator_02 = Pyro4.Proxy(Pyro4.locateNS().lookup(pulse_generator_name))

            return DAQ.pulse_generator_02.pulse_receiver(DAQ.heartbeat, pulse_generator_name)

    # end connect_and_send_heartbeat_pg

    @staticmethod
    def send_generated_pulse(generator, new_pulse):
        """
            Receptor of an artificially generated heart pulse.
        """
        print("*** DAQ (" + generator + ") - New pulse received: ***", new_pulse)

    # end get_generated_pulse

# end Heart
try:
    DAQ.main_pulse_generator_connection()

except:
    try:
        DAQ.pulse_generator_01.message("No heartbeat being received.")
    except:
        pass

    try:
        DAQ.pulse_generator_02.message("No heartbeat being received.")
    except:
        pass