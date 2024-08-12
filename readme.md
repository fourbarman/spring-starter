## IoC\DI Spring

 - **Inversion of Control** (IoC - инверсия управления) - принцип программирования, при котором управление выполнением программы передается фреймворку, а не прграммисту. Происходит потеря контроля над выполнением кода: фреймворк управляет кодом программиста, а не программист управляет фреймворком.

 - **Dependency Injection** (DI – внедрение зависимостей) – одна из реализаций IoC, посредством которой созданием объекта и компоновкой его зависимостей занимается другой объект (фреймворк).

## Фреймворк определяет и внедряет зависимости через:
 - **параметры конструктора. Плюс в том, что все поля final, а значит immutable.**
 - **параметры статического метода инициализации (фабричный метод)**. В бине есть статический метод, возвращающий новый объект бина. Конструктор при этом private.
 - **свойства объекта (set* методы)**. Нужны set методы, значит нельзя указывать поля как final. А значит объект будет не immutable. Есть возможность сделать циклические зависимости в бинах (в отличие от метода через констпуктор). Зато удобно использовать, когда есть optonal зависимость.
## IoC Container
 - **IoC Container** - объект, который занимается созданием других объектов и внедрением в них зависимостей (DI). Обычно представлен в виде обычного ассоциативного массива (Map), где ключом является String, а значением наш объект со всеми его зависимостями (bean). Реализует несколько интерфейсов:
1. org.springframework.beans.factory.BeanFactory - фабрика бинов.
2. org.springframework.context.ApplicationContext - реализует BeanFactory и предоставляет более широкий список методов для работы с бинами.
 - **Bean** - объект со всеми необходимыми зависимостями, который был создан с помощью **IoC Container** (Controller, Service, Repository).  

Для того, чтобы были созданы Bean, нужно предоставить в IoC Container некоторую информацию о них:
 - **Metadata** - представляет собой совокупность объектов (Bean Definitions), которые говорят IoC Container как создавать Beans, конфигурировать и внедрять их зависимости. (Bean Definition как чертеж, а Bean - деталь, созданная по этому чертежу).  

Metadata (Bean Definitions) могут создаваться тремя способами:
1. XML-based
2. Annotation-based
3. Java-based
 - **POJO** - обычный объект с набором свойств, конструкторов и get\set методами. Не содержит логики (DTO, Entity).

Контекст.
Если передать в контекст другой контекст, то переданный контекст станет родительским для текущего. Это дает возможность использовать один контекст с функциональностью и бинами из различных источников.  
Представляется в виде ассоциативного массива *Map<String, Object>*
# XML-based
Создание контекста на основании XML.
1. Добавляем создание нового контекста new ClassPathXmlApplicationContext("application.xml"). *Файл может быть с другим названием.*
2. Добавляем файл application.xml, где указываем bean definitions для бинов.
   - Атрибут **<bean class="Class_name.class">** - главный и основной для создания бина. *Для создания бина будет вызван конструктор по умолчанию (или другой указанный) у класса Class_name.class через ReflectionAPI*.
   - Если не указывать идентификатор, то для имени бина берется название его класса и добавляется #0. Второй такой же бин получит имя с #1 и тп.
   - Если получать бины по классу, имея несколько бинов одного класса, то будет Exception, т.к. Spring не поймет, какой бин вернуть. 
   - Лучше определить параметр **id** в атрибуте **<bean...>** для идентификации бинов, затем получать их добавляя тип: getBean("bean_name", Bean_class.class). Иначе придется приводить к типу.
   - Параметр **name** в теге **<bean...>** используется для того, чтобы дать более красивое название бина в приложении (alias). *Можно использовать для получения бинов. Хранятся в отдельной aliasMap (мапятся на соответствующий идентификатор бинов).*
   - Все bean definitions контекста можно найти в "context" -> BeanFactory -> BeanDefinitionMap. Где ключ - название бина, а значение его Bean definition (метаинформация), на основании которого будет создан бин.
   - Все Singleton bean можно найти в context -> singletonObjects.
   - Тег **<constructor-arg ...>** тега используется для указания параметров конструктора.
     1. Параметр **value** в теге <constructor-arg> используется для указания **примитивных** параметров конструктора.
     2. Если просто указывать аргументы, то важен порядок аргументов. Иначе значения будут устанавливаться не в те параметры.
     3. Spring сам конвертирует примитивные значения в нужные типы. Для list используется дополнительный тег **<list><value>**, для map: **<map><entry>**.
     4. Есть возможность явно добавить тип аргумента, добавив параметр **type** в тег <constructor-arg>.
     5. Можно указать параметр **index=номер_по_порядку** в теге <constructor-arg...> для каждого параметра, тогда очередность не будет играть роли.
     6. Альтернативно и более наглядно использовать параметр **name=имя_параметра** в теге <constructor-arg...> для каждого параметра.
     7. Параметр **value-ref** (для map entry) или **ref** (для всех остальных) в теге <constructor-arg> используется для указания **ссылочных** параметров конструктора, например ссылок на другие бины.
   - **factory-method** в теге **<bean...>** используется при DI через фабричный метод. В бине должен быть специальный статический метод.
   - **factory-bean** в теге **<bean...>** используется при DI через фабричный метод. Указывается бин, который предоставит другие бины - там будет происходить создание объектов (как обычная фабрика).
   - тег **property** в теге **<bean...>** используется при DI через set методы.

