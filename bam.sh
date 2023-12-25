#!/bin/bash
. ./checkProps4Mac.sh
mvn -f shoring clean install
. ./lm.sh
