package entities

import service.PersonService

class Person implements PersonService{
    String name
    String email
    Person(String name, String email) {
        this.name = name
        this.email = email
    }

    String getName() {
        name
    }

    void setName(String name) {
        this.name = name
    }

    String getEmail() {
       email
    }

    void setEmail(String email) {
        this.email = email
    }

    @Override
    void showInfo() {}
}