# heartbeat

Follow the next steps to run the example:

```
$ git clone https://github.com/carlosdubus/heartbeat
$ cd heartbeat
$ javac *.java
```
Open three terminals.
In the first terminal, run the active and passive Stream Servers:
```sh
java PassiveRedundancyBootstrapper
```

In the second terminal, run the FaultMonitor:
```sh
java FaultMonitor
```

In the third terminal, run a Client chosing an unused local port as its argument:
```
java Client 4000
```
Note: You can run multiple clients using different local ports, each clieny in one terminal.

The client/s will display the stream received from the StreamServer. The StreamServer will crash between 5 and 10 seconds after a client is connected. When the server crashes, the FaultMonitor will detect the failure and write "Server is dead" to the console. Then, it will switch the passive StreamServer to active, the client will be able to reconnect and it will continue streaming from the last checkpoint.
