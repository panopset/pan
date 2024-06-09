. ./deploy.properties

export INSTALLER="${INSTALLER_PFX}${PV}${INSTALLER_SFX}"
export PCI_JSON="pci_${INSTALLER}.json"
export TGT_HTML=${SITE_USR}@${SITE_NAME}:/var/www/${SITE_DN}/html

echo installer is $INSTALLER
