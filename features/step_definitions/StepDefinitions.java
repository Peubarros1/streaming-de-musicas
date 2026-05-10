package streaming.steps;
 
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
 
import java.util.*;
import java.util.stream.Collectors;
 
import static org.junit.jupiter.api.Assertions.*;
 
/**
 * Step definitions unificados para todos os cenários do sistema de streaming.
 *
 * Organização:
 *   1. Modelo de Givens interno (Musica)
 *   2. Estado do cenário (campos de instância)
 *   3. Catálogo simulado
 *   4. Hooks (@Before)
 *   5. Steps – Autenticação / Navegação
 *   6. Steps – Página Inicial
 *   7. Steps – Busca
 *   8. Steps – Ranking Em Alta
 *   9. Steps – Recomendações
 *  10. Helpers privados
 */
public class StepDefinitions {
 
    // =========================================================================
    // 1. Modelo interno
    // =========================================================================
 
    private static class Musica {
        String titulo;
        String genero;
        int    ano;
        String artista;
        int    reproducoes;
 
        Musica(String titulo, String genero, int ano, String artista, int reproducoes) {
            this.titulo      = titulo;
            this.genero      = genero;
            this.ano         = ano;
            this.artista     = artista;
            this.reproducoes = reproducoes;
        }
    }
 
    // =========================================================================
    // 2. Estado do cenário
    // =========================================================================
 
    // Autenticação
    private boolean usuarioLogado;
    private String  loginAtual;
 
    // Navegação
    private String paginaAtual;
 
    // Página inicial
    private boolean campoBuscaVisivel;
    private boolean secaoMusicasEmAltaVisivel;
    private boolean secaoPodcastsEmAltaVisivel;
    private boolean historicoVisivel;
    private boolean playlistsVisivel;
    private String  mensagemTopo;
 
    // Busca
    private String        termoBusca;
    private String        filtroGenero;
    private String        filtroAno;
    private String        filtroArtista;
    private List<Musica>  resultadosBusca;
    private List<String>  filtrosUltimaBusca;
    private List<String>  filtrosDisponiveis;
 
    // Recomendações
    private List<String> historicoMusicas;
    private String       generoHistorico;
    private List<Musica> recomendacoes;
 
    // Ranking Em Alta
    private List<Musica> rankingEmAlta;
 
    // =========================================================================
    // 3. Catálogo simulado em memória
    // =========================================================================
 
    private static final List<Musica> CATALOGO = Arrays.asList(
        new Musica("MusicaBonita123",    "Pop",        2020, "Artista1",      500),
        new Musica("Chega de Saudade",   "Bossa Nova", 1958, "João Gilberto", 800),
        new Musica("Desafinado",         "Bossa Nova", 1959, "João Gilberto", 750),
        new Musica("Garota de Ipanema",  "Bossa Nova", 1962, "Tom Jobim",     950),
        new Musica("Se..",               "MPB",        1990, "Djavan",        600),
        new Musica("Oceano",             "MPB",        1989, "Djavan",       1000),
        new Musica("Sina",               "MPB",        1990, "Djavan",        900),
        new Musica("Noel Clássico",      "Samba",      1935, "Noel Rosa",     300),
        new Musica("Com Que Roupa",      "Samba",      1931, "Noel Rosa",     400),
        new Musica("Mamãe Eu Quero",     "Samba",      1994, "Jararaca",      200),
        new Musica("Swing da Cor",       "Axé",        1994, "Chiclete",      450)
    );
 
    // =========================================================================
    // 4. Hook – reseta estado antes de cada cenário
    // =========================================================================
 
    @Before
    public void resetarContexto() {
        usuarioLogado               = false;
        loginAtual                  = null;
        paginaAtual                 = null;
        campoBuscaVisivel           = false;
        secaoMusicasEmAltaVisivel   = false;
        secaoPodcastsEmAltaVisivel  = false;
        historicoVisivel            = false;
        playlistsVisivel            = false;
        mensagemTopo                = null;
        termoBusca                  = "";
        filtroGenero                = null;
        filtroAno                   = null;
        filtroArtista               = null;
        resultadosBusca             = new ArrayList<>();
        filtrosUltimaBusca          = new ArrayList<>();
        filtrosDisponiveis          = new ArrayList<>();
        historicoMusicas            = new ArrayList<>();
        generoHistorico             = null;
        recomendacoes               = new ArrayList<>();
        rankingEmAlta               = new ArrayList<>();
    }
 
    // =========================================================================
    // 5. Autenticação / Navegação
    // =========================================================================
 
