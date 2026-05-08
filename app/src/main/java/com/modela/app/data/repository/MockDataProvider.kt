package com.modela.app.data.repository

import com.modela.app.data.model.*
import com.modela.app.data.model.JobTag
import com.modela.app.data.model.JobTagType

object MockDataProvider {

    fun getCategories(): List<Category> = listOf(
        Category("1", "Moda"), Category("2", "Comercial"),
        Category("3", "Editorial"), Category("4", "Passarela"), Category("5", "Fitness"),
        Category("6", "Glamour"), Category("7", "Plus Size"), Category("8", "Partes do Corpo")
    )

    fun getFeaturedModels(): List<ModelProfile> = listOf(

        ModelProfile(
            "1",
            "Adara",
            "file:///android_asset/amigos/adara1.jpeg",
            "Moda",
            "Modelo internacional de moda com 8 anos de experiência em alta-costura e trabalhos editoriais.",
            5.0f,
            156,
            12400,
            "1,78 m",
            "58 kg",
            "Verde",
            "Loiro",
            "86 cm",
            "61 cm",
            "89 cm",
            "37",
            listOf("file:///android_asset/amigos/adara2.jpeg"),
            mapOf("instagram" to "@sophialaurent"),
            false,
            "Blumenau, SC"
        ),

        ModelProfile(
            "2",
            "Isaac",
            "file:///android_asset/amigos/isaac1.jpeg",
            "Editorial",
            "Modelo editorial premiada, destaque em grandes revistas de moda.",
            5.0f,
            203,
            18900,
            "1,75 m",
            "57 kg",
            "Castanho",
            "Castanho Escuro",
            "84 cm",
            "59 cm",
            "87 cm",
            "36",
            listOf("file:///android_asset/amigos/isaac2.jpeg"),
            mapOf("instagram" to "@isabellacruz"),
            false,
            "Balneário Camboriú, SC"
        ),

        ModelProfile(
            "3",
            "Sofia",
            "file:///android_asset/amigos/sofia1.jpeg",
            "Passarela",
            "Modelo de passarela com experiência em eventos de moda nacionais e internacionais.",
            5.0f,
            89,
            8700,
            "1,80 m",
            "59 kg",
            "Castanho",
            "Preto",
            "86 cm",
            "63 cm",
            "91 cm",
            "39",
            listOf("file:///android_asset/amigos/sofia2.jpeg"),
            mapOf("instagram" to "@amaraokafor"),
            false,
            "Itajaí, SC"
        ),

        ModelProfile(
            "4",
            "Giovana",
            "file:///android_asset/amigos/giovana1.jpeg",
            "Comercial",
            "Modelo comercial versátil especializada em beleza, lifestyle e marcas premium.",
            5.0f,
            178,
            15200,
            "1,73 m",
            "55 kg",
            "Azul",
            "Ruivo",
            "81 cm",
            "61 cm",
            "86 cm",
            "35",
            listOf(),
            mapOf("instagram" to "@valentinarossi"),
            false,
            "Brusque, SC"
        ),

        ModelProfile(
            "5",
            "Mei Lin",
            "file:///android_asset/amigos/isaac2.jpeg",
            "Glamour",
            "Modelo glamour reconhecida pelo estilo sofisticado e elegante.",
            5.0f,
            134,
            21300,
            "1,70 m",
            "53 kg",
            "Castanho Escuro",
            "Preto",
            "84 cm",
            "59 cm",
            "84 cm",
            "34",
            listOf(),
            mapOf("instagram" to "@meilin"),
            false,
            "Indaial, SC"
        )
    )

