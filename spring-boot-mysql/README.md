
#### mysql log 보기
```
mysql> set global general_log=on;
mysql> show variables like 'general%';
$ tail -f /var/lib/mysql/c115bf2d4180.log
```

### Class.isAssignableFrom
- Class 구분
- 특정 `Class` 의 class 또는 interface 확장 여부 체크

### instanceof
- kotlin -> is
- 특정 `Object` 의 class 또는 interface 확장 여부 체크

### References
- https://www.gitmemory.com/issue/spring-projects/spring-framework/24291/618343343