--Borrar tablas existentes
drop table if exists categorias cascade;
drop table if exists categorias_unidad_medida cascade;
drop table if exists tipo_documentos cascade;
drop table if exists estado_pedidos cascade;
drop table if exists cabecera_ventas cascade;
drop table if exists unidades_medida cascade;
drop table if exists proveedores cascade;
drop table if exists productos cascade;
drop table if exists ventas cascade;
drop table if exists cabecera_pedido cascade;
drop table if exists detalle_pedido cascade;
drop table if exists historial_stock cascade;	

--Crear tablas que no tienen relacion es decir carecen de foreign key.
create table categorias(
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int,
	constraint categorias_pk primary key (codigo_cat),
	constraint categoria_padre_fk foreign key (categoria_padre)
	references categorias(codigo_cat)
);
insert into categorias (nombre,categoria_padre)
values ('Materia prima',null);
insert into categorias (nombre,categoria_padre)
values ('Proteina',1);
insert into categorias (nombre,categoria_padre)
values ('Salsas',1);
insert into categorias (nombre,categoria_padre)
values ('Punto de venta',null);
insert into categorias (nombre,categoria_padre)
values ('Bebidas',4);
insert into categorias (nombre,categoria_padre)
values ('Con alcohol',5);
insert into categorias (nombre,categoria_padre)
values ('Sin alcohol',5);

create table categorias_unidad_medida(
	codigo char(1)not null,
	nombre varchar(50)not null,
	constraint categorias_unidad_medida_pk primary key (codigo)
);
insert into categorias_unidad_medida(codigo,nombre)
values ('U','Unidades');
insert into categorias_unidad_medida(codigo,nombre)
values ('V','Volumen');
insert into categorias_unidad_medida(codigo,nombre)
values ('P','Peso');

create table tipo_documentos(
	codigo char(1) not null,
	descripcion varchar(50) not null,
	constraint tipo_documentos_pk primary key (codigo)
);
insert into tipo_documentos(codigo,descripcion)
values ('R','Ruc');
insert into tipo_documentos(codigo,descripcion)
values ('C','Cedula');

create table estado_pedidos(
	estado char(1) not null,
	descripcion varchar(30) not null,
	constraint estado_pedidos_pk primary key (estado)
);
insert into estado_pedidos(estado,descripcion)
values ('S','Solicitado');
insert into estado_pedidos(estado,descripcion)
values ('R','Recibido');

create table cabecera_ventas(
	codigo serial not null,
	fecha timestamp not null,
	total_sin_iva money not null,
	iva money not null,
	total money not null,
	constraint cabecera_ventas_pk primary key (codigo)
);
insert into cabecera_ventas(fecha,total_sin_iva,iva,total)
values ('7/11/2024 17:30',3.26,0.39,3.65);

--Crear tablas que tienen una relacion es decir un foreign key
create table unidades_medida(
	codigo char(5) not null,
	descripcion varchar(50) not null,
	categoria_udm char(1) not null,
	constraint unidades_medida_pk primary key(codigo),
	constraint categoria_udm_fk foreign key(categoria_udm)
	references categorias_unidad_medida(codigo)
);
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('ml','mililitros','V');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('l','litros','V');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('u','unidades','U');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('d','docenas','U');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('g','gramos','P');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('kg','kilogramos','P');
insert into unidades_medida(codigo,descripcion,categoria_udm)
values ('lb','libras','P');

create table proveedores(
	identificador varchar(13) not null,
	tipo_documento char(1) not null,
	nombre varchar(50) not null,
	telefono char(10) not null,
	correo varchar(30) not null,
	direccion varchar(50) not null,
	constraint proveedores_pk primary key (identificador),
	constraint tipo_documentos_fk foreign key (tipo_documento)
	references tipo_documentos(codigo)
);
insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)
values ('0504497785','C','Jefferson Toaquiza','0968493594','jitv200798@g,gmail.com','Guaytacama');
insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion)
values ('0504497785001','R','La Favorita','0976543691','lafavorita@gmail.com','Guayaquil');

---Crear tablas que tienen dos relaciones es decir dos foreign key
create table productos(
	codigo_prod serial not null,
	nombre varchar(50) not null,
	udm char(5) not null,
	precio_venta money not null,
	tien_iva boolean not null,
	coste money not null,
	categoria int not null,
	stock int not null,
	constraint productos_pk primary key(codigo_prod),
	constraint unidades_medida_fk foreign key(udm)
	references unidades_medida(codigo),
	constraint categorias_fk foreign key (categoria)
	references categorias(codigo_cat)
);
insert into productos(nombre,udm,precio_venta,tien_iva,coste,categoria,stock)
values ('Cocacola pequeña','u',0.5804,'true',0.3729,7,105);
insert into productos(nombre,udm,precio_venta,tien_iva,coste,categoria,stock)
values ('Salsa de tomate','kg',0.95,'true',0.8736,3,0);
insert into productos(nombre,udm,precio_venta,tien_iva,coste,categoria,stock)
values ('Moztasa','kg',0.95,'true',0.89,3,0);
insert into productos(nombre,udm,precio_venta,tien_iva,coste,categoria,stock)
values ('Fuze tea','u',0.8,'true',0.7,7,49);

