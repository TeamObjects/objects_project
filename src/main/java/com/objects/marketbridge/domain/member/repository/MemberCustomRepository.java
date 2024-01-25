package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.model.Member;

public interface MemberCustomRepository {
    //JPA가 지원하지 않는 기능

    Member findByIdWithPointAndAddresses(Long id);

}
