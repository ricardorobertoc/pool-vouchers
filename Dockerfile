FROM donusopsjenkinsacr.azurecr.io/donus-java-17
LABEL maintainer=get-net

VOLUME /tmp
WORKDIR /opt/app/

COPY ./app.jar .

CMD java \
    -jar ${ADDITIONAL_OPTS} app.jar

