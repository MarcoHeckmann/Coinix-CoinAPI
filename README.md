<div align="center">

# 💰 Coinix CoinAPI

**A simple and lightweight coin/economy plugin for Minecraft Paper servers.**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://adoptium.net/)
[![Paper](https://img.shields.io/badge/Paper-1.21.11%2B-blue.svg)](https://papermc.io/)
[![GitHub release](https://img.shields.io/github/v/release/SkyDynamics/CoinAPI)](https://github.com/MarcoHeckmann/Coinix-CoinAPI/releases)
[![GitHub issues](https://img.shields.io/github/issues/SkyDynamics/CoinAPI)](https://github.com/MarcoHeckmann/Coinix-CoinAPI/issues)
[![GitHub stars](https://img.shields.io/github/stars/SkyDynamics/CoinAPI?style=social)](https://github.com/MarcoHeckmann/Coinix-CoinAPI/stargazers) 

</div>

---

## 📖 Table of Contents

- [Features](#-features)
- [Requirements](#-requirements)
- [Installation](#-installation)
- [Commands](#-commands)
- [Permissions](#-permissions)
- [Configuration](#-configuration)
- [Building from Source](#-building-from-source)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

- 💵 Manage player coin balances via simple, intuitive commands
- 🗄️ MySQL database support powered by HikariCP connection pooling
- 📝 Fully customizable messages via `messages.yml`
- 🔤 Placeholder support in all plugin messages

## 📋 Requirements

| Requirement | Version |
|---|---|
| Java | 21+ |
| Paper | 1.21.11+ |
| MySQL | Any recent version |

## 🚀 Installation

1. Download the latest JAR from the [Releases](https://github.com/MarcoHeckmann/Coinix-CoinAPI/releases/) page
2. Place the JAR file into your server's `plugins/` folder
3. Start the server once so the plugin generates its default `database.yml` and `messages.yml` files
4. Stop the server and configure your MySQL connection in `plugins/CoinAPI/database.yml`
5. Restart the server

## ⌨️ Commands

| Command | Description | Permission |
|---|---|---|
| `/coins` | Check your own balance | - |
| `/coins add <player> <amount>` | Add coins to a player | `coins.add` |
| `/coins remove <player> <amount>` | Remove coins from a player | `coins.remove` |
| `/coins get <player>` | Check another player's balance | `coins.get` |
| `/coins set <player> <amount>` | Set a player's balance | `coins.set` |
| `/coins reset <player>` | Reset a player's balance to 0 | `coins.reset` |

> Amounts accept up to 2 decimal places (e.g. `100.50`) and must be zero or greater.

## 🔑 Permissions

| Permission | Description |
|---|---|
| `coins.add` | Allows adding coins |
| `coins.remove` | Allows removing coins |
| `coins.get` | Allows checking other players' balances |
| `coins.set` | Allows setting balances |
| `coins.reset` | Allows resetting balances |

## ⚙️ Configuration

Configuration files are generated in `plugins/CoinAPI/` the first time the server starts.

### `database.yml`

Your MySQL connection settings, used by HikariCP to build the connection pool:

```yaml
host: #Host of your Database Service
port: #Port of your Database
database: #Name of your Database
username: #Username of your Database
password: #Password of your Database
max-pool-size: #Maximum allowed Connections (Max-Pool-Size)
```

### `messages.yml`

All plugin messages can be fully customized, including the prefix. The following placeholders are available:

| Placeholder | Description |
|---|---|
| `%prefix%` | The plugin prefix defined in `messages.yml` |
| `%balance%` | The player's current coin balance |
| `%amount%` | The amount of coins |
| `%sender%` | The player who executed the command |
| `%receiver%` | The player who receives the coins |
| `%target%` | The target player (used in `/coins get`) |
| `%permission%` | The required permission node |

## 🛠️ Building from Source

Make sure you have [Maven](https://maven.apache.org/) and Java 21+ installed, then run:

```bash
mvn clean package
```

The compiled JAR will be located in the `target/` folder.

## 🤝 Contributing

Contributions are welcome! If you'd like to contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m 'Add my feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

Please open an [issue](https://github.com/MarcoHeckmann/Coinix-CoinAPI/issues) first for major changes or to report bugs.

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

<div align="center">

Made with ❤️ by [SkyDynamics](https://github.com/MarcoHeckmann)

</div>
