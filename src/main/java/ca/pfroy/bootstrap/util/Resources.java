package ca.pfroy.bootstrap.util;

import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 */
@Dependent
public class Resources
{
  @Produces
  @RequestScoped
  public FacesContext getFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }

  @Produces
  public Logger produceLog( final InjectionPoint injectionPoint )
  {
    return Logger.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
  }
}
