package com.s.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class sessionHelper {
	
	public void removeMessageFromSession() {
        try {
            System.out.println("removing message form session ");
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("msg");
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
