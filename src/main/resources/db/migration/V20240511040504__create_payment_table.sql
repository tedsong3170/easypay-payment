-- 테이블 생성 SQL - merchant_info
CREATE TABLE merchant_info
(
  mid               char(32)           NOT NULL,
  name             varchar(50)    NOT NULL,
  ceo_name           varchar(50)    NOT NULL,
  biz_type           varchar(50)    NOT NULL,
  biz_item           varchar(50)    NOT NULL,
  biz_owner_type      varchar(50)    NOT NULL,
  biz_number         varchar(50)    NOT NULL,
  ceo_phone_number    varchar(50)    NOT NULL,
  secret_key         char(64)       NOT NULL,
  create_at          timestamp      NOT NULL,
  create_by          varchar(50)    NOT NULL,
  update_at          timestamp      NOT NULL,
  update_by          varchar(50)    NOT NULL,
  PRIMARY KEY (mid)
);

-- 테이블 Comment 설정 SQL - merchant_info
COMMENT ON TABLE merchant_info IS '가맹점_정보';

-- 컬럼 Comment 설정 SQL - merchant_info.mid
COMMENT ON COLUMN merchant_info.mid IS '가맹점ID';

-- 컬럼 Comment 설정 SQL - merchant_info.name
COMMENT ON COLUMN merchant_info.name IS '가맹점명';

-- 컬럼 Comment 설정 SQL - merchant_info.ceo_name
COMMENT ON COLUMN merchant_info.ceo_name IS '대표자명';

-- 컬럼 Comment 설정 SQL - merchant_info.biz_type
COMMENT ON COLUMN merchant_info.biz_type IS '업태';

-- 컬럼 Comment 설정 SQL - merchant_info.biz_item
COMMENT ON COLUMN merchant_info.biz_item IS '업종';

-- 컬럼 Comment 설정 SQL - merchant_info.biz_owner_type
COMMENT ON COLUMN merchant_info.biz_owner_type IS '사업자구분. 개인, 법인';

-- 컬럼 Comment 설정 SQL - merchant_info.biz_number
COMMENT ON COLUMN merchant_info.biz_number IS '사업자번호';

-- 컬럼 Comment 설정 SQL - merchant_info.ceo_phone_number
COMMENT ON COLUMN merchant_info.ceo_phone_number IS '대표자연락처';

-- 컬럼 Comment 설정 SQL - merchant_info.secret_key
COMMENT ON COLUMN merchant_info.secret_key IS '시크릿키';

-- 컬럼 Comment 설정 SQL - merchant_info.create_at
COMMENT ON COLUMN merchant_info.create_at IS '등록일';

-- 컬럼 Comment 설정 SQL - merchant_info.create_by
COMMENT ON COLUMN merchant_info.create_by IS '등록자';

-- 컬럼 Comment 설정 SQL - merchant_info.update_at
COMMENT ON COLUMN merchant_info.update_at IS '수정일';

-- 컬럼 Comment 설정 SQL - merchant_info.update_by
COMMENT ON COLUMN merchant_info.update_by IS '수정자';


-- 테이블 생성 SQL - merchant_customer_info
CREATE TABLE merchant_customer_info
(
  di             char(32)            NOT NULL,
  mid            char(32)            NOT NULL,
  ci             char(88)        NOT NULL,
  email          varchar(100)    NOT NULL,
  phone_number    varchar(50)     NOT NULL,
  name           varchar(50)     NOT NULL,
  create_at       timestamp       NOT NULL,
  update_at       timestamp       NOT NULL,
  PRIMARY KEY (di)
);

-- 테이블 Comment 설정 SQL - merchant_customer_info
COMMENT ON TABLE merchant_customer_info IS '가맹점_사용자정보';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.di
COMMENT ON COLUMN merchant_customer_info.di IS '사용자DI';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.mid
COMMENT ON COLUMN merchant_customer_info.mid IS '가맹점ID';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.ci
COMMENT ON COLUMN merchant_customer_info.ci IS '사용자CI';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.email
COMMENT ON COLUMN merchant_customer_info.email IS '이메일주소';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.phone_number
COMMENT ON COLUMN merchant_customer_info.phone_number IS '연락처';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.name
COMMENT ON COLUMN merchant_customer_info.name IS '성명';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.create_at
COMMENT ON COLUMN merchant_customer_info.create_at IS '등록일';

