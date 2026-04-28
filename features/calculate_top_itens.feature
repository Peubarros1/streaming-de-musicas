Feature: Calcular podcasts, artistas e músicas mais escutados no mês
As a usuario
I want to saber quais os podcasts, artistas e músicas que eu mais escutei no mês
So that eu possa conhecer melhor sobre meus gostos musicais

Scenario Outline: Calcular os itens mais escutados no mês de cada categoria
Given existem registros de itens do tipo "<categoria>" do mês atual
And eu estou logado na minha conta
And eu estou na página de "Página inicial"
When o sistema calcula o ranking mensal
And eu clico em "Visualizar ranking mensal" 
Then o sistema deve retornar os itens do tipo "<categoria>" ordenados por quantidade de reproduções decrescente

Examples:
| categoria |
|  musicas  |
|  artistas |
|  podcasts |
|   gênero  |

Scenario: Retornar apenas o top 10 itens mais escutados de uma categoria
Given existem mais de 10 itens do tipo "Música" do mês atual
And eu estou logado na minha conta
And eu estou na página de "Página inicial"
When o sistema calcula o ranking mensal
And eu clico em "Visualizar top 10"
Then o sistema deve retornar apenas os 10 itens do tipo "Música" mais escutados 
And os itens devem estar ordenados por quantidade de reproduções decrescente