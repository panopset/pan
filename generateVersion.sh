#!/bin/bash
# Update the build number. Update PV in deploy.properties,
# to flag this to also update the version.
. ./checkProps4Linux.sh
/opt/panopset/bin/gv .
