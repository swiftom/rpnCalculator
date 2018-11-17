#!/bin/sh
mvn clean package
java -jar target/rpncalculator-1.0-SNAPSHOT.jar
