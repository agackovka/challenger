# Getting Started

### Create database and user
``` 
create user challenger_owner with password 'Temp4321';
grant postgres to challenger_owner;
create schema if not exists challenger_core authorization challenger_owner;
```