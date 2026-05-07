package com.modela.app.data.repository

import com.modela.app.data.model.*

object MockDataProvider {

    fun getCategories(): List<Category> = listOf(
        Category("1", "Fashion"), Category("2", "Commercial"), Category("3", "Editorial"),
        Category("4", "Runway"), Category("5", "Fitness"), Category("6", "Glamour"),
        Category("7", "Plus Size"), Category("8", "Parts")
    )

    fun getFeaturedModels(): List<ModelProfile> = listOf(
        ModelProfile("1","Sophia Laurent","","Fashion","International fashion model with 8 years of experience in haute couture and editorial work.",4.9f,156,12400,"5'10\"","128 lbs","Green","Blonde","34\"","24\"","35\"","8",listOf()  ,mapOf("instagram" to "@sophialaurent"),false,"Paris, France"),
        ModelProfile("2","Isabella Cruz","","Editorial","Award-winning editorial model featured in Vogue, Elle, and Harper's Bazaar.",4.8f,203,18900,"5'9\"","125 lbs","Brown","Dark Brown","33\"","23\"","34\"","7.5",listOf(),mapOf("instagram" to "@isabellacruz"),false,"New York, USA"),
        ModelProfile("3","Amara Okafor","","Runway","Top runway model with experience at Milan, Paris, and New York Fashion Weeks.",4.7f,89,8700,"5'11\"","130 lbs","Brown","Black","34\"","25\"","36\"","9",listOf(),mapOf("instagram" to "@amaraokafor"),false,"London, UK"),
        ModelProfile("4","Valentina Rossi","","Commercial","Versatile commercial model specializing in beauty, lifestyle, and luxury brands.",4.6f,178,15200,"5'8\"","122 lbs","Blue","Auburn","32\"","24\"","34\"","7",listOf(),mapOf("instagram" to "@valentinarossi"),false,"Milan, Italy"),
        ModelProfile("5","Mei Lin","","Glamour","Renowned glamour model with a refined, sophisticated aesthetic and global appeal.",4.9f,134,21300,"5'7\"","118 lbs","Dark Brown","Black","33\"","23\"","33\"","6.5",listOf(),mapOf("instagram" to "@meilin"),false,"Tokyo, Japan")
    )

    fun getTrendingModels(): List<ModelProfile> = listOf(
        ModelProfile("6","Aria Petrova","","Fitness","Certified fitness model and wellness advocate with a passion for health campaigns.",4.5f,67,5400,"5'8\"","135 lbs","Grey","Platinum","35\"","26\"","36\"","8",location = "Moscow, Russia"),
        ModelProfile("7","Luna Delgado","","Fashion","Rising star in the Latin American fashion scene with distinctive editorial style.",4.4f,42,3800,"5'9\"","124 lbs","Hazel","Brunette","33\"","24\"","35\"","7.5",location = "São Paulo, Brazil"),
        ModelProfile("8","Freya Jensen","","Editorial","Scandinavian model with ethereal beauty, specializing in high-end fashion editorials.",4.6f,58,4200,"5'10\"","126 lbs","Blue","Strawberry Blonde","33\"","23\"","34\"","8.5",location = "Copenhagen, Denmark"),
        ModelProfile("9","Zara Khan","","Runway","Dynamic runway presence with experience in both Eastern and Western fashion markets.",4.3f,73,6100,"5'11\"","132 lbs","Brown","Black","34\"","25\"","36\"","9",location = "Dubai, UAE"),
        ModelProfile("10","Chloe Park","","Commercial","Versatile model with strong commercial appeal across beauty and tech sectors.",4.5f,91,7800,"5'7\"","120 lbs","Dark Brown","Brown","32\"","23\"","33\"","7",location = "Seoul, South Korea")
    )

    fun getRecommendedModels(): List<ModelProfile> = getFeaturedModels().shuffled()

    fun getConversations(): List<ChatConversation> = listOf(
        ChatConversation("c1","Sophia Laurent","","Hey! I'd love to discuss the upcoming shoot details.  When would be a good time?",System.currentTimeMillis() - 300_000,2),
        ChatConversation("c2","Isabella Cruz","","Thank you for considering me for the project! I'm very interested.",System.currentTimeMillis() - 3_600_000,0),
        ChatConversation("c3","Amara Okafor","","The contract looks great. I'll review it and get back to you today.",System.currentTimeMillis() - 7_200_000,1),
        ChatConversation("c4","Valentina Rossi","","Looking forward to our collaboration! See you at the studio.",System.currentTimeMillis() - 86_400_000,0)
    )

    fun getMessages(conversationId: String): List<ChatMessage> {
        val now = System.currentTimeMillis()
        return listOf(
            ChatMessage("m1", conversationId, "other", "Hi! Thank you for reaching out.", now - 7_200_000, false),
            ChatMessage("m2", conversationId, "me", "Hello! I really loved your portfolio. Would you be available for a fashion shoot next month?", now - 7_100_000, true),
            ChatMessage("m3", conversationId, "other", "That sounds wonderful! I'd love to hear more about the project details.", now - 6_000_000, false),
            ChatMessage("m4", conversationId, "me", "It's a luxury brand campaign. 3-day shoot in Milan. We'll cover travel and accommodation.", now - 5_900_000, true),
            ChatMessage("m5", conversationId, "other", "Milan sounds amazing! What dates are you considering?", now - 3_600_000, false),
            ChatMessage("m6", conversationId, "me", "We're looking at March 15-17. Does that work for you?", now - 3_500_000, true),
            ChatMessage("m7", conversationId, "other", "Let me check my schedule and get back to you shortly!", now - 1_800_000, false)
        )
    }

    fun getReviews(): List<Review> = listOf(
        Review("r1","James Rivera","",5f,"Absolutely professional! Sophia delivered beyond our expectations for the entire campaign. Her professionalism and versatility are unmatched.",System.currentTimeMillis() - 2_592_000_000),
        Review("r2","Elena Vasquez","",4.5f,"Wonderful to work with. Great energy on set and very cooperative with the creative direction.",System.currentTimeMillis() - 5_184_000_000),
        Review("r3","David Chen","",5f,"One of the best models we've ever hired. Incredible portfolio range and very punctual.",System.currentTimeMillis() - 7_776_000_000)
    )

    fun getModelById(id: String): ModelProfile? {
        return (getFeaturedModels() + getTrendingModels()).find { it.id == id }
    }
}
