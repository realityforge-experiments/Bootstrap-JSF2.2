package ca.pfroy.bootstrap;

import ca.pfroy.bootstrap.entity.Person;
import ca.pfroy.bootstrap.entity.PersonRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class PersonPage implements Serializable {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonRepository personRepository;

    private Person newPerson;

    public List<Person> getPersons() {
        return personRepository.getPersons();
    }

    @Produces
    @Named
    public Person getNewPerson() {
        return newPerson;
    }

    public void register() throws Exception {
        newPerson.setId((int) System.currentTimeMillis());
        personRepository.register(newPerson);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
        initNewMember();
    }

    @PostConstruct
    public void initNewMember() {
        newPerson = new Person();
    }
}
