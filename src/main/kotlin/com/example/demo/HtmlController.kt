package com.example.demo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.lang.IllegalArgumentException

@Controller
class HtmlController(private val repository: CardRepository,
                     private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun app(model: Model): String {
        // instead of model.addAttribute("title", "Application")
        model["title"] = "Kotlin Stream"
        model["cards"] = repository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "app"
    }

    @GetMapping("/card/{id}")
    fun card(@PathVariable id: Long, model: Model): String {
        val card = repository
                .findById(id)
                .orElseThrow { IllegalArgumentException("Wrong card id provided") }
                .render()
        model["title"] = card.title
        model["card"] = card
        return "card"
    }

    fun Card.render() = RenderedCard(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            author,
            id,
            addedAt.format()
    )

    data class RenderedCard(
            val title: String,
            val headline: String,
            val content: String,
            val author: User,
            val id: Long?,
            val addedAt: String
    )
}