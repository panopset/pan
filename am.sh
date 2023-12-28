#!/bin/bash
git pull
. ./checkProps4Mac.sh

# Build Application Macintosh.
. ./bam.sh

# Publish Application Macintosh.
. ./pam.sh
