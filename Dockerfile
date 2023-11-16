FROM openjdk:17-slim-buster AS base
ENV JAR_FILE_NAME=AlgoSolved-0.0.1-SNAPSHOT.jar


FROM base as builder

RUN apt-get -qq update > /dev/null && \
    DEBIAN_FRONTEND=noninteractive apt-get -yq --no-install-recommends install build-essential unzip > /dev/null

RUN useradd --uid 1000 --gid users --shell /bin/bash --create-home deploy
USER deploy

RUN mkdir /home/deploy/app
WORKDIR /home/deploy/app
COPY --chown=deploy:users . .
RUN ./gradlew build


FROM base AS production
RUN useradd --uid 1000 --gid users --shell /bin/bash --create-home deploy
USER deploy

RUN mkdir /home/deploy/app
WORKDIR /home/deploy/app
COPY --chown=deploy:users --from=builder /home/deploy/app/build/libs/${JAR_FILE_NAME} /home/deploy/app/build/libs/${JAR_FILE_NAME}
EXPOSE 3000

ENTRYPOINT ["java", "-jar", "/home/deploy/app/build/libs/AlgoSolved-0.0.1-SNAPSHOT.jar"]
