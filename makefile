.PHONY: clean build deploy

env=dev

clean:
	./gradlew clean

build:
	./gradlew build

deploy:
	cd aws-lambda; \
	npm install -g serverless; \
	sls deploy -s $(env) --conceal