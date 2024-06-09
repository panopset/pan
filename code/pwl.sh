#!/bin/bash
. ./checkProps4Linux.sh
echo "publishing to $TGT_HTML"
rsync -avuzh /var/www/html/ $TGT_HTML/
