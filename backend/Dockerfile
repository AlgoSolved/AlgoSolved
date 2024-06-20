FROM openjdk:17-slim-buster AS base
ENV JAR_FILE_NAME=backend-0.0.1-SNAPSHOT.jar
ENV JAR_FILE_PATH=/home/deploy/app/backend/build/libs/${JAR_FILE_NAME}
ENV TZ=Asia/Seoul

FROM base as builder

RUN apt-get -qq update > /dev/null && \
    DEBIAN_FRONTEND=noninteractive apt-get -yq --no-install-recommends install build-essential unzip procps curl > /dev/null

RUN useradd --uid 1000 --gid users --shell /bin/bash --create-home deploy
USER deploy

RUN mkdir -p /home/deploy/app/backend
WORKDIR /home/deploy/app/backend
COPY --chown=deploy:users . .
RUN ./gradlew build -x test --parallel


FROM base AS production

ENV STAGE=production
WORKDIR /home/deploy/app/backend
COPY --chown=deploy:users --from=builder ${JAR_FILE_PATH} ${JAR_FILE_PATH}
COPY --chown=deploy:users entrypoint.sh entrypoint.sh
EXPOSE 8080

HEALTHCHECK --interval=5s --timeout=120s --retries=5 CMD curl -f http://localhost:8080/api/health/ping || exit 1

RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]


FROM builder AS development

WORKDIR /home/deploy/app/backend
COPY --chown=deploy:users entrypoint.sh entrypoint.sh
EXPOSE 8080

RUN chmod +x entrypoint.sh && ./gradlew getDeps

ENTRYPOINT ["./entrypoint.sh"]