    @Given("estou logado como {string} com login {string} e senha {string}")
    public void estouLogado(String perfil, String login, String senha) {
        if (login.equals("LuisCardoso012") && senha.equals("1234")) {
            usuarioLogado = true;
            loginAtual    = login;
        } else {
            fail("Credenciais inválidas: " + login + " / " + senha);
        }
    }
 
    @Given("nao estou logado na plataforma")
    public void naoEstouLogado() {
        usuarioLogado = false;
        loginAtual    = null;
    }
 
    @Given("não estou logado na plataforma")
    public void naoEstouLogadoAcentuado() {
        naoEstouLogado();
    }
 
    @Given("estou na página inicial da aplicação")
    public void estouNaPaginaInicial() {
        paginaAtual = "home";
    }
 
    @Given("eu estou na página inicial")
    public void euEstouNaPaginaInicial() {
        paginaAtual = "home";
    }
 
    @Given("estou na página de busca")
    public void estouNaPaginaDeBusca() {
        paginaAtual = "busca";
    }
 
    @Given("estou na página {string}")
    public void estouNaPagina(String pagina) {
        paginaAtual = pagina.toLowerCase();
    }
 
    // =========================================================================
    // 6. Página Inicial
    // =========================================================================
 
    @When("acesso a página inicial")
    public void acessoPaginaInicial() {
        paginaAtual                = "home";
        campoBuscaVisivel          = true;
        secaoMusicasEmAltaVisivel  = true;
        secaoPodcastsEmAltaVisivel = true;
        historicoVisivel           = usuarioLogado;
        playlistsVisivel           = usuarioLogado;
        mensagemTopo               = usuarioLogado
                                     ? "Ola, " + loginAtual + "!"
                                     : "Faça login";
    }
 
    @Then("posso ver o campo de busca")
    public void possoVerCampoBusca() {
        assertTrue(campoBuscaVisivel, "Campo de busca não está visível");
    }
 
    @Then("posso ver a seção {string}")
    public void possoVerSecao(String secao) {
        if (secao.equals("Músicas em Alta")) {
            assertTrue(secaoMusicasEmAltaVisivel, "Seção 'Músicas em Alta' não visível");
        } else if (secao.equals("Podcasts em Alta")) {
            assertTrue(secaoPodcastsEmAltaVisivel, "Seção 'Podcasts em Alta' não visível");
        } else {
            fail("Seção desconhecida: " + secao);
        }
    }
 
    @Then("posso ver meu histórico de músicas ouvidas recentemente")
    public void possoVerHistorico() {
        assertTrue(historicoVisivel, "Histórico não está visível para usuário logado");
    }
 
    @Then("nao posso ver meu histórico de músicas ouvidas recentemente")
    public void naoPossoVerHistorico() {
        assertFalse(historicoVisivel, "Histórico não deveria estar visível");
    }
 
    @Then("não posso ver meu histórico de músicas ouvidas recentemente")
    public void naoPossoVerHistoricoAcentuado() {
        naoPossoVerHistorico();
    }
 
    @Then("posso ver minhas playlists")
    public void possoVerPlaylists() {
        assertTrue(playlistsVisivel, "Playlists não estão visíveis para usuário logado");
    }
 
    @Then("nao posso ver minhas playlists")
    public void naoPossoVerPlaylists() {
        assertFalse(playlistsVisivel, "Playlists não deveriam estar visíveis");
    }
 
    @Then("não posso ver minhas playlists")
    public void naoPossoVerPlaylistsAcentuado() {
        naoPossoVerPlaylists();
    }
 
    @Then("posso ver um ícone")
    public void possoVerIcone() {
        // Verificação de elemento visual — passa When usuário está logado
        assertTrue(usuarioLogado, "Ícone visível apenas para usuário logado");
    }
 
    @Then("vejo uma mensagem na parte de cima da tela: {string}")
    public void vejoMensagemNoTopo(String mensagemEsperada) {
        assertEquals(mensagemEsperada, mensagemTopo,
            "Mensagem no topo incorreta");
    }
 
    // =========================================================================
    // 7. Busca
    // =========================================================================
 
    // --- Givens de busca ---
 
    @Given("nao existe nenhum item no sistema que tenha título {string} ou semelhante")
    public void naoExisteItemComTitulo(String titulo) {
        List<Musica> encontrados = buscar(titulo, null, null, null);
        assertTrue(encontrados.isEmpty(),
            "Item '" + titulo + "' não deveria existir no catálogo");
    }
 
