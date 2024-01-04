package com.detentionsystem.config;

public class Constant {

	public static final class VersionApi{
		public static final String VERSION = "/v1.0";
	}

	public static final class Claim{

		public static final String ID = "id";
		public static final String EMAIL = "email";
		public static final String ROLE = "role";
	}

	public static final class Token{

		public static final String AUTHORIZATION_HEADER = "Authorization";

		public static final String ACCESS_TOKEN_PREFIX = "Bearer ";

		public static final String ACCESS_TOKEN = "AccessToken";

	}
}
