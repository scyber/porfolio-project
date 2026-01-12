-- 
CREATE TABLE IF NOT EXISTS user_profile (
    user_id      VARCHAR(255) PRIMARY KEY,
    profile_json JSONB        NOT NULL,
    updated_at   TIMESTAMPTZ  NOT NULL
);
