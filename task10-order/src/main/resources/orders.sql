CREATE TABLE orders
(
  id character(36) NOT NULL,
  productid character(36),
  delivery_time_from character varying(36),
  delivery_time_to character varying(36),
  col int,
  summa numeric,
  status character varying(50),
  CONSTRAINT pk_orders PRIMARY KEY (id)
)