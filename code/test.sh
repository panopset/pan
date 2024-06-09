#!/bin/bash
. ./checkProps4Linux.sh
/opt/panopset/bin/fw ./slab/templates/beam/beamService.txt ~/temp/beam/
pushd ~/temp/beam
sed -i -e 's/prod/DEV/g' panopsetweb.service

echo "*******************************"
echo "*** panopsetweb.service     ***"
cat panopsetweb.service
echo "*******************************"
echo "*** installservice.sh       ***"
cat installservice.sh
echo "*******************************"
echo "*** panopsetweb.conf        ***"
cat panopsetweb.conf
popd

