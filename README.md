# cron-kit

[![Tests](https://github.com/philiprehberger/kt-cron-kit/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-cron-kit/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/cron-kit)](https://central.sonatype.com/artifact/com.philiprehberger/cron-kit)
[![License](https://img.shields.io/github/license/philiprehberger/kt-cron-kit)](LICENSE)

Cron expression parsing, scheduling, and human-readable descriptions.

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("com.philiprehberger:cron-kit:0.1.3")
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>cron-kit</artifactId>
    <version>0.1.3</version>
</dependency>
```

## Usage

```kotlin
import com.philiprehberger.cronkit.*

val cron = Cron.parse("*/15 9-17 * * MON-FRI")
cron.nextAfter(Instant.now())
cron.matches(Instant.now())
cron.describe() // "Every 15 minutes"

Cron.isValid("0 25 * * *") // false
```

## API

| Function / Class | Description |
|------------------|-------------|
| `Cron.parse(expression)` | Parse a 5-field cron expression |
| `Cron.isValid(expression)` | Check if expression is valid |
| `CronExpression.nextAfter(instant)` | Next matching time |
| `CronExpression.nextN(instant, count)` | Next N matching times |
| `CronExpression.previousBefore(instant)` | Previous matching time |
| `CronExpression.matches(instant)` | Check if instant matches |
| `CronExpression.describe()` | Human-readable description |

## Development

```bash
./gradlew test
./gradlew build
```

## License

MIT
