INSERT INTO feedbacks(film_id, user_id, feedback_date, feedback)
VALUES (1,1,'2024-06-02','good film');

INSERT INTO feedbacks(film_id, user_id, feedback_date, feedback)
VALUES (2,1,'2024-06-02','bad film');

INSERT INTO feedbacks(film_id, user_id, feedback_date, feedback)
VALUES (3,1,'2024-06-03','so so film');

INSERT INTO actors_cast(film_id, actor_id)
VALUES (1,1),
       (1,2),
       (1,3),
       (2,2),
       (2,3),
       (3,3),
       (4,5),
       (5,1),
       (6,1),
       (7,3);