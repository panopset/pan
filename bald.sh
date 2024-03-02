#!/bin/bash
# Build application for Linux, with the documentation.
# To just create the installer, during development cycles, you can run bal.sh.
. ./checkProps4Linux.sh
mvn -f shoring clean install site
. ./ll.sh
