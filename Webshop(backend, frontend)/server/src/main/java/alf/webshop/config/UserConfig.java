package alf.webshop.config;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import alf.webshop.domain.Address;
import alf.webshop.domain.ERole;
import alf.webshop.domain.Role;
import alf.webshop.domain.User;
import alf.webshop.service.RoleService;
import alf.webshop.service.UserService;

@Configuration
public class UserConfig {
    
    Logger logger = LoggerFactory.getLogger(UserConfig.class);
    
    @Bean
    CommandLineRunner userConfigCommandLineRunner(RoleService roleService, UserService userService) {
        
        return args -> {
            
            // save all roles to db
            for (ERole eRole : ERole.values()) {
                try {
                    roleService.createRole(new Role(eRole));
                } catch (EntityExistsException e) {
                    logger.warn(e.getMessage());
                }
            }

            if(!userService.userExistsByRole(ERole.ROLE_ADMIN)){
                User admin = new User(
                    "admin",
                    "admin",
                    "Laci",
                    "Bíró",
                    "admin@webshop.hu",
                    "0036301234567",
                    new Address("Country", "county", "city", "zipCode", "streetName", 1, "door"),
                    null
                );
    
                userService.createAdminUser(admin);
            }
        };
    }
}
