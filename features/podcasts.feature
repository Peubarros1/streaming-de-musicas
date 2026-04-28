Feature: Funcionalidades gerais de podcasts

  Scenario: Contabilizar acesso ao iniciar reprodução de episódio
    Given existe um episódio de podcast publicado com total de acessos igual a 10
    When um usuário iniciar a reprodução desse episódio
    Then o total de acessos do episódio passa a ser 11

  Scenario: Exibir total de acessos para qualquer usuário
    Given existe um episódio de podcast publicado com total de acessos igual a 25
    When um usuário visitar a página do episódio
    Then o sistema exibe o total de acessos igual a 25

  Scenario: Programar postagem de novo episódio para data futura
    Given o criador está na tela de criação de episódio
    And informou título, descrição e arquivo de áudio válido
    And definiu uma data futura para publicação
    When confirmar a criação do episódio
    Then o episódio fica agendado para a data informada

  Scenario: Fazer download do arquivo bruto com usuário autenticado
    Given existe um episódio de podcast publicado
    And o usuário está autenticado
    When o usuário solicitar o download do arquivo bruto do episódio
    Then o sistema permite o download

  Scenario: Bloquear download do arquivo bruto sem autenticação
    Given existe um episódio de podcast publicado
    And o usuário não está autenticado
    When o usuário solicitar o download do arquivo bruto do episódio
    Then o sistema bloqueia o download
    And retorna a mensagem "É necessário estar logado para baixar este episódio"
