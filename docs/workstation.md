# Workstation Configuration

## Requirements

* A [server](server.md) somewhere.

## Panopset dev.properties

You will need an external configuration file, to hold your deployment specifics.


Edit/verify your ~/Documents/panopset/dev.properties file:


    ./e.sh


Place the following in ~/Documents/panopset/dev.properties:



    SITE_DN=<your domain>
    SITE_NAME=<your .ssh/config alias, for that domain>
    SITE_USR=<your server user name>
    WKSN_USR=<your workstation user name>
    SITE_PWD=<your server password>
    WKSN_PWD=<your workstation password>



## ssh Configuration



Edit your ssh config file, on your PC


    ./vc.sh


Add the following lines, replacing anything in &lt;&gt; brackets, with your values:


    Host <your host name, as defined in $SITE_NAME>
    HostName <your host ip address>
    User root
    IdentityFile ~/.ssh/<your private key file>


