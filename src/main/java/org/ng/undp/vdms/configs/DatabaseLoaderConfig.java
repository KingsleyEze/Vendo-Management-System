package org.ng.undp.vdms.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Created by abdulhakim on 10/12/16.
 */
public class DatabaseLoaderConfig implements CommandLineRunner  {

    @Override
    public void run(String... strings) throws Exception {


       //Your code here...
        SecurityContextHolder.clearContext();
    }
}
