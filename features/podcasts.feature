Feature: Funcionalidades gerais de podcasts
As a usuário
I want to interagir com os episódios de podcast, como reproduzir, visualizar acessos e baixar arquivos
So that I can consumir o conteúdo de áudio e acompanhar a popularidade dos episódios

  Scenario: Contabilizar acesso ao iniciar reprodução de episódio
    Given existe um episódio de podcast publicado chamado "Episódio Piloto" com total de acessos igual a 10
    When o usuário iniciar a reprodução do episódio "Episódio Piloto"
    Then o sistema contabiliza o acesso imediatamente
    And o total de acessos do episódio passa a ser 11

  Scenario: Exibir total de acessos para qualquer usuário
    Given existe um episódio de podcast publicado chamado "Bate-papo Aberto" com total de acessos igual a 25
    When qualquer usuário (logado ou não) acessar a página do episódio "Bate-papo Aberto"
    Then o sistema exibe publicamente o total de acessos igual a 25

  Scenario: Fazer download do arquivo bruto com usuário autenticado
    Given existe um episódio de podcast publicado chamado "Resumo da Semana"
    And o usuário está autenticado no sistema
    When o usuário tenta fazer o download do arquivo bruto do episódio "Resumo da Semana"
    Then o sistema permite o download do arquivo com sucesso

  Scenario: Bloquear download do arquivo bruto sem autenticação
    Given existe um episódio de podcast publicado chamado "Entrevista Especial"
    And o usuário não está autenticado no sistema
    When o usuário tenta fazer o download do arquivo bruto do episódio "Entrevista Especial"
    Then o sistema rejeita a requisição
    And retorna a mensagem de erro "É necessário estar logado para baixar este episódio"
