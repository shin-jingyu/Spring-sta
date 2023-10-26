package com.sta.domain;

public interface OAuth2UserInfo {
	String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
