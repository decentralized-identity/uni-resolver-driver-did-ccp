![DIF Logo](https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png)

# Universal Resolver Driver: did:ccp

This is a [Universal Resolver](https://github.com/decentralized-identity/universal-resolver/) driver for Baidu **did:ccp** identifiers.

More info: 

- [Solution Homepage on Baidu Cloud](https://cloud.baidu.com/solution/digitalIdentity.html)
- [Docs Homepage](https://did.baidu.com)

## Specifications

* [W3C Decentralized Identifiers](https://w3c.github.io/did-core/)
* [Baidu DID Method Specification](https://did.baidu.com/did-spec/)

## Example DIDs

```
did:ccp:ceNobbK6Me9F5zwyE3MKY88QZLw
did:ccp:3CzQLF3qfFVQ1CjGVzVRZaFXrjAd
```

## Build and Run (Docker)

```
docker build -f ./docker/Dockerfile . -t hello2mao/driver-did-ccp
docker run -p 8080:8080 hello2mao/driver-did-ccp
curl -X GET http://localhost:8080/1.0/identifiers/did:ccp:ceNobbK6Me9F5zwyE3MKY88QZLw
```

## Build (native Java)

### Configuration
For downloading the dependencies of this project a Personal Access Token for GitHub must be configured in file [resolver/java/settings.xml](https://github.com/decentralized-identity/uni-resolver-driver-did-ccp/blob/release-0.1.x/settings.xml) according to [Creating a personal access token for the command line](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).

### Build (native Java)

Maven build:

	cd resolver/java
	mvn --settings settings.xml clean install


## Driver Metadata

The driver returns the following metadata in addition to a DID document:

* `version`: The DID version.
* `proof`: Some proof info about the DID document.
* `created`: The DID create time.
* `updated`: The DID document last update time.

## Maintainer

- Hongbin Mao [@hello2mao](https://github.com/hello2mao)
