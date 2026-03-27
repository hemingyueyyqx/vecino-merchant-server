package org.example.vecinomerchantserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vecinomerchantserver.exception.Code;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo {
    private int code;
    private String message;
    private Object data;

    private static final ResultVo EMPTY = ResultVo.builder()
            .code(200)
            .build();

    public static ResultVo ok() {
        return EMPTY;
    }

    public static ResultVo success(Object data) {
        return ResultVo.builder()
                .code(200)
                .data(data)
                .build();
    }

    public static ResultVo error(Code code) {
        return ResultVo.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();

    }
    //通用
    public static ResultVo error(int code, String message) {
        return ResultVo.builder()
                .code(code)
                .message(message)
                .build();

    }
}
