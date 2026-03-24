package phattrienungdung2ee.webbanhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phattrienungdung2ee.webbanhang.model.Account;
import phattrienungdung2ee.webbanhang.model.Role;
import phattrienungdung2ee.webbanhang.repository.AccountRepository;
import phattrienungdung2ee.webbanhang.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

        Set<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                account.getLogin_name(),
                account.getPassword(),
                authorities
        );
    }

    public Account findByLoginName(String loginName) {
        return accountRepository.findByLoginName(loginName).orElse(null);
    }

    @Transactional
    public void register(String loginName, String rawPassword) {
        if (accountRepository.findByLoginName(loginName).isPresent()) {
            throw new IllegalArgumentException("Tên đăng nhập đã được sử dụng");
        }
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new IllegalStateException("Vai trò ROLE_USER chưa được khởi tạo");
        }
        Account account = new Account();
        account.setLogin_name(loginName);
        account.setPassword(passwordEncoder.encode(rawPassword));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        account.setRoles(roles);
        accountRepository.save(account);
    }
}
