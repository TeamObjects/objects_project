package com.objects.marketbridge.domain.member.controller;


import com.objects.marketbridge.domain.member.dto.IsCheckedDto;
import com.objects.marketbridge.domain.member.dto.SignUpDto;
import com.objects.marketbridge.domain.member.dto.SignInDto;
import com.objects.marketbridge.domain.member.dto.FindPointDto;
import com.objects.marketbridge.domain.member.service.MemberService;
import com.objects.marketbridge.global.security.annotation.AuthMemberId;
import com.objects.marketbridge.global.security.annotation.GetAuthentication;
import com.objects.marketbridge.global.security.dto.JwtTokenDto;
import com.objects.marketbridge.global.common.ApiResponse;
import com.objects.marketbridge.global.security.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-email")
    public ApiResponse<IsCheckedDto> checkDuplicateEmail(@RequestParam(name="email") String email) {
        return ApiResponse.ok(memberService.isDuplicateEmail(email));
    }

    @PostMapping("/sign-up")
    public ApiResponse<Void> registerUser(@Valid @RequestBody SignUpDto signUpDto) throws BadRequestException {
        memberService.save(signUpDto);
        return ApiResponse.create();
    }

    @PostMapping("/sign-in")
    public ApiResponse<JwtTokenDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        return ApiResponse.ok(memberService.signIn(signInDto));
    }

    @DeleteMapping("/sign-out")
    public ApiResponse<Void> signOut(@AuthMemberId Long memberId) {
        memberService.signOut(memberId);
        return ApiResponse.of(HttpStatus.OK, "Logged out successfully.", null);
    }

    @PutMapping("/re-issue")
    public ApiResponse<JwtTokenDto> reIssueToken(@GetAuthentication CustomUserDetails principal) {
        return ApiResponse.ok(memberService.reIssueToken(principal));
    }

    @GetMapping("/test")
    public Long authTest(@AuthMemberId Long id) {
        return id;
    }

    @GetMapping("/membership/{id}")
    public void changeMembership(@PathVariable Long id){
        memberService.changeMemberShip(id);
    }

    @GetMapping("/point/{id}")
    public ApiResponse<FindPointDto> findPointById(@PathVariable Long id){

        FindPointDto memberPoint = memberService.findPointById(id);
        return ApiResponse.of(HttpStatus.OK,"completed",memberPoint);
    }

    @GetMapping("/coupon/{id}")
    public ApiResponse<List<MemberCouponDto>> findAllCouponById(@PathVariable Long id){

        List<MemberCouponDto> memberCouponDtos = memberService.showAllMemberCoupons(id);

        return ApiResponse.ok(memberCouponDtos);
    }
}
