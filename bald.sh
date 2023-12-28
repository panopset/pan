#!/bin/bash
# Include the documentation.
. ./checkProps4Linux.sh
mvn -f shoring clean install site
. ./ll.sh

