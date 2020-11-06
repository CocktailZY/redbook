package io.github.nihadguluzade.redbook.service;

import io.github.nihadguluzade.redbook.dao.PrivilegeMapRepository;
import io.github.nihadguluzade.redbook.dao.PrivilegeRepository;
import io.github.nihadguluzade.redbook.dao.UserRepository;
import io.github.nihadguluzade.redbook.entity.PrivilegemapEntity;
import io.github.nihadguluzade.redbook.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {

    private UserRepository userRepository;
    private PrivilegeRepository privilegeRepository;
    private PrivilegeMapRepository privilegeMapRepository;

    @Autowired
    public UsersService(UserRepository userRepository, PrivilegeRepository privilegeRepository,
                        PrivilegeMapRepository privilegeMapRepository) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.privilegeMapRepository = privilegeMapRepository;
    }

    public UsersEntity getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DefaultOAuth2User defaultOAuth2User;
        User user;
        String username;

        if (!(principal instanceof DefaultOAuth2User) && !(principal instanceof User)) {
            return null;
        }
        else if (principal instanceof DefaultOAuth2User) {
            defaultOAuth2User = (DefaultOAuth2User) principal;
            username = defaultOAuth2User.getName();
        }
        else {
            user = (User) principal;
            username = user.getUsername();
        }

        return findByUsername(username);
    }

    public void save(UsersEntity userEntity) {
        // Encode the password
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);

        // Assign default ROLE_USER
        PrivilegemapEntity newPrivilegeMap = new PrivilegemapEntity();
        newPrivilegeMap.setUsersByUserId(userEntity);
        newPrivilegeMap.setPrivilegeByPrivilegeId(privilegeRepository.findByPrivilegeName("ROLE_USER"));
        privilegeMapRepository.save(newPrivilegeMap);
    }

    public UsersEntity findByUsername(String username) {
        UsersEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User with " + username + " username not found.");
        }

        return user;
    }

    public void checkIfUserExists(OAuth2User oAuth2User) {
        if (userRepository.findByUsername(oAuth2User.getName()) == null) {
            saveRedditUser(oAuth2User);
        }
    }

    public void saveRedditUser(OAuth2User oAuth2User) {
        UsersEntity newUser = new UsersEntity();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        newUser.setUsername(oAuth2User.getName());
        newUser.setRedditUserId((String) attributes.get("id"));

        userRepository.save(newUser);
    }

}
