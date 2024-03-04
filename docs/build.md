[home](../README.md) ~ build ~ [setup](setup.md) ~ [publish](publish.md) ~ [deploy](deploy.md) ~ [verify](verify.md) ~ [upgrade](./upgrade.md)

Those who wish to build the Panopset Desktop application from the source, would run one of these scripts.
It will deploy:

* an all-in one panopset.jar to your home directory, and
* an application installer for your system in the target directory.

# Requirements

* [Java 21 JDK](https://adoptium.net/)
* [Maven 3.9.6](https://maven.apache.org/download.cgi)
* [git](https://git-scm.com/)


# Linux

Build application for Linux:


    ./bal.sh


You can use [sdkman](https://sdkman.io/) to install the requirements.


# Apple Macintosh

Build application for Apple Macintosh:


    ./bam.sh


You can use [brew](https://brew.sh/) to install the requirements.


# Microsoft Windows

Build application for Windows:

    ./baw.cmd

On Windows systems, you can place a script in your home directory (%USERPROFILE%), to set your %PATH% to point
to the requirements. Here is what mine looks like:  [setenv.cmd](platforms/win/setenv.cmd). You would make adjustments
for your installation directories, unless you follow the same conventions. Keeping versions out of the path
makes your upgrades a much simpler process.


# Note

The remaining pages are for those who wish to mirror panopset.com, this is the only documentation page you need,
for building the Panopset desktop applications from source.
