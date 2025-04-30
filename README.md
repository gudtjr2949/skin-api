# 스킨 판매 플랫폼

</br>
</br>

## 개요
티스토리와 같은 블로그에서 사용하는 스킨 파일을 거래하는 플랫폼입니다.


</br>
</br>

## 소프트웨어 아키텍처

<img width="1005" alt="Image" src="https://github.com/user-attachments/assets/9ff8a7c5-1e3b-400c-b4d5-c9978b4e0f0c" />

</br>

</br>
</br>

## 주요 기능

</br>

### 1. 판매중인 스킨 리스트 조회

로그인 후, 메인화면에 입장하면 '최근등록' 순서로 스킨이 조회됩니다.

<img width="1292" alt="Image" src="https://github.com/user-attachments/assets/8229f142-468e-443d-86a3-96f74d2c1288" />

</br>
</br>

### 2. 스킨 상세 조회

특정 스킨을 클릭하면 해당 스킨 상세조회 화면으로 이동합니다.

<img width="1292" alt="Image" src="https://github.com/user-attachments/assets/da1720e0-befe-452d-b3f5-b03de8142c5a" />

</br>
</br>

### 3. 스킨 후기 조회, 문의, 구매

상세 조회 화면에서는 해당 스킨이 적용된 블로그 예시, 사용자 후기, 판매자에게 문의할 수 있는 채팅 기능, 그리고 스킨을 구매할 수 있습니다.

<img width="1292" alt="Image" src="https://github.com/user-attachments/assets/7506b101-ba5c-4eca-b685-aa653b2c5237" />

<img width="1292" alt="Image" src="https://github.com/user-attachments/assets/229fe313-1307-4472-8196-3b157bd4ccc0" />

<img width="1292" alt="Image" src="https://github.com/user-attachments/assets/234f68af-60c4-4d79-96a7-7a528e82899d" />

<br>
<br>

## 사용 기술

<br>

### 1. PortOne을 사용한 스킨 구매

PortOne을 사용해 스킨 구매를 위한 카드 결제와 간편 결제를 적용했습니다.


<br>
<br>

### 2. Kafka(AWS MSK)를 사용한 채팅 기능 구현

메시지 브로커로 Kafka를 사용해 다중서버에서도 사용 가능한 채팅 기능을 구현하였습니다.

그리고 고가용성을 높이기 위해 단일 인스턴스에서 실행중이던 Kafka에서 AWS MSK로 마이그레이션을 진행했습니다.

<br>
<br>

### 3. 채팅 목록 저장에 더 적합안 MongoDB 사용

채팅 기능은 MongoDB에 저장되고, 나머지 데이터는 MySQL에 저장됩니다.


<br>
<br>

## 4. 채팅 목록 저장 코드에 전략 패턴 적용

채팅 메시지 저장에 MySQL을 사용하다가 MongoDB로 변경했는데, 이 과정에서 코드 수정을 최소화하고, 유지보수성을 향상시키기 위해 전략 패턴을 도입했습니다.

<br>

### MongoDB, MySQL(JPA) 전략 패턴 예시 코드


```java
public interface ChatManageStrategy {
    int CHAT_SIZE = 20;
    LocalDateTime save(ChatRoom chatRoom, Member member, String chatContent);
    List<ChatResponse> findChatList(Long chatRoomId, String chatId);
}

@Component
public class JpaChatRepositoryAdapter implements ChatManageStrategy {
    public LocalDateTime save(...) { /* JPA 저장 */ }
    public List<ChatResponse> findChatList(...) { /* JPA 조회 */ }
}

@Component
public class MongoChatRepositoryAdapter implements ChatManageStrategy {
    public LocalDateTime save(...) { /* MongoDB 저장 */ }
    public List<ChatResponse> findChatList(...) { /* MongoDB 조회 */ }
}

@Component
public class ChatManageContext {
    private final Map<String, ChatManageStrategy> strategyMap;
    
    public ChatManageStrategy getStrategy(String key) {
        return strategyMap.get(key);
    }
}
```

```java
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatManageContext chatManageContext;
    public static final String STRATEGY_KEY = "mongoChatRepositoryAdapter";

    ...
}
```

- 현재 이 코드는 채팅 저장소로 MongoDB를 사용하고 있습니다. 하지만 추후 MySQL로 전환하거나 다른 데이터베이스를 추가하더라도, 서비스 코드의 변경은 최소화될 수 있도록 설계되어 있습니다.

- 예를 들어, MongoDB에서 MySQL로 저장소를 변경하고자 할 경우, ChatServiceImpl 클래스의 STRATEGY_KEY를 jpaChatRepositoryAdapter로 수정하기만 하면 됩니다.
