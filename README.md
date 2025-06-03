Este projeto consiste no desenvolvimento de um sistema de gerenciamento de dados voltado para o universo Pokémon, criado como atividade interdisciplinar no 3º semestre do curso de Ciência da Computação. A proposta integrou as disciplinas de Gerenciamento de Dados e Programação Orientada a Objetos, com o objetivo de consolidar conhecimentos práticos em banco de dados relacional e boas práticas em Java.

A solução desenvolvida utiliza o MySQL Workbench para estruturar o banco e a linguagem Java para aplicar regras de negócio sobre os dados. Inicialmente, os pokémons são inseridos manualmente em uma tabela principal de forma desorganizada. A partir dela, o sistema realiza a separação automática dos registros em tabelas específicas por tipo (elétrico, voador e fogo), evitando duplicações com controles implementados no backend.

Além disso, o projeto contempla uma tabela totalizadora, responsável por contabilizar a quantidade de pokémons por tipo, e uma rotina de saneamento que identifica e move registros duplicados para uma tabela separada de "deletados", preservando a integridade e rastreabilidade dos dados.

A aplicação tem como foco a aplicação de princípios de design orientado a objetos, modelagem relacional e integração entre backend e banco de dados, resultando em uma entrega funcional e aprovada academicamente. O projeto está concluído e reflete um exercício prático robusto sobre persistência de dados e lógica de negócios em Java conectada ao MySQL.

