#!/bin/bash
. ./bbl.sh
rsync -avuzh ~/beam.jar ${PANOPSET_SITE_NAME}:/home/${PANOPSET_SITE_USR}/beam.jar
rsync -avuzh ~/temp/beam/ ${PANOPSET_SITE_NAME}:/home/${PANOPSET_SITE_USR}/
