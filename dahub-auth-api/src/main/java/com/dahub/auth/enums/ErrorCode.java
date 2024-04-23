package com.dahub.auth.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "주문 상태가 유효하지 않습니다."),
    INVALID_SHIPMENT_STATUS(HttpStatus.BAD_REQUEST, "배송 정보가 유효하지 않습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값 상태가 올바르지 않습니다."),
    INVALID_SELLER(HttpStatus.BAD_REQUEST, "셀러 등록이 잘못 됬습니다."),
    INVALID_INPUT_DATE_INTERVAL(HttpStatus.BAD_REQUEST, "시작일은 종료일보다 클 수 없습니다."),

    INVALID_INPUT_SEARCH_KEYWORD(HttpStatus.BAD_REQUEST, "검색 키워드를 잘못 요청 했습니다."),
    INVALID_PRODUCT_OPTION(HttpStatus.BAD_REQUEST, "상품 옵션 등록 정보를 잘못 입력했습니다."),
    INVALID_PRODUCT_GROUP(HttpStatus.BAD_REQUEST, "상품 그룹 등록 정보를 잘못 입력했습니다."),

    INVALID_PRODUCT_GROUP_MODIFY(HttpStatus.BAD_REQUEST, "상품 그룹 수정 정보를 잘못 입력했습니다."),

    INVALID_PRODUCT_STOCK_MODIFY(HttpStatus.BAD_REQUEST, "상품 재고 수정 정보를 잘못 입력했습니다."),
    INVALID_PRODUCT_IMAGE_TEMPLATE(HttpStatus.BAD_REQUEST, "상품 이미지 등록 요청을 잘못 보냈습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 계정 정보가 존재하지 않습니다"),
    FAIL_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),

    /* 403 UNAUTHORIZED : 인가되지 않은 사용자 */
    FORBIDDEN_MEMBER(HttpStatus.FORBIDDEN, "인가되지 않은 사용자 입니다."),
    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),

    INVALID_PRODUCT(HttpStatus.NOT_FOUND, "해당 제품이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리가 존재하지 않습니다"),
    BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "브랜드를 찾을 수 없습니다. 브랜드명을 정확히 입력해주세요."),
    PRODUCT_GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 제품 그룹이 존재하지 않습니다"),
    PRODUCT_DESCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 제품 상세설명이 존재하지 않습니다"),
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 제품 카테고리가 존재하지 않습니다"),

    SHIPMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 운송 정보가 존재하지 않습니다"),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 기록을 찾을 수 없습니다."),
    ORDER_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 과거 기록을 찾을 수 없습니다."),

    QNA_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "QNA 상태 값을 찾을 수 없습니다."),


    /* 500 SERVERERROR : */

    IMAGE_UPLOAD_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지를 s3 bucket에 옮기는데 실패했습니다."),

    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서버 오류입니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다");


    private final HttpStatus httpStatus;
    private final String detail;
}