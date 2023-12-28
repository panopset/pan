#!/bin/bash
# To just create the installer, during development cycles, you can run bald.sh.
. ./checkProps4Linux.sh
mvn -f shoring clean install
. ./ll.sh
