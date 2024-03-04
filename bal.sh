#!/bin/bash
# Build application for Linux.
. ./checkProps4Linux.sh
mvn -f shoring clean install
mvn -f legacy clean install
. ./ll.sh

# Copy the all-in-one jar to the home directory.
cp legacy/target/axe-jar-with-dependencies.jar ~/panopset.jar
