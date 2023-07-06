package com.chandu.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenricResponse <T>{

    private Integer statusCode;
    private String message;
    private Integer code;

    private T data;
}
