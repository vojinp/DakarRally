INSERT INTO roles (id, name) VALUES (0, 'ROLE_USER')
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN')

INSERT INTO users (id, username, password, email, name) VALUES (2, 'user', '$2a$10$rqOD/s387VIhFEEoMyUkIuAom/aAZTnJpjE3hlIQ9JGeiJLtQhu5G', 'user@gmail.com', 'user')
INSERT INTO users (id, username, password, email, name) VALUES (3, 'admin', '$2a$10$kLqqutUjoG5sTsEVegRbpubbm3/V/Os91PoRZq22aCt05p/aom/zq', 'admin@gmail.com', 'admin')

INSERT INTO users_roles (user_id, role_id) VALUES (2, 0)
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1)