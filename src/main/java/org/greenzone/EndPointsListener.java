/*
    ================================================================================================
    This code is part of the Greenzone software.

    GreenZone is a closed source software of EN (Eliah Ngugi) which is owned by EN.
    
        Elijah Ngugi Gachuki
    
    This software is private closed source software.
    
    For further details look at or request greenzone-license.txt for further details.

    Copyright (C) 2024 

    Email:  elijah.ngugi.gachuki@gmail.com
    Domain: N/A

    ================================================================================================
    Author : EN
    ================================================================================================
 */
package org.greenzone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author EN - Dec 15, 2024
 */
@Component
public class EndPointsListener {

    @EventListener
    public void handleContextRefresh( ContextRefreshedEvent event ) {

        ApplicationContext applicationContext = event.getApplicationContext();
        applicationContext.getBean( RequestMappingHandlerMapping.class )
                .getHandlerMethods()
                .forEach( this::displayEndpointInfo );
    }


    private void displayEndpointInfo( RequestMappingInfo info, HandlerMethod method ) {

        System.out.println( "info: " + info.toString() );

        if ( info.getPatternsCondition() != null ) {
            System.out.println( "Mapped URL path: " + info.getPatternsCondition().getPatterns() );
        }
        System.out.println( "Handler method: " + method );
        System.out.println( "----" );
    }
}
