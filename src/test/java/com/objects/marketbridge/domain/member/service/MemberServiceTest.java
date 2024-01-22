//package com.objects.marketbridge.domain.member.service;
//
//
//import com.objects.marketbridge.domain.member.dto.FindPointDto;
//import com.objects.marketbridge.domain.member.repository.MemberCouponJpaRepository;
//import com.objects.marketbridge.domain.model.*;
//import com.objects.marketbridge.domain.member.repository.MemberRepository;
//import com.objects.marketbridge.domain.point.repository.PointRepository;
//import com.objects.marketbridge.global.error.EntityNotFoundException;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.junit.jupiter.api.AfterEach;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//class MemberServiceTest {
//
//    @Autowired MemberService memberService;
//    @Autowired MemberRepository memberRepository;
//    @Autowired MemberCouponJpaRepository memberCouponJpaRepository;
//    @Autowired PointRepository pointRepository;
//
//    @PersistenceContext
//    private EntityManager em;
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
//        memberCouponJpaRepository.deleteAllInBatch();
//    }
//
//    @Test
//    @DisplayName("이메일이 중복이 되었으면 true를 반환한다")
//    public void checkDuplicateEmailTrue() {
//        //given
//            String email = "iiwisii@naver.com";
//
//        //when
//        boolean isDuplicateEmail = memberService.isDuplicateEmail(email);
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
//        boolean isDuplicateEmail = memberService.isDuplicateEmail(email);
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
//        Member member = memberRepository.findByEmail("iiwisii@naver.com").orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
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
//    @Test
//    @DisplayName("멤버의 모든 쿠폰조회 API")
//    public void showAllMemberCoupons(){
//            //given
//            Member member = memberRepository.findByEmail("iiwisii@naver.com")
//                    .orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
//
//            //when
//            createMemberCoupon();
//            List<MemberCoupon> memberCoupons=memberCouponJpaRepository.findByIdWithCoupon(member.getId());
//
//            //then
//            assertThat(memberCoupons).hasSize(2);
//
//        for (MemberCoupon memberCoupon : memberCoupons) { //쿠폰 size 비교 TEST추가하기
//            assertThat(memberCoupons.get(0).getCoupon().getName()).isEqualTo("회원가입기념");
//            assertThat(memberCoupons.get(1).getCoupon().getName()).isEqualTo("고객감사쿠폰");
//
//        }
//
//    }
//
//    private Point createPoint(Member member) {
//        Point point = Point.builder().balance(4500L).member(member).build();
//        point.setMember(member);
//
//        return point;
//    }
//private MemberCoupon createMemberCoupon() {
//    Member member = memberRepository.findByEmail("iiwisii@naver.com").orElseThrow(() -> new EntityNotFoundException("엔티티가 존재하지 않습니다"));
//
//    Coupon coupon1 = Coupon.builder().price(1000L).minimumPrice(20000L).name("회원가입기념").build();
//    Coupon coupon2 = Coupon.builder().price(2000L).name("고객감사쿠폰").build();
//
//    // Coupon 엔터티를 저장
//    em.persist(coupon1);
//    em.flush(); // 즉시 데이터베이스에 반영
//    em.persist(coupon2);
//    em.flush(); // 즉
//
//    MemberCoupon memberCoupon1 = MemberCoupon.builder()
//            .member(member)
//            .coupon(coupon1)
//            .isUsed(false)
//            .usedDate(LocalDateTime.now())
//            .build();
//
//    MemberCoupon memberCoupon2 = MemberCoupon.builder()
//            .member(member)
//            .coupon(coupon2)
//            .isUsed(false)
//            .usedDate(LocalDateTime.now())
//            .build();
//
//    MemberCoupon savedMemberCoupon1 = memberCouponJpaRepository.save(memberCoupon1);
//    MemberCoupon savedMemberCoupon2 = memberCouponJpaRepository.save(memberCoupon2);
//    // 로그로 출력
//
//    return savedMemberCoupon1;
//}
//    //sign up 테스트
//    //sign in 테스트
//}