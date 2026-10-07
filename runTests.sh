#!/bin/bash

javac -d bin $(find src -name "*.java")

javac -d binTest -cp "bin:lib/junit-platform-console-standalone-1.10.0.jar" $(find test -name "*.java")

java -jar lib/junit-platform-console-standalone-1.10.0.jar -cp "bin:binTest" --scan-classpath