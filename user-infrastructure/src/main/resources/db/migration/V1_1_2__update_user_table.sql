ALTER TABLE users DROP COLUMN password;
ALTER TABLE users ADD COLUMN hashed_password VARCHAR(255) NOT NULL;
ALTER TABLE users ADD COLUMN salt BYTEA NOT NULL;