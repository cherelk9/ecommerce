package cm.backend.ecommerce.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cm.backend.ecommerce.repositories.UsersRepository;
import cm.backend.ecommerce.security.CustomerUserDetails;
import cm.backend.ecommerce.utils.UserUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user = usersRepository
                .findByUsername(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(UserUtils.USER_NOT_FOUND));

        return new CustomerUserDetails(user);

    }

}
