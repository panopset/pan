#!/bin/bash
# See aw.cmd for Windows, and am.sh for Macinthosh.

git pull
. ./push.sh
. ./checkProps4Linux.sh

rm -rf /var/www/html/*
. ./bwl.sh
. ./dwl.sh
. ./pwl.sh
