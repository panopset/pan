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

