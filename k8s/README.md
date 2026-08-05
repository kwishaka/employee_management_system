# Kubernetes deployment for Employee Management System

This directory contains example Kubernetes manifests to deploy the application.

Files added/updated:
- deployment.yaml — Deployment (2 replicas) and Service (LoadBalancer). Image is a placeholder: "<IMAGE_PLACEHOLDER>". Edit to your image before applying or use kubectl set image.
- secret.yaml — Template for DB username/password (stringData). Replace placeholders or create the secret with kubectl create secret generic.
- configmap.yaml — Template for SPRING_DATASOURCE_URL.
- README.md — this file.

What changed in this update:
- pom.xml updated to compile with Java 21.
- Added Spring Boot Actuator dependency to enable /actuator/health which is used by liveness/readiness probes.
- Added src/main/resources/application-prod.properties which reads DB credentials from environment variables and sets production hibernate behavior.
- deployment.yaml now includes readiness and liveness probes and resource requests/limits.

Quick deploy steps (recommended):

1. Build and push your Docker image to a registry. Example (GitHub Container Registry):
   - Build locally or via CI and push: ghcr.io/kwishaka/employee_management_system:latest

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

5. Apply manifests (apply secret & configmap first, then deployment):

   kubectl apply -f k8s/secret.yaml
   kubectl apply -f k8s/configmap.yaml
   kubectl apply -f k8s/deployment.yaml

6. Verify rollout and service:

   kubectl rollout status deployment/ems-deployment
   kubectl get pods -l app=ems
   kubectl get svc ems-service

7. Test locally via port-forward if your cluster doesn’t provide external LB IP:

   kubectl port-forward svc/ems-service 8080:80
   Then open http://localhost:8080

Notes & recommendations:
- Do not store production credentials in Git. Use secrets or an external secret manager.
- If you use a managed DB ensure network rules allow connections from your cluster nodes.
- Consider enabling HTTPS at the Ingress/Load Balancer layer.
- For production, set spring.jpa.hibernate.ddl-auto carefully (validate/migrate/none) according to your schema migration process.

If you want, I can also add a GitHub Actions workflow to build and push Docker images to GHCR and update the deployment automatically. If so, tell me which registry you prefer and I will add the workflow and instructions to set repository secrets.
