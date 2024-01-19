//package com.objects.marketbridge.domain.member.service;
//
//
//import com.objects.marketbridge.domain.member.dto.FindPointDto;
//import com.objects.marketbridge.domain.model.Member;
//import com.objects.marketbridge.domain.member.repository.MemberRepository;
//import com.objects.marketbridge.domain.model.Membership;
//import com.objects.marketbridge.domain.model.Point;
//import com.objects.marketbridge.domain.point.repository.PointRepository;
//import jakarta.persistence.EntityNotFoundException;
//import com.objects.marketbridge.global.security.jwt.JwtTokenProvider;
//import org.junit.jupiter.api.AfterEach;
//import com.objects.marketbridge.domain.model.SocialType;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    PointRepository pointRepository;
//
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//
//    @BeforeEach
//    void init() {
//        Member member = Member.builder()
//                .email("iiwisii@naver.com")
//                .name("박정인")
//                .password("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4")
//                .phoneNo("01073784758")
//                .isAgree(true)
//                .isAlert(true)
//                .membership(Membership.WOW.toString())
//                .socialType(SocialType.DEFAULT.toString())
//                .build();
//
//        memberRepository.save(member);
//    }
//
//    @AfterEach
//    public void rollback(){
//        memberRepository.deleteAll();
//        pointRepository.deleteAllInBatch();
//    }
//
//    @Test
//    @DisplayName("이메일이 중복이 되었으면 true를 반환한다")
//    public void checkDuplicateEmailTrue() {
//        //given
//            String email = "iiwisii@naver.com";
//
//        //when
//        boolean isDuplicateEmail = (memberService.isDuplicateEmail(email)).isChecked();
//
//        //then
//        assertThat(isDuplicateEmail).isTrue();
//    }
//
//    @Test
//    @DisplayName("이메일이 중복이 되지 않았으면 false를 반환한다")
//    public void checkDuplicateEmailFalse() {
//        //given
//        String email = "iiii@naver.com";
//
//        //when
//        boolean isDuplicateEmail = (memberService.isDuplicateEmail(email)).isChecked();
//
//        //then
//        assertThat(isDuplicateEmail).isFalse();
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("멤버십 변경 API")
//    public void testUpdateWowMemberShip(){
//        //given
//        Member member = memberRepository.findByEmail("iiwisii@naver.com").orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
//        String memberShipData = "BASIC";
//        //when
//        memberService.changeMemberShip(member.getId());
//        //then
//        assertThat(member.getMembership()).isEqualTo(memberShipData);
//    }
//
//
//    @Test
//    @DisplayName("포인트 조회 API")
//    public void testFindPointById(){
//            //given
//        Member member = memberRepository.findByEmail("iiwisii@naver.com").orElseThrow(EntityNotFoundException::new);
//        Point point = createPoint(member);
//        pointRepository.save(point);
//
//            //when
//        FindPointDto findMember = memberService.findPointById(member.getId());
//
//
//            //then
//        assertThat(findMember.getBalance()).isEqualTo(4500L);
//
//    }
//
//
//    private Point createPoint(Member member) {
//        Point point = Point.builder().balance(4500L).member(member).build();
//        point.setMember(member);
//
//        return point;
//    }
//
//    //sign up 테스트
//    @Test
//    public void signUpTest() {
//        //given
//
//        //when
//
//        //then
//    }
//
//    //sign in 테스트
//    @Test
//    public void signInTest() {
//        //given
//
//        //when
//
//        //then
//    }
//
//}