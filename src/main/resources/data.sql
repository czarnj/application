INSERT INTO
    application_user (username, password, first_name, last_name)
VALUES
    -- haslo
    ('annanowak', '{bcrypt}$2a$12$HEDkUDBvv9b42JKsnhd0/OnoblgeWRDo4umXlF/KBnO9HbnxKvN9q', 'Anna', 'Nowak'),
    -- bezhasla
    ('jankowalski', '{MD5}0a5bb90a23c3f2c743c952afa8f68ee8', 'Jan', 'Kowalski'),
    -- cukierki123
    ('cukierki123', '{bcrypt}$2a$12$EwhE6Gal9C4v3wwkzkIAqe1DzCpmva8aQoXM8efsUjH.7O5WrlhVG', 'Gal', 'Anonim');

INSERT INTO
    user_role (name, description)
VALUES
    ('ADMIN', 'Ma dostęp do wszystkiego'),
    ('USER', 'Dostęp tylko do odczytu');

INSERT INTO
    user_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 2);