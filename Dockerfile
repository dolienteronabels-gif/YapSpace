FROM eclipse-temurin:26-jdk

WORKDIR /app

COPY src ./src
COPY lib ./lib

RUN mkdir -p out \
    && find src -name "*.java" > sources.txt \
    && javac -cp "lib/mysql-connector-j-26.10.0.jar" -d out @sources.txt

EXPOSE 8080

CMD ["sh", "-c", "exec java -cp 'out:lib/mysql-connector-j-26.10.0.jar' yapspace.Main"]