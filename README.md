Rental Cars Challenge

Bem vindo ao desafio técnico Java Rental Cars!

Este aplicativo tem por objetivo avaliar o desenvolvimento de códigos em Java, e funciona da seguinte maneira:

Seu objetivo é controlar os alugueis de veículos de uma locadora, lendo arquivos de entrada de dados e gerando relatórios.

Utiliza um banco de dados em memória(H2) e possui três entidades:

Carro: Armazena os dados dos carros da frota.

| Coluna          |      Tipo     |
|-----------------|:-------------:|
| ID              | int PK        |
| MODELO          | varchar(50)   |
| ANO             | varchar(4)    |
| QTD_PASSAGEIROS | int           |
| KM              | int           |
| FABRICANTE      | varchar(50)   |
| VLR_DIARIA      | int           |


Cliente: Armazena os dados dos clientes.


| Coluna          |      Tipo     |
|-----------------|:-------------:|
| ID              | int PK        |
| NOME            | varchar(100)  |
| CPF             | varchar(11)   |
| CNH             | varchar(11)   |
| TELEFONE        | varchar(13)   |


Aluguel: Armazena os dados dos alugueis dos carros.


| Coluna          |      Tipo       |
|-----------------|:---------------:|
| ID              | int PK          |
| CARRO_ID        | int FK(CARRO    |
| CLIENTE_ID      | int FK(CLIENTE) |
| DATA_ALUGUEL    | varchar(8)      |
| DATA_DEVOLUCAO  | varchar(8)      |
| VALOR           | int             |
| PAGO            | char(1)         |

OBS: As datas nessa tabela estão no formato yyyyMMdd.

Regras de negócio existentes:

- O programa deve ser iniciado simplesmente ao executar a classe Main que deve disparar todos os processos de forma sequencial.
- Atualmente o sistema inicia e dispara um processo para gerar um relatório da frota no formato txt com dados de todos os carros disponíveis.

Seu trabalho:

- Crie uma nova branch com seu nome a partir da Master, desenvolva as funcionalidades a seguir e submita um pull request no projeto.
- Desenvolva um processo de leitura de arquivo que deve ser disparado ao executar a classe Main depois de gerar o relatório da frota.
    - Esse processo deve ler o arquivo RentReport.rtn que se encontra no diretório Resources do projeto, que nada mais é do que um arquivo de texto posivional.
    - Cada linha desse arquivo RentReport.rtn possui 20 caracteres e contém os dados do aluguel de um veículo.
    - As posições dos dados no arquivo são as seguintes:
        - 1 a 2: Id do carro na tabela CARRO.
        - 3 a 4: Id do cliente na tabela Cliente.
        - 5 a 12: Data do aluguel do carro.
        - 13 a 20: Data da devolução do carro.
    - Esse processo deve ler esse arquivo e popular os dados na tabela ALUGUEL.
    - Para cada registro inserido na tabela ALUGUEL, o campo VALOR deve ser calculado multiplicando o valor da diária do carro pelo número de dias alugados.
    - O campo PAGO deve ser inserido com o valor 'N';
    - Caso algum carro ou cliente lido no arquivo não seja encontrado na base de dados, o sistema deve gerar um log de alerta informando que o ID não foi encontrado, e o processo deve continuar.
- Desenvolva um outro processo de geração de relatório que deve ser disparado ao executar a classe Main depois de ler o arquivo RentReport.rtn.
    - Esse processo deve gerar um arquivo txt no diretório base da aplicação com a seguinte nomenclatura: RelatorioAlugueis_{DATA_ATUAL}.txt
    - Esse relatório deve conter um cabeçalho somente com a data da geração do arquivo (semelhante ao relatório da frota).
    - Esse relatório deve listar todos os registros dos alugueis encontrados na base de dados.
    - Essa lista de alugueis deve conter os seguintes campos:
        - | Data do aluguel | Modelo do carro | Km do carro | Nome do cliente | telefone do cliente no formato +XX(XX)XXXXX-XXXX | Data de devolução | Valor | Pago no formato(SIM/NAO) |
    - Esse relatório deve ter um rodapé informando o valor total ainda não pago dos alugueis (Soma de todos os alugueis não pagos).
 
Diferenciais:
  - Logue as informações que achar pertinentes.
  - Trate os possíves erros de forma adequada.
  - Certifique-se que a aplicação esteja bem testada com testes unitários.
