.PHONY: clean build deploy remove postman

env=dev
function=

clean:
	./gradlew clean

build:
	./gradlew build

spotless:
	./gradlew spotlessApply

checkDependencies:
	./gradlew dependencyUpdates

deploy:
	cd plugins/uk/uk-aws-lambda; \
	npm install --save-dev serverless; \
	npm install --save-dev serverless-iam-roles-per-function; \
	sls deploy -s $(env) --conceal

remove:
	cd plugins/uk/uk-aws-lambda; \
	npm install --save-dev serverless; \
	npm install --save-dev serverless-iam-roles-per-function; \
	sls remove -s $(env) --conceal

postman:
	$(eval HOST = $(shell java -jar tools/api-gateway/build/libs/api-gateway-*-standalone.jar -n idv -s $(env)))
	cd postman; \
	npm install --save-dev newman; \
	newman run idv.postman_collection.json --env-var host=$(HOST)