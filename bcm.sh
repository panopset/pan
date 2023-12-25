#!/bin/bash
. ./checkProps4Mac.sh
echo "PLATFORM_NAME = ${PLATFORM_NAME}"
echo "INSTALLER_PFX = ${INSTALLER_PFX}"
echo "INSTALLER_SFX = ${INSTALLER_SFX}"
/Applications/panopset.app/Contents/MacOS/gi "${PLATFORM_NAME}" ${INSTALLER_PFX} ${INSTALLER_SFX}
