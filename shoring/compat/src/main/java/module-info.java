module com.panopset.compat {
  requires kotlin.stdlib;
  requires transitive java.logging;
  requires jdk.crypto.cryptoki;
  requires transitive com.google.gson;
  opens com.panopset.compat to com.google.gson;
  
  exports com.panopset.compat;
}
