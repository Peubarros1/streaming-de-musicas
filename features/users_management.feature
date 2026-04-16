Feature: Manutenção de usuários
As a administrador
I want to gerenciar usuários
So that eu possa manter as informações atualizadas no sistema

Scenario: Inserir um novo usuário a partir da área de manutenção
Given eu estou logado como administrador
And eu estou na página de "Gerenciamento de usuários"
When eu clico na opção "Adicionar usuário"
And eu preencho os dados do usuário com informações válidas
And eu clico em "Salvar"
Then o usuário deve ser cadastrado com sucesso
And ele deve aparecer na lista de usuários