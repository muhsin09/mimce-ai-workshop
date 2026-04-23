# Spec: TODO Uygulaması

## Objective
Mevcut Spring Boot workshop uygulamasına kullanıcıya özel bir TODO listesi özelliği eklemek.
Kullanıcı giriş yaptıktan sonra dashboard'da TODO ekleyebilir, tamamlayabilir ve silebilir.

## Tech Stack
- Java 25, Spring Boot 4.0.5
- Thymeleaf (server-side rendering)
- Spring Security (in-memory auth, kullanıcı: mimce/mimce)
- In-memory storage (ConcurrentHashMap, veritabanı yok)
- JUnit 5 + AssertJ (test)

## Commands
```
Build:   ./mvnw clean package -DskipTests
Test:    ./mvnw test
Run:     ./mvnw spring-boot:run
```

## Project Structure
```
src/main/java/com/mimce/workshop/
  model/
    Todo.java          → TODO entity (id, title, completed, createdAt)
  service/
    TodoService.java   → In-memory CRUD, kullanıcıya özel
  controller/
    DashboardController.java  → güncellenir (todos modele eklenir)
    TodoController.java       → POST /todos, DELETE /todos/{id}, POST /todos/{id}/toggle
src/main/resources/templates/
  dashboard.html       → güncellenir (TODO form + liste)
src/test/java/com/mimce/workshop/
  service/
    TodoServiceTest.java       → 14 unit test
  controller/
    TodoControllerTest.java    → 4 entegrasyon testi
```

## Code Style
- Standard Java, Lombok yok
- Service katmanı iş mantığını taşır, Controller ince kalır
- Thymeleaf form post + redirect-after-post pattern

```java
@Service
public class TodoService {
    private final Map<String, List<Todo>> store = new ConcurrentHashMap<>();
    // kullanıcı adı -> todo listesi
}
```

## Testing Strategy
- JUnit 5 + AssertJ
- TodoServiceTest: 14 unit test (CRUD, kullanıcı izolasyonu, edge case'ler)
- TodoControllerTest: 4 entegrasyon testi (MockMvc + Spring Security)
- Toplam: 19 test

## Boundaries
- Always: Redirect-after-post, Thymeleaf CSRF token, kullanıcı izolasyonu
- Ask first: Harici veritabanı, ek Maven bağımlılığı
- Never: Başka kullanıcının TODO'sunu gösterme/silme

## Success Criteria
- [x] Giriş yapmış kullanıcı TODO ekleyebilir
- [x] TODO listesi kullanıcıya özel görünür
- [x] TODO tamamlandı/tamamlanmadı toggle çalışır
- [x] TODO silinebilir
- [x] UI mevcut tasarıma uygun (Türkçe)
- [x] Build hatasız geçer (19 test başarılı)