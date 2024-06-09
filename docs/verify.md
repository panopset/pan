[home](../README.md) ~ [build](build.md) ~ [setup](setup.md) ~ [publish](publish.md) ~ [deploy](./deploy.md) ~ verify ~ [upgrade](./upgrade.md)

For


    echo $HOSTNAME


you should see the name of the host you originally defined when you set up the server on digitalocean.

For


    netstat -tulpn

you should see nginx listening on port 80.
... next, verify the firewall settings are good:

For

    ufw status

you should see something like this


    root@skunkworks:~# ufw status
    Status: active
    
    To                         Action      From
    --                         ------      ----
    OpenSSH                    ALLOW       Anywhere
    Nginx Full                 ALLOW       Anywhere
    OpenSSH (v6)               ALLOW       Anywhere (v6)
    Nginx Full (v6)            ALLOW       Anywhere (v6)


Another thing you can do, to verify things are fine so far, 
is hit the server from your browser. Verify you see a nginx default page.

Finally,


    htop


... and make sure the CPU has settled down.  If it is maxed, it is probably still doing something important.
