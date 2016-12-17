import Pyro4

from pacemaker import FreeRTOS

# create background process and register DAQ
background_process_main = Pyro4.Daemon()

uri01 = background_process_main.register(FreeRTOS.BasePulseGenerator)

print("Running pacemaker 01:", str(uri01))  # for debugging purposes...

# create name server
name_server = Pyro4.locateNS()

# register URIs
name_server.register("PulseGenerator01", uri01)

# start server
print("Pacemaker waiting to sense heartbeats")
background_process_main.requestLoop()
