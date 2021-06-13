CREATE USER 'ksy-slave'@'%' IDENTIFIED WITH mysql_native_password BY '1234'; 
GRANT REPLICATION SLAVE ON *.* TO 'ksy-slave'@'%';


mysql> CREATE DATABASE testdb;  
mysql> USE testdb;  
mysql> CREATE TABLE test ( no INT(8), PRIMARY KEY (no) );  
mysql> DESC test;  

$ docker exec -it mysql-master /bin/bash
$ mysqldump -uroot -p testdb > dump.sql

container -> local
$ docker cp mysql-master:/root/dump.sql .

local -> container
$ docker cp dump.sql mysql-slave:/root
$ docker exec -it mysql-slave /bin/bash
$ mysql -uroot -p

mysql> CREATE DATABASE testdb;  

$ mysql -uroot -p testdb < dump.sql

// 덤프 확인

// slave -> master 연동
mysql> show master status\G
*************************** 1. row ***************************
             File: mysql-bin.000003
         Position: 1382
     Binlog_Do_DB:
 Binlog_Ignore_DB:
Executed_Gtid_Set:
1 row in set (0.00 sec)

mysql> CHANGE MASTER TO MASTER_HOST='mysql-master', MASTER_USER='ksy-slave', MASTER_PASSWORD='1234', MASTER_LOG_FILE='mysql-bin.000003', MASTER_LOG_POS=3141;
mysql> START SLAVE;

- `MASTER_HOST` : 호스트명
- `MASTER_USER` : master 서버의 REPLICATION SLAVE 권한을 가진 계정
- `MASTER_PASSWORD` : master 서버의 REPLICATION SLAVE 권한을 가진 계정 비밀번호
- `MASTER_LOG_FILE` : master 바이너리 로그 파일명
- `MASTER_LOG_POS` : master 현재 로그의 위치

mysql> SHOW SLAVE STATUS\G
- Last_Error, Last_IO_Error 필드 에러 유무 확인
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: mysql-master
                  Master_User: ksy-slave
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000003
          Read_Master_Log_Pos: 3141
               Relay_Log_File: f8733451ebba-relay-bin.000002
                Relay_Log_Pos: 322
        Relay_Master_Log_File: mysql-bin.000003
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB:
          Replicate_Ignore_DB:
           Replicate_Do_Table:
       Replicate_Ignore_Table:
      Replicate_Wild_Do_Table:
  Replicate_Wild_Ignore_Table:
                   Last_Errno: 0
                   Last_Error:
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 3141
              Relay_Log_Space: 537
              Until_Condition: None
               Until_Log_File:
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File:
           Master_SSL_CA_Path:
              Master_SSL_Cert:
            Master_SSL_Cipher:
               Master_SSL_Key:
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error:
               Last_SQL_Errno: 0
               Last_SQL_Error:
  Replicate_Ignore_Server_Ids:
             Master_Server_Id: 1
                  Master_UUID: 8e01189b-b261-11eb-b118-0242ac130002
             Master_Info_File: mysql.slave_master_info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind:
      Last_IO_Error_Timestamp:
     Last_SQL_Error_Timestamp:
               Master_SSL_Crl:
           Master_SSL_Crlpath:
           Retrieved_Gtid_Set:
            Executed_Gtid_Set:
                Auto_Position: 0
         Replicate_Rewrite_DB:
                 Channel_Name:
           Master_TLS_Version:
       Master_public_key_path:
        Get_master_public_key: 0
            Network_Namespace:
1 row in set (0.00 sec)

// 동기화 동작 확인
insert into test values(2);