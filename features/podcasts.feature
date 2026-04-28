Feature: Processamento de Áudio
  Scenario: Upload de arquivo com formato inválido (PDF)
    Given que o criador seleciona o arquivo "aula_01.pdf" para compor um novo episódio
    When ele tenta enviar o arquivo para publicação
    Then o episódio não deve ser publicado
    And o sistema deve exibir a mensagem "Formato de arquivo não suportado. Utilize apenas MP3 ou WAV."

  Scenario: Tentativa de upload de arquivo contendo vídeo
    Given que o criador seleciona o arquivo de vídeo "videocast.mp4" para o episódio
    When ele tenta enviar o arquivo para publicação
    Then a publicação deve falhar
    And deve ser exibida a mensagem "Formato de arquivo não suportado. Utilize apenas MP3 ou WAV."


Feature: Download de Episódio
  Scenario: Tentativa de download por usuário não autenticado
    Given que um visitante não logado acessa a página do "Episódio 1"
    When ele solicita o download do arquivo bruto do podcast
    Then o download não deve ser iniciado
    And o sistema deve exibir a mensagem "É necessário estar logado para baixar o podcast."


Feature: Métricas de Popularidade
  Scenario: Visualização de acessos totais por usuários externos
    Given que o episódio "Engenharia de Software 101" acumula 500 reproduções iniciadas
    When um visitante qualquer acessa a página do episódio
    Then deve ser exibido o total de "500 acessos"
    And essa informação deve ser visível para todos os perfis de usuários


Feature: Gerenciamento de Episódios
  Scenario: Atualização de arquivo de áudio sem manter histórico
    Given que o criador possui o "Episódio 1" publicado
    When ele atualiza o episódio enviando um novo arquivo de áudio
    Then o novo áudio deve ser reproduzido nas próximas execuções do "Episódio 1"
    And o arquivo de áudio original não deve mais estar disponível para reprodução ou download

  Scenario: Upload de arquivo de longa duração
    Given que o criador selecionou um arquivo de áudio com "5 horas" de duração
    When o envio do arquivo for concluído
    Then o episódio deve ser publicado com sucesso, sem bloqueios por limite de tempo

  Scenario: Ouvir podcast offline
    Given que um usuário logado concluiu o download do arquivo bruto do "Episódio 1"
    And o seu dispositivo está desconectado da internet
    When ele tenta reproduzir o arquivo baixado localmente
    Then o áudio deve ser executado normalmente
