package com.objects.marketbridge.member.service;

import com.objects.marketbridge.common.service.KakaoPayService;
import com.objects.marketbridge.member.dto.CheckedResultDto;
import com.objects.marketbridge.member.dto.FindPointDto;
import com.objects.marketbridge.member.dto.SignInDto;
import com.objects.marketbridge.member.dto.SignUpDto;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import com.objects.marketbridge.common.security.dto.JwtTokenDto;
import com.objects.marketbridge.common.security.jwt.JwtTokenProvider;
import com.objects.marketbridge.common.domain.enums.Membership;
import com.objects.marketbridge.common.infra.entity.PointEntity;
import com.objects.marketbridge.common.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final KakaoPayService kakaoPayService;

    public CheckedResultDto isDuplicateEmail(String email){
        boolean isDuplicateEmail = memberRepository.findByEmail(email).isPresent();
        return CheckedResultDto.builder().checked(isDuplicateEmail).build();
    }

    @Transactional
    public void save(SignUpDto signUpDto) throws BadRequestException {
        boolean isDuplicateEmail = isDuplicateEmail(signUpDto.getEmail()).isChecked();

        if (isDuplicateEmail) throw new BadRequestException("이미 존재하는 이메일 입니다.");

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        MemberEntity member = signUpDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    public JwtTokenDto signIn(SignInDto signInDto) {

        String username = signInDto.getEmail();
        String password = signInDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(principal);
    }

    public void signOut(Long memberId) {
        jwtTokenProvider.deleteToken(memberId);
    }

    public JwtTokenDto reIssueToken(CustomUserDetails principal) {
        return jwtTokenProvider.generateToken(principal);
    }

    @Transactional
    public void changeMemberShip(Long id){
        MemberEntity findMember = memberRepository.findById(id)
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
        MemberEntity findMemberWithPoint=memberRepository.findByIdWithPoint(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));

        return PointEntity.toDto(findMemberWithPoint);
    }

    public void createMembership() {


    }
}
