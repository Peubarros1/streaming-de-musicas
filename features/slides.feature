# Usuário(Gledson)

#Playlists(Peuba)

#Artistas(Analia)

#Podcasts(Guerra)

# Recomendações e Busca por Filtros(Théo)
Cenário: Exibição da página inicial para usuário logado
Given estou logado como “Usuário” com login “LuisCardoso012” e senha “1234”
When acesso a página inicial
Then posso ver o campo de busca
And posso ver a seção "Músicas em Alta"
And posso ver a seção "Podcasts em Alta"
And posso ver meu histórico de músicas ouvidas recentemente
And posso ver minhas playlists
And posso ver um ícone 
And vejo uma mensagem na parte de cima da tela: “Ola, LuisCardoso012!”
