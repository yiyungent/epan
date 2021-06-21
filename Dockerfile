#FROM openjdk:11.0.4-jre-alpine AS base
FROM openjdk:11.0.4 AS base
# 时区设置
ENV TZ=Asia/Shanghai
WORKDIR /app
EXPOSE 8080

# build
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /src
COPY . .
#RUN mvn compile
RUN mvn package
RUN ls target -l
RUN echo "build success"

# final
FROM base AS final
WORKDIR /app
# 使用 target/epan-0.0.1-SNAPSHOT.jar 即可
COPY --from=build /src/target/epan-*.jar epan.jar
RUN ls -l
ENTRYPOINT ["java", "-jar", "epan.jar"]

