package com.objects.marketbridge.domain.product.repository.option;

import com.objects.marketbridge.domain.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    Option findByName(String name);
}
