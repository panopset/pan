. ./checkProps4Linux.sh

# Create the crtusr.sh command for your new server
rm -rf temp
mkdir temp
/opt/panopset/bin/fw ../docs/templates/crtusr.txt ./temp
chmod +x ./temp/*.sh
scp ./temp/* root@$SITE_NAME:/root/
