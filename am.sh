#!/bin/bash
git pull
. ./checkProps4Mac.sh

rm -rf target
mkdir target

# Build Application Macintosh.
. ./bam.sh

# Publish Application Macintosh.
. ./pam.sh

# Copy the all-in-one jar to the home directory.
cp legacy/target/axe-jar-with-dependencies.jar ~/panopset.jar
