# Panopset Website Deployment Manual

This may be used to publish a mirror of panopset.com, or as a guide to build your own Kotlin JavaFX
[WORA](https://en.wikipedia.org/wiki/Write_once,_run_anywhere) desktop software publication website.


## Requirements

* [Desktop application build instructions](build.md).
* A [server](server.md) somewhere.
* [Workstation Configuration](workstation.md)


## Deployment

Once you have a server set up, you'll want a domain name.

* [Domain](domain.md)


ssh to your server, with this script


    ./s.sh

Do the following, on your new server:


    vim init.sh


i, Paste the following in init.sh:


    apt-get -y update
    apt-get -y install nginx vim net-tools certbot python3-certbot-nginx openjdk-21-jre-headless default-jdk
    apt-get -y upgrade
    ufw allow OpenSSH
    ufw allow 'Nginx Full'
    ufw --force enable
    export HOSTNAME=$(curl -s http://169.254.169.254/metadata/v1/hostname)
    export PUBLIC_IPV4=$(curl -s http://169.254.169.254/metadata/v1/interfaces/public/0/ipv4/address)
    echo Droplet: $HOSTNAME, IP Address: $PUBLIC_IPV4 > /usr/share/nginx/html/index.html
    sed -i 's/# server_names_hash_bucket_size/server_names_hash_bucket_size/g' /etc/nginx/nginx.conf

then, esc, :wq out and

    chmod +x init.sh
    ./init.sh


Make sure the above steps executed successfully:


    echo $HOSTNAME
    java -version
    netstat -tulpn
    ufw status


[Verify](./verify.md) results.


Once you get the expected verification results, it is safe to:


    sudo reboot 0


While you are waiting for the server to reboot, on your workstation:

## Install Flywheel


If you do not have Panopset desktop application software installed, go into target and install it using the .deb file you see there.


## Create user:

Make sure your $user.home/Documents/panopset/dev.properties is correct, 

    ./e.sh

then:

    ./s.sh
    exit
    ./userprep.sh
    ./s.sh
    ./crtusr.sh
    exit


...back to your workstation again and update your


    ./vc.sh


config file again, replacing root with your username, as defined as 


    $SITE_USR


, in your 


    ~/Documents/panopset/dev.properties


file. Then:


    ./s.sh
    sudo reboot 0


short break and then...


    ./s.sh
    ./createDomain.sh

## Beam API deployment


    exit
    ./al.sh
    ./s.sh
    chmod +x *.sh
    ./installservicesite.sh


## Platform specific deployment

On a mac it is:


    pam.sh


... and on Windows it is


    paw.sh

    

References


* https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04
* https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-as-a-reverse-proxy-on-ubuntu-22-04



