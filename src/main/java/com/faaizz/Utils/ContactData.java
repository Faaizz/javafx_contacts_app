package com.faaizz.Utils;

import com.faaizz.Model.Contact;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ContactData {
    /**
     * SINGLETON CLASS TO LOAD AND SAVE CONTACTS TO JSON
     */

    public final String PATH= "saves/main.json";
    private static ContactData instance;
    private String jsonString;
    private Gson gson;
    public ObservableList<Contact> contactsList;

    private ContactData(){
        this.jsonString= "";
        this.gson= new Gson();
        this.contactsList= FXCollections.observableArrayList(new ArrayList<>());
    }

    /**
     * STATIC BLOCK TO INITIALIZE SINGLETON INSTANCE
     */
    static{
        instance= new ContactData();
    }

    /**
     * STATIC METHOD TO RETUN SINGLETON INSTANCE
     * @return ContactData
     */
    public static ContactData getInstance(){
        return instance;
    }

    /**
     * CONVERTS LIST OF CONTACTS TO JSON STRING
     * @param contacts list of contacts to convert to json string
     * @return String
     */
    public String contactsToJson(List<Contact> contacts){

        return gson.toJson(contacts);

    }

    /**
     * WRITES JSON FORMATTED STRING TO FILE
     * @param jsonString String to write to file
     */
    public void writeJsonToFile(String jsonString) throws IOException {

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH))){
            bufferedWriter.write(jsonString);
        }catch (IOException e){
            throw e;
        }

    }

    /**
     * READ STRING FROM FILE AT SUPPLIED PATH INTO "jsonString"
     * @return String
     */
    public void readJsonFromFile() throws IOException {

        try(BufferedReader bufferedReader= new BufferedReader(new FileReader(PATH))) {

            String currentLine = "";

            StringBuilder jsonSB = new StringBuilder();

            while ((currentLine = bufferedReader.readLine()) != null) {
                jsonSB.append(currentLine);
            }

            this.jsonString = jsonSB.toString();

        }
        catch (IOException e){
            throw e;
        }
    }

    /**
     * CONVERT JSON STRING TO LIST OF CONTACTS
     * @param jsonString JSON STRING CONTAINING LIST OF CONTACTS
     * @return List<Contact>
     */
    public List<Contact> jsonToContacts(String jsonString){

        //IF jsonString IS AN EMPTY STRING, RETURN connactList AS IS
        if(jsonString.isEmpty()){
            return contactsList;
        }

        //OTHERWISE, ADD CONTACTS TO contactList

        Type typeToken= new TypeToken<List<Contact>>(){}.getType();

        this.contactsList.setAll( (List<Contact>) gson.fromJson(jsonString, typeToken));

        return contactsList;
    }


    /**
     * ADD NEW CONTACT TO FILE
     * @param contact CONTACT TO ADD
     */
    public void addContactToFile(Contact contact) throws Exception {

        //LOAD EXISTING CONTACTS
        //READ JSON FROM FILE
        this.readJsonFromFile();

        List<Contact> contactList= this.jsonToContacts(this.getJsonString());

        //CHECK IF CONTACT ALREADY EXISTS
        if(contactList.contains(contact)){
            //THROW AN EXCEPTION
            throw new Exception("Contact already exists!");
        }

        //OTHERWISE
        //ADD NEW CONTACT TO LIST
        contactList.add(contact);

        //CONVERT NEW LIST TO JSON AND SAVE NEW LIST TO FILE
        this.writeJsonToFile(this.contactsToJson(contactList));


    }

    /**
     * DELETE CONTACT FROM FILE
     * @param contact CONTACT TO DELETE
     */
    public void deleteContactFromFile(Contact contact) throws Exception{

        //GET CURRENT LIST OF CONTACTS
        this.readJsonFromFile();
        this.jsonToContacts(this.getJsonString());

        List<Contact> currentList= this.contactsList;

        //SEARCH FOR CONTACT TO DELETE IN CONTACTS LIST
        if(currentList.contains(contact)){

            //DELETE IT
            currentList.remove(contact);

            //SAVE NEW CONTACT LIST
            this.writeJsonToFile(this.contactsToJson(currentList));

            //READ UPDATED FILE
            this.readJsonFromFile();

        }
        //OTHERWISE, THROW AN EXCEPTION THAT SAYS "THE CONTACT NOT FOUND"
        else{
            throw new Exception("Contact not found.");
        }

    }

    /**
     * EDITS THE CONTACT SUPPLIED AS ARGUMENT IN FILE
     * @param  contact Contact to edit
     * @param  editedContact State of Contact after edit     *
     */
    public Contact editContactInFile(Contact contact, Contact editedContact ) throws Exception {

        //REFRESH CONTACT LIST
        this.readJsonFromFile();
        List<Contact> contactList= this.jsonToContacts(this.getJsonString());

        //CHECK IF CONTACT TO EDIT EXISTS IN CONTACT LIST
        if( !(contactList.contains(contact)) ){
            //IF IT DOESN'T, THROW AN EXCEPTION
            throw new Exception("Contact ");
        }

        //GET INDEX
        int index= contactList.indexOf(contact);

        //DELETE CONTACT FROM LIST
        contactList.remove(contact);

        //ADD NEW CONTACT TO INDEX POSITION
        contactList.add(index, editedContact);

        //CONFIRM THAT CONTACT HAS BEEN SUCCESSFULLY ADDED TO INDEX POSITION
        if( !(contactList.get(index).equals(editedContact)) ){
            //IF NOT, THROW AN EXCEPTION
            throw new Exception("Could not add edited version to list.");
        }

        //WRITE NEW LIST TO FILE
        this.writeJsonToFile(this.contactsToJson(contactList));

        //RETURN EDITED CONTACT
        return editedContact;

    }

    /**
     * SEARCHES FOR CONTACTS WITH THE SPECIFIED SEARCH PARAMETER
     * @param searchParam The search parameter
     * @result A List of matched Contacts
     */
    public List<Contact> searchContacts(String searchParam) throws Exception {

        //REFRESH CONTACTS LIST
        this.readJsonFromFile();

        List<Contact> contactList= this.jsonToContacts(this.getJsonString());

        List<Contact> filtered= contactList.stream().filter(
            contact-> {

                //IF NAME, MOBILE, PHONE, OR EMAIL MATCHES THE searchParam, RETURN TRUE
                if(
                    contact.getName().toLowerCase().contains(searchParam.toLowerCase()) ||
                    contact.getMobile().contains(searchParam) ||
                    contact.getPhone().contains(searchParam) ||
                    contact.getEmail().contains(searchParam)
                ){
                    return true;
                }

                //OTHERWISE, RETURN FALSE
                else{
                    return false;
                }

        }).collect(Collectors.toList());


        //IF NOT MACTH IS FOUND, THROW AN EXCEPTION
        if(filtered.isEmpty()){
            throw new Exception("Contact not found");
        }

        return filtered;

    }


    /**
     * RETURNS JSON DATA READ FROM FILE AS A SINGLE STRING
     * @return String
     */
    public String getJsonString() {
        return this.jsonString;
    }


}


