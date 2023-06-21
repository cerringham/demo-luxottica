# Demo Luxottica - Diagnostic Report
### Docker Compose
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

When finished, you can close the Docker connection:

```bash
docker-compose down
```
## Using the Application

Once the application is up and running, it is accessible on port 8082.

### APIs

#### Find Diagnostic Report by id
`[get] /diagnostic-report/{id}`
http://127.0.0.1:8082/diagnostic-report/1129