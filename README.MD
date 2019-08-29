# tidb-docker-compose-panel

![travis](https://img.shields.io/travis/STRRL/tidb-docker-compose-panel/master)

:construction: Working in process.

## Introduction

A simple application to startup TiDB clusters with web ui, written with Java and Vue.

See [tidb-docker-compose](https://github.com/pingcap/tidb-docker-compose) to learn more.

## Quick Start

You can download latest release from [github release](https://github.com/STRRL/tidb-docker-compose-panel/releases).

Here are 2 versions for this app: cli and web. Base actions on both of them are totally same, you can use anyone to startup a TiDB cluster.

### Require

`JRE 8u212`, `docker` and `docker-compose`

### Cli

> Your can use `chmod +x tidb-docker-compose-panel-cli-${version}-exec.jar`, and then execute `./tidb-docker-compose-panel-cli-${version}-exec.jar` directly.  
> Instead of `java -jar`, this way is much more "clear". XD
> So do on `tidb-docker-compose-panel-web-${version}-exec.jar`.

Run `java -jar tidb-docker-compose-panel-cli-${version}-exec.jar`, then you enter the interactive shell like this:

```text
2019-08-29 12:13:26.383  INFO 28767 --- [           main] o.s.boot.SpringApplication               :
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.7.RELEASE)


2019-08-29 12:13:26.492  INFO 28767 --- [           main] com.github.strrl.panel.cli.Application   : Starting Application on eiu.lan with PID 28767 (/Users/strrl/playground/GitHub/tidb-docker-compose-panel/tidb-docker-compose-panel-cli/target/tidb-docker-compose-panel-cli-1.0-SNAPSHOT-exec.jar started by strrl in /Users/strrl/playground/GitHub/tidb-docker-compose-panel/tidb-docker-compose-panel-cli/target)
2019-08-29 12:13:26.492  INFO 28767 --- [           main] com.github.strrl.panel.cli.Application   : No active profile set, falling back to default profiles: default
2019-08-29 12:13:28.028  INFO 28767 --- [           main] com.github.strrl.panel.cli.Application   : Started Application in 1.968 seconds (JVM running for 2.393)
shell:>
```

Then enter commands after prompt `shell:>` appear. Here are 3 commands for managing TiDB clusters: `list`, `purge`,`startup`. And also some built-in commands such as `help`, `exit`. Use `TAB` for auto-completion is supported.

```text
shell:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

TiDB Cluster Commands
        list, ls: Get all cluster from workspace.
        purge: Purge a cluster.
        startup: Startup a cluster.


shell:>
```

Use `startup -n my_cluster` can startup a TiDB cluster called `my_cluster` with default config: 1 TiDB instance, 3 TiKV instance, 3 PD instance, 1 Prometheus, 1 Grafana, 1 pushgateway. Should use other parameter to change instance name, expose port, tag for docker image and others.

See `help startup` for more information.

```text
shell:>help startup


NAME
        startup - Startup a cluster.

SYNOPSYS
        startup [-n] string  [[--tidbs] integer]  [[--tikvs] integer]  [[--tidb-server-port] integer]  [[--tidb-status-port] integer]  [[--prometheus-port] integer]  [[--grafana-port] integer]  [[--pd-version] string]  [[--tidb-version] string]  [[--tikv-version] string]

OPTIONS
        -n or --cluster-name  string
                Cluster name.
                [Mandatory]

        --tidbs  integer
                Number of TiDB instance.
                [Optional, default = 1]

        --tikvs  integer
                Number of TiKV instance.
                [Optional, default = 3]

        --tidb-server-port  integer
                TiDB server port
                [Optional, default = 4000]

        --tidb-status-port  integer
                TiDB status port
                [Optional, default = 10080]

        --prometheus-port  integer
                Prometheus port
                [Optional, default = 9090]

        --grafana-port  integer
                Grafana port
                [Optional, default = 3000]

        --pd-version  string
                Version for docker image pingcap/pd.
                [Optional, default = latest]

        --tidb-version  string
                Version for docker image pingcap/tidb.
                [Optional, default = latest]

        --tikv-version  string
                Version for docker image pingcap/tikv.
                [Optional, default = latest]



shell:>

```

### Web

Run `java -jar tidb-docker-compose-panel-web-${version}-exec.jar`, then open [localhost:18080](http://localhost:18080), you can see web-ui like this:

Clusters:

![web-ui](https://i.imgur.com/VcXWMGK.png)
![web-ui-2](https://i.imgur.com/O7lTOtN.png)

Create new cluster:

![create-new-cluster](https://i.imgur.com/uGPGMxB.png)

## Build from source

```shell
git clone https://github.com/STRRL/tidb-docker-compose-panel.git
cd tidb-docker-compose-panel
mvn package
```

Then your can find `*-exec.jar` in `${module-name}/target` directory.

> It will download `node` and `yarn` while building module `tidb-docker-compose-panel-web-ui`. See [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) to learn more.

## Todo List

- [x] Core concepts.
- [x] Single cluster.
- [ ] docker-compose.yml syntax check
- [x] Multi cluster.
- [x] Web application.
- [x] Cli tool.
- [x] Web ui.
- [ ] Fetch available image versions from docker hub.
- [ ] Run in docker:dind.
- [ ] ~~docker swarm support.~~
