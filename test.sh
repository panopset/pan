#!/bin/bash
. scripts/platforms/checkProps4Linux.sh
/opt/panopset/bin/fw ./slab/templates/beam/beamService.txt ~/temp/beam/
pushd ~/temp/beam
#. ./installservice.sh
#popd