## Bean scopes  
#### Типы бинов
1. **Common** - Общие (ApplicationContext):
    - **singleton** - всегда в одном экземпляре. Хранятся в context в singletonObjects (map).
    - **prototype** - каждый раз создается новый бин, когда он запрашивается у ApplicationContext. Не хранятся в context, сразу отдаются тому, кто попросил.
    - **thread** (SimpleThreadScope) - используется, чтобы создавать бины соответствующему потоку. Но нужно подключать его отдельно:  
        ```java 
        Scope threadScope = new SimpleThreadScope();
        context.registerScope("thread", threadScope);
        ```
2. **Web** (WebApplicationContext)
    - **request** - для каждого запроса по HTTP создается новый бин.
    - **session** - для каждой HTTP сессии создается новый бин.
    - **application** - на ServletContext создается новый бин.
    - **websocket**
3. **Custom**
    1. реализуем интерфейс org.springframework.beans.factory.config.**Scope**
    2. регистрируем созданный scope через **ConfigurableBeanFactory.registerScope()**

## Lifecycle Callbacks
После того как вызвали set методы, можем вызвать еще какие-то методы для последующей инициализации, утилизации.  
**Методы должны быть void и не должны принимать никаких параметров.**  
Если используется несколько вариантов callbacks, то порядок вызова будет именно такой, как указан ниже.
#### Initialization Callbacks
1. **@PostConstruct** - *нужны анотации* - предпочтительный
2. **afterPropertiesSet()** - *реализация интерфейса initializingBean*
3. **init-method** - *xml*
#### Destruction Callbacks (только для sigleton beans)
Методы, которые будут вызываться, когда context закрывается (нужно явно вызвать метод close(), например обернув в try-with-resources).  
Используются например для очистки ресурсов.
1. **@PreDestroy** - *нужны анотации* - предпочтительный
2. **destroy()** - *DisposableBean*
3. **destroy-method** - *xml*

### Injection from properties files
 - Обычные текстовые properties, yml файлы  
 - Можно предоставить некоторые настройки приложения по время поднятия IoC контейнера. А значит не нужно перекомпилировать приложение.  
 - Чтобы контейнер смог считывать эти файлы, нужно предоставить один бин: **PropertySourcesPlaceholderConfigurer**, указать откуда брать настройки (в xml добавляем бину <properties..> с указанием locations или locations - в зависимости от количества файлов).  
 - Или можно изменить схему, добавив контекст, и получить доступ к *property-placeholder*. Тогда он сам создаст бин *PropertySourcesPlaceholderConfigurer*. На самом деле просто возьмет кусок xml с этим бином и внедрит в текущий xml.  
 - Можно использовать **Expression Language**:
   1. **Expression Language**: **${}** - доступ к значениям
   2. **Spring Expression Language**: **#{}** - тоже что и EL, но можем обращаться к бинам, писать условия, обращаться к любым классам и тп.
### BeanFactoryPostProcessor
 - Инициализируются в первую очередь. Такие бины нужны для инициализации других BeanDefinitions. Например, установка значений.
 - Интерфейс *Ordered* (чем ниже Int значение метода int getOrder(), тем раньше инициализируется бин). Есть пограничные значения: HIGHEST_PRECEDENCE, LOWEST_PRECEDENCE.
 - Интерфейс *PriorityOrdered* - загрузка бина будет еще раньше, перед *Ordered*. Его реализуют все BFPP.

