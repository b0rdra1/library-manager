INSERT INTO book (title, author, published_year, available_copies) VALUES
('Effective Java', 'Joshua Bloch', 2018, 3),
('Clean Code', 'Robert C. Martin', 2008, 2),
('Spring in Action', 'Craig Walls', 2021, 1);

INSERT INTO reader (name, registration_date) VALUES
('Alice', '2023-01-10'),
('Bob', '2023-02-15');

INSERT INTO rental (book_id, reader_id, rent_date, return_date) VALUES
(1, 1, '2024-04-01', null),
(2, 2, '2024-04-05', '2024-04-20');