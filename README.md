# CRN-assignment
Implement a REST API

The current project contains the following types of classes and interfaces:
•	Domain model classes include model class of the given tables as User.java, Unit.java, Role.java, UserRole.java
•	DAO interfaces which are defining objects and methods for accessing stored data of the domain models. The interfaces are UserDAO.java, UnitDAO.java, RoleDAO.java, UserRoleDAO.java
•	DAO implementation classes which implement interfaces and provide local database for our domain models. They are UserDAOImpl.java, UnitDAOImpl.java, RoleDAOImpl.java, UserRoleDAOImpl.java
•	Restfull web service classes which provide an interface for accessing and sending queries to the databases.

How to run the project:
•	Install Java 8.
•	Run Apache Tomcat v9.0 server at local host
•	Add the project to the server
•	Run the server
•	Send the following queries to the server from internal Eclipse’s terminal

The following functionalities are supported and examples of the supported queries are provided:
1.	List all users:  curl http://localhost:8080/CRN/rest/users
2.	List all users with at least one valid user role at a given unit at a given time: curl http://localhost:8080/CRN/rest/users/11/2019-11-11T00:00:00.00Z
3.	List all units: curl http://localhost:8080/CRN/rest/units
4.	List all roles: curl http://localhost:8080/CRN/rest/roles
5.	List all user roles (both currently valid and invalid) for a given user id and unit id:  curl http://localhost:8080/CRN/rest/user-roles/1/11
6.	List only valid user roles for a given user id and unit id at a given timestamp: curl http://localhost:8080/CRN/rest/user-roles/1/11/2019-11-11T00:00:00.00Z
7.	Create a new user: curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Mohammad\"}" http://localhost:8080/CRN/rest/users
8.	Update an existing user: curl -X PUT -H "Content-Type: application/json" -d "{\"name\”:\"Mohammad\”} http://localhost:8080/CRN/rest/users/1/1
9.	Delete an existing user. A user can only be deleted if there are no user roles for that user.: curl -X DELETE http://localhost:8080/CRN/rest/users/3/1
10.	Create a new user role for a given user id, unit id, role id, an optional valid from/to timestamp. If a valid to timestamp is specified, it must be after the valid from timestamp (or the current date and time if valid from timestamp is not specified in the request). At most one user role for a given combination of
user id, unit id and role id can be valid at any point in time. The requirement that the valid to timestamp, if specified, must come after the
valid from timestamp must be enforced, and an update that would cause two user roles for the same user id, unit id and role id to be valid at the
same time must be rejected: curl -X POST -H "Content-Type: application/json" -d "{\"userId\":3,\"unitId\":13,\"roleId\":103,\"validFrom\":\"2020-10-10T00:00:00.00Z\",\"validTo\":\"2020-12-12T00:00:00.00Z\"}" http://localhost:8080/CRN/rest/user-roles
11.	Update an existing user role: curl -X PUT -H "Content-Type: application/json" -d "{\"validFrom\":\"2020-12-21T23:00:00.00Z\",\"validTo\":\"2021-12-21T23:00:00.00Z\"}" http://localhost:8080/CRN/rest/user-roles/1009/1
12.	Delete an existing user role: curl -X DELETE http://localhost:8080/CRN/rest/user-roles/1009
13. For a given unit id, list all users with at least one user role at that unit (whether the user role is currently valid or not): curl http://localhost:8080/CRN/rest/users/11 
