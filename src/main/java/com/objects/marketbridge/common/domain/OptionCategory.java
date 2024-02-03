package com.objects.marketbridge.common.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionCategory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "optionCategory")
    private List<Option> options;

    @Builder
    private OptionCategory(String name) {
        this.name = name;
    }

    public void addOptions(Option option){
        options.add(option);
        option.setOptionCategory(this);
    }
}