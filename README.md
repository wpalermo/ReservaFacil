# Reserva Facil

A implementação do projeto foi feita toda em Java10 porém não foi usada nenhum biblioteca específica da versão 10 mas foram usadas varias do java8.

Como houve tempo para incrementar um pouco o projeto, fiz uma implementação em uma arquitetura de micro-serviços, a base desse projeto é a single_branch e as regras de negócio são exatamente as mesmas, com a diferença que o serviço de taxas foi separado em uma outra aplicação. 

Decidi fazer essa arquitetura pois ja havia estudado e implementado ela anteriormente, então tive o trabalho de aplicar essa arquitetura no problema que foi passado. No meu git tem um repositório chamado socio-torcedor irão encontrar algo bem parecido. O que eu tentei fazer diferente para esse que ainda não tinha feito, foi a chamada de serviço sendo feita de maneira assincrona e totalmento orientada a função.

Aqui nesse repositório podemos ver mais ou menos como foi a evolução do desenvolvimento do projeto.

* A branch  **feature/single_service** essa foi a primeira versão feita em um único projeto, nela tem exatemento o que foi pedido e onde foi definida toda a logica de negócio e está lógica está na **master** e a branch **feature/micro_service**. 

* A branch **feature/micro_service** é a single service, porém com o serviço de taxa implementado em uma aplicação a parte e é acessada via chamada de servico rest. 

* A branch  **master** é a mesma versão que está na branch **feature/micro_service_async**  essa branch é uma "evloução"da de micro_service, fazendo a chamada do servico de taxas de maneira assincrona e orientada a função.

A branch **develop** usei para deixar sempre uma versão que funciona, sempre que rodo e acho que a versão está de certa forma estável comito na develop depois volto pra branch e continuo desenvolvendo as outras funcionalidades.

### Frameworks

No startup projeto é adicionado ao banco 5 contas que são usadas para realizar as transferências, a inclusão delas no banco é feita na classe *DataLoader* que implementa um *ApplicationRunner* e sempre roda uma vez antes do sistema estar pronto par uso.


Nessa implementação foram usados os seguintes frameworks sendo os 5 ultimos do Netflix-OSS
* Spring boot
* Spring scheduler
* Spring data
* hsqldb
* Hystrix 
* ReactiveX
* Eureka
* Zuul
* FeignClient



## Fluxo da transferencia

Recebida a transferencia ele agendará essa transferencia, começará chamando o serviço de taxas (ReactiveX + Hystrix) que ir'a fazer o calculo da taxa da transação de acordo com a data de agendamento e a data da transação, retornada a taxa, o serviço armazena essa transação no banco de dados (em memória) usando spring data com o status de *AGUARDANDO_TRANSFERENCIA*. 

Caso esse serviço esteja offline ou demore muito para responder, essa chamada será executada pelo metodo de fallback do Hystrix, nesse caso a transferência é salva com o status de *AGUARDANDO_CALCULO_TAXA*

As transferencias na data correta e no status *AGUARDANDO_TRANSFERENCIA* são feitas quando é executado o scheduler do spring (explicado mais a baixo), caso a tranferência seja feita com sucesso seu status é alterado para *SUCESSO* e caso o status seja *AGUARDANDO_CALCULO_TAXA* é executado outro servico do scheduler que chama novamente o serviço de calculo de taxa e caso seja feito com sucesso, seu status é alterado para *AGUARDANDO_TRANSFERENCIA* assim entrando para ser feita a transferencia

Para a diferenciação de erros, os serviços tanto de taxa como de transferencia retornam o status code 417 (EXPECTATION_FAILED) no caso de erro de negócio. No fluxo, quando uma requisição do serviço de taxa retorna o status code 417 a transferencia é atualizada para o status *TAXA_NAO_CALCULADA*.

O caminho feliz de uma trasferencia seria
```
AGUARDANDO_TRANSFERENCIA -- taxa request --> SUCESSO
```
OU
```
AGUARDANDO_CALCULO_TAXA -- taxa request --> AGUARDANDO_TRANSFERENCIA -- taxa request--> SUCESSO
```

### Spring Boot
Foi usado o spring boot para rodar a aplicação na web com microserviços, no controller da aplicação temos um serviço post que recebe uma transferencia e um get que retorna uma lista com todas as transferencias.

### Spring Scheduler
Como citado na explicação do fluxo, o scheduler tem uma função muito parecida a de um batch, roda em um determidado intervalo de tempo realiza sua tarefa e espera novamente seu intervalo. 
No caso temos dois schedulers um para realizar a transferencia e outro para realizar o calculo de taxas, ambos rodam de 1 em 1 minuto. 

Porque o scheduler e nao o spring batch, nesse caso foi porque eu não sabia como acessar o banco de dados *hsqldb* a partir de uma aplicação externa ao serviço, imagino que tenha um jeito mas não tive tempo de pesquisar. 

### Spring Data
Faz toda a camada de persistencia na base de dados **hsqldb** e usando o spring data para fazer todas as consultas. 

### HSQLDB
Banco de dados embarcado, no caso é usado para fazer um banco de dados em memória e persistir os dados durante a execução das aplicações.

### FeingCliente
Para a chamada do serviço de taxas foi usado o *FeingCliente* para fazer as requisições, no caso dessa implementação o feing usa o Eureka para achar o serviço que ele deve chamar, sim é possível usar o FeingClient sem o Eureka, passando como parametros da anotação o endereço direto do serviço que deve ser chamado.

### Eureka
Foi citado o Eureka acima, mas o que ele faz? Ele é um service-discovery, toda a aplicação que sobe deve ser configurada como *EurekaClient*, dessa maneira ela será automaticamente registrada no Eureka e visível para todos os demais serviços que também usem o Eureka. 

