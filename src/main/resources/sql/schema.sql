create table if not exists MarketBridge.address
(
    address_id bigint auto_increment
        primary key,

    member_id  bigint       not null,
    alias      varchar(255) not null,
    city       varchar(255) not null,
    name       varchar(255) not null,
    phone_no   varchar(255) not null,
    street     varchar(255) not null,
    zipcode    varchar(255) not null,
    is_default bit          not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.admin
(

    admin_id   bigint auto_increment
        primary key,

    level      int          not null,
    emp_no     varchar(255) not null,
    name       varchar(255) not null,
    password   varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.board
(
    board_id     bigint auto_increment
        primary key,

    content_type varchar(255) not null,
    content      varchar(255) not null,

    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null

);

create table if not exists MarketBridge.cancel_payment
(
    cancel_payment_id bigint auto_increment
        primary key,

    order_id          bigint       not null,
    code              varchar(255) not null,

    created_at        datetime(6)  not null,
    updated_at        datetime(6)  not null,
    deleted_at        datetime(6)  null
);

create table if not exists MarketBridge.cart
(
    cart_id        bigint auto_increment
        primary key,

    member_id      bigint      not null,
    prod_option_id bigint      not null,
    is_subs        bit         not null,
    quantity       int         not null,

    created_at     datetime(6) not null,
    updated_at     datetime(6) not null,
    deleted_at     datetime(6) null


);

create table if not exists MarketBridge.category
(
    category_id bigint auto_increment
        primary key,

    prev_id     bigint       not null,
    next_id     bigint       not null,
    name        varchar(255) not null,

    created_at  datetime(6)  not null,
    updated_at  datetime(6)  not null,
    deleted_at  datetime(6)  null
);

create table if not exists MarketBridge.coupon
(
    coupon_id     bigint auto_increment
        primary key,

    product_id    bigint       not null,
    name          varchar(255) not null,
    price         int          not null,
    minimum_price int          not null,
    count         int          not null,
    start_date    datetime(6)  not null,
    end_date      datetime(6)  not null,

    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null


);

create table if not exists MarketBridge.coupon_budget
(
    coupon_budget_id bigint auto_increment
        primary key,

    member_id        bigint      not null,
    price            int         not null,

    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    deleted_at       datetime(6) null
);

create table if not exists MarketBridge.member_coupon
(
    member_coupon_id bigint auto_increment
        primary key,

    coupon_id        bigint      not null,
    member_id        bigint      not null,
    is_used          bit         not null,
    used_date        datetime(6) not null,

    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    deleted_at       datetime(6) null
);

create table if not exists MarketBridge.delivery
(
    delivery_id     bigint auto_increment
        primary key,

    address_id      bigint       not null,
    order_detail_id bigint       not null,
    seller_id       bigint       not null,
    delivery_type   varchar(255) not null,
    status_code     varchar(255) not null,
    carrier         varchar(255) not null,
    tracking_no     varchar(255) not null,
    ship_date       datetime(6)  not null,
    delivered_date  datetime(6)  not null,

    created_at      datetime(6)  not null,
    updated_at      datetime(6)  not null,
    deleted_at      datetime(6)  null

);

create table if not exists MarketBridge.estimated_time
(
    estimated_time_id bigint auto_increment
        primary key,

    add_day           int         not null,
    hour              int         not null,

    created_at        datetime(6) not null,
    updated_at        datetime(6) not null,
    deleted_at        datetime(6) null
);

create table if not exists MarketBridge.help_desk
(
    help_desk_id  bigint auto_increment
        primary key,

    member_id     bigint       not null,
    product_id    bigint       not null,
    prod_order_id bigint       not null,
    content_type  varchar(255) not null,
    content       varchar(255) not null,

    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null
);

create table if not exists MarketBridge.image
(
    image_id   bigint auto_increment
        primary key,

    type       varchar(255) not null,
    url        varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.member
(

    member_id   bigint auto_increment
        primary key,

    membership  varchar(255) not null,
    social_type varchar(255) not null,
    email       varchar(255) not null,
    phone_no    varchar(255) not null,
    name        varchar(255) not null,
    password    varchar(255) not null,
    is_agree    bit          not null,
    is_alert    bit          not null,

    created_at  datetime(6)  not null,
    updated_at  datetime(6)  not null,
    deleted_at  datetime(6)  null
    constraint email_unique
    unique (email)
);

create table if not exists MarketBridge.option_category
(
    option_category_id bigint auto_increment
        primary key,

    name               varchar(255) not null,

    created_at         datetime(6)  not null,
    updated_at         datetime(6)  not null,
    deleted_at         datetime(6)  null

);

create table if not exists MarketBridge.payment
(
    payment_id    bigint auto_increment
        primary key,

    prod_order_id bigint       not null,
    pay_num       varchar(255) not null,
    pg            varchar(255) not null,
    receipt_id    varchar(255) not null,
    pay_method    varchar(255) not null,
    status_code   varchar(255) not null,

    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null
);

create table if not exists MarketBridge.point
(
    point_id   bigint auto_increment
        primary key,

    member_id  bigint       not null,
    in_point   int          not null,
    out_point  int          not null,
    balance    int          not null,
    comments   varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null

);

create table if not exists MarketBridge.prod_option
(
    prod_option_id     bigint auto_increment
        primary key,

    option_category_id bigint       not null,
    product_id         bigint       not null,
    price              int          not null,
    name               varchar(255) not null,

    created_at         datetime(6)  not null,
    updated_at         datetime(6)  not null,
    deleted_at         datetime(6)  null
);

create table if not exists MarketBridge.prod_order
(
    prod_order_id  bigint auto_increment
        primary key,

    order_name     varchar(255)     null,
    order_no        varchar(255)    null,
    member_id      bigint       not null,
    status_code    varchar(255) not null,
    total_price    bigint       not null,
    point_rate     int          not null,
    saved_point    int          not null,
    delivered_date datetime(6)  not null,

    created_at     datetime(6)  not null,
    updated_at     datetime(6)  not null,
    deleted_at     datetime(6)  null
);

create table if not exists MarketBridge.prod_order_detail
(
    prod_order_detail_id bigint auto_increment
        primary key,

    product_id           bigint       not null,
    prod_order_id        bigint       not null,
    coupon_id            bigint       not null,
    status_code          varchar(255) not null,
    price                int          not null,
    used_coupon          int          not null,
    used_point           int          not null,
    quantity             int          not null,
    delivered_date       datetime(6)  not null,
    reason               varchar(255) null,
    cancelled_at         datetime(6)  null,

    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null,
    deleted_at           datetime(6)  null


);

create table if not exists MarketBridge.prod_tag
(
    prod_tag_id bigint auto_increment
        primary key,

    product_id  bigint      not null,
    tag_id      bigint      not null,

    created_at  datetime(6) not null,
    updated_at  datetime(6) not null,
    deleted_at  datetime(6) null

);

create table if not exists MarketBridge.product
(
    product_id    bigint auto_increment
        primary key,

    category_id   bigint       not null,
    name          varchar(255) not null,
    price         int          not null,
    is_own        bit          not null,
    is_subs       bit          not null,
    discount_rate int          not null,
    thumb_img     varchar(255) not null,

    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    deleted_at    datetime(6)  null
);

create table if not exists MarketBridge.product_image
(
    product_image_id bigint auto_increment
        primary key,

    image_id         bigint      not null,
    product_id       bigint      not null,

    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    deleted_at       datetime(6) null
);

create table if not exists MarketBridge.qna
(
    board_id   bigint auto_increment
        primary key,

    member_id  bigint       not null,
    seller_id  bigint       not null,
    board_type varchar(255) not null,
    content    varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.review
(
    review_id  bigint auto_increment
        primary key,

    member_id  bigint       not null,
    product_id bigint       not null,
    content    varchar(255) not null,
    rating     int          not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.review_image
(
    review_image_id bigint auto_increment
        primary key,

    image_id        bigint      not null,
    review_id       bigint      not null,

    created_at      datetime(6) not null,
    updated_at      datetime(6) not null,
    deleted_at      datetime(6) null
);

create table if not exists MarketBridge.review_survey
(
    review_survey_id          bigint auto_increment
        primary key,

    review_id                 bigint      not null,
    review_survey_category_id bigint      not null,
    survey_content_id         bigint      not null,

    created_at                datetime(6) not null,
    updated_at                datetime(6) not null,
    deleted_at                datetime(6) null
);

create table if not exists MarketBridge.review_survey_category
(
    review_survey_category_id bigint auto_increment
        primary key,

    product_id                bigint       not null,
    name                      varchar(255) not null,

    created_at                datetime(6)  not null,
    updated_at                datetime(6)  not null,
    deleted_at                datetime(6)  null
);

create table if not exists MarketBridge.reward
(

    reward_id  bigint auto_increment
        primary key,

    rate       int         not null,

    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    deleted_at datetime(6) null
);

create table if not exists MarketBridge.seller
(
    seller_id  bigint auto_increment
        primary key,

    name       varchar(255) not null,
    owner      varchar(255) not null,
    biz_no     varchar(255) not null,
    license_no varchar(255) not null,
    category   varchar(255) not null,
    detail     varchar(255) not null,
    address    varchar(255) not null,
    email      varchar(255) not null,
    account_no varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.seller_product
(
    seller_product_id bigint auto_increment
        primary key,

    product_id        bigint      not null,
    seller_id         bigint      not null,

    created_at        datetime(6) not null,
    updated_at        datetime(6) not null,
    deleted_at        datetime(6) null

);

create table if not exists MarketBridge.social_credential
(
    social_credential_id bigint auto_increment
        primary key,

    member_id            bigint       not null,
    token_id             varchar(255) not null,

    created_at           datetime(6)  not null,
    updated_at           datetime(6)  not null,
    deleted_at           datetime(6)  null

);

create table if not exists MarketBridge.status_code
(
    status_code_id   bigint auto_increment
        primary key,

    code             varchar(255) not null,
    name             varchar(255) not null,
    status_code_type varchar(255) not null,

    created_at       datetime(6)  not null,
    updated_at       datetime(6)  not null,
    deleted_at       datetime(6)  null
);

create table if not exists MarketBridge.stock
(
    stock_id          bigint auto_increment
        primary key,

    product_option_id bigint      not null,
    warehouse_id      bigint      not null,
    quantity          int         not null,

    created_at        datetime(6) not null,
    updated_at        datetime(6) not null,
    deleted_at        datetime(6) null

);

create table if not exists MarketBridge.survey_content
(
    survey_content_id  bigint auto_increment
        primary key,

    survey_category_id bigint       not null,
    seq_no             int          not null,
    content            varchar(255) not null,

    created_at         datetime(6)  not null,
    updated_at         datetime(6)  not null,
    deleted_at         datetime(6)  null
);

create table if not exists MarketBridge.tag
(
    tag_id     bigint auto_increment
        primary key,

    name       varchar(255) not null,

    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    deleted_at datetime(6)  null
);

create table if not exists MarketBridge.warehouse
(
    warehouse_id bigint auto_increment
        primary key,

    seller_id    bigint       not null,
    alias        varchar(255) not null,
    city         varchar(255) not null,
    name         varchar(255) not null,
    phone_no     varchar(255) not null,
    street       varchar(255) not null,
    zipcode      varchar(255) not null,

    created_at   datetime(6)  not null,
    updated_at   datetime(6)  not null,
    deleted_at   datetime(6)  null
);