package ca.pfroy.bootstrap.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@SuppressWarnings( "EjbProhibitedPackageUsageInspection" )
@Interceptor
@Secure()
public class SecurityInterceptor
{

  @Inject
  private Logger logger;

  @Inject
  private FacesContext facesContext;

  @AroundInvoke
  public Object invoke( final InvocationContext context )
    throws Exception
  {
    final ELContext elContext = facesContext.getELContext();

    final Secure secure = getSecureAnnotation( context.getMethod() );
    if ( null != secure )
    {
      final String expression = secure.value();

      // Populating the request map so that parameters are available (arg0, ...)
      final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
      final Object[] parameters = context.getParameters();

      for ( int i = 0; i < parameters.length; i++ )
      {
        requestMap.put( getArgName( i ), parameters[ i ] );
      }

      final Boolean expressionValue =
        (Boolean) facesContext.getApplication().
          getExpressionFactory().
          createValueExpression( elContext, expression, Boolean.class ).
          getValue( elContext );

      // Removing the parameters (arg0, arg1, ...)
      for ( int i = 0; i < parameters.length; i++ )
      {
        requestMap.remove( getArgName( i ) );
      }

      if ( null == expressionValue || !expressionValue )
      {
        throw new SecurityException();
      }
    }

    return context.proceed();
  }

  @Nullable
  private Secure getSecureAnnotation( final Method m )
  {
    for ( final Annotation a : m.getAnnotations() )
    {
      if ( a instanceof Secure )
      {
        return (Secure) a;
      }
    }
    for ( final Annotation a : m.getDeclaringClass().getAnnotations() )
    {
      if ( a instanceof Secure )
      {
        return (Secure) a;
      }
    }

    return null;
  }

  private String getArgName( int index )
  {
    return "arg" + index;
  }
}
