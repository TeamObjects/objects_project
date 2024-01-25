package com.objects.marketbridge.domain.Image;

import com.objects.marketbridge.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {
}
