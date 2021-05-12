
#### mysql log 보기
```
mysql> set global general_log=on;
mysql> show variables like 'general%';
$ tail -f /var/lib/mysql/c115bf2d4180.log
```
