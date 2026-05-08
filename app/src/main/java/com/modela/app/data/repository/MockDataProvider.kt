package com.modela.app.data.repository

import com.modela.app.data.model.*

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

    fun getProposals(): List<Proposal> = listOf(
        Proposal(
            id = "p1",
            modelName = "Adara",
            modelImageUrl = "file:///android_asset/amigos/adara1.jpeg",
            companyName = "Vogue Brasil",
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
            companyName = "Studio Luxe",
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
            companyName = "Fashion Week SC",
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
            companyName = "Beleza Natural Co.",
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
            companyName = "Atelier Couture",
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
