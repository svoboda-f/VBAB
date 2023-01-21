package cz.osu.vbap.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.osu.vbap.model.AppUserDetails;
import cz.osu.vbap.model.User;
import cz.osu.vbap.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " doesn't exist"));
        UserDetails ret = new AppUserDetails(user.getUsername(), user.getPassword());
        return ret;
    }
}
