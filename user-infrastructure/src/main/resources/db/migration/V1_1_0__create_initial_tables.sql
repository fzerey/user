-- Create 'groups' table
CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create 'attributes' table
CREATE TABLE attributes (
    id BIGSERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL
);

-- Create 'users' table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    group_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

-- Create 'user_attribute' join table for ManyToMany relationship
CREATE TABLE users_attributes (
    user_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    value VARCHAR(255) NOT NULL, 
    PRIMARY KEY (user_id, attribute_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (attribute_id) REFERENCES attributes(id)
);