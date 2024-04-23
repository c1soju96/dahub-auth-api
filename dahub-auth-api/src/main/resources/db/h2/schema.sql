DROP TABLE IF EXISTS USER;
CREATE TABLE USER(
USER_ID VARCHAR(50) NOT NULL,
PASSWORD VARCHAR(256) NOT NULL,
USER_NAME VARCHAR(50) NOT NULL,
EMAIL VARCHAR(50) NOT NULL,
REFRESH_TOKEN VARCHAR(256) NOT NULL,
ROLE_TYPE VARCHAR(10) DEFAULT 'GUEST' NOT NULL,
USER_GRADE VARCHAR(10) DEFAULT 'GENERAL' NOT NULL,
AUTH_YN VARCHAR(1) DEFAULT 'N' NOT NULL,
LAST_LOGIN_DATE TIMESTAMP DEFAULT NOW() NOT NULL,
BLACK_LIST_TYPE VARCHAR(10) DEFAULT 'GENERAL' NOT NULL,
PROVIDER_TYPE VARCHAR(10) DEFAULT 'NONE' NOT NULL,
BLACK_LIST_YN VARCHAR(1) DEFAULT 'N' NOT NULL,
DELETE_YN VARCHAR(1) DEFAULT 'N' NOT NULL,
INSERT_OPERATOR VARCHAR(50) DEFAULT 'SYSTEM' NOT NULL,
UPDATE_OPERATOR VARCHAR(50) DEFAULT 'SYSTEM' NOT NULL,
INSERT_DATE TIMESTAMP DEFAULT NOW() NOT NULL,
UPDATE_DATE TIMESTAMP DEFAULT NOW() NOT NULL,
PRIMARY KEY (USER_ID)
);

DROP TABLE IF EXISTS MEMBER_TOKEN;
CREATE TABLE MEMBER_TOKEN(
REFRESH_TOKEN_SEQ BIGINT GENERATED BY DEFAULT AS IDENTITY,
MEMBER_ID VARCHAR(50) NOT NULL,
REFRESH_TOKEN VARCHAR(256) NOT NULL,
DELETE_YN VARCHAR(1) DEFAULT 'N' NOT NULL,
INSERT_OPERATOR VARCHAR(50) DEFAULT 'SYSTEM' NOT NULL,
UPDATE_OPERATOR VARCHAR(50) DEFAULT 'SYSTEM' NOT NULL,
INSERT_DATE TIMESTAMP DEFAULT NOW() NOT NULL,
UPDATE_DATE TIMESTAMP DEFAULT NOW() NOT NULL,
PRIMARY KEY (REFRESH_TOKEN_SEQ)
);