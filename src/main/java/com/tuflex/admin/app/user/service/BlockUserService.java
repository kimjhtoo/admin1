package com.tuflex.admin.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class BlockUserService {
  @Autowired
  private SessionRegistry sessionRegistry;

  public void blockUser(String username) {
    System.out.println(sessionRegistry.getAllPrincipals().size());
    for (Object principal : sessionRegistry.getAllPrincipals()) {
      if (principal instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) principal;
        if (userDetails.getUsername().equals(username)) {
          for (SessionInformation session : sessionRegistry.getAllSessions(userDetails, true)) {
            System.out.println(session.getSessionId());
            session.expireNow();
          }
        }
      } else {
        System.out.println("not instance of Admin");
      }
    }
  }
}