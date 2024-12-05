package com.moreira.dscommerce.services;

import com.moreira.dscommerce.entities.Role;
import com.moreira.dscommerce.entities.User;
import com.moreira.dscommerce.projections.UserDetailsProjection;
import com.moreira.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if (result.isEmpty())
            throw new UsernameNotFoundException("User not found");

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection proj : result) {
            user.addRole(new Role(proj.getRoleID(), proj.getAuthority()));
        }

        return user;
    }
}