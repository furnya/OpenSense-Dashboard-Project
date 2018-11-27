/**
 * 
 */
package com.opensense.dashboard.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Result of an asyncronous call.
 * @author Denes
 *
 */
public enum ActionResultType implements IsSerializable {
	SUCCESSFUL,
	FAILED;
	
	ActionResultType() {
		// Empty default constructor for GWT serialization.
	}
}
