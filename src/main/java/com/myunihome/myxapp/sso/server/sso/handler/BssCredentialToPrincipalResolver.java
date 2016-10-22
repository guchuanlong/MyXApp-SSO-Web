package com.myunihome.myxapp.sso.server.sso.handler;

import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.PrincipalResolver;
import org.jasig.cas.authentication.principal.SimplePrincipal;

import com.myunihome.myxapp.sso.server.sso.principal.BssCredentials;

public class BssCredentialToPrincipalResolver implements PrincipalResolver {

	public BssCredentialToPrincipalResolver() {}

	@Override
	public final Principal resolve(Credential credential) {
		BssCredentials bssCredentials = (BssCredentials) credential;

		String principalId = bssCredentials.getUsername();
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("tenantId", bssCredentials.getTenantId());
		attributes.put("tenantName", bssCredentials.getTenantName());
		attributes.put("staffNo", bssCredentials.getStaffNo());
		attributes.put("staffName", bssCredentials.getStaffName());
        attributes.put("staffId", bssCredentials.getStaffId());
        attributes.put("staffnoId", bssCredentials.getStaffnoId());
        attributes.put("state", bssCredentials.getState());
        attributes.put("activeTime", bssCredentials.getActiveTime());
        attributes.put("inactiveTime", bssCredentials.getInactiveTime());
		return new SimplePrincipal(principalId , attributes);
	}

	@Override
	public boolean supports(Credential paramCredential) {
		return paramCredential!=null&&BssCredentials.class.isAssignableFrom(paramCredential.getClass());
	}

}
