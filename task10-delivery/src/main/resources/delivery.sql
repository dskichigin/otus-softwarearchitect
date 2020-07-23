CREATE TABLE slots
(
  id character(36) NOT NULL,
  orderid character(36),
  time_from character(5),
  time_to character(5),
  CONSTRAINT pk_slots PRIMARY KEY (id)
)