É interessante o uso dele pois ele fica responsável por direncionar o trafego, assim na nossa implementação não precisamos saber o IP/porta do serviços apenas com o nome dele o Eureka é capaz de direcionar para o serviço correto.

O combo Eureka + Feing torna as coisas mais interssantes uma vez que automaticamente implementam o *Ribbon* que é o loadbalancer da netflix, ou seja, no caso de um cluster o Eureka conseguira balancear esse trafego.  Por ser automatico o Ribbon não foi citado como framework, pois não será encontrado nada dele na implementação.

A implementação do Eureka é bem simples (nesse caso) se resumindo basicamente a um .properties.

### Hystrix / ReactiveX

O Hystrix é responsável pelo circuit-breaker e realiza o fallback das requisições em caso de falha.

O ReactiveX nessa implementação é usado para executar a classe que implementa o Hystrix. O hystrix funcionaria sem o ReactiveX, mas implementei o ReactiveX para efeito de demonstração, no caso desse projeto usei ele para fazer a chamada de serviço um pouco mais orientada a função junto com o fallback do hystrix. 

Usando o combo Hystrix + ReactiveX podemos implementar a chamadas de serviço externo de uma maneira mais orientada a função, quando uma transferencia é solicitada e o serviço de calculo de taxa é disparado, caso não tenha resposta imediata o metodo fallback salva a transação com o status de *AGUARDANDO_CALCULO_TAXA* e da mesma maneira que uma transferencia é feita, existe um método scheduled do spring responsável por buscar as transações que estão aguardando calculo e estão na data, nesse caso as transações são atualizadas para o status *AGUARDANDO_TRANSFERENCIA* que vai entrar no proximo schedule de transferência.

### Zuul
O Zuul não tem nenhuma função na implementação, ele é um gateway que combinado com o Eureka recebe as requisições e a partir do nome da aplicação passado na URL consegue enviar (via eureka) a requisição para o lugar certo. 

Assim como Eureka + Feing, o combo Eureka + Zuul também implementa automaticamente o Ribbon e consegue fazer o load balance das transações.


### HystrixDashboard
Assim como Zuul e Eureka, não é uma implementação e nesse caso nem de um arquivo properities precisamos para rodar, coloquei no repositório apenas para caso de curiosidade. Ele mostra a "saude"dos micro-serviços implementados do o hystrix, mostrando a quantidade de requisição, se o circuito está fechado, etc... 
Para usá-lo é so rodar o projeto do HystrixDashboard e colocar o endereço http:// endereco_do_zuul / hystrix.stream



## Testes

Foquei os testes para testar o funcionamento do sistema e suas regras de negócio, algumas partes que não achei importantes para os meus testes deixei sem cobertura.

### Taxa-app
Todos os testes feitos usando junit. Por ser um serviço mais simples, não foi necessário nem o uso de mocks.
Porem é nessa aplicação que estão os calculos das taxas, todos os testes foram feitos via junit, simulando as datas e verificando a taxa de retorno.

### Transferencia-app
As classes de teste foram criadas para testar as principais classes de implementação. 
Para os testes de transferencia e taxa foi usado o *Mockito* esses testes foram necessários para garantir que o fluxo e a logica de negócio estava funcionando antes de colocar o sistema na arquitetura netflix, pois o eureka e principalmente o zuul tem timeouts bastante baixos e ficam gerando erro durante um processo de debug dificultando bastante um teste via requisição normal.

Criei uma classe de teste chamada *TaxaResourceTest* essa classe está comentada, pois serve basicamente para testar a chamada do serviço de taxas, logo se for deixada para ser compilada, um scan do sonar provavelmente irá barrar o deploy do projeto.   

### Postman
No repositorio tem uma collection do postman para teste integrado do projeto. 




## Rodando o projeto

Para rodá-los, o transferencia-app e o taxa-app tem um arquivo *Application.java* no package "raiz". 

O Zuul tem o *GatewayApplicaction.java*

O Eureka usa o *EurekaServiceApplication.java*



Para subir toda a arquitetura a única requisição é que o **Eureka** deve ser o primeiro a estar rodando assim todos as outras aplicações conseguirão se registrar no serviço de service-discovery sem problemas. 
Lembrando que a comunicação entre a aplicação de transferência e a aplicação de taxas é feita pelo Eureka, sem ele online não ira funcionar nenhum request e as transações sempre cairão no método de fallback.

A partir daí a ordem de subida das aplicações é indiferente, o Zuul pode ou não ser usado, na collection do postman tem o endereço que deve ser usado. Por exemplo 
> Chamando serviço sem o Zuul
> http://localhost:8083/transferencia

> Chamando servico com Zuul
> http://localhost:8080/transferencia-app/transferencia

As portas configuradas sao: 
* 8082 -> taxa app
* 8083 -> transferencia-app
* 8080 -> Zuul
* 8761 -> Eureka (porta padrão)

#### Pontos de atenção
Todas as aplicações tem as portas ja definidas no arquivo aplication.yml e todos devem estar com a configuração do Eureka em seu arquivo.



## O que faltou?

Sempre que o junit é executado ele inicia um spring boot, mas como está configurado para procurar o Eureka ele vai retornar varias exceções no log, todas do Eureka, mas mesmo assim os testes sao realizados. Ficou faltando descobrir como subir os testes sem executar o Eureka ou mockando ele.

Algumas exceções ainda estão muito genéricas e não devem ser assim, não consegui descobrir como capturar uma exeção mais específica a partir do retorno do response entity.

Um ExceptionHendler e um Logger, para centralizar e padronizar o tratamento de erro da aplicação como um todo.
