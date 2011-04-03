package com.eni.dvtejb.clientJSF2.beans;

import javax.faces.bean.*;
import javax.faces.context.*;

@ManagedBean
@ApplicationScoped
public class Context {
  public String getPath() {
    ExternalContext context =
      FacesContext.getCurrentInstance().getExternalContext();
    return(context.getRequestContextPath());
  }

}