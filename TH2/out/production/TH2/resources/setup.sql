DROP TABLE IF EXISTS Flights;
DROP TABLE IF EXISTS Carriers;
DROP TABLE IF EXISTS Months;
DROP TABLE IF EXISTS Weekdays;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Bookings;

CREATE TABLE Carriers(
    cid VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE Months(
    mid INT PRIMARY KEY,
    month VARCHAR(10)
);

CREATE TABLE Weekdays(
    did INT PRIMARY KEY,
    day_of_week VARCHAR(10)
);

CREATE TABLE Flights(
    fid INT PRIMARY KEY,
    year INT,
    month_id INT,
    day_of_month INT,
    day_of_week_id INT,
    carrier_id VARCHAR(10),
    flight_num INT,
    origin_city VARCHAR(20),
    origin_state VARCHAR(20),
    dest_city VARCHAR(20),
    dest_state VARCHAR(20),
    departure_delay INT,
    taxi_out INT,
    arrival_delay INT,
    cancled INT,
    actual_time INT,
    distance INT,
    FOREIGN KEY(carrier_id) REFERENCES Carriers(cid),
    FOREIGN KEY(month_id) REFERENCES Months(mid),
    FOREIGN KEY(day_of_week_id) REFERENCES Weekdays(did)
);

CREATE TABLE Customers(
    customer_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    phone_number VARCHAR(20)
);

CREATE TABLE Bookings(
    booking_id INTEGER PRIMARY KEY,
    customer_id VARCHAR(20),
    flight_id INT,
    booking_date TEXT,
    FOREIGN KEY(customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY(flight_id) REFERENCES Flights(fid)
);

.mode csv
.import C:\Users\Lenovo\Documents\Study\CSDL\TH2\resources\data\carriers.csv Carriers
.import C:\Users\Lenovo\Documents\Study\CSDL\TH2\resources\data\months.csv Months
.import C:\Users\Lenovo\Documents\Study\CSDL\TH2\resources\data\weekdays.csv Weekdays
.import C:\Users\Lenovo\Documents\Study\CSDL\TH2\resources\data\flights-small.csv Flights



