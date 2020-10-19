create table poke_species (
	id bigint NOT NULL AUTO_INCREMENT,
	created_by varchar(255), 
	created_date timestamp, 
	modified_by varchar(255), 
	modified_date timestamp, 
	color varchar(255), 
	id_ext varchar(255), 
	name varchar(255), url varchar(255), 
	primary key (id))
	ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

create table poke_character (
	id bigint NOT NULL AUTO_INCREMENT, 
	created_by varchar(255), 
	created_date timestamp, 
	modified_by varchar(255), 
	modified_date timestamp, 
	base_experience integer, 
	height integer, 
	name varchar(255), 
	weight integer, 
	species_id bigint, 
	primary key (id),
	CONSTRAINT FOREIGN KEY fk_poke_species (species_id) REFERENCES poke_species (id)) 
	ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

GRANT ALL PRIVILEGES ON pokemon.* TO 'root'@'localhost';

