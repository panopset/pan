#!/bin/bash
. ./checkProps4Linux.sh
rsync -avuzh $TGT_HTML/downloads ~/temp/
rsync -avuzh /var/www/html/downloads ~/temp/
