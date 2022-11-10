CREATE TABLE users
(
    id         int AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE files
(
    id        int AUTO_INCREMENT,
    file_path varchar(150) NOT NULL,
    file_name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE events
(
    id         int AUTO_INCREMENT,
    event_name varchar(150) NOT NULL,
    user_id    int          NOT NULL,
    file_id    int          NOT NULL,
    date_time  datetime     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_event_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_file FOREIGN KEY (file_id) REFERENCES files (id) ON DELETE CASCADE
);
