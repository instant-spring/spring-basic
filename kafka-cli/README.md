# Kafka-CLI 사용하기
## kafka quick start
https://kafka.apache.org/quickstart

## 설치하기
https://www.apache.org/dyn/closer.cgi?path=/kafka/3.1.0/kafka_2.13-3.1.0.tgz

## zookeeper 실행하기
1. terminal에서 kafka가 설치된 위치로 이동합니다.
2. 다음 커맨드를 실행합니다.
`bin/zookeeper-server-start.sh config/zookeeper.properties`


## kafka server 실행하기
1. terminal에서 kafka가 설치된 위치로 이동합니다.
2. 다음 커맨드를 실행합니다.
`bin/kafka-server-start.sh config/server.properties` 

기본으로 localhost:9092에 zookeeper가 뜹니다.

## Kafka Topics 관련 명렁

### 토픽 생성
아래와 같은 커맨드를 실행합니다.
```
bin/kafka-topics.sh --bootstrap-server <zookeeper server 주소> --topic <topic이름> --create --partitions <partition 개수> --replication-factor <replication 개수>
```

예시
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1
```

zookeeper server 주소 : localhost:9092
topic 이름 : first_topic
parition 개수 : 3
replication 개수 : 1


### 토픽 목록 확인
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```


### 토픽 디테일 확인
```
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
```


4. 토픽 삭제
```
bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic first_topic
```



## Kafka producer 관련 명령

### 토픽에 데이터 produce하는 방법
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic first_topic
```

참고.  
만약 없는 토픽에 produce한다면, 처음에는 leader broker가 없기 때문에 warning이 발생.   
이 후에 카프카는 topic을 생성하고 leader broker를 만들어서 데이터를 넣는다.  
정상적으로 동작!  
하지만 partition이 기본으로 설정됨(server.properties에서 설정가능)

### producer property를 세팅해서 produce
예시 : acks=all
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic first_topic --producer-property acks=all
```


### Key value 방식으로 produce하는 방법
```
bin/kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic --property parse.key=true --property key.separator=,
```

> key, value



## Kafka Consumer 관련 명령

### topic의 데이터 consume

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic
```

기본적으로는 consume이 뜬 뒤에 produce되는 데이터를 사용할 수 있다.

### 과거 데이터까지 consume
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning
```
(—from-beginning을 넣으면 됨!)



### consumer group을 지정해서 consume

```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first_topic --from-beginning --group groupOne
```

group 이름 : groupOne


### Key value 방식으로 produce 되었을 때, consume하는 방법
```
bin/kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,
```


## Kafka-consumer-groups관련 명령

### Consumer group 확인
```
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```


### Consumer group 디테일 확인
```
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group groupOne
```
lag는 해당 파티션에서 읽지 않은 데이터의 수를 의미
현재 해당 consumer-group에서 consume하고 있는 machine을 찾을 수 있다.


## Replay data
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --reset-offsets --to-earliest --execute --group oneGroup --topic first_topic
```

(--reset-offsets을 설정해야하며, 이후 --to-earliest 또는 —to-latest 또는 —shift-by 등을 해야함)
(—shift-by 같은 경우에는 숫자와 같이 쓰며, 모든 파티션에서 해당 숫자만큼 이동한다.(partitions수 * 숫자)의 데이터가 나옴)