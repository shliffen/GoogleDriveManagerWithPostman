#!/usr/bin/env bash

mvn -f ../pom.xml clean install

cp ../target/GoogleDriveManager-1.0.0-SNAPSHOT.jar ../dist/GoogleDriveManager.jar

sleep 10
