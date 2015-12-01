@echo off

REM Create necessary folders
if not exist "WebContent\WEB-INF\classes" mkdir "WebContent\WEB-INF\classes"

REM Compilation
echo Compiling
javac -cp "C:/apache-tomcat/lib/servlet-api.jar" -d WebContent\WEB-INF\classes src/edu/rit/swen755/controllers/*.java src/edu/rit/swen755/models/*.java src/edu/rit/swen755/views/*.java 

echo DONE Compilation! Cleaning *.class files


REM Copying files to apache tomcat
echo Copying files to apache tomcat
if not exist "C:/apache-tomcat/webapps/Assignment4" mkdir "C:/apache-tomcat/webapps/Assignment4"
xcopy /s/e WebContent "C:/apache-tomcat/webapps/Assignment4"
pause