import Pyro4

from pacemaker import FreeRTOS

# create background process and register DAQ
background_process_backup = Pyro4.Daemon()

uri02 = background_process_backup.register(FreeRTOS.BasePulseGenerator)

print("Running pacemaker 02:", str(uri02))  # for debugging purposes...

# create name server
name_server = Pyro4.locateNS()

# register URIs
name_server.register("PulseGenerator02", uri02)

# start server
print("Pacemaker waiting to sense heartbeats")
background_process_backup.requestLoop()


