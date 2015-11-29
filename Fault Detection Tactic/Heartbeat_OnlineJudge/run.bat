@echo off

start javaw -cp bin/communication.jar;bin/client.jar edu/rit/swen755/client/MainFrame
TIMEOUT /T 1
start java -cp bin/communication.jar;bin/faultmonitor.jar edu/rit/swen755/faultmonitor/MonitorMain
TIMEOUT /T 1
start java -cp bin/communication.jar;bin/judge.jar edu/rit/swen755/judge/JudgeMain