create table ventas(
	codigo serial not null,
	cabecera_venta int not null,
	producto int not null,
	cantidad int not null,
	precio_venta money not null,
	subtotal money not null,
	subtotal_con_iva money not null,
	constraint ventas_pk primary key(codigo),
	constraint cabecera_ventas_fk foreign key(cabecera_venta)
	references cabecera_ventas(codigo),
	constraint productos_fk foreign key (producto)
	references productos(codigo_prod)
);
insert into ventas(cabecera_venta,producto,cantidad,precio_venta,subtotal,subtotal_con_iva)
values (1,1,5,0.58,2.90,3.35);
insert into ventas(cabecera_venta,producto,cantidad,precio_venta,subtotal,subtotal_con_iva)
values (1,4,1,0.36,0.36,0.4);

create table cabecera_pedido(
	numero serial not null,
	proveedor char(13) not null,
	fecha date not null,
	estatus char(1)not null,
	constraint cabecera_pedido_pk primary key(numero),
	constraint proveedores_fk foreign key(proveedor)
	references proveedores(identificador),
	constraint estado_pedidos_fk foreign key (estatus)
	references estado_pedidos(estado)
);
insert into cabecera_pedido(proveedor,fecha,estatus)
values ('0504497785','6/11/2024','S');
insert into cabecera_pedido(proveedor,fecha,estatus)
values ('0504497785','6/11/2024','R');

create table detalle_pedido(
	codigo serial not null,
	cabecera_pedio int not null,
	producto int not null,
	cantidad_solicitada int not null,
	subtotal money not null,
	cantidad_recivida int not null,
	constraint detalle_pedido_pk primary key(codigo),
	constraint cabecera_pedido_fk foreign key(cabecera_pedio)
	references cabecera_pedido(numero),
	constraint productos_fk foreign key (producto)
	references productos(codigo_prod)
);
insert into detalle_pedido(cabecera_pedio,producto,cantidad_solicitada,subtotal,cantidad_recivida)
values (1,1,100,37.39,100);
insert into detalle_pedido(cabecera_pedio,producto,cantidad_solicitada,subtotal,cantidad_recivida)
values (1,4,50,11.8,50);

--Crear tablas de movimientos
create table historial_stock(
	codigo serial not null,
	fecha timestamp not null,
	referencia varchar(50) not null,
	producto int not null,
	cantidad int not null,
	constraint historial_stock_pk primary key(codigo),
	constraint productos_fk foreign key (producto)
	references productos(codigo_prod)
);
insert into historial_stock(fecha,referencia,producto,cantidad)
values ('6/11/2024  19:36','PEDIDO 1',1,100);
insert into historial_stock(fecha,referencia,producto,cantidad)
values ('6/11/2024  19:36','PEDIDO 1',4,50);
insert into historial_stock(fecha,referencia,producto,cantidad)
values ('7/5/2024  11:44','PEDIDO 2',1,10);
insert into historial_stock(fecha,referencia,producto,cantidad)
values ('7/5/2024  12:46','VENTA 1',1,-5);
insert into historial_stock(fecha,referencia,producto,cantidad)
values ('7/6/2024  18:20','VENTA 1',4,-1);

--VISUALIACION DE TABLAS
select * from categorias
select * from categorias_unidad_medida
select * from tipo_documentos
select * from estado_pedidos
select * from cabecera_ventas
select * from unidades_medida
select * from proveedores
select * from productos
select * from ventas
select * from cabecera_pedido
select * from detalle_pedido
select * from historial_stock

--Busquedas
select prov.identificador,prov.tipo_documento,td.descripcion,prov.nombre,prov.telefono,prov.correo,prov.direccion from proveedores prov, tipo_documentos td
where prov.tipo_documento=td.codigo
and upper (nombre) like '%S%'

select codigo,descripcion from tipo_documentos

select prod.codigo_prod,prod.nombre,udm.codigo as nombre_udm,udm.descripcion as descripcion_udm,cast(prod.precio_venta as decimal(6,2)),prod.tien_iva,cast(prod.coste as decimal(5,4)),cat.codigo_cat as categoria,cat.nombre as nombre_categoria,prod.stock 
from productos prod, unidades_medida udm, categorias cat
where prod.udm = udm.codigo
and prod.categoria = cat.codigo_cat
and upper (prod.nombre) like '%S%'
--Actualizaciones
update cabecera_pedido set estatus='S' where numero=7
update detalle_pedido set cantidad_recivida=40, subtotal=20
where codigo=5