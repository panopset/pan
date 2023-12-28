#!/bin/bash
. ./checkProps4Linux.sh
/opt/panopset/bin/fw ./slab/templates/beam/beamService.txt ~/temp/beam/
pushd ~/temp/beam
sed -i -e 's/prod/DEV/g' panopsetweb.service
cat panopsetweb.service
popd

