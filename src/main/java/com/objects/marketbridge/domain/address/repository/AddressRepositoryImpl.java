package com.objects.marketbridge.domain.address.repository;

import com.objects.marketbridge.model.Address;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressJpaRepository addressJpaRepository;

    @Override
    public Address findById(Long id) {
        return addressJpaRepository.findById(id).orElseThrow(EntityExistsException::new);
    }

    @Override
    public void save(Address address) {
        addressJpaRepository.save(address);
    }

    @Override
    public void saveAll(List<Address> addresses) {
        addressJpaRepository.saveAll(addresses);
    }

    @Override
    public List<Address> findByMemberId(Long memberId) {
        return addressJpaRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteAllInBatch(){
        addressJpaRepository.deleteAllInBatch();
    }
}
