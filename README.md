## Golden Raspberry Awards API

API RESTful com lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

#### Tecnologias utilizadas:
- Kotlin 1.6.10
- Micronaut 3.4.4
- R2DBC-H2 0.8.5

### Execução

#### Pré requisitos

* Jdk17

#### Compilação
```bash
./gradlew clean build
```

#### Execução
```bash
./gradlew run
```

#### Test
http://localhost:8080/producers?winner=true&awardInterval=both

ou
```bash
./gradlew test
```

### Execução nativa (opcional)

#### Pré requisitos

* GraalVM 17 + native-image

```bash
./gradlew nativeRun
```