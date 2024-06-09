[home](../README.md) ~ [build](build.md) ~ [setup](setup.md) ~ [publish](publish.md) ~ [deploy](deploy.md) ~ [verify](verify.md) ~ upgrade



To ensure that there are no broken links, this is the upgrade process:


Artificats are published to panopset.net, by updating ~/Documents/panopset/dev.properties. See [setup](setup.md).

## Windows:

Run from a command line:


    aw.cmd


Run from git bash:


    aw.sh



## Macintosh:

Run:


    ./am.sh



## Linux

Run:


    ./al.sh



Then, they are copied over to panopset.com, directly on the server:


    rsync -avuzh /var/www/panopset.net/html/downloads /var/www/panopset.com/html/downloads


Then, back on the Linux workstation, update ~/Documents/panopset/dev.properties SITE_DN to point to panopset.com, and run 



    ./synchDownloads.sh
    ./bcl.sh
    ./updateContent.sh


Once the new downloads are ready, delete the old versions from /var/www/panopset.com/html/downloads on the server, and locally in:

    /var/www/html/downloads
    ~/temp/downloads



Final steps are to update ~/Documents/panopset/dev.properties SITE_DN to point to panopset.net, and start working on the new [version](../deploy.properties).

