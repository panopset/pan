# Panopset Continuous Integration Manual


* 
* Update deploy.properties with the new version.
  * PV: new version.
  * RV: prior version.
* Update docs/vh.html.


    ../p.sh


Verify SITE_DN is where you want to publish.
 

    ./generateVersion.sh
    ../u.sh
 
 


    


* To get started with changes for next revision, update dependency versions in deploy.properties, shoring/pom.xml, and beam/build.gradle.
* [javafx](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml) usually has a new version fairly frequently.