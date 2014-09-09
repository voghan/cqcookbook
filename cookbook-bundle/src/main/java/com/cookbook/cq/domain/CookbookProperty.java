package com.cookbook.cq.domain;

public class CookbookProperty extends Base {

	private static final long serialVersionUID = 5915137708304168791L;

	private String configuration;
	
	//cq.environemnt
	private String environemnt;
	public static final String CQ_ENVIRONMENT = "cq.environemnt";
	
	//ws.store.baseUrl
	private String storeBaseUrl;
	public static final String WS_STORE_BASEURL = "ws.store.baseUrl";
	
	
}
