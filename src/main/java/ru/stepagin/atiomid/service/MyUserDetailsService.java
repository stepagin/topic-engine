package ru.stepagin.atiomid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.stepagin.atiomid.entity.PersonEntity;
import ru.stepagin.atiomid.repository.PersonRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepo personRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonEntity person = personRepo.findById(username).orElse(null);
        if (person == null) {
            throw new UsernameNotFoundException("User with name " + username + " not found");
        }
        return User.builder()
                .username(person.getName())
                .password(person.getPassword())
                .build();
    }
}
