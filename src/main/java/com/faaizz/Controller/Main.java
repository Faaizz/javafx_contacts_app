package com.faaizz.Controller;

import com.faaizz.Model.Contact;
import com.faaizz.Utils.ContactData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main {

    @FXML
    public BorderPane root_border_pane;
    @FXML
    public ListView contact_list;
    @FXML
    public Label letter_symbol_label;
    @FXML
    public Label name_label;
    @FXML
    public Label mobile_label;
    @FXML
    public Label phone_label;
    @FXML
    public Label email_label;


    private ContactData contactData;


    public void initialize() throws IOException{

        List<Contact> contactList= new ArrayList<>();

        //GET DATA CLASS SINGLETON INSTANCE
        contactData= ContactData.getInstance();
        //READ JSON STRING FROM FILE
        contactData.readJsonFromFile();


        //GET LIST OF CONTACTS FROM JSON STRING AND GENERATE OBSERVABLE LIST FROM OBTAINED LIST
        contactData.jsonToContacts(contactData.getJsonString());

        //BIND OBSERVABLE ARRAY TO LISTVIEW CONTROL
        contact_list.setItems(contactData.contactsList);

        //SET SINGLE SELECTION MODEL FOR LIST VIEW  contact_list
        contact_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //ADD CHANGE LISTENER
        contact_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                handleListViewChange();
            }
        });

        //SELECT FIRST ITEM
        contact_list.getSelectionModel().selectFirst();


    }

    @FXML
    public void handleExit(){
        root_border_pane.getScene().getWindow().hide();

    }

    @FXML
    public void handleListViewChange(){
        //GET SELECTED ITEM
        Contact selected_contact= (Contact) contact_list.getSelectionModel().getSelectedItem();

        //SET LETTER
        letter_symbol_label.setText((String.valueOf(selected_contact.getName().charAt(0))));

        //SET NAME
        name_label.setText(selected_contact.getName());

        //SET MOBILE
        mobile_label.setText(selected_contact.getMobile());

        //SET PHONE
        phone_label.setText(selected_contact.getPhone());

        //SET EMAIL
        email_label.setText(selected_contact.getEmail());

    }

    @FXML
    public void handleNewContact() throws Exception {

        //SHOW DIALOG TO ADD NEW CONTACT
        Dialog<ButtonType> dialog= new Dialog<>();
        dialog.initOwner(root_border_pane.getScene().getWindow());
        dialog.setTitle("Add New Contact");

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("add_new_dialog.fxml"));

        //LOAD DIALOG FXML
        dialog.getDialogPane().setContent(loader.load());


        //ADD BUTTONS
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //SHOW DIALOG AND HANDLE BUTTON PRESSED
        Optional<ButtonType> result= dialog.showAndWait();

        //CLOSE DIALOG IF CANCEL IS PRESSED
        if(result.isPresent() && result.get() == ButtonType.CANCEL ){
            dialog.close();
        }

        //IF "OK" IS PRESSED
        if(result.isPresent() && result.get() == ButtonType.OK){

            AddNewDialogController controller= loader.getController();

            //ADD CONTACT TO FILE
            Contact newContact= controller.addContact();
            //SELECT NEW CONTACT
            contact_list.getSelectionModel().select(newContact);

        }
    }

    @FXML
    public void handleDeleteContact() throws Exception{

        //DISPLAY A DIALOG TO CONFIRM DELETION
        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);

        confirm.setHeaderText("Confirm Delete");

        //SELECTED CONTACT
        Contact contact= (Contact) contact_list.getSelectionModel().getSelectedItem();

        //DISPLAY DETAILS OF CONTACT TO DELETE
        confirm.setContentText(
                contact.getName() + "\n" +
                contact.getMobile() + "\n" +
                contact.getPhone() + "\n" +
                contact.getEmail()
        );

        Optional<ButtonType> result= confirm.showAndWait();

        //CONFIRMED DELETE
        if( result.isPresent() && result.get() == ButtonType.OK ){

            //DELETE CONTACT
            contactData.deleteContactFromFile(contact);

        }

    }

    @FXML
    public void handleEditContact() throws Exception {

        //SHOW DIALOG TO EDIT CONTACT
        Dialog<ButtonType> dialog= new Dialog<>();
        dialog.initOwner(root_border_pane.getScene().getWindow());
        dialog.setTitle("Edit Contact");

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("add_new_dialog.fxml"));

        //LOAD DIALOG FXML
        dialog.getDialogPane().setContent(loader.load());

        //SETUP CONTROLLER
        AddNewDialogController controller= loader.getController();

        //GET CURRENT CONTACT
        Contact currentContact= (Contact) contact_list.getSelectionModel().getSelectedItem();

        //POPULATE FIELD WITH CURRENT CONTACT INFORMATION
        controller.setupEditContact(currentContact.getName(), currentContact.getMobile(), currentContact.getPhone(), currentContact.getEmail());


        //ADD BUTTONS
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //SHOW DIALOG AND HANDLE BUTTON PRESSED
        Optional<ButtonType> result= dialog.showAndWait();

        //CLOSE DIALOG IF CANCEL IS PRESSED
        if(result.isPresent() && result.get() == ButtonType.CANCEL ){
            dialog.close();
        }

        //IF "OK" IS PRESSED
        if(result.isPresent() && result.get() == ButtonType.OK){

            //EDIT CONTACT TO FILE
            Contact editedContact= controller.editContact(currentContact);

            //SELECT EDITED CONTACT
            contact_list.getSelectionModel().select(editedContact);

        }
    }

    @FXML
    public void handleSearch() throws IOException {

        //SHOW DIALOG TO HANDLE SEARCH
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root_border_pane.getScene().getWindow());
        dialog.setTitle("Search Contacts");

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("search_dialog.fxml"));

        //LOAD DIALOG FXML
        dialog.getDialogPane().setContent(loader.load());

        //ADD CANCEL BUTTON
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        //SETUP CONTROLLER
        SearchDialogController controller= loader.getController();

        //SHOW DIALOG AND HANDLE BUTTON PRESSED
        Optional<ButtonType> result= dialog.showAndWait();


    }

}
