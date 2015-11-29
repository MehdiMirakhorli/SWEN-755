@echo off

REM Create necessary folders
if not exist "bin" mkdir "bin"

REM Compilation
echo Compiling
javac -cp lib/jsoup-1.8.3.jar -d bin src/edu/rit/swen755/pool/*.java 
jar cf bin/crawler.jar -C bin edu/rit/swen755/pool 

echo DONE Compilation! Cleaning *.class files
RD /S /Q "bin/edu"

pause