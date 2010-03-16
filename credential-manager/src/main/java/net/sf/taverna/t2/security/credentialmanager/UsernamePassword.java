package net.sf.taverna.t2.security.credentialmanager;

import java.util.Arrays;

public class UsernamePassword {
	private char[] password;
	private boolean shouldSave = false;
	private String username;

	public char[] getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isShouldSave() {
		return shouldSave;
	}

	public void resetPassword() {
		if (this.password == null) {
			return;
		}
		Arrays.fill(this.password, '\u0000');
	}

	public void setPassword(char[] password) {
		resetPassword();
		this.password = password;
	}

	public void setShouldSave(boolean shouldSave) {
		this.shouldSave = shouldSave;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	protected void finalize() throws Throwable {
		resetPassword();
	}


}
