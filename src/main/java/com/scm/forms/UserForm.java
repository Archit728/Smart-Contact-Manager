package com.scm.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
//class to hold data from the signup form
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
}
