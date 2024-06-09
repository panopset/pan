#!/bin/bash
# All Linux
# See aw.cmd for Windows, and am.sh for Macinthosh.

echo dev.properties:
cat ${HOME}/Documents/panopset/dev.properties

git pull
. ./checkProps4Linux.sh

rm -rf /var/www/html/*
mkdir -p /var/www/html/dox/site

# Build Application Linux including dokka, link and build the app.
# Use bal.sh if you are in a hurry.
. ./bald.sh

# Copy the all-in-one jar to the home directory.
cp legacy/target/axe-jar-with-dependencies.jar ~/panopset.jar

# Publish Application Linux.
. ./pal.sh

# Synch up the remote and local downloads to temp
. ./synchDownloads.sh

# Build Checksums Linux.
. ./bcl.sh

# Build Website Linux. We only need to build the website on the
# primary dev box, which is always Linux.
#   * Copies over the documentation from the prior step, and
#   * the raw images etc.
. ./bwl.sh

# Deploy Website Linux. Calls /opt/panopset/gs (generate site), which
# uses Flywheel to generate the web pages.
. ./dwl.sh

# Deploy the HATEOAS REST app.  Only from Linux here too.
. ./deployBeam.sh

# Publish Website Linux. rsync /var/www/html up to the server ${SITE_NAME}.
# Again, only on Linux do we do this.
. ./pwl.sh
