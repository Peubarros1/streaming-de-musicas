Feature: Recuperação de conta via E-mail/ Esqueci a senha
As a usuário
I want to recuperar minha conta caso eu esquecer a senha
So that eu possa usar a minha conta novamente

Scenario: Solicitar recuperação de conta com e-mail válido
Given eu tenho uma conta cadastrada com o e-mail "abc@gmail.com"
And eu estou na página de "Login"
When eu clico em "Esqueci a senha"
And eu preencho o campo "Email" com "abc@gmail.com"
Then o sistema deve enviar um e-mail com instruções para redefinição de senha
And eu devo ver uma mensagem na tela informando que um e-mail foi enviado para o e-mail informado