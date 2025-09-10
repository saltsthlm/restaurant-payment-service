-- Sample items
INSERT INTO item (id, item_id, quantity, price) VALUES
                                                    ('11111111-1111-1111-1111-111111111111', 101, 2, 49.90),
                                                    ('22222222-2222-2222-2222-222222222222', 202, 1, 129.00);

-- Payment: PENDING, amount = 49.90*2 + 129.00 = 228.80
INSERT INTO payment (id, order_id, amount, provider_payment_id, status, created_at, failure_reason)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
     'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
     228.80,
     NULL,
     'PENDING',
     '2025-09-10T08:30:00Z',
     NULL);

-- Link items to the payment
INSERT INTO payment_item (payment_id, item_id) VALUES
                                                   ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111'),
                                                   ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222');
