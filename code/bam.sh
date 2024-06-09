#!/bin/bash
# Build application for Apple Macintosh.
. ./checkProps4Mac.sh
mvn -f shoring clean install
mvn -f legacy clean install
. ./lm.sh
# Copy the all-in-one jar to the home directory.
cp legacy/target/panopset-jar-with-dependencies.jar ~/panopset.jar
