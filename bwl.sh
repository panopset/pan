#!/bin/bash
# Build Website for Linux:

function deployLocalJavadocs () {
  cp -r shoring/target/site/* "$1/dox/site/"
  deployLocalDocumentation "$1" compat
  deployLocalDocumentation "$1" fxapp
  deployLocalDocumentation "$1" blackjackEngine
  deployLocalDocumentation "$1" flywheel
  deployLocalDocumentation "$1" desk
}

function deployLocalDocumentation () {
  mkdir -p "$1/dox/site/$2/"
  cp -r ./shoring/$2/target/site/* $1/dox/site/$2/
  cp -r ./shoring/$2/target/dokka/* $1/dox/site/$2/
}

. scripts/platforms/linuxCheckProps.sh
cp -r slab/raw/* /var/www/html/
. ./dwl.sh
deployLocalJavadocs /var/www/html/
