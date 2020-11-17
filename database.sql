-- (!) IMPORTANT: Use this with caution
-- DROP DATABASE IF EXISTS redbook;
CREATE DATABASE redbook;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'redbook') THEN

      CREATE ROLE redbook LOGIN PASSWORD 'redbook';
   END IF;
END
$do$;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT schema_name 
	  FROM information_schema.schemata
      WHERE schema_name = 'redbook') THEN

      CREATE SCHEMA AUTHORIZATION redbook;
   END IF;
END
$do$;

CREATE TABLE IF NOT EXISTS redbook.image (
	image_id INT GENERATED ALWAYS AS IDENTITY,
	image_path VARCHAR(255) NOT NULL,
	PRIMARY KEY(image_id)
);

CREATE TABLE IF NOT EXISTS redbook.reaction (
	reaction_id INT GENERATED ALWAYS AS IDENTITY,
	reaction_name VARCHAR(20) NOT NULL,
	PRIMARY KEY(reaction_id)
);

CREATE TABLE IF NOT EXISTS redbook.users (
	user_id INT GENERATED ALWAYS AS IDENTITY,
	username VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(50),
	password VARCHAR(255) NOT NULL,
	date_of_birth DATE,
	join_date DATE NOT NULL,
	reddit_user_id VARCHAR(20),
	reddit_auth_token VARCHAR(128),
	avatar_id INT,
	description TEXT,
	karma INT,
	coins MONEY,
	banned BOOLEAN,
	deleted BOOLEAN,
	delete_date TIMESTAMPTZ,
	PRIMARY KEY(user_id),
	FOREIGN KEY(avatar_id)
		REFERENCES redbook.image(image_id)
			ON DELETE SET NULL
);

ALTER TABLE redbook.users DROP CONSTRAINT users_email_key;

CREATE TABLE IF NOT EXISTS redbook.privilege (
	privilege_id INT GENERATED ALWAYS AS IDENTITY,
	privilege_name VARCHAR(20),
	privilege_description VARCHAR(140),
	PRIMARY KEY(privilege_id)
);

CREATE TABLE IF NOT EXISTS redbook.privilegeMap (
	privilege_map_id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT REFERENCES redbook.users(user_id),
	privilege_id INT REFERENCES redbook.privilege(privilege_id),
	PRIMARY KEY(privilege_map_id)
);

CREATE TABLE IF NOT EXISTS redbook.movementLog (
	movementlog_id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT,
	datetime TIMESTAMPTZ NOT NULL,
	act VARCHAR(100) NOT NULL,
	PRIMARY KEY(movementlog_id),
	FOREIGN KEY(user_id)
		REFERENCES redbook.users(user_id)
			ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS redbook.page (
	page_id INT GENERATED ALWAYS AS IDENTITY,
	page_name VARCHAR(50) NOT NULL UNIQUE,
	page_description TEXT,
	nsfw BOOLEAN,
	banner_id INT REFERENCES redbook.image(image_id),
	icon_id INT REFERENCES redbook.image(image_id),
	open_date DATE NOT NULL,
	confidential BOOLEAN,
	banned BOOLEAN,
	quaratined BOOLEAN,
	PRIMARY KEY(page_id)
);

CREATE TABLE IF NOT EXISTS redbook.submissionType (
	submissiontype_id INT GENERATED ALWAYS AS IDENTITY,
	submissiontype_name VARCHAR(50) NOT NULL,
	PRIMARY KEY(submissiontype_id)
);

CREATE TABLE IF NOT EXISTS redbook.submission (
	submission_id INT GENERATED ALWAYS AS IDENTITY,
	page_id INT REFERENCES redbook.page(page_id),
	author_id INT REFERENCES redbook.users(user_id),
	submission_type_id INT REFERENCES redbook.submissionType(submissiontype_id),
	title VARCHAR(120) NOT NULL,
	_content TEXT,
	score INT NOT NULL,
	posted TIMESTAMPTZ NOT NULL,
	nsfw BOOLEAN NOT NULL,
	spoiler BOOLEAN,
	archived BOOLEAN,
	_locked BOOLEAN,
	edited BOOLEAN,
	edit_datetime TIMESTAMPTZ,
	removed BOOLEAN,
	remove_reason VARCHAR(140),
	remove_datetime TIMESTAMPTZ,
	pinned BOOLEAN,
	num_comments INT NOT NULL,
	media INT REFERENCES redbook.image(image_id),
	PRIMARY KEY(submission_id)
);

CREATE TABLE IF NOT EXISTS redbook.userVoteMap (
	vote_id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT REFERENCES redbook.users(user_id),
	submission_id INT REFERENCES redbook.submission(submission_id),
	reaction_id INT REFERENCES redbook.reaction(reaction_id),
	PRIMARY KEY(vote_id)
);

CREATE TABLE IF NOT EXISTS redbook.userPageMap (
	user_page_map_id INT GENERATED ALWAYS AS IDENTITY,
	user_id INT REFERENCES redbook.users(user_id),
	page_id INT REFERENCES redbook.submission(submission_id),
	moderator BOOLEAN NOT NULL,
	PRIMARY KEY(user_page_map_id)
);

CREATE TABLE IF NOT EXISTS redbook.bookmarkMap (
	bookmark_map_id INT GENERATED ALWAYS AS IDENTITY,
	submission BOOLEAN NOT NULL,
	post_id INT,
	user_id INT REFERENCES redbook.users(user_id),
	PRIMARY KEY(bookmark_map_id)
);

CREATE TABLE IF NOT EXISTS redbook.comments (
	comment_id INT GENERATED ALWAYS AS IDENTITY,
	author_id INT REFERENCES redbook.users(user_id),
	submission_id INT REFERENCES redbook.submission(submission_id),
	_content TEXT NOT NULL,
	score INT NOT NULL,
	posted TIMESTAMPTZ NOT NULL,
	spoiler BOOLEAN,
	edited BOOLEAN,
	edit_datetime TIMESTAMPTZ,
	deleted BOOLEAN,
	delete_reason VARCHAR(140),
	pinned BOOLEAN,
	PRIMARY KEY(comment_id)
);

INSERT INTO redbook.privilege (privilege_name, privilege_description)
VALUES 
	('ROLE_ADMIN', 'Redbook admins'),
	('ROLE_MOD', 'Page moderators'),
	('ROLE_USER', 'Common user');
		
SELECT u.username, pr.privilege_name
FROM redbook.users u
	INNER JOIN redbook.privilegemap pmap
		ON u.user_id=pmap.user_id
	INNER JOIN redbook.privilege pr
		ON pmap.privilege_id=pr.privilege_id;
