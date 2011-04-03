package com.eni.dvtejb.clientStruts2.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AutorisationInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;
	private static final String CLE_UTILISATEUR = "leclient";

	public String intercept(ActionInvocation invocation) throws Exception {		
		Map session = invocation.getInvocationContext().getSession();
		
		if(session.get(CLE_UTILISATEUR) == null) {
			addActionError(invocation, "Vous devez vous identifier pour accéder à cette page !");
			return Action.ERROR;
		}
		return invocation.invoke();
	}

	private void addActionError(ActionInvocation invocation, String message) {
		
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}
}
