package com.investree.demo.view;

import com.investree.demo.model.Users;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

@SuppressWarnings("rawtypes")
public interface UsersService extends UserDetailsService {

  Map save(Users users);

  Page<Users> list(Integer page, Integer size);

}
