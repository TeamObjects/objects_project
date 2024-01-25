DROP TABLE IF EXISTS member;

CREATE TABLE member (
                        member_id	bigint auto_increment
                            primary key,

                        member_type	varchar(255)	,
                        social_type	varchar(255)	,
                        membership	varchar(255)	,
                        email	varchar(255)	,
                        password	varchar(255)	,
                        name	varchar(255)	,
                        phone_no	varchar(255)	,
                        is_alert	bit	,
                        is_agree	bit	,
                        created_at	datetime(6)	,
                        updated_at	datetime(6)	,
                        deleted_at	datetime(6)
);

DROP TABLE IF EXISTS seller;

CREATE TABLE seller (
                        seller_id	bigint auto_increment primary key,

                        name	varchar(255)	,
                        biz_no	varchar(255)	,
                        owner	varchar(255)	,
                        category	varchar(255)	,
                        detail	varchar(255)	,
                        address	varchar(255)	,
                        license_no	varchar(255)	,
                        email	varchar(255)	,
                        account_no	varchar(255)	,
                        created_at	datetime(6)	,
                        updated_at	datetime(6)	,
                        deleted_at	datetime(6)
);

DROP TABLE IF EXISTS address;

CREATE TABLE address (
                         address_id	bigint auto_increment
                             primary key,
                         member_id	bigint	,
                         alias	varchar(255)	,
                         name	varchar(255)	,
                         phone_no	varchar(255)	,
                         city	varchar(255)	,
                         street	varchar(255)	,
                         zipcode	varchar(255)	,
                         detail varchar(255),
                         is_default	bit	,
                         created_at	datetime(6)	,
                         updated_at	datetime(6)	,
                         deleted_at	datetime(6)
);

DROP TABLE IF EXISTS social_credential;

CREATE TABLE social_credential (
                                   social_credential_id	bigint auto_increment
                                       primary key,
                                   member_id	bigint	,
                                   token_id	varchar(255)	,
                                   created_at	datetime(6)	,
                                   updated_at	datetime(6)	,
                                   deleted_at	datetime(6)
);

DROP TABLE IF EXISTS category;

CREATE TABLE category (
                          category_id	bigint auto_increment
                              primary key,
                          prev_id	bigint	,
                          next_id	bigint	,
                          name	varchar(255)	,
                          created_at	datetime(6)	,
                          updated_at	datetime(6)	,
                          deleted_at	datetime(6)
);

DROP TABLE IF EXISTS reward_policy;

CREATE TABLE reward_policy (
                               reward_id	bigint auto_increment
                                   primary key,
                               type	varchar(255)	,
                               rate	bigint	,
                               created_at	datetime(6)	,
                               updated_at	datetime(6)	,
                               deleted_at	datetime(6)
);

DROP TABLE IF EXISTS seller_product;

CREATE TABLE seller_product (
                                seller_product_id	bigint auto_increment
                                    primary key,
                                seller_id	bigint	,
                                product_id	bigint	,
                                created_at	datetime(6)	,
                                updated_at	datetime(6)	,
                                deleted_at	datetime(6)
);

DROP TABLE IF EXISTS product_image;

CREATE TABLE product_image (
                               product_image_id	bigint auto_increment
                                   primary key,
                               product_id	bigint	,
                               image_id	bigint	,
                               created_at	datetime(6)	,
                               updated_at	datetime(6)	,
                               deleted_at	datetime(6)
);

DROP TABLE IF EXISTS image;

CREATE TABLE image (
                       image_id	bigint auto_increment
                           primary key,
                       type	varchar(255)	,
                       url	varchar(255)	,
                       created_at	datetime(6)	,
                       updated_at	datetime(6)	,
                       deleted_at	datetime(6)
);

DROP TABLE IF EXISTS option_category;

CREATE TABLE option_category (
                                 option_category_id	bigint auto_increment
                                     primary key,

                                 name	varchar(255)	,
                                 created_at	datetime(6)	,
                                 updated_at	datetime(6)	,
                                 deleted_at	datetime(6)
);

