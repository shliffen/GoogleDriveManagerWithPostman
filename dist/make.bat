@echo off

mvn -f ../pom.xml clean install

copy ..\target\GoogleDriveManager-1.0.0-SNAPSHOT.jar ..\dist\GoogleDriveManager.jar
