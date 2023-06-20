# Demo Luxottica

## Prerequisites

### Services

To start the project and run all the required services, follow these steps:

Open a terminal or command prompt and navigate to the project's root directory:

```bash
cd demo-luxottica
```
Start the services using Docker Compose:

```bash
docker-compose up -d
```
Docker Compose will read the `docker-compose.yml` file provided in the project and start the necessary containers for the application.

Wait for the services to start and initialize. This process may take a few moments.

### Keycloak Realm Setup

To set up the Keycloak realm, follow these steps:
1. Open the Keycloak admin console by accessing `http://localhost:8084/auth/admin` in your web browser.

2. Log in with your Keycloak admin credentials (admin, admin).

3. Create a new realm `luxottica-spike`

4. Go to the "Realm Settings" section and click on "Partial Import" in the "Action" tab on the top-right corner.

5. Select the file `keycloak/realm-export.json` from the project directory.

6. Import all the resources and set `Skip` on the `If a resource already exists, specify what should be done` section

7. Click "Import" to import the Keycloak realm configuration.

### Docker Compose

Alternatively, you can use the provided `docker-compose.yml` file to set up all the required services using Docker Compose:

1. Make sure you have Docker and Docker Compose installed on your system.

2. Open a terminal and navigate to the project root directory.

3. Run the following command to start the services:

   ```shell
   docker-compose up -d
   ```

4. Wait for the containers to start

## Using the Application

Once the application is up and running, it is accessible on port 8082.

### APIs

#### Add a new patient
`[POST] /patient`
```json
{
	"first_name": "John",
	"last_name": "Doe",
	"birth_date": "1990-01-01",
	"phone_number": "+1234567890",
	"email": "john.doe@example.com",
	"address": {
		"country": "United States",
		"district": "California",
		"city": "San Francisco",
		"street": "Main Street",
		"number": "123"
	}
}
```

#### Edit an existing patient
`[PUT] /patient`
```json
{
   "first_name": "John",
   "last_name": "Doe",
   "birth_date": "1985-07-15",
   "phone_number": "+1234567890",
   "email": "johndoe@example.com",
   "address": {
      "country": "United States",
      "district": "California",
      "city": "San Francisco",
      "street": "Main Street",
      "number": "123"
   }
}
```

#### Remove an existing patient
`[DELETE] /patient/<id>`

#### Get a list of all the patients
`[GET] /patient`

#### Get a patient
`[GET] /patient/<id>`