DROP TABLE IF EXISTS options;

CREATE TABLE options (
                         option_id	bigint auto_increment
                             primary key,

                         option_category_id	bigint	,
                         name	varchar(255)	,
                         created_at	datetime(6)	,
                         updated_at	datetime(6)	,
                         deleted_at	datetime(6)
);

DROP TABLE IF EXISTS tag;

CREATE TABLE tag (
                     tag_id	bigint auto_increment
                         primary key,

                     name	varchar(255)	,
                     created_at	datetime(6)	,
                     updated_at	datetime(6)	,
                     deleted_at	datetime(6)
);

DROP TABLE IF EXISTS prod_tag;

CREATE TABLE prod_tag (
                          prod_tag_id	bigint auto_increment
                              primary key,

                          tag_id	bigint	,
                          product_id	bigint	,
                          created_at	datetime(6)	,
                          updated_at	datetime(6)	,
                          deleted_at	datetime(6)
);


DROP TABLE IF EXISTS status_code;

CREATE TABLE status_code (
                             status_code_id	bigint auto_increment
                                 primary key,

                             type	varchar(255)	,
                             code	varchar(255)	,
                             name	varchar(255)	,
                             created_at	datetime(6)	,
                             updated_at	datetime(6)	,
                             deleted_at	datetime(6)
);

DROP TABLE IF EXISTS prod_order;

CREATE TABLE prod_order (
                            prod_order_id	bigint auto_increment
                                primary key,

                            member_id	bigint	,
                            address_id bigint	,
                            order_name	varchar(255)	,
                            order_no	varchar(255)	,
                            total_used_coupon_price	bigint	,
                            total_price	bigint	,
                            real_price	bigint	,
                            used_point    bigint  ,
                            created_at	datetime(6)	,
                            updated_at	datetime(6)	,
                            deleted_at	datetime(6)
);

DROP TABLE IF EXISTS prod_order_detail;

CREATE TABLE prod_order_detail (
                                   prod_order_detail_id	bigint auto_increment
                                       primary key,

                                   prod_order_id	bigint	,
                                   product_id	bigint	,
                                   order_no varchar(255),
                                   coupon_id	bigint	,
                                   reward_type	varchar(255)	,
                                   quantity	bigint	,
                                   price	bigint	,
                                   status_code	varchar(255)	,
                                   delivered_date	datetime(6)	,
                                   reason	text	,
                                   payment_key varchar(255),
                                   cancelled_at	datetime(6)	,
                                   created_at	datetime(6)	,
                                   updated_at	datetime(6)	,
                                   deleted_at	datetime(6)
);

DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
                         payment_id	bigint auto_increment
                             primary key,

                         prod_order_id	bigint	,
                         order_no	varchar(255)	,
                         payment_type	varchar(255)	,
                         payment_method	varchar(255)	,
                         payment_key	varchar(255)	,
                         payment_status	varchar(255)	,
                         refund_status	varchar(255)	,

                         card_issuer_code	varchar(255)	,
                         card_no	varchar(255)	,
                         card_installment_plan_months	BIGINT	,
                         card_approve_no	varchar(255)	,
                         v_account_no	varchar(255)	,
                         v_due_date	varchar(255)	,
                         v_expired	bit	,
                         v_bank	varchar(255)	,
                         tr_bank_code	varchar(255)	,
                         deleted_at	datetime(6)	,
                         updated_at	datetime(6)	,
                         created_at	datetime(6)
);

DROP TABLE IF EXISTS review;

CREATE TABLE review (
                        review_id	bigint auto_increment
                            primary key,

                        member_id	bigint	,
                        product_id	bigint	,
                        content	text	,
                        rating	bigint	,
                        created_at	datetime(6)	,
                        updated_at	datetime(6)	,
                        deleted_at	datetime(6)
);

DROP TABLE IF EXISTS review_image;

