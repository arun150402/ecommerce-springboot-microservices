#!/bin/bash

# Set your Docker Hub username
USERNAME=arunks2002

# List of microservice modules to build and push
MODULES=("api-gateway" "product-service" "order-service" "inventory-service" "notification-service")

# Loop through each module
for module in "${MODULES[@]}"; do

    echo "Pushing $USERNAME/$module to Docker Hub..."
    docker tag "$module:0.0.1-SNAPSHOT $USERNAME/$module:0.0.1"
    docker push "$USERNAME/$module:0.0.1"

    if [ $? -ne 0 ]; then
        echo "Failed to push $USERNAME/$module"
        exit 1
    fi

    echo "Successfully pushed $USERNAME/$module"
done

echo "All images built and pushed successfully!"