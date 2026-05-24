package org.example.vecinomerchantserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Code {
    LOGIN_ERROR(Code.ERROR, "账号密码错误"),
    BAD_REQUEST(Code.ERROR, "请求错误"),
    UNAUTHORIZED(401, "未登录"),
    TOKEN_EXPIRED(403, "过期请重新登录"),
    FORBIDDEN(403, "无权限"),
    FILE_NOT_EMPTY(Code.ERROR, "文件不能为空"),
    FILE_NOT_EXIST(Code.ERROR, "文件不存在"),
    FILE_NOT_SUPPORT(Code.ERROR, "文件格式不支持"),
    FILE_NAME_EMPTY(Code.ERROR, "原始文件名为空");
    public static final int ERROR = 400;
    private final int code;
    private final String message;

}
