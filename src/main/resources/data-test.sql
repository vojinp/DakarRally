INSERT INTO roles (id, name) VALUES (0, 'ROLE_USER')
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN')

INSERT INTO users (id, username, password, email, name) VALUES (2, 'user', '$2a$10$rqOD/s387VIhFEEoMyUkIuAom/aAZTnJpjE3hlIQ9JGeiJLtQhu5G', 'user@gmail.com', 'user')
INSERT INTO users (id, username, password, email, name) VALUES (3, 'admin', '$2a$10$kLqqutUjoG5sTsEVegRbpubbm3/V/Os91PoRZq22aCt05p/aom/zq', 'admin@gmail.com', 'admin')

INSERT INTO users_roles (user_id, role_id) VALUES (2, 0)
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1)

INSERT INTO races (id, year, start_date, status) VALUES (4, 2020, '2020-01-24 01:04:05', 'PENDING')
INSERT INTO races (id, year, start_date, status) VALUES (5, 2020, '2020-01-24 01:04:05', 'RUNNING')
INSERT INTO races (id, year, start_date, status) VALUES (6, 2020, '2020-01-24 01:04:05', 'FINISHED')

INSERT INTO vehicle_statistics (id, distance, finish_time, status) VALUES (7, 0, '2020-01-24 01:04:05', 'WORKING')

INSERT INTO vehicles (id, team_name, model, manufacturing_date, vehicle_type, vehicle_statistic_id,race_id) VALUES (8, 'team', 'model', '2020-01-24 01:04:05', 'SPORTS_CAR', 7, 4)