CREATE TABLE review_image (
                              review_image_id bigint auto_increment
                                  primary key,

                              review_id	bigint	,
                              image_id	bigint	,
                              created_at	datetime(6)	,
                              updated_at	datetime(6)	,
                              deleted_at	datetime(6)
);

DROP TABLE IF EXISTS review_survey;

CREATE TABLE review_survey (
                               review_survey_id	bigint auto_increment
                                   primary key,

                               review_id	bigint	,
                               review_survey_category_id	bigint	,
                               survey_content_id	bigint	,
                               created_at	datetime(6)	,
                               updated_at	datetime(6)	,
                               deleted_at	datetime(6)
);

DROP TABLE IF EXISTS review_survey_category;

CREATE TABLE review_survey_category (
                                        review_survey_category_id	bigint auto_increment
                                            primary key,

                                        product_id	bigint	,
                                        name	varchar(255)	,
                                        created_at	datetime(6)	,
                                        updated_at	datetime(6)	,
                                        deleted_at	datetime(6)
);

DROP TABLE IF EXISTS survey_content;

CREATE TABLE survey_content (
                                survey_content_id	bigint auto_increment
                                    primary key,

                                review_survey_category_id	bigint	,
                                seq_no	bigint	,
                                content	varchar(255)	,
                                created_at	datetime(6)	,
                                updated_at	datetime(6)	,
                                deleted_at	datetime(6)
);

DROP TABLE IF EXISTS board;

CREATE TABLE board (
                       board_id	bigint auto_increment
                           primary key,

                       type	varchar(255)	,
                       subject	varchar(255)	,
                       contents	varchar(255)	,
                       created_at	datetime(6)	,
                       updated_at	datetime(6)	,
                       deleted_at	datetime(6)
);

DROP TABLE IF EXISTS qna;

CREATE TABLE qna (
                     qna_id	bigint auto_increment
                         primary key,

                     seller_id	bigint	,
                     member_id	bigint	,
                     content_type	varchar(255)	,
                     content	text	,
                     created_at	datetime(6)	,
                     updated_at	datetime(6)	,
                     deleted_at	datetime(6)
);

DROP TABLE IF EXISTS help_desk;

CREATE TABLE help_desk (
                           help_desk_id	bigint auto_increment
                               primary key,

                           member_id	bigint	,
                           product_id	bigint	,
                           prod_order_id	bigint	,
                           contents_type	varchar(255)	,
                           content	text	,
                           created_at	datetime(6)	,
                           updated_at	datetime(6)	,
                           deleted_at	datetime(6)
);

DROP TABLE IF EXISTS delivery;

CREATE TABLE delivery (
                          deliver_id	bigint auto_increment
                              primary key,

                          seller_id	bigint	,
                          address_id	bigint	,
                          prod_order_detail_id	bigint	,
                          type	varchar(255)	,
                          carrier	varchar(255)	,
                          tracking_no	varchar(255)	,
                          status	varchar(255)	,
                          ship_date	datetime(6)	,
                          delivered_date	datetime(6)	,
                          created_at	datetime(6)	,
                          updated_at	datetime(6)	,
                          deleted_at	datetime(6)
);

DROP TABLE IF EXISTS estimated_time;

CREATE TABLE estimated_time (
                                estimated_time_id	bigint auto_increment
                                    primary key,

                                hours	bigint	,
                                add_day	bigint	,
                                created_at	datetime(6)	,
                                updated_at	datetime(6)	,
                                deleted_at	datetime(6)
);

DROP TABLE IF EXISTS coupon_budget;

CREATE TABLE coupon_budget (
                               coupon_budget_id	bigint auto_increment primary key	,
                               seller_id	bigint ,
                               balance	bigint	,
                               outgoing	bigint	,
                               incoming	bigint	,
                               created_at	datetime(6)	,
                               updated_at	datetime(6)	,
                               deleted_at	datetime(6)
);

DROP TABLE IF EXISTS coupon;

