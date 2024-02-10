CREATE TABLE user_table (
    id UUID PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(30) NOT NULL,
    country VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INTEGER NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30)
);

CREATE TABLE coach (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES user_table(id) ON DELETE CASCADE,
    years_of_experience NUMERIC(5,2) NOT NULL,
    education TEXT NOT NULL,
    monthly_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE client (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES user_table(id) ON DELETE CASCADE,
    medical_condition TEXT,
    injuries TEXT
);

CREATE TABLE contract (
    id UUID PRIMARY KEY,
    coach_id UUID REFERENCES coach(id) ON DELETE SET NULL,
    client_id UUID REFERENCES client(id) ON DELETE SET NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    monthly_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE measurement (
    id UUID PRIMARY KEY,
    contract_id UUID REFERENCES contract(id) ON DELETE SET NULL,
    measurement_date DATE NOT NULL,
    body_weight NUMERIC(6, 2) NOT NULL,
    body_fat NUMERIC(5, 2) NOT NULL,
    waist_circumference NUMERIC(6, 2) NOT NULL,
    chest_circumference NUMERIC(6, 2) NOT NULL,
    arm_circumference NUMERIC(6, 2) NOT NULL,
    leg_circumference NUMERIC(6, 2) NOT NULL
);

CREATE TABLE workout (
    id UUID PRIMARY KEY,
    contract_id UUID REFERENCES contract(id) ON DELETE SET NULL,
    date_of_workout DATE,
    number_of_exercises INTEGER NOT NULL,
    warming_up_time_in_seconds INTEGER NOT NULL,
    number_of_sets INTEGER NOT NULL,
    ordinal_number_of_workout INTEGER NOT NULL,
    pause_between_sets_in_seconds INTEGER NOT NULL,
    self_rating INTEGER,
    workout_status VARCHAR(50),
    next_workout VARCHAR(50)
);

CREATE TABLE exercise (
    id UUID PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description TEXT NOT NULL,
    exercise_equipment VARCHAR(30) NOT NULL,
    difficulty_level VARCHAR(30) NOT NULL,
    workout_type VARCHAR(30) NOT NULL
);

CREATE TABLE workout_session (
    id UUID PRIMARY KEY,
    workout_id UUID REFERENCES workout(id) ON DELETE SET NULL,
    exercise_id UUID REFERENCES exercise(id) ON DELETE SET NULL,
    number_of_reps INTEGER NOT NULL,
    pause_after_exercise_in_seconds INTEGER NOT NULL,
    weight NUMERIC(6, 2) NOT NULL,
    CONSTRAINT fk_exercise
    FOREIGN KEY (exercise_id)
    REFERENCES exercise(id)
    ON DELETE SET NULL
);

CREATE TABLE workout_plan (
    id UUID PRIMARY KEY,
    workout_id UUID,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_of_workouts INTEGER NOT NULL,
    FOREIGN KEY (workout_id) REFERENCES workout(id)
);