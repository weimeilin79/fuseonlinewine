 /**DROP TABLE IF EXISTS winelist;**/

CREATE TABLE IF NOT EXISTS winelist (           
 id integer not null ,
 wine varchar(100) not null,
 price integer not null,
 year integer not null,
 gws integer not null,
 ci varchar(3) not null,
 nbj integer not null,
 productcode varchar(30) null,
 pricebookentryid varchar(30) null,
 CONSTRAINT wineid UNIQUE(id)
);