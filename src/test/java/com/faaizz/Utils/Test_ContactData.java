package com.faaizz.Utils;

import static org.junit.jupiter.api.Assertions.*;

import com.faaizz.Model.Contact;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test_ContactData {

    private static ContactData contactData;
    private static List<Contact> contacts;
    private static String contactsJson;
    private static Contact tempContact;


    @BeforeAll
    public static void initialize() {

        /* C    O   N   T   A   C   T */
        //CREATE NEW CONTACT AND ADD TO FILE
        tempContact= new Contact("Test Contact",
                "+2348000000000",
                "+2348000000000",
                "test@test.com"
        );

        /*  C   O   N   T   A   C   T   D   A   T   A   */
        contactData = ContactData.getInstance();

        /*  C   O   N   T   A   C   T   S   */
        //CREATE LIST OF CONTACTS AND ADD SOME DUMMY CONTACTS
        contacts = new ArrayList<>();
        contacts.add(new Contact("Aishat Isiaka", "+2348187456305", "+2349098837906", "yetundeisiaka@gmail.com"));
        contacts.add(new Contact("Thulib Oyebola", "+2348166050481", "+2349030745343", "thullapistol@gmail.com"));

        /* C   O   N   T   A   C   T   S        J   S   O   N */
        //BUILD UP EXPECTED JSON STRING
        StringBuilder contactsJsonSB = new StringBuilder();
        //START OF ARRAY
        contactsJsonSB.append("[");
        //FIRST CONATACT
        contactsJsonSB.append("{\"name\":\"Aishat Isiaka\",\"mobile\":\"+2348187456305\",\"phone\":\"+2349098837906\",\"email\":\"yetundeisiaka@gmail.com\"}");
        //COMMA-SEPERATOR
        contactsJsonSB.append(",");
        //SECOND CONTACT
        contactsJsonSB.append("{\"name\":\"Thulib Oyebola\",\"mobile\":\"+2348166050481\",\"phone\":\"+2349030745343\",\"email\":\"thullapistol@gmail.com\"}");
        //END OF ARRAY
        contactsJsonSB.append("]");

        contactsJson = contactsJsonSB.toString();


    }

    @BeforeEach
    public void resetJsonFile(){

        //WRITE contactsJson TO "main.json" FILE BEFORE EACH TEST TO CREATE A FRESH DATABASE
        try( BufferedWriter bw= new BufferedWriter(new FileWriter(Test_ContactData.contactData.PATH))){

            bw.write(Test_ContactData.contactsJson);

        } catch(IOException e){
            e.printStackTrace();
        }
    }


    @Test
    public void test_contactsToJson() {

        assertEquals(Test_ContactData.contactsJson, Test_ContactData.contactData.contactsToJson(contacts));

    }


    @Test
    public void test_writeJsonToFile() {

        assertDoesNotThrow(() -> {
            //WRITE TO FILE
            Test_ContactData.contactData.writeJsonToFile(Test_ContactData.contactsJson);

            //READ THE JUST WRITTEN FILE AND VERIFY THE CONTENTS
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Test_ContactData.contactData.PATH))) {
                StringBuilder readJsonSB = new StringBuilder();

                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    readJsonSB.append(temp);
                }

                String readJson = readJsonSB.toString();

                assertEquals(Test_ContactData.contactsJson, readJson);
            } catch (IOException e) {
                throw e;
            }

        });

    }


    @Test
    public void test_readJsonFromFile() {

        assertDoesNotThrow(() -> {

            //WRITE TEST JSON STRING TO FILE
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Test_ContactData.contactData.PATH))) {

                bufferedWriter.write(Test_ContactData.contactsJson);

            } catch (IOException e) {
                throw e;
            }

            Test_ContactData.contactData.readJsonFromFile();

            String readJson = Test_ContactData.contactData.getJsonString();

            assertEquals(Test_ContactData.contactsJson, readJson);

        });
    }


    @Test
    public void test_jsonToContacts() {

        assertEquals(Test_ContactData.contacts, Test_ContactData.contactData.jsonToContacts(Test_ContactData.contactsJson));

    }


    @Test
    public void test_addContactToFile(){

        //CREATE NEW CONTACT
        Contact contact= Test_ContactData.tempContact;

        //SAVE TO FILE
        assertDoesNotThrow( ()-> {
            Test_ContactData.contactData.addContactToFile(contact);
        } );

        //LOAD FROM FILE AND ENSURE THAT LAST CONTACT CORRESPONDS TO THE JUST ADDED CONTACT
        assertDoesNotThrow(()->{
            Test_ContactData.contactData.readJsonFromFile();
            List<Contact> contactList= Test_ContactData.contactData.jsonToContacts(Test_ContactData.contactData.getJsonString());

            assertEquals(contactList.get(contactList.size()-1), contact);

        });


    }


    @Test
    public void test_deleteContactFromFile(){
        assertDoesNotThrow( ()->{

            //ADD NEW CONTACT TO FILE
            Test_ContactData.contactData.addContactToFile(Test_ContactData.tempContact);

            //DELETE CONTACT FROM FILE
            Test_ContactData.contactData.deleteContactFromFile(Test_ContactData.tempContact);


            //CHECK IF CONTACT HAS BEEN SUCCESSFULLY DELETED
            //UPDATE CONTACT LIST
            Test_ContactData.contactData.readJsonFromFile();
            List<Contact> contactList= Test_ContactData.contactData.jsonToContacts(Test_ContactData.contactData.getJsonString());

            //CHECK IF CONTACT HAS BEEN DELETED
            assertFalse(contactList.contains(Test_ContactData.tempContact));

        });
    }

    @Test
    public void test_editContactInFile(){

        assertDoesNotThrow( ()-> {

            //ADD NEW CONTACT TO FILE
            Test_ContactData.contactData.addContactToFile(Test_ContactData.tempContact);

            //UPDATE CONTACT LIST
            Test_ContactData.contactData.readJsonFromFile();
            List<Contact> contactList= Test_ContactData.contactData.jsonToContacts(Test_ContactData.contactData.getJsonString());

            //VERIFY THAT CONTACT EXISTS IN FILE
            if(contactList.contains(Test_ContactData.tempContact)){

                //EDIT CONTACT
                Test_ContactData.contactData.editContactInFile(Test_ContactData.tempContact, new Contact("Test Contact Edit", "+2348147619784", "+2348147619784", "newemail@email.com"));

            }

            //CHECK THAT OLD CONTACT IS GONE
            if(contactList.contains(Test_ContactData.tempContact)){

                //IF NOT, THROW AN EXCEPTION
                throw new Exception("Old Contact still present in file");

            }

            //CHECK THAT NEW VERSION OF THE CONTACT EXISTS
            if( !(contactList.contains(new Contact("Test Contact Edit", "+2348147619784", "+2348147619784", "newemail@email.com"))) ){
               //IF IT DOESN'T, THROW AN EXCEPTION
               throw new Exception("Edited version of the Contact is not present in File");
            }

        } );

    }

    @Test
    public void test_searchContacts() throws IOException {

        assertDoesNotThrow(

                ()->{

                    //ADD NEW CONTACT TO FILE
                    Test_ContactData.contactData.addContactToFile(Test_ContactData.tempContact);

                    //SEARCH PARAMETER. CAN BE CONTACT NANE, MOBILE, PHONE,OR EMAIL
                    String searchParam= "Aisha";

                    //INITIATE SEARCH
                    List<Contact> found= Test_ContactData.contactData.searchContacts(searchParam);

                    assertFalse(found.isEmpty());

                }
        );


    }


}