# Annotation based
 - **Bean Definitions** указываются с помощью анотаций.
 - начиная с jdk9 @PostConstruct и @PreDestroy были вынесены как отдельная зависимость: 
> Spring 5.3 еще не использует Jakarta EE, но использует Java EE спецификацию => будут другие пакеты ?
 - для обработки используется класс CommonAnnotationBeanPostProcessor.
> чтобы более удобно добавлять обработчик анотаций, вместо добавления в контекст класса, можно использовать <context:annotation-config>  

### <context:annotation-config> 
 - добавляет 5 бинов в контекст для обработки анотаций:
1. CommonAnnotationBeanPostProcessor (BPP) - **@PostConstruct** **@PreDestroy** **@Resource**
2. AutowiredAnnotationBeanPostProcessor (BPP) - **@Autowired** **@Value**
3. PersistenceAnnotationBeanPostProcessor (BPP) *(добавится только если добавить Spring Data)* - **@PersistenceContext** *(EntityManager)* **@PersistenceUnit** *(EntityManagerFactory)*
4. ConfigurationClassPostProcessor (BFPP) - **@Configuration**
5. EventListenerMethodProcessor (BFPP) - **@EventListener**

### BeanPostProcessor
 - являются бинами
 - нужны для дополнительной инициализации бинов, обработки анотаций, добавлять дополнительную функциональность путем оборачивания бинов в прокси. Т.к. к BPP попадают уже бины со всеми своими зависимостями, а не BeanDefinitions
 - postProcessBeforeInitialization вызывается ДО initialization callbacks
 - postProcessAfterInitialization вызывается сразу ПОСЛЕ initialization callbacks (именно здесь нужно реализовывать логику с подменой\проксированием бинов). Так же нужно учитывать, что на вход придет не сам бин, а его прокси (например, другие BPP могут отработать раньше и вернуть прокси). Поэтому нужно где то сохранить метаинформацию о нужных бинах (это нужно делать в beforeInit методе).
 - можно управлять порядком загрузки BPP с помощью @Ordered и @PriorityOrdered анотаций
> @PostConstruct - это тоже BPP, просто он вызывается самым последним - он должен эмулировать InitializationCallback фазу (которая идет после всех BPP)
 - после отработки initialization callbacks вызывается второй метод у BPP, у тех же самых BPP, в том же самом порядке, у тех же самых бинов. Только бины могут быть уже изменены после initCallbacks и beforeInit. На этой фазе обычно происходит создание прокси, подмена одних бинов другими и т.д.
 - далее получаем уже готовый бин. И если этот бин - singleton, он остается в IoC контейнере.
 - в обоих методах BPP удобно использовать ReflectionUtils, чтобы избежать обработки исключений.
 - В Spring по умолчанию используется прокси через наследование (cglib), т.к. не получится получить бин явно при использовании dynamic proxy (прокси через интерфейсы) - придется получать нужный бин по его интерфейсу!
### Aware
 - являются бинами, но обабатываются еще раньше, чем BPP
 - интерфейс-метка
 - реализующие его обрабатываются applicationContext
 - нужны для инжекта специфичных бинов в bean lifecycle (например Environment или даже сам Context), поэтому идут первыми
 - инжектом в Aware бины занимается ApplicationContextAware BPP
### @Autowired, @Value, @Resource
 - **@Autowired** (AutowiredAnnotationBeanPostProcessor) - стандартная аннотоция для инжекта бинов в Spring. 
     - Не позволяет указывать имя бина для инжекта, только required=true\false. 
     - Для того чтобы указать имя бина можно добавить вторую аннотацию **@Qualifier**. Если название поля совпадает с именем бина, то @Qualifier не нужен.
     - Позволяет инжектить зависимость в виде коллекций (причем можно управлять порядком попадания бинов в коллекцию через *@Ordered*)
 - **@Value** (AutowiredAnnotationBeanPostProcessor) - инжект полей или свойств из пропертей, используя EL или SPEL.
 - **@Resource** (CommonAnnotationBeanPostProcessor) - так же инжектит бины как Autowired и нужна для JSR250 (Java EE), но имеет меньший функционал (нельзя ставить над конструктором и другой аннотацией). Так же позволяет указать имя бина для инжекта.
