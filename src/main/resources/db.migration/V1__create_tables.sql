CREATE TABLE senders (
     id SERIAL PRIMARY KEY,
     email_from VARCHAR(255) NOT NULL,
     UNIQUE(email_from)
);