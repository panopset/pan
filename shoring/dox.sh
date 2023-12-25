mvn clean install dokka:dokka
rsync -avuzh compat/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/compat
rsync -avuzh desk/target/dokka ${SITE_NAME}:/var/www/${SITE_DN}/html/dox/desk

