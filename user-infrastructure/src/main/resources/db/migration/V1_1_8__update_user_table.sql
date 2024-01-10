ALTER TABLE users
ADD CONSTRAINT email_unique_constraint UNIQUE (email),
ADD CONSTRAINT phone_number_unique_constraint UNIQUE (phone_number),
ADD CONSTRAINT username_unique_constraint UNIQUE (username);