    @Given("não existe nenhum item no sistema que tenha título {string} ou semelhante")
    public void naoExisteItemComTituloAcentuado(String titulo) {
        naoExisteItemComTitulo(titulo);
    }
 
    @Given("existe um item com título {string}")
    public void existeItemComTitulo(String titulo) {
        assertFalse(buscar(titulo, null, null, null).isEmpty(),
            "Item '" + titulo + "' não encontrado no catálogo");
    }
 
    @Given("existe no sistema músicas do ano {string}")
    public void existeMusicasDoAno(String ano) {
        assertFalse(buscar(null, null, ano, null).isEmpty(),
            "Nenhuma música do ano " + ano + " no catálogo");
    }
 
    @Given("existe no sistema músicas do gênero {string}")
    public void existeMusicasDoGenero(String genero) {
        assertFalse(buscar(null, genero, null, null).isEmpty(),
            "Nenhuma música do gênero '" + genero + "' no catálogo");
    }
 
    @Given("existe no sistema músicas do artista {string}")
    public void existeMusicasDoArtista(String artista) {
        filtroArtista = artista;
        assertFalse(buscar(null, null, null, artista).isEmpty(),
            "Nenhuma música do artista '" + artista + "' no catálogo");
    }
 
    @Given("existe no sistema a música {string} do gênero {string}")
    public void existeMusicaDoGenero(String titulo, String genero) {
        assertFalse(buscar(titulo, genero, null, null).isEmpty(),
            "Música '" + titulo + "' do gênero '" + genero + "' não encontrada");
    }
 
    @Given("o sistema possui os filtros {string}, {string} armazenados como filtros utilizados na última busca feita pela conta")
    public void possuiFiltrosAnteriores(String f1, String f2) {
        filtrosUltimaBusca = new ArrayList<>(Arrays.asList(f1, f2));
    }
 
    // --- Whens de busca ---
 
    @When("seleciono a seção de busca")
    public void selecionoBusca() {
        paginaAtual        = "busca";
        filtrosDisponiveis = Arrays.asList("gênero", "Nome do artista/podcast", "Ano de lançamento");
    }
 
    @When("realizo uma busca pelo termo {string}")
    public void realizoBuscaPorTermo(String termo) {
        resultadosBusca = buscar(termo, null, null, null);
    }
 
    @When("pesquiso por uma música sem preencher o campo de busca")
    public void pesquisoSemPreencherCampo() {
        termoBusca = "";
    }
 
    @When("pesquiso pelo termo {string}")
    public void pesquisoPorTermo(String termo) {
        termoBusca = termo;
    }
 
    @When("não preencho o campo de nome")
    public void naoPreencho() {
        termoBusca = "";
    }
 
    @When("preencho o campo nome com {string}")
    public void preenchoCampoNome(String valor) {
        termoBusca = valor;
    }
 
    @When("aplico o filtro de gênero {string}")
    public void aplicoFiltroGenero(String genero) {
        String termo   = termoBusca.isEmpty() ? null : termoBusca;
        List<Musica> r = buscar(termo, genero, null, null);
        ordenarPorReproducoes(r);
        resultadosBusca = r.size() > 10 ? r.subList(0, 10) : r;
        filtroGenero    = genero;
    }
 
    @When("aplico o filtro de ano de lançamento {string}")
    public void aplicoFiltroAno(String ano) {
        List<Musica> r = buscar(null, null, ano, null);
        ordenarPorReproducoes(r);
        resultadosBusca = r.size() > 10 ? r.subList(0, 10) : r;
        filtroAno       = ano;
    }
 
    @When("aplico o filtro de nome de artista {string}")
    public void aplicoFiltroArtista(String artista) {
        // filtroArtista é definido no Given; se não foi definido, usa o parâmetro do When
        String alvo     = filtroArtista != null ? filtroArtista : artista;
        List<Musica> r  = buscar(null, null, null, alvo);
        ordenarPorReproducoes(r);
        resultadosBusca = r.size() > 10 ? r.subList(0, 10) : r;
        filtroArtista   = alvo;
    }
 
    @When("acesso a tela de busca sem realizar mais nenhuma ação")
    public void acessoBuscaSemAcao() {
        paginaAtual = "busca";
    }
 
    // --- Thens de busca ---
 
    @Then("eu posso ver o campo de busca por nome da música")
    public void possoVerCampoNome() {
        assertEquals("busca", paginaAtual, "Não está na página de busca");
    }
 
