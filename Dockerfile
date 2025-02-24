FROM registry.redhat.io/jboss-eap-7/eap74-openjdk11-openshift-rhel8 as BUILDER

ENV GALLEON_PROVISION_LAYERS=cloud-server,web-clustering

RUN /usr/local/s2i/assemble

# From EAP 7.4 runtime image, copy the builder's server & add the war
FROM registry.redhat.io/jboss-eap-7/eap74-openjdk11-runtime-openshift-rhel8 as RUNTIME
USER root

COPY --from=BUILDER --chown=jboss:root $JBOSS_HOME $JBOSS_HOME
COPY  ./target/counter.war $JBOSS_HOME/standalone/deployments/

RUN chmod -R ug+rwX $JBOSS_HOME
USER jboss
CMD $JBOSS_HOME/bin/openshift-launch.sh