/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.api.impl;

import java.util.Date;

import org.springframework.social.MissingAuthorizationException;

public class MadAbstractFacebookOperations {
	
	private final boolean isAuthorized;

	public MadAbstractFacebookOperations(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public String getAdAccountId(String id) {
		return "act_" + id;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException("facebook");
		}
	}
	
	public String getUnixTime(Date date) {
		return date != null ? String.valueOf(date.getTime() / 1000L) : "";
	}
	
}