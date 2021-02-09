FROM openjdk:11-jdk
# alpine (plus petit linux)

RUN mkdir /subboard
# \ (à la fin de la ligne)
# commande installer yarn \
# yarn install

COPY target/subboard-0.0.1.jar /subboard/subboard.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/subboard/subboard.jar" ]
