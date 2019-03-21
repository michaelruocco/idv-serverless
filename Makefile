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
	$(eval HOST = $(shell java -jar tools/api-gateway/build/libs/api-gateway-*-standalone.jar -n idv -s $(env)))
	cd postman; \
	npm install --save-dev newman; \
	newman run idv.postman_collection.json --env-var host=$(HOST)