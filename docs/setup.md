[home](../README.md) ~ [build](build.md) ~ setup ~ [publish](publish.md) ~ [deploy](deploy.md) ~ [verify](verify.md) ~ [upgrade](./upgrade.md)

# Additional Requirements

### beam
* Gradle 8.6

Gradle expects JAVA_HOME to be set, so put this at the end of your ~/.profile file:


    export JAVA_HOME="/usr/lib/jvm/java-1.21.0-openjdk-amd64"


... make adjustments to the path to match your system.



### git

    git config user.email <your email address>
    git config -l

## Environment Variables

Set up these variables on your development PC, create a file called ~/Documents/panopset/dev.properties:

Adjust for your environment:

    SITE_DN=your site deployment domain name, ie: panopset.com.
    SITE_NAME=corresponds to the Host entry for your deployment site Host entry
    SITE_USR=user name you would like setup on your deployment site.
    WKSN_USR=your workstation user name, which is likely the same as your workstation account.
    SITE_PWD=deployment site user's password
    WKSN_PWD=workstation user's 'password
    SITE_REDIS_URL=your redis cache URL
    SITE_REDIS_PWD=your redis cache password

## Platform Specific

### Script conventions

Macintosh (m) and Windows (w) only have scripts to build the desktop applications. Linux (l) is used to publish 
<a href="https://panospet.com">panopset.com</a>.
We only need scripts for those platforms, when building and publishing the desktop application.

Names of scripts that are entry points are kept short.

Action, first letter:

    a = All.
    b = Build.
    d = Deploy locally.
    l = Link.
    p = Publish.

Subject, second letter:

    a = The JavaFX desktop applications.
    b = Beam, the Spring Boot application.
    c = Checksums.
    p = Properties.
    w = Web content.

Platform, last letter:

    l = Linux.
    m = Macintosh.
    w = Windows.

### Samples


    ./al.sh build and deploy everything on Linux.
    ./aw.sh build and deploy the desktop application for Windows.
    ./am.sh build and deploy the desktop application for Macintosh.
    ./bal Just build the desktop application on Linux.
    ./dwl Just build and deploy the web site locally. (Linux only).

Linux Mint development machine basics you'll need, make whatever adjustments you need for your favorite Linux distro:


    sudo apt -y install openjdk-19-jdk nginx maven gradle openssh-server vim git gitk
    mkdir ~/.ssh
    touch ~/.ssh/config
    chmod 700 ~/.ssh
    chmod 600 ~/.ssh/config
