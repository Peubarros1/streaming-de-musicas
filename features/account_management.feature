Feature: Manutenção de conta
As a usuário
I want to ser capaz de atualizar ou desligar minha conta
So that eu possa gerenciar a minha conta

Scenario: Atualização de uma informação da conta
Given eu estou logado na minha conta
And a senha atual da minha conta é "Senhasupersecreta1!"
And eu estou na página de "Página inicial”
When eu clico no botão de "Atualizar conta"
And eu preencho o campo "Senha" com "Senhasupersecreta2!"
And eu clico em "Confirmar"
Then eu vejo uma mensagem na tela dizendo "A sua senha foi alterada com sucesso."
And eu devo conseguir autenticar com a nova senha