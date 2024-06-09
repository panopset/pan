#!/bin/bash
. ./checkProps4Linux.sh
rsync -avuzh --delete --ignore-existing /var/www/html/ $TGT_HTML/