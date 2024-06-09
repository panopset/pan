#!/bin/bash
mvn -f shoring clean
mvn -f legacy clean
gradle -p beam clean