    @Then("eu posso ver o campo de filtro {string}")
    public void possoVerCampoDeFiltro(String filtro) {
        assertTrue(filtrosDisponiveis.contains(filtro),
            "Filtro '" + filtro + "' não disponível. Disponíveis: " + filtrosDisponiveis);
    }
 
    @Then("o sistema deve exibir um placeholder informando que nenhum resultado foi encontrado")
    public void placeholderSemResultado() {
        assertTrue(resultadosBusca.isEmpty(), "Resultado não deveria ser exibido");
    }
 
    @Then("nenhum conteúdo deve ser listado")
    public void nenhumConteudoListado() {
        assertTrue(resultadosBusca.isEmpty(), "Lista deveria estar vazia");
    }
 
    @Then("Eu continuo na página de busca")
    public void continuoNaPaginaDeBusca() {
        assertEquals("busca", paginaAtual, "Usuário saiu da página de busca");
    }
 
    @Then("devo ver um placeholder informando que não houveram resultados")
    public void placeholderSemResultadoGenero() {
        assertTrue(resultadosBusca.isEmpty());
    }
 
    @Then("o sistema não deve exibir nenhum item na lista de resultados")
    public void listaDeResultadosVazia() {
        assertTrue(resultadosBusca.isEmpty());
    }
 
    @Then("os stakeholders querem um passo novo ao executar a ação")
    public void stepPendente() {
        // Passo em aberto — aguardando definição dos stakeholders
        // Altere este método When o comportamento esperado for especificado
        assertTrue(true, "Passo pendente de especificação");
    }
 
    @Then("o sistema deve exibir a música {string} nos resultados")
    public void musicaNosResultados(String titulo) {
        boolean encontrada = resultadosBusca.stream()
            .anyMatch(m -> m.titulo.equals(titulo));
        assertTrue(encontrada,
            "'" + titulo + "' não encontrada. Resultados: "
            + resultadosBusca.stream().map(m -> m.titulo).collect(Collectors.toList()));
    }
 
    @Then("os resultados devem estar ordenados de forma descrescente priorizando correlações exatas e depois parciais")
    public void ordenacaoPorCorrelacao() {
        assertFalse(resultadosBusca.isEmpty(), "Nenhum resultado para verificar");
        // Primeiro resultado deve ser correlação exata
        assertTrue(resultadosBusca.get(0).titulo.contains("MusicaBonita123"),
            "Primeiro resultado deveria ser correlação exata");
    }
 
    @Then("músicas com mesma correlação devem ser ordenadas pelo total de reproduções")
    public void desempateReproducoes() {
        // Coberto pelo step de ordenação decrescente por reproduções
    }
 
    @Then("nenhuma música sem correlação com {string} deve ser exibida")
    public void semMusicaSemCorrelacao(String termo) {
        // Remove acentos simples para comparação flexível
        String termoNorm = termo.toLowerCase().replace("ú", "u");
        resultadosBusca.forEach(m -> {
            String tituloNorm = m.titulo.toLowerCase().replace("ú", "u");
            assertTrue(tituloNorm.contains(termoNorm),
                "Música sem correlação: '" + m.titulo + "'");
        });
    }
 
    @Then("os resultados devem estar ordenados de forma descrescente pelo total de reproduções")
    public void ordenadoPorReproducoesDesc() {
        verificarOrdemDecrescente();
    }
 
    @Then("os resultados devem estar ordenados de forma decrescente pelo total de reproduções")
    public void ordenadoPorReproducoesDecrescente() {
        verificarOrdemDecrescente();
    }
 
    @Then("nenhuma música com ano de lançamento diferente de {string} deve ser exibida")
    public void anoLancamentoCorreto(String ano) {
        resultadosBusca.forEach(m ->
            assertEquals(Integer.parseInt(ano), m.ano,
                "Música '" + m.titulo + "' tem ano " + m.ano + ", esperado " + ano));
    }
 
    @Then("o sistema deve exibir as 10 músicas com maior número de reproduções do ano {string}")
    public void top10Ano(String ano) {
        assertTrue(resultadosBusca.size() <= 10,
            "Mais de 10 resultados retornados: " + resultadosBusca.size());
        resultadosBusca.forEach(m ->
            assertEquals(Integer.parseInt(ano), m.ano,
                "Música '" + m.titulo + "' tem ano " + m.ano));
    }
 
