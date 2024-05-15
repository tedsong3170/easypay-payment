# 결제시스템

## 1. ERD
## 1.1 결제시스템
![결제시스템_ERD.png](%EA%B2%B0%EC%A0%9C%EC%8B%9C%EC%8A%A4%ED%85%9C_ERD.png)

## 1.2 토큰시스템
![토큰시스템_ERD.png](%ED%86%A0%ED%81%B0%EC%8B%9C%EC%8A%A4%ED%85%9C_ERD.png)


## 2. 기동방법
```shell
$ docker-compose -p payment up -d
```

- 결제시스템 : 8080 포트사용
- 토큰시스템 : 8081 포트사용
- 승인시스템 : 8082 포트사용
