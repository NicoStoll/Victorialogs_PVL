# Middleware Logging Demo 

This project demonstrates a clean, modern and open-source log aggregation pipeline suitable for microservices and cloud-native systems. It showcases
structured logging, normalization, centralized storage and visualization.

## Overview

This repository demonstrates  complete log aggregation system using:

- Vector: collects, normalizes and forwards logs
- VictoriaLogs: Log storage
- Grafana: Visualization and dashboards

The system follows the **12-factor app** guidelines.

## Features

- fully open-source stack
- multi-service log aggregation (stdout + file)
- structured logs in JSON
- normalization of logs
- high-performance ingestion into VictoriaLogs
- Grafana dashboards powered by the VictoriaLogs datasource
- includes a sample REST service with full OpenAPI documentation that conforms to HATEOAS


## Technology stack

| Component | Purpose |
| -- | ---|
| VictoriaLogs | log database, optimized for high-volume ingestion |
| Vector | log collector & transformer |
| Grafana | dashboard & queries |
| Docker Compose | local orchestration |
| Mock microservice | generate mock logs |
| Order Service | API with structured logs (stdout) |

## Architecture 

![Architecture](./architecture.png)

## Prerequisites

- **Docker** >= Version 20
- **Docker Compose** >= Version 2

## Getting Started

Run everything: 

```sh
docker compose up -d
```

After startup containers will run on:

- [Grafana - http://localhost:3000](http://localhost:3000)
- [Order Service - http://localhost:8080](http://localhost:8080)
- [Vector - http://localhost:8686](http://localhost:8686)
- [VictoriaLogs - http:/localhost:9428](http:/localhost:9428)

## Order Service documentation

[Oder Service - Documentation](./orders/README.md)

## Grafana

Automated import of the dashboard was not possible in this demo. It has to be manually added in the GUI. Click add dashboard in the GUI 
and select the dashboard under `./grafana/provisioning/dashboards/logs.json`