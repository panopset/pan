[![Panopset](https://panopset.com/images/panopset.png)](https://panopset.com) ~ [home](../README.md) ~ version history

# History
# 1.2.7 (2023.12.28)
* Introduce new Flywheel m command.
* Fix version check https bug.

# 1.2.6 (2023.12.21)
* Skyscraper added, because I don't want my API credentials in the cloudy clouds.
* Dependency updates.
* Kotlin conversion continues.
* Web page moved back to this project, site project discontinued.
  * Back end is handled by the Beam project.
  * Flywheel web page templates and raw files are in the slab folder.
* panopset.jar is no longer published, as "all-in-one" jars are obsolete. 
  * Most people will use a platform installer.
  * You may still run it as Java code, from an IDE.

# 1.2.5 (2023.07.15)
* Dependency updates
* Kotlin conversion continues.
* Bug fixes.
  * Version check not working.

# 1.2.4 (2023.06.15)
* Global replace
  * New feature
    * Line separated multiple replacements.
  * Bug fixes 
    * Don't touch files that aren't going to change.
    * Extensions list was ignored.
* Dependency updates.

# 1.2.3 (2023.01.14)

* Global Replace tab in Flywheel, recursive global replace bug fix.
* Dependency updates.

# 1.2.2 (2022.10.26)

* Dependency updates.
* Web module moved to its own project, [site](https://github.com/panopset/site).
* Kotlin migrations.

# 1.1 (2022.02.22)

* Dependency updates.

# 1.0.9 (2021.12.31)

* Minimum Java version upgraded from 11 to 17, bug fixes.

# 1.0.8 (2021.10.16)

* flywheel: Parameters from application launch.

# 1.0.7 (2021.10.10)

* flywheel: Recognize system properties (as well as environment variables), as template variables.

# 1.0.6 (2021.09.16)

* Flywheel: [Global Replace Prior Line Check](./uses/gblrpl/priorLineCheck.md).

# 1.0.5 (2021.09.11)

* Flywheel: Global replace replaces all files if no regex or extension list specified.

# 1.0.4 (2021.09.04)

* Checksum: Fixed issue w/modules.

# 1.0.3 (2021.08.21)

* Blackjack: Dealer down card had stopped painting.

# 1.0.2 (2021.08.19)

* Add print view to [panopset.com](https://panopset.com).
* Clean up some bad combo box handling.

# 1.0.1 (2021.08.07)

* Switched version designations to be consistent with [Microsoft standards](https://msdn.microsoft.com/en-us/library/aa370859%28v=VS.85%29.aspx), with the publication of panopset-1.0.1.msi.  Apple doesn't like 0 in the major version, so starting with 1.0.1.

# 2021.07.31

* Fix missing Choose item in Flywheel Samples drop down list.

# 2021.07.03

* Consistency in tokens, escape character handling.
* Introduce platform installers.

# 2021.06.05

* Version verification broke in version 2021.05.30.

# 2021.05.30

* Entire project restructured, and set up as Java 9 modules.

# 2021.05.29

* Last release prior to restructuring and consolidation to [src](https://github.com/panopset/src) project.

# 2021.04.17

* Flywheel enhancements.

# 2000 something

* Flywheel was moved to Java.

# 1996

* First version of Flywheel was written as an MPW tool on the Apple Macintosh Platform.