CREATE TABLE coupon (
                        coupon_id	bigint auto_increment
                            primary key,

                        product_id	bigint	,
                        name	varchar(255)	,
                        price	varchar(255)	,
                        count	bigint	,
                        minimum_price	bigint	,
                        start_date	datetime(6)	,
                        end_date	datetime(6)	,
                        created_at	datetime(6)	,
                        updated_at	datetime(6)	,
                        deleted_at	datetime(6)
);

DROP TABLE IF EXISTS point;

CREATE TABLE point (
                       point_id	bigint auto_increment
                           primary key,

                       member_id	bigint	,
                       in_point	bigint	,
                       out_point	bigint	,
                       balance	bigint	,
                       type	varchar(255)	,
                       comments	varchar(255)	,
                       created_at	datetime(6)	,
                       updated_at	datetime(6)	,
                       deleted_at	datetime(6)
);

DROP TABLE IF EXISTS member_coupon;

CREATE TABLE member_coupon (
                               member_coupon_id	bigint auto_increment primary key	,
                               member_id	bigint	,
                               coupon_id	bigint	,
                               is_used	bit	,
                               used_date	datetime(6)	,
                               end_date	datetime(6)	,
                               created_at	datetime(6)	,
                               updated_at	datetime(6)	,
                               deleted_at	datetime(6)
);

DROP TABLE IF EXISTS admin;

CREATE TABLE admin (
                       admin_id	bigint auto_increment primary key,
                       emp_no	varchar(255)	,
                       password	varchar(255)	,
                       name	varchar(255)	,
                       level	bigint	,
                       created_at	datetime(6)	,
                       updated_at	datetime(6)	,
                       deleted_at	datetime(6)
);

DROP TABLE IF EXISTS cart;

CREATE TABLE cart (
                      cart_id	bigint auto_increment primary key,
                      member_id	bigint	,
                      product_id	bigint	,
                      is_subs	bit	,
                      quantity	bigint
);

/*DROP TABLE IF EXISTS like;

CREATE TABLE like (
                        like_id	bigint auto_increment primary key,
                        Field	varchar(255))
);

DROP TABLE IF EXISTS prod_subs;

CREATE TABLE prod_subs (
                             prod_subs_id	bigint auto_increment primary key,
                             period	bigint	,
                             mem_id	integer	,
                             subs_type	varchar(255)	,
                             ord_id	varchar(255)	,
                             delivey_value	integer	,
                             payment_schedule	date	,
                             payment_date	date	,
                             delivery_schedule	date	,
                             status	integer	,
                             receipt_url	varchar(255)	,
                             created_at	date	,
                             updated_at	date	,
                             payment_price	int
);*/



DROP TABLE IF EXISTS order_temp;

CREATE TABLE order_temp (
                            order_temp_id	bigint auto_increment
                                primary key,

                            order_no	varchar(255)	,
                            amount	bigint	,
                            address_id bigint,
                            product	varchar(255)
);

DROP TABLE IF EXISTS prod_option;

CREATE TABLE prod_option (
                             prod_option_id	bigint auto_increment primary key,
                             product_id	bigint	,
                             option_id	bigint	,
                             created_at	datetime(6)	,
                             updated_at	datetime(6)	,
                             deleted_at	datetime(6)
);

DROP TABLE IF EXISTS product;

CREATE TABLE product (
                         product_id	bigint auto_increment primary key,
                         category_id	bigint	,
                         product_no	varchar(255)	,
                         is_own	bit	,
                         name	varchar(255)	,
                         price	bigint	,
                         is_subs	bit	,
                         stock	bigint	,
                         thumb_img	varchar(255)	,
                         discount_rate	bigint	,
                         created_at	datetime(6)	,
                         updated_at	datetime(6)	,
                         deleted_at	datetime(6)
);

DROP TABLE IF EXISTS seller_account;

create table seller_account
(
    seller_account_id BIGINT auto_increment primary key ,
    seller_id         BIGINT null,
    incoming          BIGINT null,
    outgoing          BIGINT null,
    balance           BIGINT null

);


