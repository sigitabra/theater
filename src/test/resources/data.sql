-- Inserting data into theater_play table
INSERT INTO theater_play (title, premiere)
VALUES
    ('Hamlet', '2023-01-15'),
    ('Macbeth', '2023-02-20'),
    ('Othello', '2023-03-10'),
    ('Romeo and Juliet', '2023-04-05'),
    ('The Tempest', '2023-05-12');

-- Inserting data into scheduled_play table
INSERT INTO scheduled_play (address, total_reserved_seats, total_seats, date, time, theater_play_id)
VALUES
    ('123 Theater St, Cityville', 2, 100, '2024-06-01', '19:00:00', 1),
    ('456 Drama Ln, Cityville', 3, 150, '2024-06-02', '20:00:00', 2),
    ('789 Stage Rd, Cityville', 1, 200, '2024-06-03', '18:30:00', 3),
    ('321 Show Blvd, Cityville', 3, 120, '2024-06-04', '19:30:00', 4),
    ('654 Performance Ave, Cityville', 2, 180, '2024-06-05', '20:30:00', 5);

-- Inserting data into reservation table
INSERT INTO reservation (email, name, reserved_seats, scheduled_play_id)
VALUES
    ('john.doe@example.com', 'John Doe', 2, 1),
    ('jane.smith@example.com', 'Jane Smith', 3, 2),
    ('alice.jones@example.com', 'Alice Jones', 1, 3),
    ('bob.brown@example.com', 'Bob Brown', 3, 4),
    ('carol.white@example.com', 'Carol White', 2, 5);


