CREATE MEMORY TABLE "PUBLIC"."CURRENCY"(

    "CODE" CHARACTER VARYING(5) NOT NULL,

    "CREATED_TIME" TIMESTAMP,

    "EXCHANGE_RATE" NUMERIC(10, 4) NOT NULL,

    "NAME" CHARACTER VARYING(50) NOT NULL,

    "SYMBOL" CHARACTER VARYING(10),

    "UPDATE_TIME" TIMESTAMP

);