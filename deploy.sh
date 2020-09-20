#!/bin/zsh

# did you change the version number?
sbt clean
sbt assembly
sbt docker:publishLocal
docker image tag iotkafkadoorsensors:latest intel-server-03:5000/iotkafkadoorsensors
docker image push intel-server-03:5000/iotkafkadoorsensors

# Server side:
# kubectl apply -f /home/appuser/deployments/doorSensor.yaml
# If needed:
# kubectl delete deployment iot-kafka-door-sensor
# For troubleshooting
# kubectl exec --stdin --tty iot-kafka-door-sensor -- /bin/bash
