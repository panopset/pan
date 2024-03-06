[home](../README.md) ~ [build](build.md) ~ [setup](setup.md) ~ [publish](publish.md) ~ deploy ~ [verify](verify.md) ~ [upgrade](./upgrade.md)

# Site Deployment

## Create and configure the server

Edit/verify your ~/Documents/panopset/dev.properties file:


    ./e.sh


Once you have your development system all set up with the environment variables
described on the [setup](setup.md) page, you're ready to deploy your server.

These instructions were tested using [Digitalocean](https://digitalocean.com)* Ubuntu Linux.


Create a digitalocean server.


Edit your ssh config file, on your PC


    ./vc.sh


Add the following lines, replacing anything in <> with your values:


    Host <your host name, as defined in $SITE_NAME>
    HostName <your host ip address>
    User root
    IdentityFile ~/.ssh/<your private key file>


Now, ssh out there (after cd'ing back into this project's directory):


    ./s.sh

Do the following, on your new server:


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


Make sure the above steps executed successfully:


    echo $HOSTNAME
    java -version
    netstat -tulpn
    ufw status


[Verify](./verify.md) results.


Once you get the expected verification results, it is safe to:


    sudo reboot 0


## Create user:

Make sure your $user.home/Documents/panopset/dev.properties is correct, then:

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


    ${user.home}/Documents/panopset/dev.properties


file. Then:


    ./s.sh
    sudo reboot 0


short break and then...


    ./s.sh
    ./createDomain.sh

## Beam API deployment




References


* https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04
* https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-as-a-reverse-proxy-on-ubuntu-22-04



<sub><sup>* Disclaimer, while I try to be as platform neutral as possible, 
the author owns [DOCN](https://digitalocean.com) shares. That said, it's just an ISP, you can
still host this anywhere, you'd just have to make the necessary, and hopefully
obvious, modifications to this process.</sub></sup>