    fun getTrendingModels(): List<ModelProfile> = listOf(

        ModelProfile(
            "6",
            "Aria Petrova",
            "file:///android_asset/amigos/sofia2.jpeg",
            "Fitness",
            "Modelo fitness e defensora do bem-estar, especializada em campanhas esportivas.",
            4.5f,
            67,
            5400,
            "1,73 m",
            "61 kg",
            "Cinza",
            "Platinado",
            "89 cm",
            "66 cm",
            "91 cm",
            "37",
            location = "Pomerode, SC"
        ),

        ModelProfile(
            "7",
            "Luna Delgado",
            "file:///android_asset/amigos/adara2.jpeg",
            "Moda",
            "Modelo em ascensão na cena fashion brasileira com estilo editorial marcante.",
            4.4f,
            42,
            3800,
            "1,75 m",
            "56 kg",
            "Avelã",
            "Morena",
            "84 cm",
            "61 cm",
            "89 cm",
            "36",
            location = "Gaspar, SC"
        ),

        ModelProfile(
            "8",
            "Freya Jensen",
            "",
            "Editorial",
            "Modelo editorial especializada em campanhas de moda premium.",
            4.6f,
            58,
            4200,
            "1,78 m",
            "57 kg",
            "Azul",
            "Loiro Avermelhado",
            "84 cm",
            "59 cm",
            "86 cm",
            "38",
            location = "Timbó, SC"
        ),

        ModelProfile(
            "9",
            "Zara Khan",
            "",
            "Passarela",
            "Presença forte nas passarelas e experiência em grandes campanhas.",
            4.3f,
            73,
            6100,
            "1,80 m",
            "60 kg",
            "Castanho",
            "Preto",
            "86 cm",
            "63 cm",
            "91 cm",
            "39",
            location = "Jaraguá do Sul, SC"
        ),

        ModelProfile(
            "10",
            "Chloe Park",
            "",
            "Comercial",
            "Modelo versátil com destaque em campanhas de beleza e tecnologia.",
            4.5f,
            91,
            7800,
            "1,70 m",
            "54 kg",
            "Castanho Escuro",
            "Castanho",
            "81 cm",
            "59 cm",
            "84 cm",
            "35",
            location = "Rio do Sul, SC"
        )
    )
    fun getRecommendedModels(): List<ModelProfile> = getFeaturedModels().shuffled()

    fun getConversations(): List<ChatConversation> = listOf(
        ChatConversation(
            "c1",
            "Sophia Laurent",
            "",
            "Oi! Gostaria de conversar sobre os detalhes do próximo ensaio. Qual seria um bom horário?",
            System.currentTimeMillis() - 300_000,
            2
        ),

        ChatConversation(
            "c2",
            "Isabella Cruz",
            "",
            "Obrigada por me considerar para o projeto! Tenho muito interesse.",
            System.currentTimeMillis() - 3_600_000,
            0
        ),

        ChatConversation(
            "c3",
            "Amara Okafor",
            "",
            "O contrato parece ótimo. Vou revisar e retorno ainda hoje.",
            System.currentTimeMillis() - 7_200_000,
            1
        ),

        ChatConversation(
            "c4",
            "Valentina Rossi",
            "",
            "Ansiosa pela nossa colaboração! Nos vemos no estúdio.",
            System.currentTimeMillis() - 86_400_000,
            0
        )
    )

    fun getMessages(conversationId: String): List<ChatMessage> {
        val now = System.currentTimeMillis()

        return listOf(
            ChatMessage(
                "m1",
                conversationId,
                "other",
                "Oi! Obrigada por entrar em contato.",
                now - 7_200_000,
                false
            ),

            ChatMessage(
                "m2",
                conversationId,
                "me",
                "Olá! Adorei seu portfólio. Você teria disponibilidade para um ensaio de moda no próximo mês?",
                now - 7_100_000,
                true
            ),

            ChatMessage(
                "m3",
                conversationId,
                "other",
                "Parece incrível! Gostaria de saber mais detalhes sobre o projeto.",
                now - 6_000_000,
                false
            ),

            ChatMessage(
                "m4",
                conversationId,
                "me",
                "É uma campanha para uma marca de luxo. Sessão de 3 dias em Milão. Vamos cobrir viagem e hospedagem.",
                now - 5_900_000,
                true
            ),

            ChatMessage(
                "m5",
                conversationId,
                "other",
                "Milão parece maravilhoso! Quais datas vocês estão considerando?",
                now - 3_600_000,
                false
            ),

            ChatMessage(
                "m6",
                conversationId,
                "me",
                "Estamos planejando entre os dias 15 e 17 de março. Funciona para você?",
                now - 3_500_000,
                true
            ),

            ChatMessage(
                "m7",
                conversationId,
                "other",
                "Vou verificar minha agenda e te retorno em breve!",
                now - 1_800_000,
                false
            )
        )
    }

