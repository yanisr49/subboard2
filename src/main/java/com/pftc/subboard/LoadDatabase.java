package com.pftc.subboard;

import com.pftc.subboard.models.role.ERole;
import com.pftc.subboard.models.role.Role;
import com.pftc.subboard.models.user.User;
import com.pftc.subboard.repositories.RoleRepository;
import com.pftc.subboard.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepo, RoleRepository roleRepo) {
    Role roleAdmin = new Role(ERole.ROLE_ADMIN);
    Role roleUser = new Role(ERole.ROLE_USER);

    User user1 = new User("John doe", "azerty");
    user1.addRole(roleUser);
    
    User user2 = new User("root", "root");
    user2.addRole(roleAdmin);
    user2.addRole(roleUser);
    
    return args -> {
      log.info("Preloading " + roleRepo.save(roleAdmin));
      log.info("Preloading " + roleRepo.save(roleUser));

      log.info("Preloading " + userRepo.save(user1));
      log.info("Preloading " + userRepo.save(user2));
    };
  }
}
