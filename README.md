# Middleware Logging Demo — Docker Compose (VictoriaLogs + Vector + Grafana)

This document provides a clean and production‑inspired demonstration of log aggregation using a fully open-source stack:

VictoriaLogs — high‑performance log database

Vector — log collector, transformer, and shipper

Grafana — visualization and querying

## The system:

mocks multiple services writing logs (some to file, some to stdout),

uses Vector to collect & normalize logs following 12‑Factor App logging principles,

stores and queries logs in VictoriaLogs,

visualizes logs in Grafana using the VictoriaLogs datasource plugin,

uses only open-source components (matches Open Cloud Initiative goals).

## Architecture Overview
+-------------+       +-----------+        +----------------+
| mock-service| ----> |           | -----> |                |
| mock-2      | ----> |  Vector   | -----> |  VictoriaLogs  |
| mock-3      | ----> |           | -----> |                |
+-------------+       +-----------+        +----------------+
                                      \
                                       \--> Grafana (Explore/ dashboards)

Vector tails files, reads stdout, adds metadata, batches, and sends logs to VictoriaLogs using its HTTP ingestion API.

Grafana queries VictoriaLogs using the official datasource plugin.