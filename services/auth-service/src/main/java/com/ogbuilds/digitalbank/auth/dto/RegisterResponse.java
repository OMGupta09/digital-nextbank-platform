package com.ogbuilds.digitalbank.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private Long userId;

    private String email;

    private String message;

}