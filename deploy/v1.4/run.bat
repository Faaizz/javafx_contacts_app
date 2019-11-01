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

::SET TO USE LOCAL JAVA FX SDK
SET module_path="windows\javafx-sdk-11.0.2\lib" 

::LAUNCH APPLICATION
java -jar --module-path %module_path% --add-modules=javafx.controls,javafx.fxml contacts-1.4.jar