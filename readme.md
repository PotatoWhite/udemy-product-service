# docker-compose를 이용한 mongodb 설치
```yaml
version: "3"
services:
  mongodb:
    image: mongo
    restart: always
    container_name: mongodb
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: mongo
    volumes:
      - ~/Infraspace/mongo/data:/data/db
```


# mongodb embeㅇded 설정
```yaml
spring:
  data:
    mongodb:
      database: productdb
  mongodb:
    embedded:
      version: 3.6.2
```


# mongodb external 설정
```yaml
spring:
  data:
    mongodb:
      database: productdb
      host: localhost
      port: 27017
      username: product
      password: product12345
```


# product db 생성

```mongodb-json-query
use productdb;

db.createUser({
    user: 'product',
    pwd: 'product12345',
    roles: [
        {
            role: 'readWrite',
            db: 'productdb'
        }
    ]
});
```