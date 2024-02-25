#!/bin/bash
. ./checkProps4Win.sh
echo scp ./target/* $TGT_HTML/downloads/
scp ./target/* $TGT_HTML/downloads/
scp ~/panopset.jar $TGT_HTML/downloads/$FX_ARCH/
