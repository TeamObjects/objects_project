package com.objects.marketbridge.domain.address.repository;

import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.model.Address;
import com.objects.marketbridge.model.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AddressRepositoryImplTest {

    @Autowired
    AddressRepository addressRepository;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    void tearDown(){
        addressRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("findByMemberId 테스트")
    @Test
    void findByMemberId(){
        //given
        String testEmail = "test@email.com";
        Member member = Member.builder().email(testEmail).build();
        memberRepository.save(member);
        Address address = Address.builder().member(member).build();
        addressRepository.save(address);

        //when
        Member findMember = memberRepository.findByEmail(testEmail).orElseThrow(IllegalArgumentException::new);
        Address findAddress = addressRepository.findByMemberId(findMember.getId()).get(0);

        //then
        assertThat(findAddress.getMember().getEmail()).isEqualTo(testEmail);
    }
}