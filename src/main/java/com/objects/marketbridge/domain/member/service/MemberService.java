package com.objects.marketbridge.domain.member.service;

import com.objects.marketbridge.domain.member.dto.FindPointDto;
import com.objects.marketbridge.domain.member.dto.MemberCouponDto;
import com.objects.marketbridge.domain.member.dto.SignUpDto;
import com.objects.marketbridge.domain.member.repository.MemberCouponJpaRepository;
import com.objects.marketbridge.domain.model.*;
import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.global.security.jwt.JwtToken;
import com.objects.marketbridge.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberCouponJpaRepository memberCouponJpaRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public boolean isDuplicateEmail(String email){
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void save(SignUpDto signUpDto) throws BadRequestException {
        boolean isDuplicateEmail = isDuplicateEmail(signUpDto.getEmail());

        if (isDuplicateEmail) throw new BadRequestException("이미 존재하는 이메일 입니다.");

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = signUpDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    @Transactional
    public JwtToken signIn(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public void changeMemberShip(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id)); // id 를 통한 조회실패 예외발생

        if(findMember.getMembership().equals("BASIC")){//멤버십 WOW 등록
            findMember.setMembership(Membership.WOW.toString());
            memberRepository.save(findMember);
        }else {// 멤버십 BASIC으로 해제
            findMember.setMembership(Membership.BASIC.toString());
            memberRepository.save(findMember);
        }
    }

    public FindPointDto findPointById(Long id){
        Member findMemberWithPoint=memberRepository.findByIdWithPoint(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));

        return Point.toDto(findMemberWithPoint);
    }

    public List<MemberCouponDto> showAllMemberCoupons (Long memberId){
        List<MemberCoupon> couponList = memberCouponJpaRepository.findByIdWithCoupon(memberId);

        return couponList.stream()
                .map(MemberCouponDto::from)
                .collect(Collectors.toList());
    }
}
