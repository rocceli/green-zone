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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author EN - Dec 15, 2024
 */
@SpringBootApplication
@ComponentScan( basePackages = { "org.greenzone" } )
public class GreenZoneApplication {

    private static ConfigurableApplicationContext context;

    public static void main( String[] args ) {

        context = SpringApplication.run( GreenZoneApplication.class, args );
    }


    public static void restart() {

        ApplicationArguments args = context.getBean( ApplicationArguments.class );

        Thread thread = new Thread( () -> {
            context.close();
            context = SpringApplication.run( GreenZoneApplication.class, args.getSourceArgs() );
        } );

        thread.setDaemon( false );
        thread.start();
    }
}
