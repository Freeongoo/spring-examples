# FlyWay migrate

Flyway tries to read database migration scripts from classpath:db/migration folder by default.

All the migration scripts must follow a particular naming convention - V<VERSION_NUMBER>__<NAME>.sql (https://flywaydb.org/documentation/migrations#naming)

## How does Flyway manage migrations?

Flyway creates a table called flyway_schema_history when it runs the migration for the first time and stores all the meta-data required for versioning the migrations in this table.

if you need to add new changes - you need to add a new patch file for sql