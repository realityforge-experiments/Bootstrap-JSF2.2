package ca.pfroy.bootstrap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class AbstractController
  implements Serializable
{
  private static final long serialVersionUID = -4862069600034765532L;

  protected transient Logger logger = Logger.getLogger( getClass().getName() );
  @Inject
  private FacesContext _facesContext;

  protected final void addInfoMessage( final String summary )
  {
    addInfoMessage( summary, null );
  }

  protected final void addInfoMessage( final String summary, final String detail )
  {
    addMessage( FacesMessage.SEVERITY_INFO, summary, detail );
  }

  protected final void addWarningMessage( final String summary )
  {
    addWarningMessage( summary, null );
  }

  protected final void addWarningMessage( final String summary, final String detail )
  {
    addMessage( FacesMessage.SEVERITY_WARN, summary, detail );
  }

  /**
   * Adds a global error message to the response.
   *
   * @param summary The message summary.
   */
  protected final void addErrorMessage( String summary )
  {
    addErrorMessage( summary, null );
  }

  protected final void addErrorMessage( final String summary, final String detail )
  {
    addMessage( FacesMessage.SEVERITY_ERROR, summary, detail );
  }

  protected final void addFatalMessage( final String summary )
  {
    addFatalMessage( summary, null );
  }

  protected final void addFatalMessage( final String summary, final String detail )
  {
    addMessage( FacesMessage.SEVERITY_FATAL, summary, detail );
  }

  protected final void keepMessages()
  {
    _facesContext.getExternalContext().getFlash().setKeepMessages( true );
  }

  protected final void addMessage( final Severity severity, final String summary, final String detail )
  {
    _facesContext.addMessage( null, new FacesMessage( severity, summary, detail ) );
  }

  /**
   * Handle de-serialization from passivated session and restore transient
   * fields.
   *
   * @param ois The ObjectInputStream object.
   * @throws java.io.IOException
   * @throws ClassNotFoundException
   */
  private void readObject( ObjectInputStream ois )
    throws IOException, ClassNotFoundException
  {
    ois.defaultReadObject();
    logger = Logger.getLogger( getClass().getName() );
  }
}
