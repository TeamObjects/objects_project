package com.objects.marketbridge.member.service.port;

import com.objects.marketbridge.common.infra.entity.AddressEntity;
import com.objects.marketbridge.common.infra.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    MemberEntity findByIdWithPointAndAddresses(Long id);

    MemberEntity findMemberById(Long id);

    AddressEntity findAddressById(Long id);

    List<AddressEntity> findByMemberId(Long memberId);

    void save(AddressEntity addressEntity);

    void saveAll(List<AddressEntity> addressEntities);

    void deleteAllInBatch();
}
