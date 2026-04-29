Feature: Funcionalidades gerais de podcasts
As a usuário
I want to interagir com os episódios de podcast, como reproduzir, visualizar acessos e baixar arquivos
So that I can consumir o conteúdo de áudio e acompanhar a popularidade dos episódios

  Scenario: Contabilizar acesso ao iniciar reprodução de episódio
    Given estou logado como "Usuário" com login "Zezinho"
    And existe um episódio de podcast publicado chamado "Episódio Piloto" com total de acessos igual a 10
    When inicio a reprodução do episódio "Episódio Piloto"
    Then o sistema contabiliza o acesso imediatamente
    And o total de acessos do episódio passa a ser 11

  Scenario: Exibir total de acessos para usuário logado
    Given estou logado como "Usuário" com login "Zezinho"
    And existe um episódio de podcast publicado chamado "Bate-papo Aberto" com total de acessos igual a 25
    When acesso a página do episódio "Bate-papo Aberto"
    Then o sistema exibe publicamente o total de acessos igual a 25

  Scenario: Exibir total de acessos para usuário não logado
    Given não estou logado 
    And existe um episódio de podcast publicado chamado "Bate-papo Aberto" com total de acessos igual a 25
    When acesso a página do episódio "Bate-papo Aberto"
    Then o sistema exibe publicamente o total de acessos igual a 25

  Scenario: Exibir total de acessos para artista
    Given eu estou logado como "Artista" com login "Juninho"
    And existe um episódio de podcast publicado chamado "Bate-papo Aberto" com total de acessos igual a 25
    When acesso a página do episódio "Bate-papo Aberto"
    Then o sistema exibe publicamente o total de acessos igual a 25

  Scenario: Fazer download do arquivo bruto com usuário autenticado
    Given estou logado como "Usuário" com login "Zezinho"
    And existe um episódio de podcast publicado chamado "Resumo da Semana"
    And estou autenticado no sistema
    When tento fazer o download do arquivo bruto do episódio "Resumo da Semana"
    Then o sistema permite o download do arquivo com sucesso

  Scenario: Bloquear download do arquivo bruto sem autenticação
    Given estou logado como "Usuário" com login "Zezinho"
    And existe um episódio de podcast publicado chamado "Entrevista Especial"
    And não estou autenticado no sistema
    When tento fazer o download do arquivo bruto do episódio "Entrevista Especial"
    Then o sistema rejeita a requisição
    And retorna a mensagem de erro "É necessário estar logado para baixar este episódio"
