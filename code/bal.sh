#!/bin/bash
# Build application for Linux.
mvn -f shoring clean install
mvn -f legacy clean install
. ./ll.sh

# Copy the all-in-one jar to the home directory.
cp legacy/target/panopset-jar-with-dependencies.jar ~/panopset.jar
