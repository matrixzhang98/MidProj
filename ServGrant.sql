use EmploymentServGrant;

create table ServGrant(
	id int not null primary key identity(1,1),
	grantName varchar(max) not null,
	business varchar(max) not null,
	phone varchar(max) not null,
	extensionNum varchar(max) not null
);

select * from ServGrant;

drop table if exists ServGrant;


create table Gallery(
	galleryId int not null primary key identity(1,1),
	imageName varchar(50) not null,
	imageFile varbinary(max) not null
);

select * from Gallery;

drop table if exists Gallery;