create database clarity_avetmiss;

use clarity_avetmiss;

create table unit (
    id int(10) NOT NULL AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    field_of_education_identifier varchar(20),
    primary key (id)
);

create INDEX idx_unit_code on unit (code);