-- 컬럼 Comment 설정 SQL - merchant_customer_info.update_at
COMMENT ON COLUMN merchant_customer_info.update_at IS '수정일';


-- 테이블 생성 SQL - payment_info
CREATE TABLE payment_info
(
  payment_id           char(32)              NOT NULL,
  di                  varchar(50)       NOT NULL,
  mid                 varchar(50)       NOT NULL,
  type                varchar(50)       NOT NULL,
  order_id             varchar(50)       NOT NULL,
  order_name           varchar(50)       NOT NULL,
  total_amount         numeric(18, 0)    NOT NULL,
  balance             numeric(18, 0)    NOT NULL,
  status              varchar(50)       NOT NULL,
  installment_month    integer           NULL,
  requested_at         timestamp         NOT NULL,
  approved_at          timestamp         NULL,
  error_code           char(5)           NULL,
  error_message        varchar(50)       NULL,
  create_at            timestamp         NOT NULL,
  update_at            timestamp         NOT NULL,
  PRIMARY KEY (payment_id)
);

-- 테이블 Comment 설정 SQL - payment_info
COMMENT ON TABLE payment_info IS '결제정보';

-- 컬럼 Comment 설정 SQL - payment_info.payment_id
COMMENT ON COLUMN payment_info.payment_id IS '결제ID';

-- 컬럼 Comment 설정 SQL - payment_info.di
COMMENT ON COLUMN payment_info.di IS '사용자DI';

-- 컬럼 Comment 설정 SQL - payment_info.mid
COMMENT ON COLUMN payment_info.mid IS '가맹점ID';

-- 컬럼 Comment 설정 SQL - payment_info.type
COMMENT ON COLUMN payment_info.type IS '결제타입. 간편,정기 등';

-- 컬럼 Comment 설정 SQL - payment_info.order_id
COMMENT ON COLUMN payment_info.order_id IS '주문ID';

-- 컬럼 Comment 설정 SQL - payment_info.order_name
COMMENT ON COLUMN payment_info.order_name IS '주문명';

-- 컬럼 Comment 설정 SQL - payment_info.total_amount
COMMENT ON COLUMN payment_info.total_amount IS '총결제금액';

-- 컬럼 Comment 설정 SQL - payment_info.balance
COMMENT ON COLUMN payment_info.balance IS '결제잔액';

-- 컬럼 Comment 설정 SQL - payment_info.status
COMMENT ON COLUMN payment_info.status IS '결제상태. 요청, 승인, 거절 등';

-- 컬럼 Comment 설정 SQL - payment_info.installment_month
COMMENT ON COLUMN payment_info.installment_month IS '할부개월수. 카드일때만';

-- 컬럼 Comment 설정 SQL - payment_info.requested_at
COMMENT ON COLUMN payment_info.requested_at IS '결제요청일자. 최초결제요청일시';

-- 컬럼 Comment 설정 SQL - payment_info.approved_at
COMMENT ON COLUMN payment_info.approved_at IS '결제승인일자. 최종결제승인일시';

-- 컬럼 Comment 설정 SQL - payment_info.error_code
COMMENT ON COLUMN payment_info.error_code IS '에러코드';

-- 컬럼 Comment 설정 SQL - payment_info.error_message
COMMENT ON COLUMN payment_info.error_message IS '에러메세지';

-- 컬럼 Comment 설정 SQL - payment_info.create_at
COMMENT ON COLUMN payment_info.create_at IS '등록일';

-- 컬럼 Comment 설정 SQL - payment_info.update_at
COMMENT ON COLUMN payment_info.update_at IS '수정일';


