package com.investree.demo.view;

import com.investree.demo.model.Users;
import java.util.Map;
import org.springframework.data.domain.Page;

@SuppressWarnings("rawtypes")
public interface UsersService {

  Map save(Users users);

  Page<Users> list(Integer page, Integer size);

}
