package com.objects.marketbridge.member.service;

import com.objects.marketbridge.common.domain.Member;
import com.objects.marketbridge.common.domain.Membership;
import com.objects.marketbridge.common.security.dto.JwtTokenDto;
import com.objects.marketbridge.common.security.jwt.JwtTokenProvider;
import com.objects.marketbridge.common.security.user.CustomUserDetails;
import com.objects.marketbridge.member.dto.CheckedResultDto;
import com.objects.marketbridge.member.dto.SignInDto;
import com.objects.marketbridge.member.dto.SignUpDto;
import com.objects.marketbridge.member.service.port.MemberRepository;
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

    public CheckedResultDto isDuplicateEmail(String email){
        boolean isDuplicateEmail = memberRepository.findOptionalByEmail(email).isPresent();
        return CheckedResultDto.builder().checked(isDuplicateEmail).build();
    }

    @Transactional
    public void save(SignUpDto signUpDto) throws BadRequestException {
        boolean isDuplicateEmail = isDuplicateEmail(signUpDto.getEmail()).isChecked();

        if (isDuplicateEmail) throw new BadRequestException("이미 존재하는 이메일 입니다.");

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = signUpDto.toEntity(encodedPassword);
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

        // 이제 orElseThrow 는 전부 MemberRepositoryImpl 에서 처리하기로 했습니다. by 정민우
//        Member findMember = memberRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id)); // id 를 통한 조회실패 예외발생

        Member findMember = memberRepository.findById(id);

        if(findMember.getMembership().equals("BASIC")){//멤버십 WOW 등록
            findMember.setMembership(Membership.WOW.toString());
            memberRepository.save(findMember);
        }else {// 멤버십 BASIC으로 해제
            findMember.setMembership(Membership.BASIC.toString());
            memberRepository.save(findMember);
        }
    }

    // Point 도입 하지 않기로해서 해당 내용은 주석 처리 하였습니다 by 정민우
//    public FindPointDto findPointById(Long id){
//        Member findMemberWithPoint=memberRepository.findByIdWithPoint(id)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));
//
//        return Point.toDto(findMemberWithPoint);
//    }
}