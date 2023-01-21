package cz.osu.vbab.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.osu.vbab.model.User;
import cz.osu.vbab.repository.UserRepository;
import cz.osu.vbab.model.AppUserDetails;

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
