# sbt-paradox-swagger

A simple paradox plugin, which will embed swagger documentation.

### Example:
````
@swagger[swagger-all.yaml](swagger.yaml)
@swagger[swagger-frontend.yaml](docs/frontend/swagger.yaml)
@swagger[swagger-mobile.yaml](docs/mobile/swagger.yaml)
````
This will get the files relative from the project root.
If you have multiple swagger files with the same name (but in different path), 
please make sure that the name inside the `[brackets]` are unique in the whole project.
The plugin works with jsons too, but the name inside the `[brackets]` must refer the correct filetype.

### Install (to the `project/plugins.sbt`):
```
resolvers += "jitpack" at "https://jitpack.io"
addSbtPlugin("com.github.TeamWanari" % "sbt-paradox-swagger" % "-SNAPSHOT")
```

### TODO
Not tested in multimodule setups.
