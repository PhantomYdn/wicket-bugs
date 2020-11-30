package org.orienteer;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class AuthWebSession extends AuthenticatedWebSession {

	public AuthWebSession(Request request) {
		super(request);
	}

	@Override
	protected boolean authenticate(String username, String password) {
		return true;
	}

	@Override
	public Roles getRoles() {
		return isSignedIn()?new Roles(Roles.ADMIN): new Roles("GUEST");
	}
}