    fun getReviews(): List<Review> = listOf(
        Review(
            "r1",
            "James Rivera",
            "",
            5f,
            "Absolutamente profissional! Sophia superou nossas expectativas durante toda a campanha. Sua versatilidade é incomparável.",
            System.currentTimeMillis() - 2_592_000_000
        ),

        Review(
            "r2",
            "Elena Vasquez",
            "",
            4.5f,
            "Foi maravilhoso trabalhar com ela. Ótima energia no set e muito colaborativa com a direção criativa.",
            System.currentTimeMillis() - 5_184_000_000
        ),

        Review(
            "r3",
            "David Chen",
            "",
            5f,
            "Uma das melhores modelos que já contratamos. Portfólio incrível e extremamente pontual.",
            System.currentTimeMillis() - 7_776_000_000
        )
    )
    fun getModelById(id: String): ModelProfile? {
        return (getFeaturedModels() + getTrendingModels()).find { it.id == id }
    }

    fun getJobOpenings(): List<JobOpening> = listOf(
        JobOpening(
            id = "j1",
            companyName = "Veyra Editorial",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/editorial_summer.jpg",
            companyLocation = "São Paulo, SP",
            jobTitle = "Ensaio Editorial Verão 2026",
            jobDescription = "Buscamos modelo para ensaio fotográfico editorial da coleção verão 2026. Produção completa incluída.",
            fullDescription = "Estamos selecionando modelos para o ensaio editorial da nossa próxima edição de moda — coleção Verão 2026. O trabalho inclui 2 dias de produção em estúdio profissional em São Paulo, com equipe completa de styling, maquiagem e fotografia. As fotos serão publicadas na edição impressa e digital da Veyra Editorial.\n\nO pagamento é feito via plataforma Modela com garantia de segurança escrow.",
            category = "Editorial",
            location = "Balneário Camboriú, SC",
            date = "15 Jun 2026",
            budget = "R$ 3.500",
            tags = listOf(
                JobTag("Urgente", JobTagType.URGENT),
                JobTag("Editorial", JobTagType.CATEGORY),
                JobTag("Verão", JobTagType.SEASON)
            ),
            requirements = listOf("Altura mínima 1,73m", "Experiência editorial", "Disponibilidade integral 2 dias"),
            postedAt = System.currentTimeMillis() - 7_200_000,
            applicants = 12
        ),
        JobOpening(
            id = "j2",
            companyName = "Luma Studio",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/winter_studio.jpg",
            companyLocation = "Blumenau, SC",
            jobTitle = "Campanha Moda de Inverno",
            jobDescription = "Campanha fotográfica para coleção de inverno de marca premium. 2 dias em estúdio profissional.",
            fullDescription = "Luma Studio está produzindo campanha da coleção Inverno 2026 para marca de moda premium nacional. Procuramos perfis sofisticados e versáteis para representar a identidade da marca.\n\nA produção acontece em nosso estúdio em Blumenau, com equipe completa. Todas as peças são cedidas durante as gravações.",
            category = "Comercial",
            location = "Blumenau, SC",
            date = "22 Jun 2026",
            budget = "R$ 5.000",
            tags = listOf(
                JobTag("Moda de Inverno", JobTagType.SEASON),
                JobTag("Exclusivo", JobTagType.EXCLUSIVE),
                JobTag("Comercial", JobTagType.CATEGORY)
            ),
            requirements = listOf("Perfil versátil", "Portfolio atualizado", "Disponibilidade 2 dias"),
            postedAt = System.currentTimeMillis() - 3_600_000,
            applicants = 8,
            isRemote = false
        ),
        JobOpening(
            id = "j3",
            companyName = "Aurora Runway",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/runway_show.jpg",
            companyLocation = "Itajaí, SC",
            jobTitle = "Desfile Coleção Inverno — Passarela",
            jobDescription = "Seleção de modelos para desfile da coleção de inverno no Aurora Runway 2026. Ensaio e passarela.",
            fullDescription = "Aurora Runway seleciona modelos para o grande desfile de apresentação da coleção de Inverno 2026. O evento acontece no Centro de Convenções de Itajaí e reúne marcas independentes do estado.\n\nO casting inclui ensaio técnico 2 dias antes do evento. Todas as passagens e hospedagem fora de Itajaí serão custeadas pela produção.",
            category = "Passarela",
            location = "Itajaí, SC",
            date = "10 Jul 2026",
            budget = "R$ 2.800",
            tags = listOf(
                JobTag("Novo", JobTagType.NEW),
                JobTag("Passarela", JobTagType.CATEGORY),
                JobTag("Inverno", JobTagType.SEASON)
            ),
            requirements = listOf("Altura mínima 1,75m", "Experiência em passarela", "Disponível 10-12 Jul"),
            postedAt = System.currentTimeMillis() - 1_800_000,
            applicants = 23
        ),
        JobOpening(
            id = "j4",
            companyName = "Botanika Lab",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/skincare_beauty.jpg",
            companyLocation = "Brusque, SC",
            jobTitle = "Campanha Skincare — Botanika",
            jobDescription = "Ensaio para lançamento de linha de produtos de beleza natural e sustentável. Campanha digital e impressa.",
            fullDescription = "Botanika Lab lança sua nova linha de skincare sustentável e busca rostos autênticos para representar a campanha. Valorizamos diversidade e beleza real.\n\nO trabalho inclui sessão fotográfica de 1 dia e gravação de vídeos curtos para redes sociais. Todo o material produzido terá crédito da modelo.",
            category = "Comercial",
            location = "Brusque, SC",
            date = "05 Jul 2026",
            budget = "R$ 4.200",
            tags = listOf(
                JobTag("Exclusivo", JobTagType.EXCLUSIVE),
                JobTag("Beleza", JobTagType.CATEGORY),
                JobTag("Urgente", JobTagType.URGENT)
            ),
            requirements = listOf("Perfil natural/espontâneo", "Sem restrições de medidas", "Experiência com produtos"),
            postedAt = System.currentTimeMillis() - 86_400_000,
            applicants = 31
        ),
        JobOpening(
            id = "j5",
            companyName = "Altura Atelier",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/couture_lookbook.jpg",
            companyLocation = "Florianópolis, SC",
            jobTitle = "Lookbook Alta-Costura — Coleção Exclusiva",
            jobDescription = "Produção de lookbook exclusivo para nova coleção de alta-costura. Locação externa em Florianópolis.",
            fullDescription = "Altura Atelier apresenta sua nova coleção de alta-costura e convida modelos para um lookbook editorial em locações externas exclusivas em Florianópolis.\n\nO projeto tem 3 dias de produção em pontos icônicos da cidade. O fotógrafo é internacionalmente reconhecido e o material será licenciado para publicações internacionais.",
            category = "Moda",
            location = "Florianópolis, SC",
            date = "20 Jul 2026",
            budget = "R$ 6.000",
            tags = listOf(
                JobTag("Alta Costura", JobTagType.EXCLUSIVE),
                JobTag("Novo", JobTagType.NEW),
                JobTag("Moda", JobTagType.CATEGORY)
            ),
            requirements = listOf("Experiência editorial obrigatória", "Altura 1,75m+", "Portfolio profissional"),
            postedAt = System.currentTimeMillis() - 172_800_000,
            applicants = 5
        ),
        JobOpening(
            id = "j6",
            companyName = "Pulse Forma",
            companyImageUrl = "",
            campaignImageUrl = "file:///android_asset/campaigns/fitness_wellness.jpg",
            companyLocation = "Joinville, SC",
            jobTitle = "Ensaio Fitness & Wellness",
            jobDescription = "Ensaio para capa e editorial interno de revista de lifestyle saudável. Ambiente externo ao ar livre.",
            fullDescription = "Pulse Forma está produzindo sua edição especial de Inverno 2026 com foco em lifestyle e wellness. Buscamos modelos com perfil fitness para ensaio externo em parques e espaços naturais de Joinville.\n\nA edição será distribuída em todo o território nacional e terá versão digital com alcance internacional.",
            category = "Fitness",
            location = "Joinville, SC",
            date = "18 Jun 2026",
            budget = "R$ 2.200",
            tags = listOf(
                JobTag("Fitness", JobTagType.CATEGORY),
                JobTag("Inverno", JobTagType.SEASON),
                JobTag("Novo", JobTagType.NEW)
            ),
            requirements = listOf("Perfil fitness/esportivo", "Disponível meio período", "Experiência outdoor"),
            postedAt = System.currentTimeMillis() - 259_200_000,
            applicants = 17
        )
    )

