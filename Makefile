build:
	@docker build -f ./docker/Dockerfile -t arcblock/driver-did-abt --build-arg GITHUB_READ_PACKAGES_TOKEN=$(GITHUB_READ_PACKAGES_TOKEN) --build-arg GITHUB_READ_PACKAGES_OWNER=dingpl716 .

run:
	@docker run -p 8080:8080 arcblock/driver-did-abt