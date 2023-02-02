package io.vdubovsky.retailapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorMessage {
    private int errorCode;
    private String errorDescription;
}
