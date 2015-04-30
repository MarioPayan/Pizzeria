drop table  if exists itemPedido;
drop table  if exists Factura;
drop table  if exists PizzaBase;
drop table  if exists Carne;
drop table  if exists Vegetal;
drop table  if exists Salsa;
drop table  if exists IngredienteAdicional;

create table PizzaBase(
pizza_id varchar(11) primary key,
nombre varchar(50) not null,
tamanio integer not null,
presentacion varchar(50) not null,
precio real not null,
foto bytea not null
);

create table IngredienteAdicional(
ingrediente_id varchar(11) primary key,
nombre varchar(50) not null,
precioPorcion real not null,
cantidad integer not null,
tipo varchar(50) not null,
foto bytea not null
);

create table Carne(
carne_id varchar(11) primary key,
presentacion varchar(50) not null,
cantidadGrasas real not null,
animal varchar(50) not null
)INHERITS(IngredienteAdicional);

create table Vegetal(
vegetal_id varchar(11) primary key,
carbohidratos integer not null
)INHERITS(IngredienteAdicional);

create table Salsa(
salsa_id varchar(11) primary key,
azucares varchar(50) not null,
carbohidratos integer not null,
grasa real not null
)INHERITS(IngredienteAdicional);

create table Factura(
factura_id varchar(11) primary key,
fecha varchar(50) not null,
precioTotal real not null
);

create table itemPedido(
factura_id varchar(11) primary key,
pizza_id_Ingrediente_id varchar (11),
CONSTRAINT item_id FOREIGN KEY (factura_id) REFERENCES Factura (factura_id),
CONSTRAINT item_fk FOREIGN KEY (pizza_id_Ingrediente_id) REFERENCES PizzaBase (pizza_id)
);