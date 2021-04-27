@echo off

set arg1=%*

copy ..\target\GoogleDriveManager-1.0.0-SNAPSHOT.jar ..\dist\GoogleDriveManager.jar

java -jar GoogleDriveManager.jar %arg1%
