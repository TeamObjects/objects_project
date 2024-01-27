package com.objects.marketbridge.member.repository;

import com.objects.marketbridge.common.infra.entity.AddressEntity;
import com.objects.marketbridge.common.infra.entity.MemberEntity;
import com.objects.marketbridge.common.infra.repository.MemberJpaRepository;
import com.objects.marketbridge.common.infra.repository.AddressJpaRepository;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.model.QAddress;
import com.objects.marketbridge.model.QMember;
import com.objects.marketbridge.model.QPoint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final AddressJpaRepository addressJpaRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberEntity findByIdWithPointAndAddresses(Long id) {
        QMember member = new QMember("member");
        QPoint point = new QPoint("point");
        QAddress address = new QAddress("address");

        MemberEntity findMember = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.point, point).fetchJoin() // 일대일 관계 fetchJoin
                .leftJoin(member.addresses, address).fetchJoin() // 일대다 관계 fetchJoin
                .where(member.id.eq(id))
                .fetchOne();

        if (findMember == null) {
            throw new EntityNotFoundException("엔티티가 존재하지 않습니다");
        }

        return findMember;

    }

    @Override
    public AddressEntity findById(Long id) {
        return addressJpaRepository.findById(id).orElseThrow(EntityExistsException::new);
    }

    @Override
    public void save(AddressEntity addressEntity) {
        addressJpaRepository.save(addressEntity);
    }

    @Override
    public void saveAll(List<AddressEntity> addressEntities) {
        addressJpaRepository.saveAll(addressEntities);
    }

    @Override
    public List<AddressEntity> findByMemberId(Long memberId) {
        return addressJpaRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteAllInBatch(){
        addressJpaRepository.deleteAllInBatch();
    }
}
