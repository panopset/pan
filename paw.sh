#!/bin/bash
. ./checkProps4Win.sh
echo scp ./target/* $TGT_HTML/downloads/
scp ./target/* $TGT_HTML/downloads/
