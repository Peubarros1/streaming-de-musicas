Feature: Processamento de Áudio
Scenario: Upload de arquivo com formato inválido
Given que o serviço de upload recebeu um arquivo "aula_01.pdf"
When o validador de formato processar o arquivo
Then o sistema deve rejeitar o arquivo
And retornar o erro "Formato de arquivo não suportado. Utilize apenas MP3 ou WAV."

Feature: Download de Episódio
Scenario: Tentativa de download por usuário não autenticado
Given que um visitante não logado acessa a página do episódio "Episódio 1"
When o visitante clica no botão de "Baixar arquivo"
Then o sistema deve bloquear o download
And exibir a mensagem de erro "É necessário estar logado para baixar o podcast."