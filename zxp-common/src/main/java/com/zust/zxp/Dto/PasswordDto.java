package com.zust.zxp.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PasswordDto {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
