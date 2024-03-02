#!/bin/bash
# Build application for Apple Macintosh.
. ./checkProps4Mac.sh
mvn -f shoring clean install
mvn -f legacy clean install
. ./lm.sh
