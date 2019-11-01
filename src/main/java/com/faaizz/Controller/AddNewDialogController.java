package com.faaizz.Controller;

import com.faaizz.Model.Contact;
import com.faaizz.Utils.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddNewDialogController {

    @FXML
    public TextField nameTF;
    @FXML
    public TextField mobileTF;
    @FXML
    public TextField phoneTF;
    @FXML
    public TextField emailTF;


    /**
     * SAVE NEW CONTACT TO FILE
     */
    public Contact addContact() throws Exception {

        Contact newContact= new Contact(
            nameTF.getText(),
            mobileTF.getText(),
            phoneTF.getText(),
            emailTF.getText()
        );

        ContactData contactData= ContactData.getInstance();

        contactData.addContactToFile(newContact);

        return newContact;

    }

    /**
     * POPULATE FIELDS FOR EDIT
     * @param name Current Contact Name
     * @param mobile Current Contact Mobile
     * @param phone Current Contact Phone
     * @param email Current Contact Email
     */
    public void setupEditContact(String name, String mobile, String phone, String email){

        nameTF.setText(name);
        mobileTF.setText(mobile);
        phoneTF.setText(phone);
        emailTF.setText(email);

    }

    /**
     * EDIT CONTACT
     * @param toEdit Contact to edit
     */
    public Contact editContact(Contact toEdit) throws Exception {

        //SETUP EDITED CONTACT
        Contact editedContact= new Contact(nameTF.getText(), mobileTF.getText(), phoneTF.getText(), emailTF.getText());

        return ContactData.getInstance().editContactInFile(toEdit, editedContact);
    }


}
