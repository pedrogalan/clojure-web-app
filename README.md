# Yet another silly web application in Clojure

A simple web app in Clojure created with the sole intention of playing a little with the language and learning the basics.

* First steps are based on a [blog post](http://matthewlisp.com/set-up-clojure-api) by Matthew Lisp.
* Simple database connection from [this blog post](https://devcenter.heroku.com/articles/clojure-web-application) by Heroku.

## Usage

Here is some HTTP requests and their responses:

### Return a static JSON
```
$ curl -s http://localhost:3000/info | jq .
{
  "version": "0.1",
  "created": "April 11th 2020"
}
```

### Return a list of records from the database
```
$ curl -s http://localhost:3000/books | jq .
[
  {
    "id": "6f3a629e-d9df-48ec-90d6-7dd528b49b5f",
    "title": "La peste"
  },
  {
    "id": "798f4dd5-e19d-459a-aa26-6d68da18eee7",
    "title": "Cuentos de Eva Luna"
  },
  {
    "id": "dbcf4530-4983-42a3-bc14-72abb64a0d0c",
    "title": "El ingenioso hidalgo don Quijote de La Mancha"
  },
  {
    "id": "e34e14ab-92eb-4de1-9e25-3d7dc735b42a",
    "title": "El ingenioso caballero don Quijote de La Mancha"
  },
  {
    "id": "def40a4c-47ae-4f2b-945d-6ca0186c2ab3",
    "title": "Clean code"
  }
]
```

### Return a single record from the database
```
$ curl -s http://localhost:3000/books/6f3a629e-d9df-48ec-90d6-7dd528b49b5f | jq .
[
  {
    "id": "6f3a629e-d9df-48ec-90d6-7dd528b49b5f",
    "title": "La peste"
  }
]
```

### Return a single record from the database
```
$ curl -s http://localhost:3000/xxx
<h1>Page not found</h1>
```

## Database

I am using a Postgres database, running in a Docker container. I didn't waste a minute configuring anything. I just created the container from the latest Postgres image.

```
$ docker run --name postgres-docker -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```

### Database creation

The scripts to create the database:

```
-- Database: books

CREATE DATABASE books
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

```
-- Table: public.books

CREATE TABLE public.books
(
    id uuid NOT NULL,
    title character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT books_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.books
    OWNER to postgres;
```

```
CREATE EXTENSION "uuid-ossp";
INSERT INTO public.books VALUES (uuid_generate_v4(), 'Clean code');
...
```

## License

Copyright © 2020 Pedro Galán

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
