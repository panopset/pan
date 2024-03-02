#!/bin/bash
# Build, Deploy locally, and Publish the website to the system in ~/Documents/panopset/dev.properties SITE_NAME, SITE_DN.

git pull
. ./push.sh
. ./updateContentLocal.sh
. ./pwl.sh

