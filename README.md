# cron-kit

[![Tests](https://github.com/philiprehberger/kt-cron-kit/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-cron-kit/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/cron-kit.svg)](https://central.sonatype.com/artifact/com.philiprehberger/cron-kit)
[![Last updated](https://img.shields.io/github/last-commit/philiprehberger/kt-cron-kit)](https://github.com/philiprehberger/kt-cron-kit/commits/main)

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

## Support

If you find this project useful:

⭐ [Star the repo](https://github.com/philiprehberger/kt-cron-kit)

🐛 [Report issues](https://github.com/philiprehberger/kt-cron-kit/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

💡 [Suggest features](https://github.com/philiprehberger/kt-cron-kit/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

❤️ [Sponsor development](https://github.com/sponsors/philiprehberger)

🌐 [All Open Source Projects](https://philiprehberger.com/open-source-packages)

💻 [GitHub Profile](https://github.com/philiprehberger)

🔗 [LinkedIn Profile](https://www.linkedin.com/in/philiprehberger)

## License

[MIT](LICENSE)
