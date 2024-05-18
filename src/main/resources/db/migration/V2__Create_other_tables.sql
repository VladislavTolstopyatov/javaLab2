CREATE TABLE actors
(
    actor_id  BIGSERIAL PRIMARY KEY,
    fio       TEXT  NOT NULL,
    birthdate DATE  NOT NULL,
);


CREATE TABLE directors
(
    director_id BIGSERIAL PRIMARY KEY,
    "name"      TEXT    NOT NULL,
    surname     TEXT    NOT NULL,
    patronymic  TEXT    NOT NULL,
    birthdate   DATE    NOT NULL,
    oscar       BOOLEAN NOT NULL,
);

CREATE TABLE movies
(
    movie_id        BIGSERIAL PRIMARY KEY,
    title           TEXT    NOT NULL,
    description     TEXT    NOT NULL,
    genre           TEXT    NOT NULL,
    date_of_release DATE    NOT NULL,
    duration        INTEGER NOT NULL,
    director_id     BIGINT  NOT NULL,
    CONSTRAINT film_director_id_fk FOREIGN KEY (director_id)
        REFERENCES directors (director_id) ON DELETE CASCADE
);

CREATE TABLE actors_cast
(
    actor_cast_id BIGSERIAL PRIMARY KEY,
    film_id       BIGINT NOT NULL,
    actor_id      BIGINT NOT NULL,
    CONSTRAINT actor_cast_film_fk FOREIGN KEY (film_id)
        REFERENCES movies (movie_id) ON DELETE CASCADE,
    CONSTRAINT actor_cast_actor_id_fk FOREIGN KEY (actor_id)
        REFERENCES actors (actor_id) ON DELETE CASCADE
);


CREATE TABLE feedbacks
(
    feedback_id   BIGSERIAL PRIMARY KEY,
    film_id       BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    feedback_date DATE   NOT NULL,
    feedback      TEXT   NOT NULL,
    CONSTRAINT feedback_movie_id_fk FOREIGN KEY (film_id)
        REFERENCES movies (movie_id) ON DELETE CASCADE,
    CONSTRAINT feedback_user_id_fk FOREIGN KEY (user_id)
        REFERENCES users (user_id) ON DELETE CASCADE
);