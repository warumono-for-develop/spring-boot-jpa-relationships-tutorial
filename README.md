# Spring Boot with JPA for Relationships Tutorial
**An tutorial application using Spring Boot as JPA Relationships back-end.**

More details about the codes, please read the online **[Spring Boot](https://projects.spring.io/spring-boot)** and **[Spring Data JPA](https://projects.spring.io/spring-data-jpa/).**

Requirements
------
Running in
+ [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 or newer
+ [Spring Boot](https://github.com/spring-projects/spring-boot) 1.5.9.RELEASE or newer
+ [Gradle](https://github.com/gradle/gradle) 3.5.1 or newer
org.projectlombok:lombok

Optional
------
+ YAML
+ [Logback](https://logback.qos.ch) with [Spring Boot Logging](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html)
+ [org.projectlombok:lombok](https://projectlombok.org) 1.16.18 or newer
+ [org.springframework.security:spring-security-crypto:5.0.0.RELEASE](https://projectlombok.org) 5.0.0.RELEASE or newer

Dependencies
------
+ [org.springframework.boot:spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
+ [com.zaxxer:HikariCP:2.7.4](https://mvnrepository.com/artifact/com.zaxxer/HikariCP)
+ [io.springfox:springfox-swagger2:2.7.0](https://mvnrepository.com/artifact/io.springfox/springfox-swagger2)
+ [io.springfox:springfox-swagger-ui:2.7.0](https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui)
+ [com.google.guava:guava:23.5-jre](hhttps://mvnrepository.com/artifact/com.google.guava/guava)
+ [org.apache.commons:commons-lang3:3.7](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3)
+ [com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.9.3](https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-hibernate5)

+ [org.springframework.boot:spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
+ [com.h2database:h2](https://mvnrepository.com/artifact/com.h2database/h2)

Latest Update
------
+ 1.0 (Dec 13, 2017)

Domain ER(Entity-Relation)
------

### Code tables

#### [ROLE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/many2many/Role.java) (권한)
A credential table that is given to a user

사용자에게 부여되는 권한 정보 테이블

#### [ACCEPTANCE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/many2many/embeddables/Acceptance.java) (동의사항)
The table of consent information required for the user

사용자에게 요구되는 동의사항 정보 테이블


### Custom tables

#### [USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/User.java) (사용자)
User information table that is the basis of data

데이터의 기초가 되는 사용자 정보 테이블

#### [PROFILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java) (프로필)
User's detailed information table

사용자의 상세 정보 테이블

#### [MOBILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2many/Mobile.java) (휴대전화)
User's phone information table

사용자의 휴대전화 정보 테이블


### Generated tables

#### PROFILE_ACCEPTANCE (사용자의 동의사항)
Information table that user agrees

사용자가 동의한 사항 정보 테이블

#### USER_ROLE (사용자의 권한)
Authorization information table granted to user

사용자에게 부여된 권한 정보 테이블


Relationship features
------

USER    (1) --- (1) PROFILE

PROFILE (1) --- (N) MOBILE

USER    (N) --- (M) ROLE

PROFILE (N) --- (M) ACCEPTANCE


### Case 1 : 1 ( @OneToOne )

##### The forward part of the reference
**[USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @JsonManagedReference
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "host")
  @JoinColumn(name = "user_seq", referencedColumnName = "seq")
  private Profile profile;
```

##### The back part of the reference
**[PROFILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @JsonBackReference
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(updatable = false, nullable = false, name = "user_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_PROFILE"))
  private User host;
```

### Case 1 : N ( @OneToMany )

##### The forward part of the reference
**[PROFILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @JsonManagedReference
  @Default
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "holder")
  private Collection<Mobile> mobiles = Sets.newHashSet();
```

##### The back part of the reference
**[MOBILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2many/Mobile.java)**

```java
  @JsonBackReference
  @ManyToOne
  @JoinColumn(updatable = false, nullable = false, name = "profile_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_PROFILE_SEQ_IN_MOBILE"))
  private Profile holder;
```

### Case N : M ( @ManyToMany ) `[Use the '@JoinTable']`

##### The forward part of the reference
**[USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @Default
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable
  (
    name = "USER_ROLE", 
    joinColumns = @JoinColumn(name = "user_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_USER")), 
    inverseJoinColumns = @JoinColumn(name = "role_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_ROLE_SEQ_IN_ROLE")), 
    foreignKey = @ForeignKey(name = "FKEY_USER_SEQ__ROLE_SEQ_IN_USER_ROLE")
  )
  private Collection<Role> roles = Sets.newHashSet();
```

##### The back part of the reference
**[ROLE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/many2many/Role.java)**

No attribute, only specified in [USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java).

`Instead, a new table(**USER_ROLE**) is created that connects the two tables.`

**Check your DBMS**.




### Case N : M ( @ManyToMany ) `[Use the '@EmbeddedId']`
It is actually **1 : N ( @OneToMany )**, not **N : M ( @ManyToMany )**.

However, since they have the same characteristics as **N : M ( @ManyToMany )**, they are titled **N : M ( @ManyToMany )**.

You can also add columns by extending entity.


#### @Embeddable
**[ProfileAcceptanceId.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/many2many/embeddables/ProfileAcceptanceId.java)**

A new **ID table** that connects two tables.

```java
  @Embeddable // <--- annotation
  public class ProfileAcceptanceId implements Serializable // <--- implements Serializable
  {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(updatable = false, nullable = false, name = "profile_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_PROFILE_SEQ_IN_PROFILE"))
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(updatable = false, nullable = false, name = "acceptance_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_ACCEPTANCE_SEQ_IN_ACCEPTANCE"))
    private Acceptance acceptance;
  }
```


A new **Properties(Columns) table**.

#### @Entity
**[PROFILE_ACCEPTANCE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/many2many/embeddables/ProfileAcceptance.java)**

```java
  @Entity
  public class ProfileAcceptance
  {
    @NonNull
    @EmbeddedId // <--- annotation
    private ProfileAcceptanceId primaryKey; // <--- ID

    /* extra properties(columns) */
    @Default
    @Column(updatable = true, nullable = false, columnDefinition = "BOOLEAN DEFAULT 0 COMMENT '수신동의 여부'")
    private Boolean accepted = Boolean.FALSE;
    ...
  }
```

No attribute, only specified in [USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java).



Super - Common properties(columns) and toString
------

### Properties(Columns)

The following properties(columns) are automatically assigned when saving, even if you do not specify them directly.
+ seq - auto_increment by DBMS
+ identity - generate by UUID
+ updatedAt - apply by Spring Data Annotation
+ registedAt - apply by Spring Data Annotation

#### Case 1 : 
With **identity** property(column)

##### [AuditingEntity](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingEntity.java)

#### Case 2 : 
Without **identity** property(column)

##### [AuditingWithoutIdentityEntity](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)

Override toString to **ToStringBuilder**

### Usage

```java
  public class WithIdentity extends AuditingEntity // <--- extends
```

or

```java
  public class WithoutIdentity extends AuditingWithoutIdentityEntity // <--- extends
```

ID with Custom key
------

### seq
+ Auto increment sequence
+ [AuditingWithoutIdentityEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)
```java

```

Unique Identification Keys in the Server

서버내의 고유 식별 키

서버내에서 사용하는 Primary Key

### identity
+ UUID
+ [AuditingEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingEntity.java)
```java
  private String identity = UUID.randomUUID().toString();
```

Unique identification key between client and server

클라이언트와 서버간의 고유 식별 키

클라이언트측에서 임의로 seq 를 간단히 변경(자동증가 숫자를 증감 또는 입력)하여 서버측으로 요청함으로써 발생하는 보안취약사항을 방지하기 위한 키

##### H2 DBMS

```java
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(updatable = false, nullable = false, columnDefinition = "LONG COMMENT '서버내의 고유 식별 키'")
  private Long seq;
  ...
```

##### MySQL DMBS

```java
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false, nullable = false, columnDefinition = "BIGINT(10) UNSIGNED COMMENT '서버내의 고유 식별 키'")
  private Long seq;
  ...
```


Infinite Recursion with Jackson JSON and Hibernate JPA issue
------
### Solution
+ Hibernate5Module
+ ToStringBuilder
+ @JsonIdentityInfo
+ @JsonManagedReference

#### How to

#### Step 1 : 
##### Add customized ObjectMaper to MappingJackson2HttpMessageConverter
**[WebMvcConfiguration.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/WebMvcConfiguration.java)**

```java
  @Bean
  public ObjectMapper jsonObjectMapper()
  {
      Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder.json();
      ...
      return jackson2ObjectMapperBuilder.build().registerModule(new Hibernate5Module()); // <--- module
  }
```

and

**[WebMvcConfiguration.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/WebMvcConfiguration.java)**
```java
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
  {
      MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter(jsonObjectMapper()).getJackson2HttpMessageConverter();

      converters.add(jackson2HttpMessageConverter);
  }
```

#### Step 2 : 
##### Override toString to **ToStringBuilder**

This project only applies to common **[AuditingWithoutIdentityEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)**.

**[AuditingWithoutIdentityEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)**
```java
  public class AuditingWithoutIdentityEntity
  {
      ...
      @Override
      public String toString()
      {
          return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).toString();
      }
  }
```

#### Step 3 : 
##### Add @JsonIdentityInfo annotation to Entity class

This project only applies to common **[AuditingWithoutIdentityEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)**.

**[AuditingWithoutIdentityEntity.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/AuditingWithoutIdentityEntity.java)**
```java
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seq") // <--- annotation
  ...
  public class AuditingWithoutIdentityEntity
  {
      ...
  }
```

#### Step 4 : 
##### Add @JsonManagedReference annotation to Entity properties

##### The forward part of the reference
**[USER](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @JsonManagedReference
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "host")
  @JoinColumn(name = "user_seq", referencedColumnName = "seq")
  private Profile profile;
```

##### The back part of the reference
**[PROFILE](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/entities/one2one/Profile.java)**

```java
  @JsonBackReference
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(updatable = false, nullable = false, name = "user_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_PROFILE"))
  private User host;
```


Configuration features
------
### Annotations
#### [CORSConfiguration](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/CORSConfiguration.java)

#### [JpaConfiguration](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/JpaConfiguration.java)

#### [SwaggerConfiguration](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/SwaggerConfiguration.java)

#### [WebMvcConfiguration](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/configurations/WebMvcConfiguration.java)

Test datas
------
+ Reference file **[DataLoader.java](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/java/com/warumono/DataLoader.java)**.

API
------
#### Configuration
By default Spring Boot applications run on port **8080**.
But may vary depending on what ports are in use on your machine (check the terminal after entering the ./gradlew bootRun command).
If you require to change which port the application runs on by default, add the following to:

#### [application.yml](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/resources/application.yml)
```yml
server: 
  context-path: /
  port: 8080 # ---> change other port via. 9999
```

#### Swagger
```http
http://localhost:8080/swagger-ui.html
```

DBMS
------
#### [application-loc.yml](https://github.com/warumono-for-develop/spring-boot-jpa-relationships-tutorial/blob/master/src/main/resources/application-loc.yml)
```yml
spring: 
  h2: 
    console: 
      path: /h2-console
      enabled: true
```

#### Console
```http
http://localhost:8080/h2-console
```

Conclusion
------
**Configurations**

**JsonObjectMapper**

**ToStringBuilder**

**JsonManagedReference** with **JsonBackReference**

Author
------
**warumono** - <warumono.for.develop@gmail.com>
