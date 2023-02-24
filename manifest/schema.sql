CREATE ROLE delivery_service WITH
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    INHERIT
    LOGIN
    NOREPLICATION
    NOBYPASSRLS
    CONNECTION LIMIT -1
    PASSWORD 'nSGxvmFGxr';

-- DROP SCHEMA auth_service;

CREATE SCHEMA delivery_service AUTHORIZATION delivery_service;


-- Permissions

GRANT ALL ON SCHEMA delivery_service TO delivery_service;

CREATE TABLE delivery_service.message (
	uuid varchar(100) NOT NULL,
	CONSTRAINT message_pkey PRIMARY KEY (uuid)
);

ALTER TABLE delivery_service.message OWNER TO delivery_service;
GRANT ALL ON TABLE delivery_service.message TO delivery_service;


CREATE TABLE delivery_service.courier (
   id bigserial NOT NULL,
   fio varchar(50) NOT NULL,
   phone varchar(50) NOT NULL,
   CONSTRAINT courier_pkey PRIMARY KEY (id)
);

INSERT INTO delivery_service.courier(fio, phone)
VALUES
    ('Борис',  '375291011010'),
    ('Сергей', '375297788990'),
    ('Илья',   '375291233211');

ALTER TABLE delivery_service.courier OWNER TO delivery_service;
GRANT ALL ON TABLE delivery_service.courier TO delivery_service;

CREATE TABLE delivery_service.courier_schedule (
      id bigserial NOT NULL,
      courier_id int8 NOT NULL,
      delivery_date date NOT NULL,
      delivery_hour int8 NOT NULL,
      order_id int8,
      CONSTRAINT courier_schedule_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS delivery_service.courier_schedule
    ADD CONSTRAINT fk_courier_schedule FOREIGN KEY (courier_id) REFERENCES delivery_service.courier (id);

ALTER TABLE delivery_service.courier_schedule OWNER TO delivery_service;
GRANT ALL ON TABLE delivery_service.courier_schedule TO delivery_service;