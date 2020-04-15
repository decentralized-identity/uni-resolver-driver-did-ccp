![DIF Logo](https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png)

# Universal Resolver Driver: did:abt

This is a [Universal Resolver](https://github.com/decentralized-identity/universal-resolver/) driver for ABT DID provided by ArcBlock **did:abt** identifiers.

More info: 

- [Solution Homepage](https://www.abtnetwork.io/en/)

## Specifications

* [W3C Decentralized Identifiers](https://w3c.github.io/did-core/)
* [ABT DID Method Specification](https://arcblock.github.io/abt-did-spec/)

## Example DIDs

```
did:abt:z116ygT18P67xBp3scBtZLU6xVoDy268bgnU
did:abt:z11MVbRGLFt6RXaHzX7Xj7rmHfeiyFkJiiRE
```
## Configuration
For downloading the dependencies of this project a Personal Access Token for GitHub must be configured in file `settings.xml` according to [Creating a personal access token for the command line](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).
Then, add the USER Name and TOKEN to the environment variable as follows,

    GITHUB_READ_PACKAGES_OWNER=OWNER
    GITHUB_READ_PACKAGES_TOKEN=TOKEN
## Build and Run (Docker)

```
docker build -f ./docker/Dockerfile . -t arcblock/driver-did-abt
docker run -p 8080:8080 arcblock/driver-did-abt
curl -X GET http://localhost:8080/1.0/identifiers/did:abt:z116ygT18P67xBp3scBtZLU6xVoDy268bgnU
```

## Build (native Java)

Maven build:

	mvn --settings settings.xml clean install


## Driver Metadata

The driver returns the following metadata in addition to a DID document:

* `version`: The DID version.
* `proof`: Some proof info about the DID document.
* `created`: The DID create time.
* `updated`: The DID document last update time.
