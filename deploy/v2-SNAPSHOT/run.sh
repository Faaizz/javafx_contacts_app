#!/usr/bin/env bash

#SETUP SAVE FILE
DIR=./saves
FILE=main.json

#CHECK IF DIRECTORY DOESN NOT EXISTS
if [[ ! -d $DIR ]]; then 
    mkdir $DIR
fi

#cd INTO THE DIRECTORY
cd $DIR

#IF FILE DOES NOT EXIST, CREATE IT
if [[ ! -a "$FILE" ]]; then
    touch $FILE
fi

#RETURN BACK TO ROOT FOLDER
cd ../

#SETUP JAVAFX MODULE
#CHECK IF MODULE PATH IS SUPPLIED AS ARGUMENT
if [[ -n $1 ]]; then 
    module_path=$1
else module_path="/etc/javafx-sdk-11.0.2/lib"
fi

#RUN APPLICATION WITH REQUIRED COMMAND-LINE ARGUMENTS
java -jar --module-path $module_path --add-modules=javafx.controls,javafx.fxml contacts-1.0-SNAPSHOT.jar