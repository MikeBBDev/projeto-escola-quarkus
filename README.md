# projeto-escola-quarkus

Olá Professor!

Este projeto foi construído por alguém que ainda tem muito o que aprender para programar no nível dos meus companheiros de trabalho. Sou Engenheiro Químico de formação e dei muitas voltas até descobrir que a carreira não era para mim.

Tive a felicidade de me encontrar com a programação ao passar no concurso do BB após falhar no último concurso da PF (que até então era um sonho). O estudo prévio ajudou bastante, mas a experiência com a codificação de verdade não chega nem a 6 meses.

Agradeço pelas aulas, de toda a Let's Code, essas foram as melhores (e a concorrência era boa). Muita integração com tudo o que vimos nessa jornada. Desejo bastante sucesso nessa sua jornada. E você tem mais um inscrito no YT, valeu!

    Miqueias.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/hello-world-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
