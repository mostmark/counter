# Sample counter application

This simple example application demonstrates the distributed session manager in Red Hat JBoss EAP and how it replicates http session data between EAP instances/pods when running in OpenShift. This example was build and tested using OpenJDK 11 and Red Hat JBoss EAP 7.4.21 (make sure to set JAVA_HOME before packaging with maven).

1. Build the application:

```
mvn package

```

2. Build and push the container image:

NOTE: Replace YOUR-USER-NAME with your Quay.io username

```
podman build -t rh/counter .
podman tag rh/counter quay.io/YOUR-USER-NAME/counter:latest
podman push quay.io/YOUR-USER-NAME/counter:latest

```

3. Deploy the application to OpenShift:

NOTE: Replace YOUR-USER-NAME in k8s/counter-deploy.yaml with your Quay.io username

```
oc new-project counter
oc apply -f k8s/

```


## To test the application

1. Get the url to the applicaiton and open it in a browser.

```
echo https://$(oc get route counter-route --output jsonpath={.spec.host})/counter/counter

```

2. Reload the page a couple of times to increase the counter.


3. Make a note of the serving pod ("Server Hostname" in the web application)

4. Delete the pod

```
oc delete pod THE-POD-NAME-GOES-HERE

```

5. Reload the page and note that the value of the counter and the session data is preserved while the serving pod has changed.

