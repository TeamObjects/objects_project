package com.objects.marketbridge.member.controller;

import com.objects.marketbridge.domain.member.dto.*;
import com.objects.marketbridge.member.dto.CheckedResultDto;
import com.objects.marketbridge.member.dto.FindPointDto;
import com.objects.marketbridge.member.dto.SignInDto;
import com.objects.marketbridge.member.dto.SignUpDto;
import com.objects.marketbridge.member.service.MemberService;
import com.objects.marketbridge.common.security.annotation.AuthMemberId;
import com.objects.marketbridge.common.security.annotation.GetAuthentication;
import com.objects.marketbridge.common.security.dto.JwtTokenDto;
import com.objects.marketbridge.common.interceptor.ApiResponse;
import com.objects.marketbridge.common.security.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.objects.marketbridge.member.constant.MemberConst.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-email")
    public ApiResponse<CheckedResultDto> checkDuplicateEmail(@RequestParam(name="email") String email) {
        CheckedResultDto checked = memberService.isDuplicateEmail(email);
        return ApiResponse.ok(checked);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signUp(@Valid @RequestBody SignUpDto signUpDto) throws BadRequestException {
        memberService.save(signUpDto);
        return ApiResponse.create();
    }

    @PostMapping("/sign-in")
    public ApiResponse<JwtTokenDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        JwtTokenDto jwtTokenDto = memberService.signIn(signInDto);
        return ApiResponse.ok(jwtTokenDto);
    }

    @DeleteMapping("/sign-out")
    public ApiResponse<Void> signOut(@AuthMemberId Long memberId) {
        memberService.signOut(memberId);
        return ApiResponse.of(HttpStatus.OK, LOGGED_OUT_SUCCESSFULLY, null);
    }

    @PutMapping("/re-issue")
    public ApiResponse<JwtTokenDto> reIssueToken(@GetAuthentication CustomUserDetails principal) {
        JwtTokenDto jwtTokenDto = memberService.reIssueToken(principal);
        return ApiResponse.ok(jwtTokenDto);
    }

    @PostMapping("/membership/{id}")
    public void createMembership(@PathVariable Long id){
        // 서비스-레파지토리
        //서비스는 레파지토리에 요청 보내고, dto 매핑하고
        // 0. 결제 진행 -> paymentService ready -> pg_토큰 값 내려줌
        // approve url -> "/membership/approve/{pg_token}"


    }

    @PostMapping("/membership/approve/{}")
    public void approveMembership(@PathVariable String id, @RequestParam String pgToken){
        // tid 조회 해서
        //승인하기

        // 1. member -> wow변경 update
        //2. 구독 결제 정보 저장 -> 구독 결제 카드 정보 create sid, tid
        //3. 구독 정보 저장 -> 매월 몇일 결제, 금액 create
        // -> 트랜젝션
    }

    //구독해제
    @PatchMapping("/membership/{id}")
    public void changeMembership(@PathVariable Long id){
        memberService.changeMemberShip(id);
    }

    @GetMapping("/point/{id}")
    public ApiResponse<FindPointDto> findPointById(@PathVariable Long id){

        FindPointDto memberPoint = memberService.findPointById(id);
        return ApiResponse.of(HttpStatus.OK, memberPoint);
    }
}
