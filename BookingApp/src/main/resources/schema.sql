DROP TABLE IF EXISTS seat_reserved;
DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS screening;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS movie;

create table customer (
    id bigint identity not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    primary key (id)
)

create table movie (
    id bigint identity not null,
    screening_time int not null,
    title varchar(50) not null,
    primary key (id)
)

create table reservation (
    id bigint identity not null,
    expiration_time datetime2 not null,
    price double precision not null,
    customer_id bigint not null,
    screening_id bigint not null,
    primary key (id)
)

create table room (
    id bigint identity not null,
    capacity int not null,
    name varchar(50) not null,
    primary key (id)
)

create table screening (
    id bigint identity not null,
    start_time datetime2 not null,
    movie_id bigint not null,
    room_id bigint not null,
    primary key (id)
)

create table seat (
    id bigint identity not null,
    row_number int not null,
    seat_number int not null,
    room_id bigint not null,
    primary key (id)
)

create table seat_reserved (
    seat_id bigint not null,
    reservation_id bigint not null,
    primary key (reservation_id, seat_id)
)

alter table reservation
    add constraint FK41v6ueo0hiran65w8y1cta2c2
        foreign key (customer_id)
            references customer

alter table reservation
    add constraint FKsus9r7msj3uas10wxl1jvj8xb
        foreign key (screening_id)
            references screening

alter table screening
    add constraint FKfp7sh76xc9m508stllspchnp9
        foreign key (movie_id)
            references movie

alter table screening
    add constraint FKd1m1np9gx570qj5ycbddv7fji
        foreign key (room_id)
            references room

alter table seat
    add constraint FKd7f42843rt05tt66t6vcb7s9u
        foreign key (room_id)
            references room

alter table seat_reserved
    add constraint FKkc4tetul8ya0dqo7r8bygygy1
        foreign key (reservation_id)
            references reservation

alter table seat_reserved
    add constraint FKoif100090qur1dyga3dnoye41
        foreign key (seat_id)
            references seat