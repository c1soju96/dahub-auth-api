package com.dahub.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
    MASTER("MASTER", "관리자"),
    USER("USER", "사용자"),
    GUEST("GUEST", "게스트");

    private final String code;
    private final String displayName;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(GUEST);
    }

    public static boolean isRightRole(String inputRole, String findRole){
        return RoleType.of(findRole).equals(RoleType.of(inputRole));
    }

    public boolean isMaster(){
        return this.equals(RoleType.MASTER);
    }


}
