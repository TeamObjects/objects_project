package com.objects.marketbridge.global.security.user;

import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));

        // db에 저장하지 않고 여기서 권한 부여
        List<String> roles = new ArrayList<>();
        roles.add("USER");

      return new CustomUserDetails(member.getId(), member.getEmail(), passwordEncoder.encode(member.getPassword()), roles);
    }
}
