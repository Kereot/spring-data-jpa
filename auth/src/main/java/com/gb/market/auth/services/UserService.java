package com.gb.market.auth.services;

import com.gb.market.api.dto.RegistrationUserDto;
import com.gb.market.auth.entities.Privilege;
import com.gb.market.auth.entities.User;
import com.gb.market.auth.repositories.PrivilegeRepository;
import com.gb.market.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrivilegeRepository privilegeRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapPrivilegesToAuthorities(user.getPrivileges()));
    }

    private Collection<? extends GrantedAuthority> mapPrivilegesToAuthorities(Collection<Privilege> privileges) {
        return privileges.stream().map(privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList());
    }

    public void createUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setPrivileges(List.of(privilegeRepository.findByName("READ_ONLY").get()));
        userRepository.save(user);
    }
}