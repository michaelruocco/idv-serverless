.PHONY: clean build deploy postman

env=dev

clean:
	./gradlew clean

build:
	./gradlew build

deploy:
	cd aws-lambda; \
	npm install -g serverless; \
	sls deploy -s $(env) --conceal

postman:
	cd postman; \
	npm install -g newman; \
	newman run idv.postman_collection.json -e environment/idv-dev.postman_environment.json