[home](../../README.md) ~ [shoring](../README.md)

# lowerclass
Quickly generate a java compatibility report, for example on your maven repo.

# MavenRepoReport.java
If you run com.panopset.lowerclass.MavenRepoReport.java, you'll get a report on
each jar in your ${user.home}/.m2/repository directory.

Each jar will get a line, with the number of classes associated with each found Java version.

For example, for the lowerclass jar (this project's jar), you'll see that it has 8 Java 9 classes.

    lowerclass-1.0.1.jar > Java SE 9 8
    
(jar name) > (Java version name) (number of classes found)

# Related Useful Maven Commands

    mvn dependency:tree
    mvn verify
