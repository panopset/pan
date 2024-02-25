#!/bin/bash
. ./checkProps4Mac.sh
scp ./target/* $TGT_HTML/downloads/
scp ~/panopset.jar $TGT_HTML/downloads/$FX_ARCH/
