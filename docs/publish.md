[home](../README.md) ~ [build](build.md) ~ [setup](setup.md) ~ publish ~ [deploy](deploy.md) ~ [verify](verify.md) ~ [upgrade](./upgrade.md)


One time service deployment on your workstation:

    pushd ~/temp/beam
    chmod +x installService.sh
    ./installService.sh
    popd

In your nginx config file (default, or your domain), in /etc/nginx/sites-available, add the following:

    location /beam/ {
     proxy_pass http://127.0.0.1:8090/beam;
    }

... and then

    sudo nginx -t
    sudo systemctl restart nginx
    sudo netstat -tulpn | grep 8090

... and verify that you see something like this:
    
    tcp6  0 0 :::8090    :::*   LISTEN   780/java


You may use a different port on your local system, just change it in beam/src/main/resources/application-DEV.yaml.


