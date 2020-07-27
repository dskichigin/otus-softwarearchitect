CREATE TABLE payments
(
  id character(36) NOT NULL,
  orderid character(36),
  summa numeric,
  status character varying(50),
  CONSTRAINT pk_payments PRIMARY KEY (id)
)