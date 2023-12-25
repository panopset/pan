#!/bin/bash
. ./checkProps4Linux.sh
rsync -avuzh /var/www/html/ $TGT_HTML/
