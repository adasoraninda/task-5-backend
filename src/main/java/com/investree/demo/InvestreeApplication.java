package com.investree.demo;

import com.investree.demo.model.Role;
import com.investree.demo.model.Users;
import com.investree.demo.repository.RoleRepository;
import com.investree.demo.repository.UsersRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InvestreeApplication implements CommandLineRunner {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private RoleRepository rolesRepository;

  public static void main(String[] args) {
    SpringApplication.run(InvestreeApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (usersRepository.count() == 0) {
      ArrayList<Role> roles = new ArrayList<>();
      roles.add(
          new Role(
              null,
              "ADMIN"
          )
      );

      rolesRepository.save(roles.get(0));

      usersRepository.save(
          new Users(
              null,
              "username",
              passwordEncoder.encode("password"),
              true,
              null,
              roles
          )
      );
    }
  }
}
