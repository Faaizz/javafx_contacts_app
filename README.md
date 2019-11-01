# Sample JavaFX Application
A simple phonebook application to demonstrate knowledge of **Java** and __JavaFX__. The project also provides a __bash__ shell script to launch the __jar__ artifact with required JavaFX modules.

## Basic requirements:
* Linux OS with Java 11 and JavaFX SDK
* Basic knowledge of the command-line (capability of launching an application from command-line)

## Launch the application
**Linux**:
1. Run /deploy/v2/**run.sh** with path to JavaFX SDK as optional argument (set default is */etc/javafx-sdk-11.0.2/lib*). Example:   
*./run.sh /etc/javafx-sdk/lib*  

1. Run the .jar artifact /deploy/v2/**contacts-1.0-SNAPSHOT.jar** with the *--module-path* and *--add-modules* options as shown below:  
*java -jar --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml contacts-1.0-SNAPSHOT.jar*