# Secure Session Management

This is a web API written in java that use token based authentication for secure session management. It listens to:

```
/auth
/users
```

The client is an JavaScript application that runs in the browse as single page application. Requests within the client is marked with and !# in the url. The following requests are within the client:

```
!#/home
!#/login
!#/logout
!#/userlist
```

## To compile:
```
javac tests/*.java src/*.java -cp ./lib/json-simple-1.1.1.jar:./lib/junit-4.12.jar

```
## To run:
```
java -cp ./src:./lib/json-simple-1.1.1.jar Main
```
## To run tests:
```
java -cp ./src/:./tests:./lib/json-simple-1.1.1.jar:./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore SessionManagerTest SessionTest
```
TestRunner

## Or run the following script
```
$ ./TestsRunner SessionManagerTest
```

## To test in browser:
```
localhost:8000
```

