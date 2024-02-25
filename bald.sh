#!/bin/bash
. ./checkProps4Linux.sh
mvn -f shoring clean install site
. ./ll.sh
