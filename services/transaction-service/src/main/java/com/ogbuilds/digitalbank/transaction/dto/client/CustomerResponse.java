package com.ogbuilds.digitalbank.transaction.dto.client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