    fun getCompanyCampaigns(): List<CompanyCampaign> = listOf(
        CompanyCampaign(
            id = "c1",
            title = "Editorial Verão 2026",
            type = "Campanha",
            coverImageUrl = "file:///android_asset/campaigns/editorial_summer.jpg",
            description = "Produção editorial para catálogo e social media, com estética limpa e foco em moda premium.",
            location = "São Paulo, SP",
            date = "15 Jun 2026",
            budget = "R$ 3.500",
            status = "Recebendo perfis",
            tags = listOf("Editorial", "Urgente", "Verão"),
            applicants = 12
        ),
        CompanyCampaign(
            id = "c2",
            title = "Casting Skincare Natural",
            type = "Casting",
            coverImageUrl = "file:///android_asset/campaigns/skincare_beauty.jpg",
            description = "Seleção de rostos para campanha de skincare com proposta natural e diversidade de perfis.",
            location = "Brusque, SC",
            date = "05 Jul 2026",
            budget = "R$ 4.200",
            status = "Triagem",
            tags = listOf("Beleza", "Comercial", "Premium"),
            applicants = 31
        ),
        CompanyCampaign(
            id = "c3",
            title = "Lookbook Alta-Costura",
            type = "Job",
            coverImageUrl = "file:///android_asset/campaigns/couture_lookbook.jpg",
            description = "Lookbook externo para coleção autoral, com direção de arte sofisticada e peças de alto impacto.",
            location = "Florianópolis, SC",
            date = "20 Jul 2026",
            budget = "R$ 6.000",
            status = "Publicado",
            tags = listOf("Moda", "Premium", "Editorial"),
            applicants = 5
        )
    )

