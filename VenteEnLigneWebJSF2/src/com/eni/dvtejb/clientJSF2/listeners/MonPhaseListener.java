package com.eni.dvtejb.clientJSF2.listeners;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MonPhaseListener  implements PhaseListener
{
  private static final long serialVersionUID = 1L;

  public MonPhaseListener()
  {
  }

  public void beforePhase(PhaseEvent pe)
  {
   if (pe.getPhaseId() == PhaseId.RESTORE_VIEW)
      System.out.println("Nouvelle requête en cours ...");        
      System.out.println("avant - " + pe.getPhaseId().toString());
  }

  public void afterPhase(PhaseEvent pe)
  { 
    System.out.println("après - " + pe.getPhaseId().toString());
    if (pe.getPhaseId() == PhaseId.RENDER_RESPONSE)
     System.out.println("Fin d'analyse de la requête.\n");
  }

  public PhaseId getPhaseId()
  {  
    return PhaseId.ANY_PHASE;
  }
}