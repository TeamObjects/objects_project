package com.objects.marketbridge.domain.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {
    @NotBlank()
    @Email()
    private String email;

    @NotBlank()
    private String password;
}
