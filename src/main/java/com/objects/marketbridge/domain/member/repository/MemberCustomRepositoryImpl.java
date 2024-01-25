package com.objects.marketbridge.domain.member.repository;

import com.objects.marketbridge.model.Member;
import com.objects.marketbridge.model.QAddress;
import com.objects.marketbridge.model.QMember;
import com.objects.marketbridge.model.QPoint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByIdWithPointAndAddresses(Long id) {
        QMember member = new QMember("member");
        QPoint point = new QPoint("point");
        QAddress address = new QAddress("address");

        Member findMember = queryFactory
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
}
