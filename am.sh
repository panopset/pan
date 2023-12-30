#!/bin/bash
git pull
. ./checkProps4Mac.sh

rm -rf target
mkdir target

# Build Application Macintosh.
. ./bam.sh

# Publish Application Macintosh.
. ./pam.sh
