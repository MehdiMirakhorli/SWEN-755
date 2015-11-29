+-----------------------------------------------------------------------------+
| NOTICE: Check the Documentation.pdf for more explanations.                  |
+-----------------------------------------------------------------------------+

ENVIRONMENT REQUIREMENTS
=======================
- JDK/JRE 1.7+
- JAVA_HOME variable properly configured


HOW TO RUN/TEST APPLICATION USING COMMANDS
=======================
+-----------------------------------------------------------------------------------+
| NOTICE: If you have Windows OS, you can compile the application using compile.bat |
+-----------------------------------------------------------------------------------+

+ Compilation Commands 
	javac -cp lib/jsoup-1.8.3.jar -d bin src/edu/rit/swen755/pool/*.java 
	jar cf bin/crawler.jar -C bin edu/rit/swen755/pool 

+ Execution Commands: (NOTICE: provide the url to the Web page to be crawled using a program arg - the <BASE_URL> shown below)
	java -cp lib/jsoup-1.8.3.jar;bin/crawler.jar edu/rit/swen755/pool/Main <BASE_URL>
	(Example, if you want to crawl RIT's library web site, you run with the following command:
		java -cp lib/jsoup-1.8.3.jar;bin/crawler.jar edu/rit/swen755/pool/Main http://library.rit.edu)

OUTPUTS SHOWN
=======================
It shows the URL to the Web page and the total number of found links. After that, a list of HTTP status code and visited URLs are printed.
