#!/bin/bash
# Build application for Linux.
. ./checkProps4Linux.sh
mvn -f shoring clean install
mvn -f legacy clean install
. ./ll.sh
