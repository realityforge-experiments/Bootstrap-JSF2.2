package ca.pfroy.bootstrap.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository
  implements Serializable
{
  private final Map<Integer, Person> persons = new TreeMap<>();

  @PostConstruct
  protected void init()
  {
    register( new Person( 1, "Person 1", "person1@server.com" ) );
    register( new Person( 2, "Person 2", "person2@server.com" ) );
    register( new Person( 3, "Person 3", "person3@server.com" ) );
    register( new Person( 4, "Person 4", "person4@server.com" ) );
    register( new Person( 5, "Person 5", "person5@server.com" ) );
  }

  public List<Person> getPersons()
  {
    return new ArrayList<>( persons.values() );
  }

  public void register( final Person person )
  {
    persons.put( person.getId(), person );
  }
}
