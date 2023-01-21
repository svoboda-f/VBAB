package cz.osu.vbab.service;

import java.util.Optional;

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
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with name " + username + " doesn't exist");
        UserDetails ret = new AppUserDetails(user.get().getUsername(), user.get().getPassword());
        return ret;
    }
}
