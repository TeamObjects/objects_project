/*-- Address
INSERT INTO address (address_id, city, street, zip_code)
VALUES
    (1, 'Seoul', 'Gangnam-gu', '12345'),
    (2, 'Busan', 'Haeundae-gu', '67890');

-- Category
INSERT INTO category (category_id, name)
VALUES
    (1, 'Electronics'),
    (2, 'Clothing');

-- Option
INSERT INTO option (option_id, name, value)
VALUES
    (1, 'Color', 'Red'),
    (2, 'Size', 'XL');

-- Product
INSERT INTO product (product_id, category_id, stock, product_no, is_own, name, price, is_subs, thumb_img, discount_rate)
VALUES
    (1, 1, 100, 'P123', TRUE, 'Smartphone', 500000, FALSE, 'phone.jpg', 10),
    (2, 2, 50, 'C456', FALSE, 'T-shirt', 30000, TRUE, 'shirt.jpg', 15);

-- ProdOption
INSERT INTO prod_option (prod_option_id, product_id, option_id)
VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 2, 2);

-- Member
INSERT INTO member (member_id, social_type, membership, email, password, name, phone_no, is_alert, is_agree)
VALUES
    (1, 'Google', 'Gold', 'user1@example.com', 'password123', 'User1', '123456789', TRUE, TRUE),
    (2, 'Facebook', 'Silver', 'user2@example.com', 'password456', 'User2', '987654321', FALSE, TRUE);

-- ProdOrder
INSERT INTO prod_order (prod_order_id, member_id, address_id, order_name, order_no, total_price, point_rate, saved_point, delivered_date)
VALUES
    (1, 1, 1, 'Order1', '123ABC', 550000, 5, 5000, '2024-01-16 12:30:00'),
    (2, 2, 2, 'Order2', '456DEF', 35000, 3, 1000, '2024-01-17 09:00:00');

-- ProdOrderDetail
INSERT INTO prod_order_detail (prod_order_detail_id, prod_order_id, product_id, coupon_id, prod_option_id, used_coupon, quantity, price, status_code, delivered_date, used_point, reason, cancelled_at)
VALUES
    (1, 1, 1, 1, 1, 0, 2, 100000, 'COMPLETED', '2024-01-17 10:00:00', 0, NULL, NULL),
    (2, 1, 2, 2, 2, 1, 1, 25000, 'COMPLETED', '2024-01-17 11:30:00', 2000, NULL, NULL),
    (3, 2, 2, 1, 3, 0, 1, 30000, 'COMPLETED', '2024-01-17 12:45:00', 0, NULL, NULL);

-- MemberCoupon
INSERT INTO member_coupon (member_coupon_id, member_id, coupon_id, is_used, used_date, end_date)
VALUES
    (1, 1, 1, FALSE, NULL, '2024-12-31'),
    (2, 1, 2, TRUE, '2024-01-17 10:30:00', '2024-12-31'),
    (3, 2, 1, FALSE, NULL, '2024-12-31');

-- Coupon
INSERT INTO coupon (coupon_id, product_id, name, price, count, minimum_price, start_date, end_date)
VALUES
    (1, 1, 'DiscountCoupon1', 5000, 100, 100000, '2024-01-01', '2024-06-30'),
    (2, 2, 'DiscountCoupon2', 2000, 50, 20000, '2024-01-01', '2024-06-30');
*/