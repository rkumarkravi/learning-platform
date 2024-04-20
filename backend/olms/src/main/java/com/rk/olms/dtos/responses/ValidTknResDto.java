package com.rk.olms.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidTknResDto {
    private boolean isValid;
    private UserLoginResDto userDetails;
}