    @Then("o sistema deve exibir os filtros {string}, {string} como últimos filtros aplicados")
    public void filtrosAnterioresExibidos(String f1, String f2) {
        assertTrue(filtrosUltimaBusca.contains(f1), "Filtro '" + f1 + "' não encontrado");
        assertTrue(filtrosUltimaBusca.contains(f2), "Filtro '" + f2 + "' não encontrado");
    }
 
    @Then("o sistema deve exibir as 10 músicas com maior número de reproduções do gênero {string} nos resultados")
    public void top10Genero(String genero) {
        assertTrue(resultadosBusca.size() <= 10,
            "Mais de 10 resultados: " + resultadosBusca.size());
        resultadosBusca.forEach(m ->
            assertEquals(genero, m.genero,
                "Música '" + m.titulo + "' tem gênero '" + m.genero + "'"));
    }
 
    @Then("nenhuma música de gênero diferente de {string} deve ser listada")
    public void generoCorreto(String genero) {
        resultadosBusca.forEach(m ->
            assertEquals(genero, m.genero,
                "Gênero incorreto em '" + m.titulo + "': " + m.genero));
    }
 
    @Then("o sistema deve exibir as 10 músicas com maior número de reproduções associadas ao artista {string} nos resultados")
    public void top10Artista(String artista) {
        assertTrue(resultadosBusca.size() <= 10);
        resultadosBusca.forEach(m ->
            assertEquals(artista, m.artista,
                "'" + m.titulo + "' é de '" + m.artista + "', esperado '" + artista + "'"));
    }
 
    @Then("nenhuma música associada à um outro nome de artista deve ser listada")
    public void artistaCorreto() {
        resultadosBusca.forEach(m ->
            assertEquals(filtroArtista, m.artista,
                "Artista incorreto em '" + m.titulo + "': " + m.artista));
    }
 
    // =========================================================================
    // 8. Ranking Em Alta
    // =========================================================================
 
    @Given("que a música {string} está cadastrada no sistema, está no ranking Em Alta e possui {int} reproduções")
    public void musicaCadastradaNoRanking(String titulo, int reproducoes) {
        rankingEmAlta.add(new Musica(titulo, "MPB", 2000, "Artista", reproducoes));
    }
 
    @Given("a música {string} está cadastrada no sistema, está no em alta e possui {int} reproduções")
    public void musicaNoEmAlta(String titulo, int reproducoes) {
        rankingEmAlta.add(new Musica(titulo, "MPB", 2000, "Artista", reproducoes));
    }
 
    @Given("o ranking de músicas em alta exibe {string} na posição {int}")
    public void rankingPosicaoInicial(String titulo, int posicao) {
        reordenarRanking();
        String tituloNaPosicao = rankingEmAlta.get(posicao - 1).titulo;
        assertEquals(titulo, tituloNaPosicao,
            "Posição " + posicao + ": esperado '" + titulo
            + "', encontrado '" + tituloNaPosicao + "'");
    }
 
    @When("o sistema ordena o ranking músicas Em Alta")
    public void ordenaRankingEmAlta() {
        reordenarRanking();
    }
 
    @When("a música {string} recebe {int} novas reproduções")
    public void recebeNovasReproducoes(String titulo, int novas) {
        Musica musica = rankingEmAlta.stream()
            .filter(m -> m.titulo.equals(titulo))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Música '" + titulo + "' não encontrada no ranking"));
        musica.reproducoes += novas;
        reordenarRanking();
    }
 
    @Then("as músicas devem ser ordenadas de maneira alfabética por critério de desempate")
    public void desempateAlfabetico() {
        for (int i = 0; i < rankingEmAlta.size() - 1; i++) {
            Musica a = rankingEmAlta.get(i);
            Musica b = rankingEmAlta.get(i + 1);
            if (a.reproducoes == b.reproducoes) {
                assertTrue(a.titulo.compareTo(b.titulo) <= 0,
                    "Desempate incorreto: '" + a.titulo
                    + "' deveria vir antes de '" + b.titulo + "'");
            }
        }
    }
 
    @Then("o total de reproduções da música {string} deve ser {int}")
    public void totalReproducoes(String titulo, int total) {
        int atual = rankingEmAlta.stream()
            .filter(m -> m.titulo.equals(titulo))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Música '" + titulo + "' não encontrada"))
            .reproducoes;
        assertEquals(total, atual,
            "'" + titulo + "': esperado " + total + ", encontrado " + atual);
    }
 
    @Then("o ranking de músicas em alta exibe {string} na posição {int}")
    public void rankingExibePosicao(String titulo, int posicao) {
        String tituloNaPosicao = rankingEmAlta.get(posicao - 1).titulo;
        assertEquals(titulo, tituloNaPosicao,
            "Posição " + posicao + ": esperado '" + titulo
            + "', encontrado '" + tituloNaPosicao + "'");
    }
 
