#!/bin/bash
# Build and publish application for Apple Macintosh.
git pull
. ./checkProps4Mac.sh

rm -rf target
mkdir target

# Build Application Macintosh.
. ./bam.sh

# Publish Application Macintosh.
. ./pam.sh
