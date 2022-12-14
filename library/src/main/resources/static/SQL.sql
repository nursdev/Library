create table person(
                       id int generated by default as identity primary key,
                       "name" varchar not null,
                       lastname varchar not null,
                       email varchar not null unique,
                       yearOfBirth int check ( yearOfBirth > 0)
);


create table book(
                     id int generated by default as identity primary key,
                     title varchar not null,
                     author varchar not null,
                     "year" int check ( "year" > 0 ),
                     person_id int references person(id) on DELETE set null
)