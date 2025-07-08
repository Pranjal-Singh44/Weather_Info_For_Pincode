CREATE TABLE IF NOT EXISTS pincode_info (
    pincode VARCHAR(255) NOT NULL PRIMARY KEY,
    city VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE
);

CREATE TABLE IF NOT EXISTS weather_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pincode VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    temperature DOUBLE NOT NULL,
    humidity INT NOT NULL,
    description VARCHAR(255) NOT NULL
);