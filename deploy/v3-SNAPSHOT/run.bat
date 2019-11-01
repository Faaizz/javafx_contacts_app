::SETUP SAVE FILE

@ECHO OFF

::Check if save folder exists
IF EXIST "saves" (
    ::IF IT DOES, NAVIGATE IN AND CHECK IF save file DOESN'T EXIST
    CD saves
    IF NOT EXIST "main.json" (
        ::CREATE IT
        ECHO >> main.json

    )

) ELSE (
    ::IF IT DOESN'T
    ::CREATE THE saves DIRECTORY  
    MKDIR saves
    ::ENTER THE DIRECTORY
    CD saves
    ::CREATE main.json FILE
    copy NUL main.json

)

::NAVIAGATE OUT OF saves FOLDER
CD ../

::============================================================

::CHECK IF PATH TO JAVA FX SDK IS NOT SPECIFIED
::The ~ here removes the double quotes when the comparison is made
::The double quotes are needed for the comparison
IF "%~1"=="" ( 
    ::If path to sdk" is not specified, set to default
    SET module_path="C:\Program Files\Java\JavaFX\javafx-sdk-11.0.2\lib" 

) ELSE (
    ::Otherwise, use specified path to SDK
    SET module_path=%1
)

::LAUNCH APPLICATION
java -jar --module-path %module_path% --add-modules=javafx.controls,javafx.fxml contacts-1.0-SNAPSHOT.jar