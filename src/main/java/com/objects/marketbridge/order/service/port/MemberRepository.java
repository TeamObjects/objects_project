package com.objects.marketbridge.order.service.port;

import com.objects.marketbridge.common.infra.entity.AddressEntity;
import com.objects.marketbridge.common.infra.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    MemberEntity findByIdWithPointAndAddresses(Long id);

    MemberEntity findById(Long id);

    List<AddressEntity> findByMemberId(Long memberId);

    void save(AddressEntity addressEntity);

    void saveAll(List<AddressEntity> addressEntities);

    void deleteAllInBatch();
}
