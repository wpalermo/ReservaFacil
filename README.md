# Reserva Facil

A implementação do projeto foi feita toda em Java10 porém não foi usada nenhum biblioteca específica da versão 10 mas foram usadas varias do java8.
Aqui no repositório irá ter uma branch  **feature/single_service** essa foi a primeira versão feita, em um único projeto, nela está a mesma logica de negócio que está na branch **master** mas de maneira mais simples. 

## Branch single_service

No startup projeto é adicionado ao banco 5 contas que são usadas para realizar as transferências, a inclusão delas no banco é feita na classe *DataLoader* que implementa um *ApplicationRunner* e sempre roda uma vez antes do sistema estar pronto par uso.

Nessa implementação foi usado os seguintes frameworks
* Spring boot
* Spring scheduler
* Spring data
* hsqldb

Foi usado o spring boot para rodar a aplicação na web com microserviços, no controller da aplicação temos um serviço post que recebe uma transferencia.

Recebida a transferencia ele agendará essa transferencia, começará chamando o serviço de taxas que calculará a taxa da transação de acordo com a data de agendamento e a data da transação, retornada a taxa, o serviço armazena essa transação no banco de dados (em memória) usando spring data com o status de *AGUARDANDO_TRANSFERENCIA*

Quando o sistema está rodando, o spring scheduler é executado a cada minuto, a cada execução ele chama o método de transferência que realiza uma busca no banco de dados pelas transferencias que devem ser realizadas na data atual e estao com status AGUARDANDO_TRANSFERENCIA. 
Essa busca retorna uma lista de transferências que devem ser realizadas, durante o processo são feitas verificações basicas, como se a conta existe, se o saldo é suficiente. 
Caso não em alguma validação é subida uma exceção para cada tipo de erro de negócio tratado e retornado status code 417 e a mensagem de erro no body.
Caso esteja tudo certo com a transferência, o valor com a taxa é subtraido da conta origem e somado a conta destino e retornado status code 200 para o cliente.

## Branch Master (micro-services)

Como houve tempo para incrementar um pouco o projeto, fiz uma implementação em uma arquitetura de micro-serviços, a base desse projeto é a single_branch e as regras de negócio são exatamente as mesmas, com a diferença que o serviço de taxas foi separado em um outro micro serviço. 

Isso também poderia ter sido feito com o serviço de contas por exemplo, mas não acredito que teria tempo e seria mais do mesmo, não conseguiria mostrar nenhuma outro framework ou técnologia.

Nessa implementação foram usados os seguintes frameworks
* Spring boot
* Spring scheduler
* Spring data
* hsqldb
* Hystrix
* ReactiveX
* Eureka
* Zuul
* FeignClient

Foram usados os frameworks da netflix para implementação da arquitetura. 
#### FeingCliente
Para a chamada do serviço de taxas foi usado o *FeingCliente* para fazer as requisições, no caso dessa implementação o feing usa o Eureka para achar o serviço que ele deve chamar, sim é possível usar o FeingClient sem o Eureka, passando como parametros da anotação o endereço direto do serviço que deve ser chamado.

#### Eureka
Foi citado o Eureka acima, mas o que ele faz? Ele é um service-discovery, toda a aplicação que sobe deve ser configurada como *EurekaClient*, dessa maneira ela será automaticamente registrada no Eureka e visível para todos os demais serviços que também usem o Eureka. 

É interessante o uso dele pois ele fica responsável por direncionar o trafego, assim na nossa implementação não precisamos saber o IP/porta do serviços apenas com o nome dele o Eureka é capaz de direcionar para o serviço correto.

O combo Eureka + Feing torna as coisas mais interssantes uma vez que automaticamente implementam o *Ribbon* que é o loadbalancer da netflix, ou seja, no caso de um cluster o Eureka conseguira balancear esse trafego.  Por ser automatico o Ribbon não foi citado como framework, pois não será encontrado nada dele na implementação.

A implementação do Eureka é bem simples (nesse caso) se resumindo basicamente a um .properties.

#### Hystrix / ReactiveX

O Hystrix é responsável pelo circuit-breaker e realiza o fallback das requisições em caso de falha, no caso dessa implementação caso a requisição falhe ele executa um metodo com um log de erro e retorna null, a exceção é capturada e a transferencia é adicionada na base com o status *TAXA_NAO_CALCULADA*.

O ReactiveX nessa implementação é usado para executar a classe que implementa o Hystrix. O hystrix funcionaria sem o ReactiveX, mas implementei o ReactiveX para efeito de demonstração, no caso desse projeto ele não chega a ser usado da melhor maneira, que seria mais orientado a função. Como o sistema está aguardando a taxa ser calculada (sincrono), o reactiveX é usado apenas para chamar o hystrix que por usa vez faz a requisição.

#### Zuul
O Zuul não tem nenhuma função na implementação, ele é um gateway que combinado com o Eureka recebe as requisições e a partir do nome da aplicação passado na URL consegue enviar (via eureka) a requisição para o lugar certo. 

Assim como Eureka + Feing, o combo Eureka + Zuul também implementa automaticamente o Ribbon e consegue fazer o load balance das transações.

# Testes

#### Taxa-app
Todos os testes feitos usando junit. Por ser um serviço mais simples, não foi necessário nem o uso de mocks.

#### Transferencia-app
As classes de teste foram criadas para testar as principais classes de implementação. 
Para os testes de transferencia e taxa foi usado o *Mockito* esses testes foram necessários para garantir que o fluxo estava funcionando antes de colocar o sistema na arquitetura netflix, pois o eureka e principalmente o zuul tem timeouts bastante baixos e ficam gerando erro durante um processo de debug dificultando bastante um teste via requisição normal.

Criei uma classe de teste chamada *TaxaResourceTest* essa classe está comentada, pois serve basicamente para testar a chamada do serviço de taxas, logo se for deixada para ser compilada, um scan do sonar provavelmente irá barrar o deploy do projeto.   

#### Postman
No repositorio tem uma collection do postman para teste integrado do projeto. 



