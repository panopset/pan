#!/bin/bash
# Build application for Linux, with the documentation.
# To just create the installer, during development cycles, you can run bal.sh.
. ./bal.sh
mvn -f shoring site