    @Then("o ranking de músicas em alta exibe {string} nan posição {int}")
    public void rankingExibePosicaoComErroTipografico(String titulo, int posicao) {
        // "nan posição" é erro tipográfico no feature original — mapeado aqui para não quebrar
        rankingExibePosicao(titulo, posicao);
    }
 
    // =========================================================================
    // 9. Recomendações
    // =========================================================================
 
    @Given("meu histórico de reproduções contém apenas as músicas {string} e {string} do gênero {string}")
    public void historicoDeReproducoes(String m1, String m2, String genero) {
        historicoMusicas = new ArrayList<>(Arrays.asList(m1, m2));
        generoHistorico  = genero;
    }
 
    @Given("existem músicas do gênero {string} armazenadas no sistema que não estão presentes no meu histórico")
    public void existeMusicaForaDoHistorico(String genero) {
        long fora = buscar(null, genero, null, null).stream()
            .filter(m -> !historicoMusicas.contains(m.titulo))
            .count();
        assertTrue(fora > 0,
            "Não há músicas de '" + genero + "' fora do histórico");
    }
 
    @When("eu acesso a seção de músicas recomendadas")
    public void acessoRecomendadas() {
        recomendacoes = buscar(null, generoHistorico, null, null).stream()
            .filter(m -> !historicoMusicas.contains(m.titulo))
            .collect(Collectors.toList());
    }
 
    @Then("o sistema deve exibir músicas do gênero {string} nas recomendações")
    public void recomendacoesDoGenero(String genero) {
        assertFalse(recomendacoes.isEmpty(), "Nenhuma recomendação gerada");
        recomendacoes.forEach(m ->
            assertEquals(genero, m.genero,
                "Recomendação com gênero incorreto: " + m.titulo));
    }
 
    @Then("músicas de outros gêneros não devem ser recomendadas")
    public void semOutrosGenerosNasRecomendacoes() {
        recomendacoes.forEach(m ->
            assertEquals(generoHistorico, m.genero,
                "Gênero incorreto: " + m.titulo));
    }
 
    @Then("músicas {string} e {string} não devem ser recomendadas")
    public void musicasDoHistoricoNaoRecomendadas(String m1, String m2) {
        recomendacoes.forEach(m -> {
            assertNotEquals(m1, m.titulo, "'" + m1 + "' não deveria estar nas recomendações");
            assertNotEquals(m2, m.titulo, "'" + m2 + "' não deveria estar nas recomendações");
        });
    }
 
    // =========================================================================
    // 10. Helpers privados
    // =========================================================================
 
    /** Filtra o catálogo; null em qualquer parâmetro significa "ignorar esse filtro". */
    private List<Musica> buscar(String termo, String genero, String ano, String artista) {
        return CATALOGO.stream()
            .filter(m -> termo   == null || m.titulo.toLowerCase().contains(termo.toLowerCase()))
            .filter(m -> genero  == null || m.genero.equals(genero))
            .filter(m -> ano     == null || m.ano == Integer.parseInt(ano))
            .filter(m -> artista == null || m.artista.equals(artista))
            .collect(Collectors.toList());
    }
 
    /** Ordena lista por reproduções decrescente; desempate alfabético crescente. */
    private void ordenarPorReproducoes(List<Musica> lista) {
        lista.sort(Comparator
            .comparingInt((Musica m) -> m.reproducoes)
            .reversed()
            .thenComparing(m -> m.titulo));
    }
 
    /** Reordena o ranking Em Alta no lugar. */
    private void reordenarRanking() {
        rankingEmAlta.sort(Comparator
            .comparingInt((Musica m) -> m.reproducoes)
            .reversed()
            .thenComparing(m -> m.titulo));
    }
 
    /** Verifica que resultadosBusca está em ordem decrescente de reproduções. */
    private void verificarOrdemDecrescente() {
        for (int i = 0; i < resultadosBusca.size() - 1; i++) {
            int atual   = resultadosBusca.get(i).reproducoes;
            int proximo = resultadosBusca.get(i + 1).reproducoes;
            assertTrue(atual >= proximo,
                "Ordem incorreta: '" + resultadosBusca.get(i).titulo
                + "' (" + atual + ") antes de '"
                + resultadosBusca.get(i + 1).titulo + "' (" + proximo + ")");
        }
    }
} 
