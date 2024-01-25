package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    //JpaRepository에서 제공되는 기본메서드 사용
    Optional<Member> findByEmail(String email);

    Optional<Member> findMemberById(Long id);

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.addresses WHERE m.id = :memberId")
    Optional<Member> findByIdWithAddresses(@Param("memberId") Long id);

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.point WHERE m.id = :memberId")
    Optional<Member> findByIdWithPoint(@Param("memberId") Long id);

}
