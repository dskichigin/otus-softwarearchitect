CREATE TABLE warehouses
(
  id character(36) NOT NULL,
  orderid character(36),
  productid character(36),
  col numeric,
  type character varying(50),
  active integer,
  CONSTRAINT pk_warehouse PRIMARY KEY (id)
)