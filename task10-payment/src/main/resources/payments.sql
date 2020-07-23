CREATE TABLE payment
(
  id character(36) NOT NULL,
  orderid character(36),
  summa numeric,
  status character varying(50),
  CONSTRAINT pk_payment PRIMARY KEY (id)
)