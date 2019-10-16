INSERT INTO gift_certificate(name, description, price, creation_date, last_modified,
duration_in_days)
VALUES ('race track', 'pair kart racing', 30.0, '2019-06-20', '2019-06-25', 60);
INSERT INTO gift_certificate(name, description, price, creation_date, last_modified,
duration_in_days)
VALUES ('latin american dances', 'lesson with the trainer. 8 visits of 1.5 hours',
45.5, '2019-06-25', '2019-06-25', 20);
INSERT INTO gift_certificate(name, description, price, creation_date, last_modified,
duration_in_days)
VALUES ('gym visit', 'unlimited gym visit for 1 month at Bedy, 4',
10.7, '2019-06-25', '2019-06-25', 20);

INSERT INTO tag(name)
VALUES ('track');
INSERT INTO tag(name)
VALUES ('brave');
INSERT INTO tag(name)
VALUES ('pair');
INSERT INTO tag(name)
VALUES ('fast');
INSERT INTO tag(name)
VALUES ('dance');
INSERT INTO tag(name)
VALUES ('latin');
INSERT INTO tag(name)
VALUES ('gym');
INSERT INTO tag(name)
VALUES ('power');

INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (1, 2);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (1, 3);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (1, 4);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (2, 5);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (2, 6);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (2, 2);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (3, 7);
INSERT INTO gift_certificate_to_tag(gift_certificate_id, tag_id)
VALUES (3, 8);