-- 테이블 생성 SQL - payment_ledger
CREATE TABLE payment_ledger
(
  ledger_id        char(32)              NOT NULL,
  payment_id       char(32)              NOT NULL,
  method          varchar(50)       NOT NULL,
  method_id        varchar(50)       NOT NULL,
  token           char(32)          NOT NULL,
  amount          numeric(18, 0)    NOT NULL,
  status          varchar(50)       NOT NULL,
  error_code       char(5)           NULL,
  error_message    varchar(50)       NULL,
  create_at        timestamp         NOT NULL,
  PRIMARY KEY (ledger_id)
);

-- 테이블 Comment 설정 SQL - payment_ledger
COMMENT ON TABLE payment_ledger IS '결제원장';

-- 컬럼 Comment 설정 SQL - payment_ledger.ledger_id
COMMENT ON COLUMN payment_ledger.ledger_id IS '결제원장ID';

-- 컬럼 Comment 설정 SQL - payment_ledger.payment_id
COMMENT ON COLUMN payment_ledger.payment_id IS '결제ID';

-- 컬럼 Comment 설정 SQL - payment_ledger.method
COMMENT ON COLUMN payment_ledger.method IS '결제수단';

-- 컬럼 Comment 설정 SQL - payment_ledger.method_id
COMMENT ON COLUMN payment_ledger.method_id IS '결제수단ID';

-- 컬럼 Comment 설정 SQL - payment_ledger.token
COMMENT ON COLUMN payment_ledger.token IS '결제토큰';

-- 컬럼 Comment 설정 SQL - payment_ledger.amount
COMMENT ON COLUMN payment_ledger.amount IS '결제금액';

-- 컬럼 Comment 설정 SQL - payment_ledger.status
COMMENT ON COLUMN payment_ledger.status IS '결제상태. 요청, 승인, 거부 등';

-- 컬럼 Comment 설정 SQL - payment_ledger.error_code
COMMENT ON COLUMN payment_ledger.error_code IS '에러코드';

-- 컬럼 Comment 설정 SQL - payment_ledger.error_message
COMMENT ON COLUMN payment_ledger.error_message IS '에러메세지';

-- 컬럼 Comment 설정 SQL - payment_ledger.create_at
COMMENT ON COLUMN payment_ledger.create_at IS '등록일';


-- 테이블 생성 SQL - payment_cancel_ledger
CREATE TABLE payment_cancel_ledger
(
  ledger_id          char(32)              NOT NULL,
  payment_id         char(32)              NOT NULL,
  origin_ledger_id    char(32)              NOT NULL,
  amount            numeric(18, 0)    NOT NULL,
  status            varchar(50)       NOT NULL,
  error_code         char(5)           NULL,
  error_message      varchar(50)       NULL,
  create_at          timestamp         NOT NULL,
  PRIMARY KEY (ledger_id)
);

-- 테이블 Comment 설정 SQL - payment_cancel_ledger
COMMENT ON TABLE payment_cancel_ledger IS '결제취소원장';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.ledger_id
COMMENT ON COLUMN payment_cancel_ledger.ledger_id IS '결제원장ID';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.payment_id
COMMENT ON COLUMN payment_cancel_ledger.payment_id IS '결제ID';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.origin_ledger_id
COMMENT ON COLUMN payment_cancel_ledger.origin_ledger_id IS '원결제원장ID';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.amount
COMMENT ON COLUMN payment_cancel_ledger.amount IS '취소금액';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.status
COMMENT ON COLUMN payment_cancel_ledger.status IS '취소상태. 요청, 승인, 거부 등';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.error_code
COMMENT ON COLUMN payment_cancel_ledger.error_code IS '에러코드';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.error_message
COMMENT ON COLUMN payment_cancel_ledger.error_message IS '에러메세지';

-- 컬럼 Comment 설정 SQL - payment_cancel_ledger.create_at
COMMENT ON COLUMN payment_cancel_ledger.create_at IS '등록일';
