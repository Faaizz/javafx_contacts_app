package com.faaizz.Controller;

import com.faaizz.Model.Contact;
import com.faaizz.Utils.ContactData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;


public class SearchDialogController {

    @FXML
    private GridPane root_pane;
    @FXML
    private HBox searchbar_hbox;
    @FXML
    private TextField search_textfield;
    @FXML
    private Button search_button;


    public void initialize(){

    }

    @FXML
    public void handleSearch(){

        //CONDUCT CONTACT SEARCH
        try {

            ContactData contactData = ContactData.getInstance();
            List<Contact> search_result = contactData.searchContacts(search_textfield.getText().trim());

            //IF THERE ARE NO RESULTS, THROW AN EXCEPTION
            if(search_result.isEmpty()){
                throw new Exception("No Contacts found");
            }

            //IF MATCHES ARE FOUND, DISPLAY RESULTS AS A SINGLE-COLUMN TABLE
            TableView<Contact> result_table=  new TableView<>();
            result_table.setPrefWidth(root_pane.getWidth());

            //ADD RESULTS TO TABLE
            result_table.getItems().addAll(search_result);


            //CREATE TABLE COLUMNS
            //NAME
            TableColumn<Contact,String> nameCol = new TableColumn<Contact,String>("Name");
            //SET CELL VALUE PROPERTY
            nameCol.setCellValueFactory(new PropertyValueFactory("name"));

            //MOBILE
            TableColumn<Contact,String> mobileCol = new TableColumn<Contact,String>("Mobile");
            mobileCol.setCellValueFactory(new PropertyValueFactory("mobile"));

            //PHONE
            TableColumn<Contact,String> phoneCol = new TableColumn<Contact,String>("Phone");
            phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));

            //EMAIL
            TableColumn<Contact,String> emailCol = new TableColumn<Contact,String>("Email");
            emailCol.setCellValueFactory(new PropertyValueFactory("email"));


            //DISPLAY TABLE
            root_pane.add(result_table, 0, 1);



            //ADD COLUMNS TO TABLE
            result_table.getColumns().addAll(nameCol, mobileCol, phoneCol, emailCol);
        } catch(Exception e){

            //CREATE LABEL TO COMMUNICATE THAT NO CONTACT WAS FOUND WITH THE SUPPLIED SEARCH PARAMETER
            Label not_found= new Label("No Contact found");

            //DISPLAY LABEL
            root_pane.add(not_found, 0, 1);

        }

    }



}
