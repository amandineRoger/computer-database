package com.excilys.cdb.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.model.UserRole;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserService.class);
    private static final String TAG = "UserService says : ";

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        LOGGER.debug(TAG + " f_loadUserByUsername");

        User user = userDAO.findByName(username);

        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);

    }

    /**
     * Create a org.springframework.security.core.userdetails.User from a
     * com.excilys.cdb.model.User.
     *
     * @param user
     *            the user to create from
     * @param authorities
     *            user's roles
     * @return userdetails.User construct from user data
     */
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(
            User user, List<GrantedAuthority> authorities) {
        LOGGER.debug(TAG + " f_buildUserForAuthentication");
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), authorities);
    }

    /**
     * Construct a list with user roles. If the role parameter is "ROLE_ADMIN",
     * "ROLE_USER" is automatically add in the list.
     *
     * @param role
     *            user's role
     * @return List of GrantedAuthority with all the user's roles
     */
    private List<GrantedAuthority> buildUserAuthority(UserRole role) {
        LOGGER.debug(TAG + " f_buildUserAuthority");
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(role.toString()));
        if (role == UserRole.ROLE_ADMIN) { // FIXME
            grantedAuthorityList.add(
                    new SimpleGrantedAuthority(UserRole.ROLE_USER.toString()));
        }

        return grantedAuthorityList;
    }

}
