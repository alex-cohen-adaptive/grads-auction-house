package com.weareadaptive.auction.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthentication {
  @Override
  public String getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
