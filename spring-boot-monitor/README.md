# Spring Boot Monitor

## ELK
- Elastic 사에서 제공하는 오픈소스 프로젝트
- Elasticsearch
    - 검색 및 분석 엔진으로 Logstash 로부터 수집된 로그데이터 저장
    - 해시테이블 형테로 데이터 저장 -> 시간복잡도 O(1)
- Logstash
    - 여러 소스에서 동시에 데이터를 수집 변환 후 elasticsearch 에 전송
    - input, filter, output 으로 구성된 데이터 처리 파이프라인
        - input
            - 파일을 읽거나 port 를 개방하여 데이터를 스트리밍 방식으로 받아들일 수 있음
        - filter
            - output 으로 이동하는 과정에서 구문을 분석하거나 변환 e.g. elasticsearch 에서 정의한 mapping 정보로 데이터 변환
        - output
            - 원하는 곳으로 데이터 전송
- Kibana
    - elasticsearch 에 저장된 데이터를 손쉽게 시각화해주는 인터페이스
    
### 구성
- spring boot -> logstash -> elasticsearch -> kibana

### Install
```shell
$ git clonse https://github.com/deviantony/docker-elk
$ docker-compose build
$ docker-compose up
```

### Config
#### elasticsearch
- docker-elk/elasticsearch/config/elasticsearch.yml

#### logstash
- docker-elk/logstash/config/logstash.yml
- docker-elk/logstash/pipeline/logstash.conf
    - 파이프라인 설정
    - input, filter, output elasticsearch server 설정
    
```shell
input {
	tcp {
		port => 5000
		codec => json_lines
	}
}

## Add your filters / logstash plugins configuration here

output {
	elasticsearch {
		hosts => "elasticsearch:9200" # 도커 아닐 경우 -> "http://localhost:9200"
		index => "springboot-elk-01"
		user => "elastic"
		password => "changeme"
	}
}
```

#### kibana
- docker-elk/kibana/config/kibana.yml
- Stack Management > Kibana > Index patterns > Create index pattern > index 추가


### Docker-compose run
```shell
$ docker-compose -f ./docker-elk/docker-compose.yml up
```

## Prometheus + Grafana
### Prometheus 
```shell
$ docker run \ 
  -p 9090:9090 \
  --name prometheus \
  -d prom/prometheus \
  -v /Users/ksy/IdeaProjects/spring-boot-sample/spring-boot-monitor/prometheus.yml:/etc/prometheus/prometheus.yml \
  --config.file=/etc/prometheus/prometheus.yml
```
- metric expression
    - ex) jvm_memory_max_bytes, http_server_requests_seconds_count

### Grafana
```shell
# host.docker.internal
$ docker run -d -p 3000:3000 \
  --name grafana \
  grafana/grafana
```
### config

#### Data Sources config 
- Prometheus
```shell
- URL : host.docker.internal:9090
```

- New dashboard > metric > jvm_memory_max_bytes, http_server_requests_seconds_count
- PromQL