    fun getProposals(): List<Proposal> = listOf(
        Proposal(
            id = "p1",
            modelName = "Adara",
            modelImageUrl = "file:///android_asset/amigos/adara1.jpeg",
            companyName = "Veyra Editorial",
            companyImageUrl = "",
            jobTitle = "Ensaio Editorial Verão 2026",
            jobDescription = "Sessão fotográfica para coleção de verão. Produção completa com maquiagem e styling inclusos.",
            category = "Editorial",
            location = "Balneário Camboriú, SC",
            date = "15 Jun 2026",
            budget = "R$ 3.500",
            status = ProposalStatus.PENDING
        ),
        Proposal(
            id = "p2",
            modelName = "Isaac",
            modelImageUrl = "file:///android_asset/amigos/isaac1.jpeg",
            companyName = "Luma Studio",
            companyImageUrl = "",
            jobTitle = "Campanha Publicitária - Marca Premium",
            jobDescription = "Campanha fotográfica para marca de luxo. 2 dias de produção em estúdio profissional.",
            category = "Comercial",
            location = "Blumenau, SC",
            date = "22 Jun 2026",
            budget = "R$ 5.000",
            status = ProposalStatus.ACCEPTED
        ),
        Proposal(
            id = "p3",
            modelName = "Sofia",
            modelImageUrl = "file:///android_asset/amigos/sofia1.jpeg",
            companyName = "Aurora Runway",
            companyImageUrl = "",
            jobTitle = "Desfile Coleção Inverno",
            jobDescription = "Desfile de moda para apresentação da coleção de inverno. Ensaio e passarela.",
            category = "Passarela",
            location = "Itajaí, SC",
            date = "10 Jul 2026",
            budget = "R$ 2.800",
            status = ProposalStatus.PENDING
        ),
        Proposal(
            id = "p4",
            modelName = "Giovana",
            modelImageUrl = "file:///android_asset/amigos/giovana1.jpeg",
            companyName = "Botanika Lab",
            companyImageUrl = "",
            jobTitle = "Campanha Skincare",
            jobDescription = "Ensaio para lançamento de linha de produtos de beleza natural e sustentável.",
            category = "Comercial",
            location = "Brusque, SC",
            date = "05 Jul 2026",
            budget = "R$ 4.200",
            status = ProposalStatus.COMPLETED
        ),
        Proposal(
            id = "p5",
            modelName = "Adara",
            modelImageUrl = "file:///android_asset/amigos/adara1.jpeg",
            companyName = "Altura Atelier",
            companyImageUrl = "",
            jobTitle = "Lookbook Alta-Costura",
            jobDescription = "Produção de lookbook exclusivo para nova coleção de alta-costura. Locação externa.",
            category = "Moda",
            location = "Florianópolis, SC",
            date = "20 Jul 2026",
            budget = "R$ 6.000",
            status = ProposalStatus.PENDING
        )
    )
}
