package com.objects.marketbridge.domain.address.repository;

import com.objects.marketbridge.common.infra.entity.AddressEntity;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
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
class AddressEntityRepositoryImplTest {

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
        MemberEntity member = MemberEntity.builder().email(testEmail).build();
        memberRepository.save(member);
        AddressEntity addressEntity = AddressEntity.builder().member(member).build();
        addressRepository.save(addressEntity);

        //when
        MemberEntity findMember = memberRepository.findByEmail(testEmail).orElseThrow(IllegalArgumentException::new);
        AddressEntity findAddressEntity = addressRepository.findByMemberId(findMember.getId()).get(0);

        //then
        assertThat(findAddressEntity.getMember().getEmail()).isEqualTo(testEmail);
    }
}