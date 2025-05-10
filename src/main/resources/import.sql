DROP TABLE IF EXISTS public.student_course;

DROP TABLE IF EXISTS public.course;

DROP TABLE IF EXISTS public.student_finance;

DROP TABLE IF EXISTS public.student;

-- Tabel mahasiswa
CREATE TABLE
    public.student (
        id_student UUID PRIMARY KEY,
        student_number VARCHAR(100) NOT NULL UNIQUE,
        fullname VARCHAR(255) NOT NULL,
        major VARCHAR(255) NOT NULL,
        gpa NUMERIC(3, 2) NOT NULL,
        birth DATE NOT NULL
    );

-- Tabel keuangan mahasiswa
CREATE TABLE
    public.student_finance (
        id_student_finance UUID PRIMARY KEY,
        id_student UUID NOT NULL REFERENCES public.student (id_student) ON DELETE CASCADE,
        credit_hours INTEGER NOT NULL,
        rate_per_credit NUMERIC(10, 2) NOT NULL,
        is_paid BOOLEAN NOT NULL DEFAULT FALSE
    );

-- Tabel mata kuliah
CREATE TABLE
    public.course (
        id_course UUID PRIMARY KEY,
        course_code VARCHAR(50) NOT NULL UNIQUE,
        course_name VARCHAR(255),
        credit_hours INTEGER NOT NULL,
        lecturer VARCHAR(255) NOT NULL
    );

-- Relasi mahasiswa ke mata kuliah
CREATE TABLE
    public.student_course (
        id_student_course UUID PRIMARY KEY,
        id_student UUID NOT NULL REFERENCES public.student (id_student) ON DELETE CASCADE,
        id_course UUID NOT NULL REFERENCES public.course (id_course) ON DELETE CASCADE,
        enrolled_on DATE DEFAULT CURRENT_DATE
    );