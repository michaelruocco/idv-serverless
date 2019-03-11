.PHONY: clean build deploy postman

env=dev

clean:
	./gradlew clean

build:
	./gradlew build

deploy:
	cd aws-lambda; \
	npm install --save-dev serverless; \
	npm install --save-dev serverless-iam-roles-per-function; \
	sls deploy -s $(env) --conceal

postman:
	cd postman; \
	npm install --save-dev newman; \
	newman run idv.postman_collection.json -e environment/idv-dev.postman_environment.json