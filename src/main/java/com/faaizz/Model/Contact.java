package com.faaizz.Model;

public class Contact {

    private String name;
    private String mobile;
    private String phone;
    private String email;

    public Contact(String name, String mobile, String phone, String email){

        this.name= name;
        this.mobile= mobile;
        this.phone= phone;
        this.email= email;

    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * TO STRING
     */
    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object otherContactObj){

        Contact otherContact= (Contact) otherContactObj;

        if(otherContact == null){
            return false;
        }

        if(
                this.getName().equals(otherContact.getName()) &&
                this.getMobile().equals(otherContact.getMobile()) &&
                this.getPhone().equals(otherContact.getPhone()) &&
                this.getEmail().equals(otherContact.getEmail())
        ){
            return true;
        }
        else{
            return false;
        }
    }


}
