# API FATEC

## Desafio

Ter um bom entendimento do clima de uma região é imprescindível para o planejamento de atividades em várias áreas. Por exemplo, conhecer a temperatura e umidade média em determinados períodos do ano pode influenciar na escolha do que plantar em uma fazenda. Atualmente, existem diversas bases de dados públicas que fornecem essas informações na forma de arquivos CSV. Entretanto, existem diversos arquivos para uma mesma cidade (um para cada estação de monitoramento) que, dependendo do tipo da estação, podem apresentar diferentes formatos. Com base no exposto, gostaríamos de um Sistema de Banco de Dados capaz de receber esses arquivos, validar seu conteúdo e prover relatórios.

## Objetivo do projeto

Criar uma solução computacional (CRUD) de um sistema de gerenciamento de relatórios climáticos, onde será possível uma melhor organização e acesso às informações sobre o clima e tempo em cada região escolhida, assim como manipular (Adicionar, remover e editar). O programa será desenvolvido em Java com acesso a sistema gerenciador de BD (SGBD) usando JDBC.

## Requisitos Funcionais e Não Funcionais:

### Requisitos Funcionais:

- **Relatório de valor médio das variáveis climáticas por cidade:** Deve ser possível escolher uma cidade e um período de tempo. O relatório deve ter periodicidade horária (um registro a cada hora).

- **Gerar dados que possibilitem a criação de um gráfico boxplot com dados de uma estação em uma determinada data:** BOXPLOT: Em um gráfico do tipo boxplot é exibido o resumo dos 5 números, que são: mínimo, primeiro quartil, mediana, terceiro quartil e o máximo. Essas 5 estatísticas apresentadas anteriormente são fundamentais para a construção deste gráfico.

- **Relatório de situação:** Apresentar os valores médios das últimas medidas para cada cidade.

- **Gerenciamento de estações, cidades e unidades de medida:** Deve ser possível visualizar e alterar informações sobre esses elementos do sistema. Cada estação está localizada em uma cidade do estado de SP. Cada variável climática medida possui uma unidade de medida.

- **Carregamento e validação de arquivos CSV contendo variáveis climáticas:** Registros suspeitos (exemplo: registro com temperatura acima de 60 graus Celsius ou inferior a -20 graus Celsius) devem ser armazenados à parte para revisão manual. Cada arquivo possui registros de apenas uma estação (referenciada no nome do arquivo). Cada registro apresenta valores para todas as variáveis, mas elas devem ser armazenadas separadamente (um registro para temperatura, outro para umidade, etc., em vez de um único registro contendo temperatura, umidade, etc.).

- **Tratamento de registros suspeitos:** Permitir exclusão ou revisão. Deve ser possível alterar os valores e passá-los à base de dados principal ou excluí-los.

### Requisitos Não Funcionais:

- **Linguagem:** Java.
- **Banco de Dados:** Relacional.
- **Documentação:** Manual de usuário, diagrama entidade-relacionamento e instruções de instalação.
