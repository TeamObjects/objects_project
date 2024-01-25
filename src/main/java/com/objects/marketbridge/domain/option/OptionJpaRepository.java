package com.objects.marketbridge.domain.option;

import com.objects.marketbridge.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionJpaRepository extends JpaRepository<Option, Long> {

    Option findByName(String name);
}
