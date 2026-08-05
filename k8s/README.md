# Kubernetes deployment for Employee Management System

This directory contains example Kubernetes manifests to deploy the application.

Files added:
- deployment.yaml — Deployment (2 replicas) and Service (LoadBalancer). Edit the image placeholder before deploying.
- secret.yaml — Template for DB username/password (stringData). Replace placeholders or use kubectl to create the secret.
- configmap.yaml — Template for SPRING_DATASOURCE_URL.

Quick deploy steps (recommended):

1. Build and push your Docker image to a registry. Example (GitHub Container Registry):
   - Build locally or via CI and push: ghcr.io/OWNER/employee-management-system:latest

2. Update the image in k8s/deployment.yaml:
   - Replace <IMAGE_PLACEHOLDER> with your registry image (e.g. ghcr.io/kwishaka/employee_management_system:latest)
   - Alternatively, you can leave the placeholder and run `kubectl set image` after applying the manifest.

3. Create the DB secret (preferred instead of editing secret.yaml with plaintext):

   kubectl create secret generic ems-db-secret \
     --from-literal=username=YOUR_DB_USER \
     --from-literal=password=YOUR_DB_PASSWORD

   OR edit k8s/secret.yaml to replace <DB_USER> and <DB_PASSWORD> and then apply it.

4. Create the ConfigMap for the JDBC URL (or edit configmap.yaml):

   kubectl create configmap ems-config --from-literal=SPRING_DATASOURCE_URL="jdbc:mysql://DB_HOST:3306/DB_NAME"

   OR apply the file:
   kubectl apply -f k8s/configmap.yaml

5. Apply manifests:

   kubectl apply -f k8s/secret.yaml
   kubectl apply -f k8s/configmap.yaml
   kubectl apply -f k8s/deployment.yaml

6. Check rollout and service:

   kubectl get pods -l app=ems
   kubectl describe pod <pod-name>
   kubectl get svc ems-service

7. To update image:

   kubectl set image deployment/ems-deployment ems=<YOUR_IMAGE>:tag

Notes & recommendations:
- This repo currently uses an H2 in-memory DB by default. For production you should use MySQL (or another persistent DB) and the SPRING_DATASOURCE_URL / credentials provided via the ConfigMap / Secret above.
- Consider adding readiness/liveness probes (HTTP /actuator/health) if you add Spring Boot Actuator.
- Do not store production credentials in Git. Use secrets or an external secret manager.
- Recommended follow-ups (I can add these for you):
  - Update pom.xml compiler plugin to use Java 21 (or align java.version).
  - Add application-prod.properties to read DB settings from env vars.
  - Add a GitHub Actions workflow to build and push the image to your registry.
