-- Inserting workout details for contracts
INSERT INTO Workout (id, contract_id, date_of_workout, number_of_exercises, warming_up_time_in_seconds, number_of_sets, ordinal_number_of_workout, pause_between_sets_in_seconds, self_rating, workout_status, next_workout)
VALUES
('5d023fe0-f953-46b1-8e3d-9183aae9d4a4', 'e75833d3-af66-4c11-b787-3d8414e3c331', '2023-01-05', 10, 300, 4, 5, 60, 4, 'WAITING', 'PULL'),
('b899b5fe-2a26-49e0-8320-d5de5219c2c9', 'f24a456e-3984-4c89-b857-95a54ebaa73e', '2023-01-08', 8, 240, 3, 4, 45, 5, 'SUCCESSFUL_WORKOUT', 'PULL'),
('f052bd01-6d4b-4535-8b90-68e1b2221f78', '13e328a9-3308-4f05-9f26-0fc82e83b2d0', '2023-01-12', 12, 360, 4, 6, 60, 4, 'WAITING', 'PULL'),
('2d0a3f9c-0d36-4d26-a4fc-0d1f36a7f9ae', 'f8e13460-af6c-456e-a928-51a3e80e7070', '2023-01-15', 6, 180, 2, 3, 30, 3, 'SUCCESSFUL_WORKOUT', 'PULL'),
('bc4e71b6-9639-4d0b-982f-7a3c2d9e9c8b', 'e9e8cf3e-5e40-45d0-aa63-2e6b6f8650d3', '2023-01-18', 14, 420, 5, 7, 75, 5, 'WAITING', 'PULL');