> Отличной практикой является инжект через конструктор, а значит **@Autowired нужно ставить над конструктором**.
### \<context:component-scan/>
 - автоматически сканирует и по фильтру создает Bean Definitions из классов, находящихся по указанному пути. Потом из них будут созданы бины.
 - этот фильтр - аннотация **@Component** (а так же **@Repository, @Service**) - разделение логическое. Некоторые аннотации Spring так же являются компонентами (например @RestController)
 - уже включает в себя все бины, которые несет в себе **\<context:annotation-config/>**
 - для создания объектов, нужен конструктор и автоскан возьмет тот, который помечен *@Autowired*. Если же в классе только один конструктор, то он и будет вызван - Autowired можно не ставить.
#### Component scans
1. **XML \<context: component-scan/>** - ComponentScanBeanDefinitionParser
    - **base-packages** - указание где сканировать BeanDefinitions
    - **annotation-config** - по умолчанию = true
    - **name-generator** - генератор имен бинов (по умолчанию берется название класса с маленькой буквы, либо берется параметр аннотации @Component)
    - **resource-pattern** - тип файлов для поиска BeanDefinitions - дефолтное значение = "**/*.class"
    - **scoped-proxy** - по умолчанию не создается прокси для бинов, но можно указать:
      * interfaces - создание прокси через интерфейсы - DynamicProxy
      * no - нет (по умолчанию)
      * targetClasses - создание прокси на основании наследования cglib
    - **scope-resolver** - для изменения поведения резолва scope бинов. 
         * AnnotationScopeMetadataResolver Занимается определением scope бина по аннотации @Scope.
         * можно создать свой scope и пометить его аннотацией @Scope.
    - **use-default-filters** - использование фильтров для поиска BeanDefinitions в узанном месте (пакете). Если выставить в false, то фильтры не будут подключены. Есть 5 типов TypeFilter *(все они имеют дефолтную реализацию)*:
        * annotation - сканируют аннотацию Component. Можно добавить свою аннотацию. Если находит, то можно создавать на его основании BeanDefinition.
        * assignable - у класса вызывается метод isAssignableFrom(), в котором можно указать интерфейс или класс. Если доступен, то тогда можно на его основании создать BeanDefinition.
        * regex - регулярное выражение на основании имени класса. Если удовлетворяет, то можно создавать на его основании BeanDefinition.
        * aspectj - AspectJ паттерн.
        * custom - любая реализация интерфейса TypeFilter.
    - чтобы отключить\подключить фильтры:
         * <context:include-filter...
         * <context:exclude-filter...
2. **Annotation @ComponentScan** - ComponentScanAnnotationParser
### Bean Definition Reader
1. **XML based**
   - Интерфейс BeanDefinitionReader:
     1. XmlBeanDefinitionReader - парсит xml, находит элементы \<bean/> и на их основании создает BeanDefinition.
     2. GroovyBeanDefinitionReader - для Groovy.
     3. PropertiesBeanDefinitionReader *(deprecated)* - читает property и из них создает BeanDefinition.
2. **Annotation based**
    - ClassPathBeanDefinitionScanner
    - AnnotatedBeanDefinitionReader - для того, чтобы вручную зарегистрировать бины (имеет много методов register)
3. **Java based**
    - ConfigurationClassBeanDefinitionReader
      1. @Configuration
      2. @Bean
### JSR-250, JSR-330
 - **Java Specification Requests**
 - набор интерфейсов и аннотаций, которые необходимо реализовывать, чтобы соблюдать спецификации
 - набор предоставляет Java EE
 - **JSR-250** - Java annotations
 - **JSR-300** - Dependency injection annotations
# Java based configuration
 - представление BeanDefinitions в java коде, без xml.
 - **ConfigurationClassPostProcessor** (BFPP!) обрабатывает аннотацию **@Configuration**:
   - все классы, из которых будут созданы BeanDefinitions должны быть помечены @Configuration, потому что они обрабатываются в первую очередь.
 - **@PropertySource** - для указания пути сканирования пропертей. Замена \<context:property-placeholder/>
 - **@ComponentScan** - для обработки аннотаций, компонентов. Замена xml \<context:component-scan/>