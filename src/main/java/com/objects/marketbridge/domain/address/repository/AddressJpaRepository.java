package com.objects.marketbridge.domain.address.repository;

import com.objects.marketbridge.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressJpaRepository extends JpaRepository<Address, Long> {
    List<Address> findByMemberId(Long memberId);
}
