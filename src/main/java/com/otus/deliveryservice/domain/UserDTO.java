
package com.otus.deliveryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
