CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    year INT,
    available_copies INT
);

CREATE TABLE reader (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    registration_date DATE
);

CREATE TABLE rental (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    reader_id BIGINT,
    rent_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (reader_id) REFERENCES reader(id)
);