#!/bin/bash
# Build Beam for Linux
. ./checkProps4Linux.sh
/opt/panopset/bin/fw ./slab/templates/beam/beamService.txt temp/beam/
gradle -p beam clean build
cp beam/build/libs/beam.jar ~/
pushd temp/beam
sed -i -e 's/prod/DEV/g' panopsetweb.service
. ./installservice.sh
popd

