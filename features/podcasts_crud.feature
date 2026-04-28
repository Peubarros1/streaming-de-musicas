Feature: Cadastro e manutenção de episódios de podcast
As a usuário artista 
I want to criar, editar e excluir episódios de podcast
So that I can gerenciar meu conteúdo de forma eficiente

  Scenario: Criar episódio de podcast com dados válidos
    Given o usuário está na tela de criação de episódio de podcast
    When o usuário tenta criar um episódio com nome "Episódio 1", descrição "primeiro episódio", arquivo "ep1.mp3" válido
    Then data e duração são preenchidos automaticamente
    And retorna a mensagem de sucesso "Episódio criado com sucesso"
    And o episódio fica disponível para visualização pública


  Scenario: Criar episódio de podcast com título nulo
    Given o usuário está na tela de criação de episódio de podcast
    When o usuário tenta criar um episódio com nome "", descrição "segundo episódio", arquivo "ep2.mp3" válido
    Then data e duração são preenchidos automaticamente
    Then o sistema rejeita a requisição
    And retorna a mensagem de erro "O título do episódio é obrigatório"

  Scenario: Criar episódio de podcast com descrição nula
    Given o usuário está na tela de criação de episódio de podcast
    When o usuário tenta criar um episódio com nome "Episódio 3", descrição "", arquivo "ep3.mp3" válido
    Then data e duração são preenchidos automaticamente
    Then o sistema rejeita a requisição
    And retorna a mensagem de erro "A descrição do episódio é obrigatória"

  Scenario: Criar episódio de podcast com arquivo que não é áudio
    Given o usuário está na tela de criação de episódio de podcast
    When o usuário tenta criar um episódio com nome "Episódio 4", descrição "quarto episódio", arquivo "video.mp4" inválido
    Then data e duração são preenchidos automaticamente
    Then o sistema rejeita a requisição
    And retorna a mensagem de erro "Apenas arquivos de áudio são permitidos"

  Scenario: Programar episódio de podcast para data futura
    Given o usuário está na tela de criação de episódio de podcast
    When o usuário tenta criar um episódio com nome "Episódio 5", descrição "quinto episódio", arquivo "ep5.mp3" válido  
    And ele define a data "30/04/2027"
    Then a duração é preenchida automaticamente
    And retorna a mensagem de sucesso "Episódio agendado com sucesso"
    And o episódio fica agendado para a data informada

  Scenario: Editar episódio de podcast
    Given o usuário tem um episódio de podcast criado com título "Episódio 6" e descrição "sexto episódio"
    And o episódio atual possui um arquivo de áudio publicado
    When o usuário editar o título para "Episódio 6 Editado" e a descrição para "sexto episódio editado"
    And substituir o arquivo de áudio do episódio
    Then as alterações são salvas com sucesso
    And o arquivo antigo é permanentemente substituído
    And o sistema não mantém histórico de edições

  Scenario: Excluir episódio de podcast
    Given o usuário tem um episódio de podcast criado com título "Episódio 7" e descrição "sétimo episódio"
    When o usuário excluir o episódio
    Then o episódio é removido com sucesso
