CREATE DATABASE IF NOT EXISTS voting;

USE voting;

CREATE TABLE
    IF NOT EXISTS region (region_area VARCHAR(50) PRIMARY KEY);

CREATE TABLE
    IF NOT EXISTS party (
        party_name VARCHAR(50) PRIMARY KEY,
        seats INT DEFAULT 0
    );

CREATE TABLE
    IF NOT EXISTS party_represent (
        username VARCHAR(50) PRIMARY KEY,
        pass VARCHAR(50) NOT NULL,
        partyname VARCHAR(50),
        FOREIGN KEY (partyname) REFERENCES party (party_name)
    );

CREATE TABLE
    IF NOT EXISTS party_candidates (
        party_name VARCHAR(50),
        can_name VARCHAR(50),
        can_mobile BIGINT,
        can_dob VARCHAR(15),
        region_area VARCHAR(50),
        votes INT DEFAULT 0,
        PRIMARY KEY (party_name, region_area),
        FOREIGN KEY (party_name) REFERENCES party (party_name),
        FOREIGN KEY (region_area) REFERENCES region (region_area)
    );

CREATE TABLE
    IF NOT EXISTS user (
        adhar_id BIGINT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        mobile BIGINT,
        dob VARCHAR(15),
        regionarea VARCHAR(50),
        voting ENUM ('YES', 'NO') DEFAULT 'NO',
        FOREIGN KEY (regionarea) REFERENCES region (region_area)
    );

INSERT INTO
    party (party_name)
VALUES
    ('bjp'),
    ('congress'),
    ('aap'),
    ('nota');

INSERT INTO
    party_represent (username, pass, partyname)
VALUES
    ('bjp', 'bjp123', 'bjp'),
    ('congress', 'congress123', 'congress'),
    ('aap', 'aap123', 'aap');

CREATE INDEX idx_region ON region (region_area);

CREATE INDEX idx_party ON party (party_name);

CREATE INDEX idx_party_candidates ON party_candidates (party_name, region_area);

CREATE INDEX idx_user ON user (adhar_id);

CREATE INDEX idx_user_region ON user (regionarea);

CREATE INDEX idx_party_represent ON party_represent (username, pass);