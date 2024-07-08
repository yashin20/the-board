package com.project.the_board.config;

import com.project.the_board.entity.Member;
import com.project.the_board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsServiceAuthorities implements UserDetailsService {

    @Autowired private MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));

        return new User(member.getUsername(), member.getPassword(), getAuthorities(member));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole().name()));
        return authorities;
    }
}
