package ca.pfroy.bootstrap;

import ca.pfroy.bootstrap.entity.Person;
import ca.pfroy.bootstrap.entity.PersonRepository;
import ca.pfroy.bootstrap.security.Secure;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class PersonController
  extends AbstractController
{
  @Inject
  private PersonRepository personRepository;

  private Person newPerson;

  public List<Person> getPersons()
  {
    return personRepository.getPersons();
  }

  @Produces
  @Named
  public Person getNewPerson()
  {
    return newPerson;
  }

  @Secure( "#{personPage.newPerson.name == 'fred'}" )
  public void register()
    throws Exception
  {
    newPerson.setId( (int) System.currentTimeMillis() );
    personRepository.register( newPerson );
    addInfoMessage( "Registered!", "Registration successful" );
    initNewMember();
  }

  @PostConstruct
  public void initNewMember()
  {
    newPerson = new Person();
  }
}
