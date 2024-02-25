#!/bin/bash
# See aw.cmd for Windows, and am.sh for Macintosh.

git pull
. ./push.sh
. ./checkProps4Linux.sh

. ./bwl.sh
. ./dwl.sh
. ./pwl.sh
