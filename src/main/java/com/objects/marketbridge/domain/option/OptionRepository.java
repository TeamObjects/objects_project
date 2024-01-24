package com.objects.marketbridge.domain.option;

import com.objects.marketbridge.model.Option;

public interface OptionRepository {

    void save(Option option);

    Option findById(Long id);

    Option findByName(String name);
}
